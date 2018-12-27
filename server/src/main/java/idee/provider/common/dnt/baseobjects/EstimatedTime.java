package idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimatedTime {

  @JsonProperty(DntConstants.DAYS)
  private final int days;
  @JsonProperty(DntConstants.HOURS)
  private final int hours;
  @JsonProperty(DntConstants.MINUTES)
  private final int minutes;

  public int getDays() {
    return days;
  }

  public int getHours() {
    return hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public EstimatedTime(
      @JsonProperty(value = DntConstants.DAYS) final int days,
      @JsonProperty(value = DntConstants.HOURS) final int hours,
      @JsonProperty(value = DntConstants.MINUTES) final int minutes
  ) {
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
  }

  public String toString() {
    String toString = "";
    if (days != 0) {
      toString += days + "d";
    }
    if (hours != 0) {
      toString += hours + "h";
    }
    if (minutes != 0) {
      toString += minutes + "m";
    }
    return toString;
  }
}
