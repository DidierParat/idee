package com.github.didierparat.idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericObject {

  @JsonProperty(DntConstants.ID)
  private final String id;
  @JsonProperty(DntConstants.NAME)
  private final String name;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonCreator
  public GenericObject(
      @JsonProperty(value = DntConstants.ID) final String id,
      @JsonProperty(value = DntConstants.NAME) final String name) {
    this.id = id;
    this.name = name;
  }
}
