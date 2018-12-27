package idee.model;

// Order is important to compare weather. From SUNNY (0) best to rainy (3) worst
// TODO support temperature, wind
public enum Weather {
  SUNNY,
  PARTIALLY_CLOUDY,
  CLOUDY,
  RAINY,
  SNOWY,
  UNKNOWN
}
