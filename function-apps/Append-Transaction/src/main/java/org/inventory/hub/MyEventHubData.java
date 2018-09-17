/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class MyEventHubData {
    public String id;
    public String description;
    public String type;
    public String transactionTime;
    public MyEventHubData.ProductInformation productInformation;
    public MyEventHubData.PointOfTransactionLocation pointOfTransaction;

    public class ProductInformation {
        public String productId;
        public String productName;
        public String description;
        public String count;
    }

    // TODO: retrieve this record from the CosmosDB may be?
    public class PointOfTransactionLocation {
        public String id;
        public String description;
        public String location;
        public String longitude;
        public String latitude;
    }

    MyEventHubData() {
    }

    public String getValue() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

