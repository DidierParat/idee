package idee.model.nasjonalturbase.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.model.nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo {

  @JsonProperty(TurbaseConstants.TITLE)
  private final String title;
  @JsonProperty(TurbaseConstants.ADRESSE1)
  private final String adresse1;
  @JsonProperty(TurbaseConstants.ADRESSE2)
  private final String adresse2;
  @JsonProperty(TurbaseConstants.ZIP_CODE)
  private final String zipCode;
  @JsonProperty(TurbaseConstants.POST_MAIL)
  private final String postMail;
  @JsonProperty(TurbaseConstants.PHONE)
  private final int phone;
  @JsonProperty(TurbaseConstants.MOBILE)
  private final int mobile;
  @JsonProperty(TurbaseConstants.FAX)
  private final int fax;
  @JsonProperty(TurbaseConstants.EMAIL)
  private final String email;
  @JsonProperty(TurbaseConstants.URL)
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
      @JsonProperty(value = TurbaseConstants.TITLE, required = true) final String title,
      @JsonProperty(value = TurbaseConstants.ADRESSE1, required = true) final String adresse1,
      @JsonProperty(value = TurbaseConstants.ADRESSE2, required = true) final String adresse2,
      @JsonProperty(value = TurbaseConstants.ZIP_CODE, required = true) final String zipCode,
      @JsonProperty(value = TurbaseConstants.POST_MAIL, required = true) final String postMail,
      @JsonProperty(value = TurbaseConstants.PHONE, required = true) final int phone,
      @JsonProperty(value = TurbaseConstants.MOBILE, required = true) final int mobile,
      @JsonProperty(value = TurbaseConstants.FAX, required = true) final int fax,
      @JsonProperty(value = TurbaseConstants.EMAIL, required = true) final String email,
      @JsonProperty(value = TurbaseConstants.URL, required = true) final String url
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
