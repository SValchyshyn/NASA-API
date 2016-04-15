package com.example.valchyshyn.nasa_api.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


public class NasaPicture {
    private String url;
    @SerializedName("media_type")
    private String mediaType;
    private String explanation;
    private Object concepts;
    private String title;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Object getConcepts() {
        return concepts;
    }

    public void setConcepts(Object concepts) {
        this.concepts = concepts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
