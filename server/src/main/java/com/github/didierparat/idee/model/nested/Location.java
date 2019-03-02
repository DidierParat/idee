package com.github.didierparat.idee.model.nested;

import lombok.Value;

import java.io.Serializable;

@Value
public class Location implements Serializable {

  private final Float longitude;
  private final Float latitude;
}
