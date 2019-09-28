/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class Transaction {

    @JsonProperty(value = "transactionTime")
    private String transactionTime;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "productInformation")
    private ProductInformation productInformation;

    @JsonProperty(value = "pointOfTransaction")
    private PointOfTransaction pointOfTransaction;

    public Transaction(){

    }

    public String getTransactionTime(){
      return transactionTime;
    }

    public void setTransactionTime(String transactionTime){
      this.transactionTime = transactionTime;
    }

    public String getId(){
      return id;
    }

    public void setId(String id){
      this.id = id;
    }

    public String getType(){
      return type;
    }

    public void setType(String type){
      this.type = type;
    }

    public String getDescription(){
      return description;
    }

    public void setDescription(String description){
      this.description = description;
    }

    public ProductInformation getProductInformation(){
      return productInformation;
    }

    public void setProductInformation(ProductInformation productInformation){
      this.productInformation = productInformation;
    }

    public PointOfTransaction getPointOfTransaction(){
      return this.pointOfTransaction;
    }

    public void setPointOfTransaction(PointOfTransaction pointOfTransaction){
      this.pointOfTransaction = pointOfTransaction;
    }

    @Override
    public String toString() {

        String typeString;
        if (type.equals("sell"))
          typeString = "SOLD";
        else if (type.equals("intake"))
          typeString = "ARRIVED";
        else typeString = type;

        return typeString + ": " + productInformation.getCount()
          + " of " 
          + productInformation.getProductName() + " @ "
          + pointOfTransaction.getDescription() + " in "
          + pointOfTransaction.getLocation() + " at "
          + transactionTime
          // + " ["
          // + pointOfUpdate.getLongitude() + ", " 
          // + pointOfUpdate.getLatitude() + "]" 
          ;

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


 * SAMPLE (1,2886416,5299): {
  "pointOfUpdate": {
    "latitude": "47� 36' 37.3608\" N",
    "description": "Warehouse 1001",
    "location": "Bellevue",
    "id": "1001",
    "longitude": "47� 36' 37.3608\" N"
  },
  "description": "EventHubTimeTrigger1",
  "productInformation": {
    "name": "coffeePike",
    "count": "5",
    "description": "Pike Coffee",
    "id": "1"
  },
  "id": "68d675d6-564a-4985-afd7-9fcaf8400cf3",
  "type": "intake",
  "transactionTime": "Tue Apr 10 05:44:40 GMT 2018"
}
 */


/**
 * {
  "transactionTime": "Mon Apr 09 06:26:00 GMT 2018",
  "pointOfUpdate": {
    "latitude": "47° 36' 37.3608\" N",
    "description": "Coffee Shop 2001",
    "location": "Bellevue",
    "id": "2001",
    "longitude": "47° 36' 37.3608\" N"
  },
  "description": "EventHubTimeTrigger0",
  "productInformation": {
    "name": "coffeePike",
    "count": "2",
    "description": "Pike Coffee",
    "id": "1"
  },
  "id": "0ac9af7c-301a-4421-83e1-8a71f10bc2c7",
  "type": "sell"

 */