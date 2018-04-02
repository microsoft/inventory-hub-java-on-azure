package org.inventory.hub;

import java.util.UUID;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;
import org.json.JSONObject;

/**
 * Function for updating inventory.
 * Phase one - use Java in Azure Functions
 * Phase two - replace it with Spring Cloud Function
 */
public class UpdateInventory {

    @FunctionName("Update-Inventory")
    public void update(
            @EventHubTrigger(name = "data", eventHubName = "inventoryeh", 
                connection = "InventoryEventHubConnectionString") String data,
            @DocumentDBOutput(name = "document", databaseName = "main", 
                collectionName = "events", 
                connection = "InventoryCosmosDBConnectionString") 
                    OutputBinding<String> document,
            final ExecutionContext context) {
        context.getLogger().info("Java Event Hub trigger processed a request: " + data);
        JSONObject eventGridMessage = new JSONObject(data);
        eventGridMessage.put("id", java.util.UUID.randomUUID().toString());
        context.getLogger().info("message: " + eventGridMessage.toString());
        document.setValue(eventGridMessage.toString());
    }
}
