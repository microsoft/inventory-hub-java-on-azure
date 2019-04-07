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
import com.microsoft.azure.functions.annotation.CosmosDBTrigger;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Function for notifying inventory update.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class NotifyInventoryUpdate {
    @FunctionName("Notify-Inventory-Update")
    public void notifyInventoryUpdate(
        @CosmosDBTrigger(name = "document", databaseName = "%NOTIFICATIONS_COSMOSDB_DBNAME%",
            collectionName = "%NOTIFICATIONS_COSMOSDB_COLLECTION_NAME%",
            connectionStringSetting = "NOTIFICATIONS_COSMOSDB_CONNECTION_STRING",
            leaseCollectionName = "%NOTIFY_INVENTORY_UPDATE_FUNCTION_APP_NAME%", createLeaseCollectionIfNotExists = true)
            String document,
        @EventHubOutput(name = "dataOutput", eventHubName = "%NOTIFICATIONS_EVENT_HUB_NAME%",
            connection = "NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING") OutputBinding<String> dataOutput,
        final ExecutionContext context) {

        context.getLogger().info("Java CosmosDB Notification trigger processed a request: " + document);

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // We don't want to send the document content as is since it contains some properties we might not want
        List<TransactionEvent> transactionEvents = gson.fromJson(document, new TypeToken<ArrayList<TransactionEvent>>(){}.getType());

        context.getLogger().info("Creating Event Hub notifications: " + gson.toJson(transactionEvents));
        dataOutput.setValue(gson.toJson(transactionEvents));
    }

    static final class TransactionEvent {
        public String id;
        public String description;
        public String type;
        public String transactionTime;
        public TransactionEvent.ProductInformation productInformation;
        public TransactionEvent.PointOfTransactionLocation pointOfTransaction;

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

}

/*
//        @EventHubTrigger(name = "dataInput", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
//            connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING",
//            consumerGroup = "TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME") String dataInput,
//
//        context.getLogger().info("Java Event Hub Notification trigger processed a request: " + dataInput);
//
//        JSONObject eventHubMessage = new JSONObject(dataInput);
//        eventHubMessage.put("id", java.util.UUID.randomUUID().toString());
//        context.getLogger().info("message: " + eventHubMessage.toString());
//        dataOutput.setValue(eventHubMessage.toString());
//
//        JSONArray jsonArray = new JSONArray(document);
//        JSONObject eventHubMessage = jsonArray.getJSONObject(0);

 */
