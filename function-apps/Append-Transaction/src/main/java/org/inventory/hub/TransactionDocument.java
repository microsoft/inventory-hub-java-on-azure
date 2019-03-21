/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package org.inventory.hub;

public class TransactionDocument {
    public String id;
    public String description;
    public String type;
    public String transactionTime;
    public TransactionEvent.ProductInformation productInformation;
    public TransactionEvent.PointOfTransactionLocation pointOfTransaction;

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

    TransactionDocument() {
    }
}

