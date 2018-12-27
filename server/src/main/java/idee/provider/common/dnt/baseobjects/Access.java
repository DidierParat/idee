package idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Access {

  @JsonProperty(DntConstants.SUMMER)
  private final String summer;
  @JsonProperty(DntConstants.WINTER)
  private final String winter;

  public String getSummer() {
    return summer;
  }

  public String getWinter() {
    return winter;
  }

  public Access(
      @JsonProperty(value = DntConstants.SUMMER) final String summer,
      @JsonProperty(value = DntConstants.WINTER) final String winter
  ) {
    this.summer = summer;
    this.winter = winter;
  }
}
