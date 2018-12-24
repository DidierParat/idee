package idee.models.nasjonalturbase.baseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.models.nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimatedTime {

  @JsonProperty(TurbaseConstants.DAYS)
  private final int days;
  @JsonProperty(TurbaseConstants.HOURS)
  private final int hours;
  @JsonProperty(TurbaseConstants.MINUTES)
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
      @JsonProperty(value = TurbaseConstants.DAYS, required = true) final int days,
      @JsonProperty(value = TurbaseConstants.HOURS, required = true) final int hours,
      @JsonProperty(value = TurbaseConstants.MINUTES, required = true) final int minutes
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
