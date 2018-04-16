/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package org.inventory.hub.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductInformation {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "id")
    private String id;

    public ProductInformation (){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}