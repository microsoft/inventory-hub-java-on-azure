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
        String location = System.getenv("LOCATION");
        if (location == null || location.equals("")) {
            location = "westus";
        }
        final Gson gson = new GsonBuilder().create();
        final UpdatePayloadEvent payload = new UpdatePayloadEvent(10, location);
        byte[] payloadBytes = gson.toJson(payload).getBytes(Charset.defaultCharset());

        String Output = gson.toJson(payload);
        try {
            final ConnectionStringBuilder connStr = new ConnectionStringBuilder(System.getenv("InventoryEventHubConnectionString"));
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

        return Output;
    }

    /**
     * actual application-payload, ex: an inventory update
     */
    static final class UpdatePayloadEvent {
        UpdatePayloadEvent(final int seed, String location) {
            this.id = "EventHubTimeTrigger" + new Random().nextInt(seed);
            this.type = "inventory";
            this.currentTime = new Date().toString();
            this.productInformation = new ProductInformation();
            this.productInformation.id = "1";
            this.productInformation.name = "coffeePike";
            this.productInformation.description = "Pike Coffee";
            this.count = new Random().nextInt(seed);
            this.productInformation.location = location;
            this.pointOfUpdate = new PointOfUpdateWarehouse();
            this.pointOfUpdate.id = "1001";
            this.pointOfUpdate.description = "Coffee Shop 1001";
            this.pointOfUpdate.location = "Redmond";
        }

        public String id;
        public String type;
        public String currentTime;
        public long count;
        public ProductInformation productInformation;
        public PointOfUpdateWarehouse pointOfUpdate;

        // TODO: retrieve this record from the CosmosDB
        static final class ProductInformation {
            public String id;
            public String name;
            public String description;
            public String location;
        }

        // TODO: retrieve this record from the CosmosDB
        static final class PointOfUpdateWarehouse {
            public String id;
            public String description;
            public String location;
            public String longitude;
            public String latitude;
        }
    }
}
