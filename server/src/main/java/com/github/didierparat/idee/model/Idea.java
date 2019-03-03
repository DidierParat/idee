package com.github.didierparat.idee.model;

import lombok.Value;

@Value
public class Idea {

  private final Forecast forecast;
  private final Trip trip;
}
