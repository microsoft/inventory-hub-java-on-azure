/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class POT {
    @FunctionName("Point-of-Transaction")
    public void sell(@TimerTrigger(name = "timerInfo", schedule = "*/30 * * * * *")
                         String timerInfo,
                     @EventHubOutput(name = "data", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
                         connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING")
                         OutputBinding<String> Output,
                     @CosmosDBInput(name = "documents", databaseName = "%PRODUCT_ITEMS_COSMOSDB_DBNAME%",
                         collectionName = "%PRODUCT_ITEMS_COSMOSDB_COLLECTION_NAME%",
                         connectionStringSetting = "PRODUCT_ITEMS_COSMOSDB_CONNECTION_STRING",
                         sqlQuery = "SELECT * FROM root r") String documents,
                     final ExecutionContext executionContext) {

        final Gson gson = new GsonBuilder().create();
        List<ProductItem> productItems = gson.fromJson(documents, new TypeToken<ArrayList<ProductItem>>(){}.getType());

        executionContext.getLogger().info(String.format("Found %d documents", productItems.size()));

        final POT.TransactionEvent transactionEvent = productItems.size() > 0 ? new POT.TransactionEvent(10, productItems) : new POT.TransactionEvent(10);
        executionContext.getLogger().info("Timer trigger input: " + timerInfo);
        executionContext.getLogger().info(transactionEvent.description);

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
        TransactionEvent(int seed, List<ProductItem> productItems) {
            int idx = new Random().nextInt(productItems.size());
            ProductItem productItem = productItems.get(idx);
            this.id = java.util.UUID.randomUUID().toString();
            this.description = "\tType " + System.getenv("POT_FUNCTION_APP_TYPE") + " from "
                + System.getenv("POT_FUNCTION_APP_DESCRIPTION")
                + "(" + System.getenv("POT_FUNCTION_APP_ID") + ")"
                + " to event hub "
                + System.getenv("TRANSACTIONS_EVENT_HUB_NAME");
            this.type = System.getenv("POT_FUNCTION_APP_TYPE");
            this.transactionTime = new Date().toString();
            this.productInformation = new POT.TransactionEvent.ProductInformation();
            this.productInformation.productId = productItem.productId;
            this.productInformation.productName = productItem.productName;
            this.productInformation.description = productItem.description;
            this.productInformation.count = Long.toString(new Random().nextInt(seed) + 1);

            this.pointOfTransaction = new POT.TransactionEvent.PointOfTransactionLocation();
            this.pointOfTransaction.id = System.getProperty("POT_FUNCTION_APP_ID");
            this.pointOfTransaction.description = System.getenv("POT_FUNCTION_APP_DESCRIPTION");
            this.pointOfTransaction.location = System.getenv("POT_FUNCTION_APP_LOCATION_NAME");
            this.pointOfTransaction.latitude = System.getenv("POT_FUNCTION_APP_LOCATION_LATITUDE");
            this.pointOfTransaction.longitude = System.getenv("POT_FUNCTION_APP_LOCATION_LONGITUDE");
        }

        TransactionEvent(final int seed) {
            this.id = java.util.UUID.randomUUID().toString();
            this.description = "\tType " + System.getenv("POT_FUNCTION_APP_TYPE") + " from "
                + System.getenv("POT_FUNCTION_APP_DESCRIPTION")
                + "(" + System.getenv("POT_FUNCTION_APP_ID") + ")"
                + " to event hub "
                + System.getenv("TRANSACTIONS_EVENT_HUB_NAME");
            this.type = System.getenv("POT_FUNCTION_APP_TYPE");
            this.transactionTime = new Date().toString();
            this.productInformation = new POT.TransactionEvent.ProductInformation();
            this.productInformation.productId = "1";
            this.productInformation.productName = "coffee";
            this.productInformation.description = "Coffee";
            this.productInformation.count = Long.toString(new Random().nextInt(seed) + 1);

            this.pointOfTransaction = new POT.TransactionEvent.PointOfTransactionLocation();
            this.pointOfTransaction.id = System.getProperty("POT_FUNCTION_APP_ID");
            this.pointOfTransaction.description = System.getenv("POT_FUNCTION_APP_DESCRIPTION");
            this.pointOfTransaction.location = System.getenv("POT_FUNCTION_APP_LOCATION_NAME");
            this.pointOfTransaction.latitude = System.getenv("POT_FUNCTION_APP_LOCATION_LATITUDE");
            this.pointOfTransaction.longitude = System.getenv("POT_FUNCTION_APP_LOCATION_LONGITUDE");
        }

        public String id;
        public String description;
        public String type;
        public String transactionTime;
        public POT.TransactionEvent.ProductInformation productInformation;
        public POT.TransactionEvent.PointOfTransactionLocation pointOfTransaction;

        static final class ProductInformation {
            public String productId;
            public String productName;
            public String description;
            public String count;
        }

        // TODO: retrieve this record from the CosmosDB may be?
        static final class PointOfTransactionLocation {
            public String id;
            public String description;
            public String location;
            public String longitude;
            public String latitude;
        }
    }

    static final class ProductItem {
        public String id;
        public String productId;
        public String productName;
        public String description;
    }

}
