/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        JSONObject eventHubMessage = new JSONObject(data);
        eventHubMessage.put("id", UUID.randomUUID().toString());
        context.getLogger().info("\tFound eventGridMessage: " + eventHubMessage.toString());

        String pointOfTransactionData = (String) eventHubMessage.get("pointOfTransaction").toString();
        context.getLogger().info("\tFound pointOfTransactionData: " + pointOfTransactionData);
        JSONObject pointOfTransaction = new JSONObject(pointOfTransactionData);
        context.getLogger().info("\tFound pointOfTransaction: " + pointOfTransaction);
        context.getLogger().info("\tFound pointOfTransaction location: " + pointOfTransaction.get("location"));
        eventHubMessage.put("location", pointOfTransaction.get("location"));
        eventHubMessage.remove("pointOfTransaction");

        String productInformationData = (String) eventHubMessage.get("productInformation").toString();
        context.getLogger().info("\tFound productInformationData: " + productInformationData);
        JSONObject productInformation = new JSONObject(productInformationData);
        context.getLogger().info("\tFound pointOfTransaction: " + productInformation);
        context.getLogger().info("\tFound pointOfTransaction productId: " + productInformation.get("productId"));
        context.getLogger().info("\tFound pointOfTransaction productName: " + productInformation.get("productName"));
        context.getLogger().info("\tFound pointOfTransaction description: " + productInformation.get("description"));
        context.getLogger().info("\tFound pointOfTransaction count: " + productInformation.get("count"));
        eventHubMessage.put("productId", productInformation.get("productId"));
        eventHubMessage.put("productName", productInformation.get("productName"));
        eventHubMessage.put("productDescription", productInformation.get("description"));
        eventHubMessage.put("productCount", productInformation.get("count"));
        eventHubMessage.remove("productInformation");

        context.getLogger().info("\tSaving: " + eventHubMessage.toString());
        document.setValue(eventHubMessage.toString());
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