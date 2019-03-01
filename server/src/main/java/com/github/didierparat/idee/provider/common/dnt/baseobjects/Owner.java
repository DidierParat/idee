package com.github.didierparat.idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {

  @JsonProperty(DntConstants.NAME)
  private final String name;
  @JsonProperty(DntConstants.PHONE)
  private final String phone;
  @JsonProperty(DntConstants.EMAIL)
  private final String email;
  @JsonProperty(DntConstants.URL)
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
      @JsonProperty(value = DntConstants.NAME) final String name,
      @JsonProperty(value = DntConstants.PHONE) final String phone,
      @JsonProperty(value = DntConstants.EMAIL) final String email,
      @JsonProperty(value = DntConstants.URL) final String url
  ) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.url = url;
  }
}
