package idee.models.nasjonalturbase.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.models.nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

  @JsonProperty(TurbaseConstants.TITLE)
  private final String title;
  @JsonProperty(TurbaseConstants.TYPE)
  private final String type;
  @JsonProperty(TurbaseConstants.URL)
  private final String url;

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  @JsonCreator
  public Link(
      @JsonProperty(value = TurbaseConstants.TITLE, required = true) final String title,
      @JsonProperty(value = TurbaseConstants.TYPE, required = true) final String type,
      @JsonProperty(value = TurbaseConstants.URL, required = true) final String url
  ) {
    this.title = title;
    this.type = type;
    this.url = url;
  }

  public String toString() {
    return "title: " + title + ", type: " + type + ", url: " + url;
  }
}
