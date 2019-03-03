package com.github.didierparat.idee.provider.common.dnt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BasicData implements Serializable {

  @JsonProperty(DntConstants.ID)
  private final String id;
  @JsonProperty(DntConstants.CONTENT_PROVIDER)
  private final String contentProvider;
  @JsonProperty(DntConstants.LAST_MODIFIED)
  private final String lastModified;
  @JsonProperty(DntConstants.CHECKSUM)
  private final String checksum;
  @JsonProperty(DntConstants.LICENSE)
  private final String license;
  @JsonProperty(DntConstants.NAMING)
  private final String naming;
  @JsonProperty(DntConstants.PUBLISH_STATUS)
  private final String publishStatus;
  @JsonProperty(DntConstants.NAME)
  private final String name;
  @JsonProperty(DntConstants.DESCRIPTION)
  private final String description;
  @JsonProperty(DntConstants.TAGS)
  private final String[] tags;
  // Private field, only visible for providers
  // private final String privat;

  @JsonCreator
  public BasicData(
      @JsonProperty(value = DntConstants.ID)
      final String id,
      @JsonProperty(value = DntConstants.CONTENT_PROVIDER)
      final String contentProvider,
      @JsonProperty(value = DntConstants.LAST_MODIFIED)
      final String lastModified,
      @JsonProperty(value = DntConstants.CHECKSUM)
      final String checksum,
      @JsonProperty(value = DntConstants.LICENSE)
      final String license,
      @JsonProperty(value = DntConstants.NAMING)
      final String naming,
      @JsonProperty(value = DntConstants.PUBLISH_STATUS)
      final String publishStatus,
      @JsonProperty(value = DntConstants.NAME)
      final String name,
      @JsonProperty(value = DntConstants.DESCRIPTION)
      final String description,
      @JsonProperty(value = DntConstants.TAGS)
      final String[] tags
  ) {
    this.id = id;
    this.contentProvider = contentProvider;
    this.lastModified = lastModified;
    this.checksum = checksum;
    this.license = license;
    this.naming = naming;
    this.publishStatus = publishStatus;
    this.name = name;
    this.description = description;
    this.tags = tags;
  }

  public String getId() {
    return id;
  }

  public String getContentProvider() {
    return contentProvider;
  }

  public String getLastModified() {
    return lastModified;
  }

  public String getChecksum() {
    return checksum;
  }

  public String getLicense() {
    return license;
  }

  public String getNaming() {
    return naming;
  }

  public String getPublishStatus() {
    return publishStatus;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String[] getTags() {
    return tags;
  }
}
