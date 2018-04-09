package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.EventHubOutput;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import com.microsoft.azure.serverless.functions.annotation.TimerTrigger;
import com.microsoft.azure.serverless.functions.OutputBinding;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

public class POS {
    @FunctionName("Point-of-Sale")
    public void sell(@TimerTrigger(name = "timerInfo", schedule = "*/60 * * * * *")
                                      String timerInfo,
                                  @EventHubOutput(name = "data", eventHubName = "eventhub-for-transactions",
                                      connection = "InventoryEventHubTransactionsConnectionString")
                                      OutputBinding<String> Output,
                                  final ExecutionContext executionContext) {
        executionContext.getLogger().info("Timer trigger input: " + timerInfo);
        final Gson gson = new GsonBuilder().create();
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
        final TransactionEvent transactionEvent = new TransactionEvent(10, locationName, locationLatitude, locationLongitude);

        executionContext.getLogger().info("Sending: " + gson.toJson(transactionEvent));

        Output.setValue(gson.toJson(transactionEvent));
    }

    /**
     * actual application-payload, ex: a telemetry event
     */
    /**
     * actual application-payload, ex: an inventory update
     */
    static final class TransactionEvent {
        TransactionEvent(final int seed, String locationName, String locationLatitude, String locationLongitude) {
            this.id = java.util.UUID.randomUUID().toString();
            this.description = "EventHubTimeTrigger" + new Random().nextInt(seed);
            this.type = "sell";
            this.currentTime = new Date().toString();
            this.productInformation = new ProductInformation();
            this.productInformation.id = "1";
            this.productInformation.name = "coffeePike";
            this.productInformation.description = "Pike Coffee";
            this.productInformation.count = Long.toString(new Random().nextInt(seed));
            this.pointOfUpdate = new PointOfUpdateLocation();
            this.pointOfUpdate.id = "2001";
            this.pointOfUpdate.description = "Coffee Shop 2001";
            this.pointOfUpdate.location = locationName;
            this.pointOfUpdate.latitude = locationLatitude;
            this.pointOfUpdate.longitude = locationLatitude;
        }

        public String id;
        public String description;
        public String type;
        public String currentTime;
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