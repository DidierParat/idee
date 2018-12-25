package idee.model.nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.model.nasjonalturbase.baseobjects.Access;
import idee.model.nasjonalturbase.baseobjects.Link;
import idee.model.nasjonalturbase.baseobjects.PolygonGeoJson;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place extends BasicData {

  @JsonProperty(TurbaseConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(TurbaseConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(TurbaseConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(TurbaseConstants.PARENTS_AREAS)
  private final String[] parentsAreasId;
  @JsonProperty(TurbaseConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(TurbaseConstants.PICTURES)
  private final String[] picturesId;
  @JsonProperty(TurbaseConstants.URL)
  private final String url;
  @JsonProperty(TurbaseConstants.MAP)
  private final String map;
  @JsonProperty(TurbaseConstants.HIKE_MAP)
  private final String hikeMap;
  @JsonProperty(TurbaseConstants.AREAS)
  private final String[] areasId;
  @JsonProperty(TurbaseConstants.ALTERNATIVE_NAME)
  private final String[] alternativeNames;
  @JsonProperty(TurbaseConstants.SSR_ID)
  private final int ssrId;
  @JsonProperty(TurbaseConstants.ACCESS)
  private final Access access;
  @JsonProperty(TurbaseConstants.FACILITIES)
  private final Map<String, String> facility;
  @JsonProperty(TurbaseConstants.CONSTRUCTION_YEAR)
  private final int constructionYear;
  // Not available yet
  //@JsonProperty(TurbaseConstants.VISITOR_STATISTICS)
  //private final VisitorStat visitorStat;
  @JsonProperty(TurbaseConstants.SERVICE_LEVEL)
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
      @JsonProperty(value = TurbaseConstants.ID, required = true)
      final String id,
      @JsonProperty(value = TurbaseConstants.CONTENT_PROVIDER, required = true)
      final String contentProvider,
      @JsonProperty(value = TurbaseConstants.LAST_MODIFIED, required = true)
      final String lastModified,
      @JsonProperty(value = TurbaseConstants.CHECKSUM, required = true)
      final String checksum,
      @JsonProperty(value = TurbaseConstants.LICENSE, required = true)
      final String license,
      @JsonProperty(value = TurbaseConstants.NAMING, required = true)
      final String naming,
      @JsonProperty(value = TurbaseConstants.PUBLISH_STATUS, required = true)
      final String publishStatus,
      @JsonProperty(value = TurbaseConstants.NAME, required = true)
      final String name,
      @JsonProperty(value = TurbaseConstants.DESCRIPTION, required = true)
      final String description,
      @JsonProperty(value = TurbaseConstants.TAGS, required = true)
      final String[] tags,
      @JsonProperty(value = TurbaseConstants.GEO_JSON, required = false)
      final PolygonGeoJson polygonGeoJson,
      @JsonProperty(value = TurbaseConstants.MUNICIPALITIES, required = false)
      final String[] municipalities,
      @JsonProperty(value = TurbaseConstants.COUNTIES, required = false)
      final String[] counties,
      @JsonProperty(value = TurbaseConstants.LINKS, required = false)
      final List<Link> links,
      @JsonProperty(value = TurbaseConstants.PARENTS_AREAS, required = false)
      final String[] parentsAreasId,
      @JsonProperty(value = TurbaseConstants.GROUPS, required = false)
      final String[] groupsId,
      @JsonProperty(value = TurbaseConstants.PICTURES, required = true)
      final String[] picturesId,
      @JsonProperty(value = TurbaseConstants.URL, required = false)
      final String url,
      @JsonProperty(value = TurbaseConstants.MAP, required = false)
      final String map,
      @JsonProperty(value = TurbaseConstants.HIKE_MAP, required = false)
      final String hikeMap,
      @JsonProperty(value = TurbaseConstants.AREAS, required = false)
      final String[] areasId,
      @JsonProperty(value = TurbaseConstants.ALTERNATIVE_NAME, required = false)
      final String[] alternativeNames,
      @JsonProperty(value = TurbaseConstants.SSR_ID, required = false)
      final int ssrId,
      @JsonProperty(value = TurbaseConstants.ACCESS, required = false)
      final Access access,
      @JsonProperty(value = TurbaseConstants.FACILITIES, required = false)
      final Map<String, String> facility,
      @JsonProperty(value = TurbaseConstants.CONSTRUCTION_YEAR, required = false)
      final int constructionYear,
      @JsonProperty(value = TurbaseConstants.SERVICE_LEVEL, required = true)
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
