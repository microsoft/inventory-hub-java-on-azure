package org.inventory.hub;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.OutputBinding;
import com.microsoft.azure.serverless.functions.annotation.DocumentDBOutput;
import com.microsoft.azure.serverless.functions.annotation.EventHubTrigger;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import org.json.JSONObject;

import java.util.Map;

/**
 * Function for updating inventory.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class UpdateProductsInventory {
    @FunctionName("Update-Products-Inventory")
    public void update(
        @EventHubTrigger(name = "data", eventHubName = "eventhub-for-transactions",
            connection = "InventoryEventHubTransactionsConnectionString") String data,
        @DocumentDBOutput(name = "document", databaseName = "inventory",
            collectionName = "ProductsInventory2",
            connection = "InventoryCosmosDBConnectionString",
            createIfNotExists = true)
            OutputBinding<String> document,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub transaction trigger processed a request: " + data);
        JSONObject eventGridMessage = new JSONObject(data);
//        Map<String, String> productInformation = (Map<String, String>) eventGridMessage.get("productInformation");
//        context.getLogger().info(String.format("message: id=%s name=%s", productInformation.get("id"), productInformation.get("name")));
        //eventGridMessage.put("id", java.util.UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventGridMessage.get("productInformation").toString());
        context.getLogger().info("message: " + eventGridMessage.toString());
        document.setValue(eventGridMessage.toString());
    }
}
