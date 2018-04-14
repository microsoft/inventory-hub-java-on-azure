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

import java.util.Map;
import java.util.UUID;

/**
 * Function for capturing a transaction into the CosmosDB database.
 */
public class UpdateProductInventory {
    @FunctionName("Update-Product-Inventory")
    public void update(
        @EventHubTrigger(name = "data", eventHubName = "TRANSACTIONS_EVENT_HUB_NAME",
            connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING",
            consumerGroup = "TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME") String data,
        @DocumentDBOutput(name = "document", databaseName = "PRODUCT_INVENTORY_DOCUMENTDB_DBNAME",
            collectionName = "PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME",
            connection = "PRODUCT_INVENTORY_DOCUMENTDB_CONNECTION_STRING",
            createIfNotExists = true)
            OutputBinding<String> document,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub transaction trigger from "
            + System.getenv("UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_NAME")
            + "(" + System.getenv("UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_NAME")
            + ") processed a request: " + data);
        JSONObject eventGridMessage = new JSONObject(data);
        eventGridMessage.put("id", UUID.randomUUID().toString());

        JSONObject pointOfTransaction = new JSONObject(eventGridMessage.get("pointOfTransaction"));
        eventGridMessage.put("location", pointOfTransaction.get("location"));
        context.getLogger().info("\tFound location: " + eventGridMessage.get("location"));
        JSONObject productInformation = new JSONObject(eventGridMessage.get("productInformation"));
        eventGridMessage.put("productId", productInformation.get("productId"));
        context.getLogger().info("\tFound productId: " + eventGridMessage.get("productId"));

//        eventGridMessage.put("productId", productInformation.get("productId"));
//        eventGridMessage.put("productName", productInformation.get("productName"));
//        eventGridMessage.put("description", productInformation.get("description"));
//        eventGridMessage.put("count", productInformation.get("count"));

//        JSONObject documentOutput = new JSONObject();
//        documentOutput.put("id", UUID.randomUUID().toString());
//        JSONObject pointOfTransaction = eventGridMessage.getJSONObject("pointOfTransaction");
//        JSONObject productInformation = eventGridMessage.getJSONObject("productInformation");
//        documentOutput.put("location", pointOfTransaction.get("location"));
//        documentOutput.put("productId", productInformation.get("productId"));
//        documentOutput.put("productName", productInformation.get("productName"));
//        documentOutput.put("description", productInformation.get("description"));
//        documentOutput.put("count", productInformation.get("count"));
//        documentOutput.put("type", eventGridMessage.get("type"));
//        context.getLogger().info("Saving: " + documentOutput.toString());
//        document.setValue(documentOutput.toString());

        context.getLogger().info("\tSaving: " + eventGridMessage.toString());
        document.setValue(eventGridMessage.toString());
    }
}

/*
{
    "id": "12",
    "productId": "2",
    "productName": "coffeeVerona",
    "description": "Verona Coffee",
    "totalCount": "150",
    "location": "Seattle"
}
            this.transactionTime = new Date().toString();
            this.productInformation = new POT.TransactionEvent.ProductInformation();
            this.productInformation.productId = "1";
            this.productInformation.productName = "coffee";
            this.productInformation.description = "Coffee";
            this.productInformation.count = Long.toString(new Random().nextInt(seed));

            this.pointOfTransaction = new POT.TransactionEvent.PointOfTransactionLocation();
            this.pointOfTransaction.id = System.getProperty("POT_FUNCTION_APP_ID");
            this.pointOfTransaction.description = System.getenv("POT_FUNCTION_APP_DESCRIPTION");
            this.pointOfTransaction.location = System.getProperty("POT_FUNCTION_APP_LOCATION_NAME");
            this.pointOfTransaction.latitude = System.getProperty("POT_FUNCTION_APP_LOCATION_LATITUDE");
            this.pointOfTransaction.longitude = System.getProperty("POT_FUNCTION_APP_LOCATION_LONGITUDE");

 */