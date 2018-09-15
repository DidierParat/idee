package idee.models.Nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.models.Nasjonalturbase.BaseObjects.PolygonGeoJSON;
import idee.models.Nasjonalturbase.BaseObjects.Img;
import idee.models.Nasjonalturbase.BaseObjects.Owner;
import idee.models.Nasjonalturbase.BaseObjects.Photographer;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Picture extends BasicData {

  @JsonProperty(TurbaseConstants.GROUPS)
  private final String[] groupsID;
  @JsonProperty(TurbaseConstants.IMG)
  private final List<Img> imgs;
  @JsonProperty(TurbaseConstants.PHOTOGRAPHER)
  private final Photographer photographer;
  @JsonProperty(TurbaseConstants.OWNER)
  private final Owner owner;

  public String[] getGroupsID() {
    return groupsID;
  }

  public List<Img> getImgs() {
    return imgs;
  }

  public Photographer getPhotographer() {
    return photographer;
  }

  public Owner getOwner() {
    return owner;
  }

  @JsonCreator
  public Picture(
      @JsonProperty(value = TurbaseConstants.ID, required = true) final String id,
      @JsonProperty(value = TurbaseConstants.CONTENT_PROVIDER, required = true) final String contentProvider,
      @JsonProperty(value = TurbaseConstants.LAST_MODIFIED, required = true) final String lastModified,
      @JsonProperty(value = TurbaseConstants.CHECKSUM, required = true) final String checksum,
      @JsonProperty(value = TurbaseConstants.LICENSE, required = true) final String license,
      @JsonProperty(value = TurbaseConstants.NAMING, required = true) final String naming,
      @JsonProperty(value = TurbaseConstants.PUBLISH_STATUS, required = true) final String publishStatus,
      @JsonProperty(value = TurbaseConstants.NAME, required = true) final String name,
      @JsonProperty(value = TurbaseConstants.DESCRIPTION, required = true) final String description,
      @JsonProperty(value = TurbaseConstants.TAGS, required = false) final String[] tags,
      @JsonProperty(value = TurbaseConstants.GEO_JSON, required = false) final PolygonGeoJSON polygonGeoJson,
      @JsonProperty(value = TurbaseConstants.GROUPS, required = false) final String[] groupsID,
      @JsonProperty(value = TurbaseConstants.IMG, required = true) final List<Img> imgs,
      @JsonProperty(value = TurbaseConstants.PHOTOGRAPHER, required = true) final Photographer photographer,
      @JsonProperty(value = TurbaseConstants.OWNER, required = true) final Owner owner
  ) {
    super(id,
        contentProvider,
        lastModified,
        checksum,
        license,
        naming,
        publishStatus,
        name,
        description,
        tags);
    this.groupsID = groupsID;
    this.imgs = imgs;
    this.photographer = photographer;
    this.owner = owner;
  }
}
