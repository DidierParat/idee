package com.github.didierparat.idee.provider.common.dnt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Access;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Link;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.PolygonGeoJson;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place extends BasicData {

  @JsonProperty(DntConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(DntConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(DntConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(DntConstants.PARENTS_AREAS)
  private final String[] parentsAreasId;
  @JsonProperty(DntConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(DntConstants.PICTURES)
  private final String[] picturesId;
  @JsonProperty(DntConstants.URL)
  private final String url;
  @JsonProperty(DntConstants.MAP)
  private final String map;
  @JsonProperty(DntConstants.HIKE_MAP)
  private final String hikeMap;
  @JsonProperty(DntConstants.AREAS)
  private final String[] areasId;
  @JsonProperty(DntConstants.ALTERNATIVE_NAME)
  private final String[] alternativeNames;
  @JsonProperty(DntConstants.SSR_ID)
  private final int ssrId;
  @JsonProperty(DntConstants.ACCESS)
  private final Access access;
  @JsonProperty(DntConstants.FACILITIES)
  private final Map<String, String> facility;
  @JsonProperty(DntConstants.CONSTRUCTION_YEAR)
  private final int constructionYear;
  // Not available yet
  //@JsonProperty(DntConstants.VISITOR_STATISTICS)
  //private final VisitorStat visitorStat;
  @JsonProperty(DntConstants.SERVICE_LEVEL)
  private final String serviceLevel;

  public String[] getMunicipalities() {
    return municipalities;
  }

  public String[] getCounties() {
    return counties;
  }

  public List<Link> getLinks() {
    return links;
  }

  public String[] getParentsAreasId() {
    return parentsAreasId;
  }

  public String[] getGroupsId() {
    return groupsId;
  }

  public String[] getPicturesId() {
    return picturesId;
  }

  public String getUrl() {
    return url;
  }

  public String getMap() {
    return map;
  }

  public String getHikeMap() {
    return hikeMap;
  }

  public String[] getAreasId() {
    return areasId;
  }

  public String[] getAlternativeNames() {
    return alternativeNames;
  }

  public int getSsrId() {
    return ssrId;
  }

  public Access getAccess() {
    return access;
  }

  public final Map<String, String> getFacility() {
    return facility;
  }

  public int getConstructionYear() {
    return constructionYear;
  }

  public String getServiceLevel() {
    return serviceLevel;
  }

  @JsonCreator
  public Place(
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
      @JsonProperty(value = DntConstants.MUNICIPALITIES)
      final String[] municipalities,
      @JsonProperty(value = DntConstants.COUNTIES)
      final String[] counties,
      @JsonProperty(value = DntConstants.LINKS)
      final List<Link> links,
      @JsonProperty(value = DntConstants.PARENTS_AREAS)
      final String[] parentsAreasId,
      @JsonProperty(value = DntConstants.GROUPS)
      final String[] groupsId,
      @JsonProperty(value = DntConstants.PICTURES)
      final String[] picturesId,
      @JsonProperty(value = DntConstants.URL)
      final String url,
      @JsonProperty(value = DntConstants.MAP)
      final String map,
      @JsonProperty(value = DntConstants.HIKE_MAP)
      final String hikeMap,
      @JsonProperty(value = DntConstants.AREAS)
      final String[] areasId,
      @JsonProperty(value = DntConstants.ALTERNATIVE_NAME)
      final String[] alternativeNames,
      @JsonProperty(value = DntConstants.SSR_ID)
      final int ssrId,
      @JsonProperty(value = DntConstants.ACCESS)
      final Access access,
      @JsonProperty(value = DntConstants.FACILITIES)
      final Map<String, String> facility,
      @JsonProperty(value = DntConstants.CONSTRUCTION_YEAR)
      final int constructionYear,
      @JsonProperty(value = DntConstants.SERVICE_LEVEL)
      final String serviceLevel
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
    this.municipalities = municipalities;
    this.counties = counties;
    this.links = links;
    this.parentsAreasId = parentsAreasId;
    this.groupsId = groupsId;
    this.picturesId = picturesId;
    this.url = url;
    this.map = map;
    this.hikeMap = hikeMap;
    this.areasId = areasId;
    this.alternativeNames = alternativeNames;
    this.ssrId = ssrId;
    this.access = access;
    this.facility = facility;
    this.constructionYear = constructionYear;
    this.serviceLevel = serviceLevel;
  }
}
