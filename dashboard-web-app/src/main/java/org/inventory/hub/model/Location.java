/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.model;

import java.util.Objects;

public class Location {
    private String name;
    private int longitude;
    private int latitude;

    public Location() {
    }

    public Location(String name, int longitude, int latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof Location)) {
            return false;
        }
        final Location location = (Location) o;
        return Objects.equals(this.getName(), location.getName())
                && Objects.equals(this.longitude, 
                    location.longitude) 
                && Objects.equals(this.latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, longitude, latitude);
    }
}

