/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.model;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.microsoft.azure.spring.data.documentdb.core.mapping.PartitionKey;

import java.util.Objects;


@Document(collection = "ProductsInventory")

public class ProductsInventory {
    
    private String productName;

    @PartitionKey
    private String location;
    
    private String totalCount;

    public ProductsInventory() {
    }

    public ProductsInventory(String productName, String location, String totalCount) {
        this.productName = productName;
        this.location = location;
        this.totalCount = totalCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

