package idee.provider.common.dnt.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.provider.common.dnt.DntConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo {

  @JsonProperty(DntConstants.TITLE)
  private final String title;
  @JsonProperty(DntConstants.ADRESSE1)
  private final String adresse1;
  @JsonProperty(DntConstants.ADRESSE2)
  private final String adresse2;
  @JsonProperty(DntConstants.ZIP_CODE)
  private final String zipCode;
  @JsonProperty(DntConstants.POST_MAIL)
  private final String postMail;
  @JsonProperty(DntConstants.PHONE)
  private final int phone;
  @JsonProperty(DntConstants.MOBILE)
  private final int mobile;
  @JsonProperty(DntConstants.FAX)
  private final int fax;
  @JsonProperty(DntConstants.EMAIL)
  private final String email;
  @JsonProperty(DntConstants.URL)
  private final String url;

  public String getTitle() {
    return title;
  }

  public String getAdresse1() {
    return adresse1;
  }

  public String getAdresse2() {
    return adresse2;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getPostMail() {
    return postMail;
  }

  public int getPhone() {
    return phone;
  }

  public int getMobile() {
    return mobile;
  }

  public int getFax() {
    return fax;
  }

  public String getEmail() {
    return email;
  }

  public String getUrl() {
    return url;
  }

  @JsonCreator
  public ContactInfo(
      @JsonProperty(value = DntConstants.TITLE) final String title,
      @JsonProperty(value = DntConstants.ADRESSE1) final String adresse1,
      @JsonProperty(value = DntConstants.ADRESSE2) final String adresse2,
      @JsonProperty(value = DntConstants.ZIP_CODE) final String zipCode,
      @JsonProperty(value = DntConstants.POST_MAIL) final String postMail,
      @JsonProperty(value = DntConstants.PHONE) final int phone,
      @JsonProperty(value = DntConstants.MOBILE) final int mobile,
      @JsonProperty(value = DntConstants.FAX) final int fax,
      @JsonProperty(value = DntConstants.EMAIL) final String email,
      @JsonProperty(value = DntConstants.URL) final String url
  ) {
    this.title = title;
    this.adresse1 = adresse1;
    this.adresse2 = adresse2;
    this.zipCode = zipCode;
    this.postMail = postMail;
    this.phone = phone;
    this.mobile = mobile;
    this.fax = fax;
    this.email = email;
    this.url = url;
  }
}
