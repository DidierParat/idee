package com.github.didierparat.idee.model.nested;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {

  private final Float longitude;
  private final Float latitude;
}
