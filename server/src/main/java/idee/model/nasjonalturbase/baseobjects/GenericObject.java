package idee.model.nasjonalturbase.baseobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.model.nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericObject {

  @JsonProperty(TurbaseConstants.ID)
  private final String id;
  @JsonProperty(TurbaseConstants.NAME)
  private final String name;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonCreator
  public GenericObject(
      @JsonProperty(value = TurbaseConstants.ID, required = true) final String id,
      @JsonProperty(value = TurbaseConstants.NAME, required = true) final String name) {
    this.id = id;
    this.name = name;
  }
}
