package com.github.didierparat.idee.model;

import com.github.didierparat.idee.model.nested.Location;
import lombok.Value;

@Value
public class Trip {

  private final String name;
  private final String url;
  private final String estimatedTime;
  private final Location location;
}
