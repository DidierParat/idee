package com.github.didierparat.idee.provider.trip.model;

import lombok.Data;

@Data
public class ProviderTrip {

  private final String name;
  private final String description;
  private final int distance;
  private final String direction;
  private final String[] municipalities;
  private final String[] counties;
  private final String access;
  private final String grading;
  private final String[] suitableFor;
  private final String[] handicap;
  private final String[] seasons;
  private final String estimatedTime;
  private final String url;
}
