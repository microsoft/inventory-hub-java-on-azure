/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.OutputBinding;
import com.microsoft.azure.serverless.functions.annotation.EventHubOutput;
import com.microsoft.azure.serverless.functions.annotation.EventHubTrigger;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import org.json.JSONObject;

/**
 * Function for notifying inventory update.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class NotifyInventoryUpdate {
    @FunctionName("Notify-Inventory-Update")
    public void notify(
        @EventHubTrigger(name = "dataInput", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
            connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING",
            consumerGroup = "TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME") String dataInput,
        @EventHubOutput(name = "dataOutput", eventHubName = "NOTIFICATIONS_EVENT_HUB_NAME",
            connection = "NOTIFICATIONS_EVENT_HUB_CONNECTION_STRING") OutputBinding<String> dataOutput,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub Notification trigger processed a request: " + dataInput);

        JSONObject eventHubMessage = new JSONObject(dataInput);
        eventHubMessage.put("id", java.util.UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventHubMessage.toString());
        // TODO: query CosmosDB and retrieve any other data we need for the Web App notification processor
        dataOutput.setValue(eventHubMessage.toString());
    }
}
