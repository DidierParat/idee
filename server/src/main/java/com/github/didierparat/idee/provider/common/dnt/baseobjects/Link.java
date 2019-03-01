package com.github.didierparat.idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

  @JsonProperty(DntConstants.TITLE)
  private final String title;
  @JsonProperty(DntConstants.TYPE)
  private final String type;
  @JsonProperty(DntConstants.URL)
  private final String url;

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  @JsonCreator
  public Link(
      @JsonProperty(value = DntConstants.TITLE) final String title,
      @JsonProperty(value = DntConstants.TYPE) final String type,
      @JsonProperty(value = DntConstants.URL) final String url
  ) {
    this.title = title;
    this.type = type;
    this.url = url;
  }

  public String toString() {
    return "title: " + title + ", type: " + type + ", url: " + url;
  }
}
