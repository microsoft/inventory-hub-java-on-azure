/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.annotation.EventHubOutput;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import com.microsoft.azure.serverless.functions.annotation.TimerTrigger;
import com.microsoft.azure.serverless.functions.OutputBinding;

import java.util.Date;
import java.util.Random;

public class POT {
    @FunctionName("Point-of-Transaction")
    public void sell(@TimerTrigger(name = "timerInfo", schedule = "*/30 * * * * *")
                         String timerInfo,
                     @EventHubOutput(name = "data", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
                         connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING")
                         OutputBinding<String> Output,
                     final ExecutionContext executionContext) {
        final Gson gson = new GsonBuilder().create();
        final POT.TransactionEvent transactionEvent = new POT.TransactionEvent(10);
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

        // TODO: retrieve this record from the CosmosDB
        static final class ProductInformation {
            public String productId;
            public String productName;
            public String description;
            public String count;
        }

        // TODO: retrieve this record from the CosmosDB/ENV settings
        static final class PointOfTransactionLocation {
            public String id;
            public String description;
            public String location;
            public String longitude;
            public String latitude;
        }
    }
}
