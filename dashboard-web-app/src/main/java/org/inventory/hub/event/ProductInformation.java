/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductInformation {

    @JsonProperty(value = "productName")
    private String productName;

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "productId")
    private String productId;

    public ProductInformation (){

    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductName(String name){
        this.productName = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getProductId(){
        return productId;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

}

/**
 2018-04-18T02:11:36.240 [Information] message:
 {
     "pointOfTransaction": {
         "latitude":"42.374572",
         "description":"Warehouse 1 Thomson Square",
         "location":"Boston",
         "longitude":"-71.062986"
     },
     "description":"\tType intake from Warehouse 1 Thomson Square(2FE5A135-3E22-4556-8930-071D9FE1E487) to event hub eventhub-for-transactions",
     "productInformation": {
         "productId":"1",
         "count":"10",
         "description":"Coffee",
         "productName":"coffee"
     },
     "id":"1c7ac3d5-18cc-4b11-98b6-1157224206c4",
     "type":"intake",
     "transactionTime":"Wed Apr 18 01:24:00 GMT 2018"
 }
 */
