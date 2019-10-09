/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class PointOfTransaction {

    @JsonProperty(value = "location")
    private String location;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "longitude")
    private String longitude;

    @JsonProperty(value = "latitude")
    private String latitude;

    public PointOfTransaction(){

    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setLatitude (String latitude){
        this.latitude = latitude;
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
