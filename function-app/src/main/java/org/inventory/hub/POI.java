package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.PartitionSender;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import com.microsoft.azure.serverless.functions.annotation.TimerTrigger;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class POI {
    @FunctionName("Point-of-Inventory")
    public String functionHandler(@TimerTrigger(name = "timerInfo", schedule = "*/20 * * * * *") String timerInfo,
                                  final ExecutionContext executionContext) {
        executionContext.getLogger().info("Timer trigger input: " + timerInfo);
        String locationName = System.getenv("LOCATION_NAME");
        if (locationName == null || locationName.equals("")) {
            locationName = "Redmond";
        }
        String locationLatitude = System.getenv("LOCATION_LATITUDE");
        if (locationLatitude == null || locationLatitude.equals("")) {
            locationLatitude = "Redmond";
        }
        String locationLongitude = System.getenv("LOCATION_LONGITUDE");
        if (locationLongitude == null || locationLongitude.equals("")) {
            locationLongitude = "Redmond";
        }
        final Gson gson = new GsonBuilder().create();
        final TransactionEvent transactionEvent = new TransactionEvent(10, locationName, locationLatitude, locationLongitude);
        byte[] payloadBytes = gson.toJson(transactionEvent).getBytes(Charset.defaultCharset());

        String Output = gson.toJson(transactionEvent);
        try {
            final ConnectionStringBuilder connStr = new ConnectionStringBuilder(System.getenv("InventoryEventHubTransactionsConnectionString"));
            final EventData sendEvent = EventData.create(payloadBytes);

            final ExecutorService executorService = Executors.newSingleThreadExecutor();
            final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);

            PartitionSender sender = null;
            ehClient.send(sendEvent).get();
            final String partitionKey = "partitionTheStream";
            ehClient.sendSync(sendEvent, partitionKey);

            // Type-3 - Send to a Specific Partition
            sender = ehClient.createPartitionSenderSync("0");
            sender.sendSync(sendEvent);

            sender.closeSync();
            ehClient.closeSync();

            executorService.shutdown();

        } catch (Exception e) {
            executionContext.getLogger().info(e.getMessage());
            for (StackTraceElement stackTrace : e.getStackTrace()) {
                executionContext.getLogger().info(String.format("%s:%s with %s::%s",
                    stackTrace.getFileName(),
                    stackTrace.getLineNumber(),
                    stackTrace.getClassName(),
                    stackTrace.getMethodName()));
            }
        }

        executionContext.getLogger().info("Sending: " + Output);

        return Output;
    }

    /**
     * actual application-payload, ex: an inventory update
     */
    static final class TransactionEvent {
        TransactionEvent(final int seed, String locationName, String locationLatitude, String locationLongitude) {
            this.id = java.util.UUID.randomUUID().toString();
            this.description = "EventHubTimeTrigger" + new Random().nextInt(seed);
            this.type = "intake";
            this.transactionTime = new Date().toString();
            this.productInformation = new ProductInformation();
            this.productInformation.id = "1";
            this.productInformation.name = "coffeePike";
            this.productInformation.description = "Pike Coffee";
            this.productInformation.count = Long.toString(new Random().nextInt(seed));
            this.pointOfUpdate = new PointOfUpdateLocation();
            this.pointOfUpdate.id = "1001";
            this.pointOfUpdate.description = "Warehouse 1001";
            this.pointOfUpdate.location = locationName;
            this.pointOfUpdate.latitude = locationLatitude;
            this.pointOfUpdate.longitude = locationLatitude;
        }

        public String id;
        public String description;
        public String type;
        public String transactionTime;
        public ProductInformation productInformation;
        public PointOfUpdateLocation pointOfUpdate;

        // TODO: retrieve this record from the CosmosDB
        static final class ProductInformation {
            public String id;
            public String name;
            public String description;
            public String count;
        }

        // TODO: retrieve this record from the CosmosDB/ENV settings
        static final class PointOfUpdateLocation {
            public String id;
            public String description;
            public String location;
            public String longitude;
            public String latitude;
        }
    }
}
