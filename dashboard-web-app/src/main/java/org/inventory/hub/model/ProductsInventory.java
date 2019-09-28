/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.model;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

import java.util.Objects;


@Document(collection = "product-inventory")
public class ProductsInventory {

    @Id
    private String id;

    private String productName;

    @PartitionKey
    private String location;
    
    private String totalCount;

    private String description;

    public ProductsInventory() {
    }

    public ProductsInventory(String productName, String description, String location, String totalCount) {
        this.productName = productName;
        this.description = description;
        this.location = location;
        this.totalCount = totalCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount){
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof ProductsInventory)) {
            return false;
        }
        final ProductsInventory ProductsInventory = (ProductsInventory) o;
        return Objects.equals(this.getProductName(), ProductsInventory.getProductName())
                && Objects.equals(this.getLocation(), 
                    ProductsInventory.getLocation())
                && Objects.equals(this.totalCount, ProductsInventory.getTotalCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, location, totalCount);
    }

    @Override
    public String toString() {
        return productName + " " + location + "=" + totalCount;
    }
}

