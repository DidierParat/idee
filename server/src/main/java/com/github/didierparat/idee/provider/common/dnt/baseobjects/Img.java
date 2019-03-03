package com.github.didierparat.idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Img {

  @JsonProperty(DntConstants.URL)
  private final String url;
  @JsonProperty(DntConstants.WIDTH)
  private final int width;
  @JsonProperty(DntConstants.HEIGHT)
  private final int height;
  @JsonProperty(DntConstants.SIZE)
  private final int size;

  public String getUrl() {
    return url;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getSize() {
    return size;
  }

  @JsonCreator
  public Img(
      @JsonProperty(value = DntConstants.URL) final String url,
      @JsonProperty(value = DntConstants.WIDTH) final int width,
      @JsonProperty(value = DntConstants.HEIGHT) final int height,
      @JsonProperty(value = DntConstants.SIZE) final int size
  ) {
    this.url = url;
    this.width = width;
    this.height = height;
    this.size = size;
  }
}
