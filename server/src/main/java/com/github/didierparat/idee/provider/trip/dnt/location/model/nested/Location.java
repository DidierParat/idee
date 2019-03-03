package com.github.didierparat.idee.provider.trip.dnt.location.model.nested;

import lombok.Value;

import java.io.Serializable;

@Value
public class Location implements Serializable {

  private final Double longitude;
  private final Double latitude;
}
