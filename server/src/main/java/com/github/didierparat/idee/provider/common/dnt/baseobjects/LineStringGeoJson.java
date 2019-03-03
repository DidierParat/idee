package com.github.didierparat.idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineStringGeoJson implements Serializable {

  @JsonProperty(DntConstants.COORDINATES)
  private final Double[][] coordinates;
  @JsonProperty(DntConstants.TYPE)
  private final String type;

  public Double[][] getCoordinates() {
    return coordinates;
  }

  public LineStringGeoJson(
      @JsonProperty(value = DntConstants.COORDINATES)
      final Double[][] coordinates,
      @JsonProperty(value = DntConstants.TYPE)
      final String type
  ) {
    this.coordinates = coordinates;
    this.type = type;
  }
}
