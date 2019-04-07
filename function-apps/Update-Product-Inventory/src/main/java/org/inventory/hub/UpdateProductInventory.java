/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.PartitionKey;
import com.microsoft.azure.documentdb.RequestOptions;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.Cardinality;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function for capturing a transaction into the CosmosDB database.
 */
public class UpdateProductInventory {
    @FunctionName("Update-Product-Inventory")
    public void update(
        @EventHubTrigger(name = "data", eventHubName = "%TRANSACTIONS_EVENT_HUB_NAME%",
            connection = "TRANSACTIONS_EVENT_HUB_CONNECTION_STRING",
            cardinality = Cardinality.ONE,
            consumerGroup = "%TRANSACTIONS_EVENT_HUB_CONSUMER_GROUP_NAME%") String data,
        @CosmosDBOutput(name = "document", databaseName = "%PRODUCT_INVENTORY_COSMOSDB_DBNAME%",
            collectionName = "%PRODUCT_INVENTORY_COSMOSDB_COLLECTION_NAME%",
            connectionStringSetting = "PRODUCT_INVENTORY_COSMOSDB_CONNECTION_STRING",
            createIfNotExists = true)
            OutputBinding<String> document,
        @CosmosDBInput(name = "documents", databaseName = "%PRODUCT_INVENTORY_COSMOSDB_DBNAME%",
        collectionName = "%PRODUCT_INVENTORY_COSMOSDB_COLLECTION_NAME%",
        connectionStringSetting = "PRODUCT_INVENTORY_COSMOSDB_CONNECTION_STRING",
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
        // context.getLogger().info("\tFound CosmosDB: " + inputDoc);
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
            // context.getLogger().info("\tBuilt Map of product inventory: " + gson.toJson(currentProductInventoryByLocation));

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

        String cosmosDBUri = System.getenv("PRODUCT_INVENTORY_COSMOSDB_URI");
        String cosmosDBKey = System.getenv("PRODUCT_INVENTORY_COSMOSDB_KEY");

        // Extract CosmosDB URI and key
        String cosmosDbConnectionString = System.getenv("PRODUCT_INVENTORY_COSMOSDB_CONNECTION_STRING");
        String pattern = "AccountEndpoint=(.*);AccountKey=(.*);";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cosmosDbConnectionString);
        if (m.find()) {
            cosmosDBUri = m.group(1);
            cosmosDBKey = m.group(2);
        }

        DocumentClient client = new DocumentClient(cosmosDBUri, cosmosDBKey, null, null);

        final String storedProcedureLink = String.format("/dbs/%s/colls/%s/sprocs/update-product-inventory",
            System.getenv("PRODUCT_INVENTORY_COSMOSDB_DBNAME"),
            System.getenv("PRODUCT_INVENTORY_COSMOSDB_COLLECTION_NAME"));
        Object[] procedureParams = {
            eventHubMessage.get("type"),
            productInformation.get("count").toString(),
            productInformation.get("productId").toString(),
            productInformation.get("productName"),
            productInformation.get("description"),
            pointOfTransaction.get("location")
        };
        try {
            context.getLogger().info("\t Stored Procedure call: " + gson.toJson(procedureParams));
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.setPartitionKey(new PartitionKey(pointOfTransaction.get("location")));
            client.executeStoredProcedure(storedProcedureLink, requestOptions, procedureParams);
        } catch (Exception e) {
            context.getLogger().info("ERROR Stored Procedure call failed: " + gson.toJson(e));
        }
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

/*

Corresponding stored procedure: "update-product-inventory"

function updateProductInventory(typeOfTransaction, count, productId, productName, description, location) {
    var collection = getContext().getCollection();

    // var id = uuidv4();

    // Query documents and take 1st item.
    var query = { query: "select * from root r where r.productName=@productName and r.location=@location", parameters:
        [
            {name: "@productName", value: productName},
            {name: "@location", value: location}
        ]};
    var isAccepted = collection.queryDocuments(
        collection.getSelfLink(),
        query,
    function (err, feed, options) {
        if (err) throw err;

        if (!feed || !feed.length) {
            var response = getContext().getResponse();
            console.log('No documents found; this is a new document.');
            var id = uuidv4();
            var doc = {
                id: id,
                productId: productId,
                productName: productName,
                description: description,
                location: location,
                totalCount: count};
            console.log(JSON.stringify(doc));
            response.setBody(JSON.stringify(doc));

            var isNewDocument = collection.createDocument(collection.getSelfLink(),
                doc,
                function (err, doc) {
                    if (err) throw new Error('Error' + err.message);
                    response.setBody(JSON.stringify(doc))
                });
            if (!isNewDocument) console.log("Failed to create new document " + JSON.stringify(doc));
        } else {
            var response = getContext().getResponse();
            var doc = feed[0];

            var totalCountValue = parseInt(doc.totalCount);
            var countValue = parseInt(count);
            if (typeOfTransaction == "intake") {
                totalCountValue += countValue;
            } else if (typeOfTransaction == "sell") {
                totalCountValue -= countValue;
            }
            doc.totalCount = "" + totalCountValue;
            response.setBody(doc);
            var isReplacedDocument = collection.replaceDocument(feed[0]._self, doc,
                function (err2, docReplaced) {
                    if (err) throw "Unable to update document, abort";
                });
            if (!isReplacedDocument) console.log("Failed to replace document " + JSON.stringify(doc));
        }
    });

    function uuidv4() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }
}
*/
