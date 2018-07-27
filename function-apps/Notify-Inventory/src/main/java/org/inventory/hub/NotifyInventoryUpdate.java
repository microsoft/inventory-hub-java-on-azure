/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.CosmosDBTrigger;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Function for notifying inventory update.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class NotifyInventoryUpdate {
    @FunctionName("Notify-Inventory-Update")
    public void notify(
        @CosmosDBTrigger(name = "document", databaseName = "%NOTIFICATIONS_DOCUMENTDB_DBNAME%",
            collectionName = "%NOTIFICATIONS_DOCUMENTDB_COLLECTION_NAME%",
            connectionStringSetting = "NOTIFICATIONS_DOCUMENTDB_CONNECTION_STRING",
            leaseCollectionName = "", createLeaseCollectionIfNotExists = true)
            String document,
        @EventHubOutput(name = "dataOutput", eventHubName = "NOTIFICATIONS_EVENT_HUB_NAME",
            connection = "NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING") OutputBinding<String> dataOutput,
        final ExecutionContext context) {

        context.getLogger().info("Java CosmosDB Notification trigger processed a request: " + document);

        dataOutput.setValue(document);
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
