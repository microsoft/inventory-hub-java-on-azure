package org.inventory.hub;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.OutputBinding;
import com.microsoft.azure.serverless.functions.annotation.DocumentDBInput;
import com.microsoft.azure.serverless.functions.annotation.EventHubOutput;
import com.microsoft.azure.serverless.functions.annotation.EventHubTrigger;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Function for notifying inventory update.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class NotifyInventoryUpdate {
    @FunctionName("Notify-Inventory-Update")
    public void notify(
        @EventHubTrigger(name = "dataInput", eventHubName = "eventhub-for-transactions",
            connection = "InventoryEventHubTransactionsConnectionString") String dataInput,
        @EventHubOutput(name = "dataOutput", eventHubName = "eventhub-for-notifications",
            connection = "InventoryEventHubNotificationsConnectionString") OutputBinding<String> Output,
//        @DocumentDBInput(name = "document", databaseName = "inventory",
//            collectionName = "products",
//            connection = "InventoryCosmosDBConnectionString") String data,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub Notification trigger processed a request: " + dataInput);
        final Gson gson = new GsonBuilder().create();
        JSONObject eventGridMessage = new JSONObject(dataInput);
        eventGridMessage.put("id", java.util.UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventGridMessage.toString());
        // TODO: query CosmosDB and retrieve any other data we need for the Web App notification processor
        Output.setValue(gson.toJson(eventGridMessage));
    }
}
