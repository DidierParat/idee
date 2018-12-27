package idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSpent {

  @JsonProperty(DntConstants.NORMAL)
  private final EstimatedTime normal;
  @JsonProperty(DntConstants.MIN)
  private final EstimatedTime min;
  @JsonProperty(DntConstants.MAX)
  private final EstimatedTime max;

  public TimeSpent(
      @JsonProperty(value = DntConstants.NORMAL) final EstimatedTime normal,
      @JsonProperty(value = DntConstants.MIN) final EstimatedTime min,
      @JsonProperty(value = DntConstants.MAX) final EstimatedTime max
  ) {
    this.normal = normal;
    this.max = max;
    this.min = min;
  }

  public EstimatedTime getNormal() {
    return normal;
  }

  public EstimatedTime getMin() {
    return min;
  }

  public EstimatedTime getMax() {
    return max;
  }
}
