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
import com.microsoft.azure.serverless.functions.annotation.DocumentDBInput;
import com.microsoft.azure.serverless.functions.annotation.DocumentDBOutput;
import com.microsoft.azure.serverless.functions.annotation.EventHubTrigger;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        @DocumentDBInput(name = "documents", databaseName = "PRODUCT_INVENTORY_DOCUMENTDB_DBNAME",
            collectionName = "PRODUCT_INVENTORY_DOCUMENTDB_COLLECTION_NAME",
            connection = "PRODUCT_INVENTORY_DOCUMENTDB_CONNECTION_STRING",
            sqlQuery = "SELECT * FROM root r")
            String inputDoc,
        final ExecutionContext context) {
        context.getLogger().info("Java Event Hub transaction trigger from "
            + System.getenv("UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_NAME")
            + "(" + System.getenv("UPDATE_PRODUCT_INVENTORY_FUNCTION_APP_NAME")
            + ") processed a request: " + data);
        final Gson gson = new GsonBuilder().create();
        JSONObject eventHubMessage = new JSONObject(data);
        eventHubMessage.put("id", UUID.randomUUID().toString());
        context.getLogger().info("\tFound eventGridMessage: " + eventHubMessage.toString());
        context.getLogger().info("\tFound CosmosDB: " + inputDoc);
        Map<String, Map<String, ProductInventory>> currentProductInventoryByLocation = new HashMap<>();
        if (inputDoc != null) {
            JSONArray currentProductInventory = new JSONArray(inputDoc);
            for (Object item : currentProductInventory) {
                context.getLogger().info("\tFound currentProductInventory item: " + item.toString());

                ProductInventory productInventory = gson.fromJson(item.toString(), ProductInventory.class);
                if (productInventory.location != null && productInventory.productId != null) {
                    if (currentProductInventoryByLocation.get(productInventory.location) == null) {
                        Map<String, ProductInventory> productById = new HashMap<>();
                        productById.put(productInventory.productId, productInventory);
                        currentProductInventoryByLocation.put(productInventory.location, productById);
                    } else {
                        Map<String, ProductInventory> productById = currentProductInventoryByLocation.get(productInventory.location);
                        productById.putIfAbsent(productInventory.productId, productInventory);
                    }
                }
            }
            context.getLogger().info("\tBuilt Map of product inventory: " + gson.toJson(currentProductInventoryByLocation));

            eventHubMessage.put("id", "1");
        }

        String pointOfTransactionData = (String) eventHubMessage.get("pointOfTransaction").toString();
        context.getLogger().info("\tFound pointOfTransactionData: " + pointOfTransactionData);
        JSONObject pointOfTransaction = new JSONObject(pointOfTransactionData);
        context.getLogger().info("\tFound pointOfTransaction: " + pointOfTransaction);
        context.getLogger().info("\tFound pointOfTransaction location: " + pointOfTransaction.get("location"));

        String productInformationData = (String) eventHubMessage.get("productInformation").toString();
        context.getLogger().info("\tFound productInformationData: " + productInformationData);
        JSONObject productInformation = new JSONObject(productInformationData);
        context.getLogger().info("\tFound pointOfTransaction: " + productInformation);
        context.getLogger().info("\tFound pointOfTransaction productId: " + productInformation.get("productId"));
        context.getLogger().info("\tFound pointOfTransaction productName: " + productInformation.get("productName"));
        context.getLogger().info("\tFound pointOfTransaction description: " + productInformation.get("description"));
        context.getLogger().info("\tFound pointOfTransaction count: " + productInformation.get("count"));

        ProductInventory productInventoryOutput = new ProductInventory();
        productInventoryOutput.id = UUID.randomUUID().toString();
        productInventoryOutput.location = pointOfTransaction.get("location").toString();
        productInventoryOutput.productId = productInformation.get("productId").toString();
        productInventoryOutput.productName = productInformation.get("productName").toString();
        productInventoryOutput.description = productInformation.get("description").toString();

        long currentCount = Long.valueOf(productInformation.get("count").toString());
        Map<String, ProductInventory> productFromMap = currentProductInventoryByLocation.get(productInventoryOutput.location);
        if (productFromMap != null && productFromMap.get(productInventoryOutput.productId) != null) {
            productInventoryOutput.id = productFromMap.get(productInventoryOutput.productId).id;
            String totalCount = productFromMap.get(productInventoryOutput.productId).totalCount;
            long currentProductTotal = Long.valueOf(totalCount != null ? totalCount : "0");
            if (eventHubMessage.get("type").toString().equals("intake")) {
                currentCount += currentProductTotal; // add to the inventory
            } else if (eventHubMessage.get("type").toString().equals("sell")) {
                currentCount = currentProductTotal - currentCount; // subtract from the inventory
            } else {
                currentCount = 0;
            }
        } else {
            if (eventHubMessage.get("type").toString().equals("intake")) {
                currentCount = currentCount; // intake
            } else if (eventHubMessage.get("type").toString().equals("sell")) {
                currentCount = -currentCount; // sell
            } else {
                currentCount = 0;
            }
        }
        productInventoryOutput.totalCount = Long.toString(currentCount);

        context.getLogger().info("\tSaving: " + gson.toJson(productInventoryOutput));
        document.setValue(gson.toJson(productInventoryOutput));
    }

    static final class ProductInventory {
        public String id;
        public String productId;
        public String productName;
        public String description;
        public String location;
        public String totalCount;
    }
}
