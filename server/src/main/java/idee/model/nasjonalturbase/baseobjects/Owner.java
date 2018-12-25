package idee.model.nasjonalturbase.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.model.nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {

  @JsonProperty(TurbaseConstants.NAME)
  private final String name;
  @JsonProperty(TurbaseConstants.PHONE)
  private final String phone;
  @JsonProperty(TurbaseConstants.EMAIL)
  private final String email;
  @JsonProperty(TurbaseConstants.URL)
  private final String url;

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
  public Owner(
      @JsonProperty(value = TurbaseConstants.NAME, required = true) final String name,
      @JsonProperty(value = TurbaseConstants.PHONE, required = true) final String phone,
      @JsonProperty(value = TurbaseConstants.EMAIL, required = true) final String email,
      @JsonProperty(value = TurbaseConstants.URL, required = true) final String url
  ) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.url = url;
  }
}
