/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.sample.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
    private String name;
    private Map<String, Integer> countByLocation = new HashMap<String, Integer>();

    public Product() {
    }

    public Product(String name, Map<String, Integer> countByLocation) {
        this.name = name;
        this.countByLocation = countByLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getCountByLocation() {
        return countByLocation;
    }

    public void setCountByLocation(Map<String, Integer> countByLocation) {
        this.countByLocation = countByLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof Product)) {
            return false;
        }
        final Product product = (Product) o;
        return Objects.equals(this.getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countByLocation);
    }

    @Override
    public String toString() {
        return name + countByLocation.toString();
    }
}

