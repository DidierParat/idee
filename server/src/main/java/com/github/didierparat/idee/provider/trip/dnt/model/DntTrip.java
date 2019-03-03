package com.github.didierparat.idee.provider.trip.dnt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.BasicData;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.LineStringGeoJson;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Link;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.TimeSpent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DntTrip extends BasicData {

  @JsonProperty(DntConstants.DISTANCE)
  private final int distance;
  @JsonProperty(DntConstants.DIRECTION)
  private final String direction;
  @JsonProperty(DntConstants.AREAS)
  private final String[] areasId;
  @JsonProperty(DntConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(DntConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(DntConstants.ACCESS)
  private final String access;
  @JsonProperty(DntConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(DntConstants.GRADING)
  private final String grading;
  @JsonProperty(DntConstants.SUITABLE_FOR)
  private final String[] suitableFor;
  @JsonProperty(DntConstants.HANDICAP)
  private final String[] handicap;
  @JsonProperty(DntConstants.SEASONS)
  private final String[] seasons;
  @JsonProperty(DntConstants.TIME_SPENT)
  private final TimeSpent timeSpent;
  @JsonProperty(DntConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(DntConstants.PICTURES)
  private final String[] picturesId;
  @JsonProperty(DntConstants.PLACES)
  private final String[] placesId;
  @JsonProperty(DntConstants.URL)
  private final String url;
  @JsonProperty(DntConstants.GEO_JSON)
  private final LineStringGeoJson lineStringGeoJson;

  public int getDistance() {
    return distance;
  }

  public String getDirection() {
    return direction;
  }

  public String[] getAreasId() {
    return areasId;
  }

  public String[] getMunicipalities() {
    return municipalities;
  }

  public String[] getCounties() {
    return counties;
  }

  public String getAccess() {
    return access;
  }

  public List<Link> getLinks() {
    return links;
  }

  public String getGrading() {
    return grading;
  }

  public String[] getSuitableFor() {
    return suitableFor;
  }

  public String[] getHandicap() {
    return handicap;
  }

  public String[] getSeasons() {
    return seasons;
  }

  public TimeSpent getTimeSpent() {
    return timeSpent;
  }

  public String[] getGroupsId() {
    return groupsId;
  }

  public String[] getPicturesId() {
    return picturesId;
  }

  public String[] getPlacesId() {
    return placesId;
  }

  public String getUrl() {
    return url;
  }

  public LineStringGeoJson getLineStringGeoJson() {
    return lineStringGeoJson;
  }

  @JsonCreator
  public DntTrip(
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
      final LineStringGeoJson lineStringGeoJson,
      @JsonProperty(value = DntConstants.DISTANCE)
      final int distance,
      @JsonProperty(value = DntConstants.DIRECTION)
      final String direction,
      @JsonProperty(value = DntConstants.AREAS)
      final String[] areasId,
      @JsonProperty(value = DntConstants.MUNICIPALITIES)
      final String[] municipalities,
      @JsonProperty(value = DntConstants.COUNTIES)
      final String[] counties,
      @JsonProperty(value = DntConstants.ACCESS)
      final String access,
      @JsonProperty(value = DntConstants.LINKS)
      final List<Link> links,
      @JsonProperty(value = DntConstants.GRADING)
      final String grading,
      @JsonProperty(value = DntConstants.SUITABLE_FOR)
      final String[] suitableFor,
      @JsonProperty(value = DntConstants.HANDICAP)
      final String[] handicap,
      @JsonProperty(value = DntConstants.SEASONS)
      final String[] seasons,
      @JsonProperty(value = DntConstants.TIME_SPENT)
      final TimeSpent timeSpent,
      @JsonProperty(value = DntConstants.GROUPS)
      final String[] groupsId,
      @JsonProperty(value = DntConstants.PICTURES)
      final String[] picturesId,
      @JsonProperty(value = DntConstants.PLACES)
      final String[] placesId,
      @JsonProperty(value = DntConstants.URL)
      final String url
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
    this.lineStringGeoJson = lineStringGeoJson;
    this.distance = distance;
    this.direction = direction;
    this.areasId = areasId;
    this.municipalities = municipalities;
    this.counties = counties;
    this.access = access;
    this.links = links;
    this.grading = grading;
    this.suitableFor = suitableFor;
    this.handicap = handicap;
    this.seasons = seasons;
    this.timeSpent = timeSpent;
    this.groupsId = groupsId;
    this.picturesId = picturesId;
    this.placesId = placesId;
    this.url = url;
  }
}
