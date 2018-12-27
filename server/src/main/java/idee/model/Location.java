package idee.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {

  private final Double longitude;
  private final Double latitude;

  public String toString() {
    return "Longitude: " + longitude + ". Latitude: " + latitude;
  }
}
