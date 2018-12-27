package idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photographer {

  @JsonProperty(DntConstants.NAME)
  private final String name;
  @JsonProperty(DntConstants.PHONE)
  private final String phone;
  @JsonProperty(DntConstants.EMAIL)
  private final String email;

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  @JsonCreator
  public Photographer(
      @JsonProperty(value = DntConstants.NAME) final String name,
      @JsonProperty(value = DntConstants.PHONE) final String phone,
      @JsonProperty(value = DntConstants.EMAIL) final String email
  ) {
    this.name = name;
    this.phone = phone;
    this.email = email;
  }
}
