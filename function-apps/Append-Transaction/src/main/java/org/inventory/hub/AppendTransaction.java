/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.OutputBinding;
import com.microsoft.azure.serverless.functions.annotation.DocumentDBOutput;
import com.microsoft.azure.serverless.functions.annotation.EventHubTrigger;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Function for capturing a transaction into the CosmosDB database.
 */
public class AppendTransaction {
    @FunctionName("Append-Transaction")
    public void update(
        @EventHubTrigger(name = "data", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
            connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING",
            consumerGroup = "TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME") String data,
        @DocumentDBOutput(name = "document", databaseName = "TRANSACTIONS_DOCUMENTDB_DBNAME",
            collectionName = "TRANSACTIONS_DOCUMENTDB_COLLECTION_NAME",
            connection = "TRANSACTIONS_DOCUMENTDB_CONNECTION_STRING",
            createIfNotExists = true)
            OutputBinding<String> document,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub transaction trigger from "
            + System.getenv("APPEND_TRANSACTION_FUNCTION_APP_NAME")
            + "(" + System.getenv("APPEND_TRANSACTION_FUNCTION_APP_ID")
            + ") processed a request: " + data);
        JSONObject eventGridMessage = new JSONObject(data);
        eventGridMessage.put("id", UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventGridMessage.toString());
        document.setValue(eventGridMessage.toString());
    }
}
