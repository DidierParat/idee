package com.github.didierparat.idee.provider.common.dnt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Owner;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Img;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Photographer;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.PolygonGeoJson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Picture extends BasicData {

  @JsonProperty(DntConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(DntConstants.IMG)
  private final List<Img> imgs;
  @JsonProperty(DntConstants.PHOTOGRAPHER)
  private final Photographer photographer;
  @JsonProperty(DntConstants.OWNER)
  private final Owner owner;

  public String[] getGroupsId() {
    return groupsId;
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
      @JsonProperty(value = DntConstants.ID)
      final String id,
      @JsonProperty(value = DntConstants.CONTENT_PROVIDER)
      final String contentProvider,
      @JsonProperty(value = DntConstants.LAST_MODIFIED)
      final String lastModified,
      @JsonProperty(value = DntConstants.CHECKSUM)
      final String checksum,
      @JsonProperty(value = DntConstants.LICENSE)
      final String license,
      @JsonProperty(value = DntConstants.NAMING)
      final String naming,
      @JsonProperty(value = DntConstants.PUBLISH_STATUS)
      final String publishStatus,
      @JsonProperty(value = DntConstants.NAME)
      final String name,
      @JsonProperty(value = DntConstants.DESCRIPTION)
      final String description,
      @JsonProperty(value = DntConstants.TAGS)
      final String[] tags,
      @JsonProperty(value = DntConstants.GEO_JSON)
      final PolygonGeoJson polygonGeoJson,
      @JsonProperty(value = DntConstants.GROUPS)
      final String[] groupsId,
      @JsonProperty(value = DntConstants.IMG)
      final List<Img> imgs,
      @JsonProperty(value = DntConstants.PHOTOGRAPHER)
      final Photographer photographer,
      @JsonProperty(value = DntConstants.OWNER)
      final Owner owner
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
    this.groupsId = groupsId;
    this.imgs = imgs;
    this.photographer = photographer;
    this.owner = owner;
  }
}
