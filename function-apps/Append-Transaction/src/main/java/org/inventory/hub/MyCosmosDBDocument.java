/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import java.util.UUID;

import com.microsoft.azure.functions.ExecutionContext;
import org.json.JSONObject;

public class MyCosmosDBDocument {
    private String value;
    private ExecutionContext context;

    MyCosmosDBDocument() {
    }

    public MyCosmosDBDocument(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        JSONObject eventHubMessage = new JSONObject(value);
        eventHubMessage.put("id", UUID.randomUUID().toString());
        this.value = eventHubMessage.toString();
        if (context != null) context.getLogger().info("My CosmosDB document: " + this.value);
    }
}

