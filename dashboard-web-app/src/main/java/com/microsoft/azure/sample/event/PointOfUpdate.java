package com.microsoft.azure.sample.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class PointOfUpdate{

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "location")
    private String location;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "longitude")
    private String longitude;

    @JsonProperty(value = "latitude")
    private String latitude;

    public PointOfUpdate(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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