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

    @JsonProperty(value = "pointOfUpdate")
    private PointOfUpdate pointOfUpdate;

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

    public PointOfUpdate getPointOfUpdate(){
      return pointOfUpdate;
    }

    public void setPointOfUpdate(PointOfUpdate pointOfUpdate){
      this.pointOfUpdate = pointOfUpdate;
    }

    @Override
    public String toString() {

        String typeString;
        if (type.equals("sell"))
          typeString = "SOLD";
        else if (type.equals("intake"))
          typeString = "ARRIVED";
        else typeString = type;

        return "======== ^^ " + typeString + ": " + productInformation.getCount() 
          + " of " 
          + productInformation.getName() + " @ " 
          + pointOfUpdate.getDescription() + " in " 
          + pointOfUpdate.getLocation() 
          // + " ["
          // + pointOfUpdate.getLongitude() + ", " 
          // + pointOfUpdate.getLatitude() + "]" 
          ;

    }
}

/**
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