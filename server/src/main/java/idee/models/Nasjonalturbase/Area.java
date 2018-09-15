package idee.models.Nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.models.Location;
import idee.models.Nasjonalturbase.BaseObjects.PolygonGeoJSON;
import idee.models.Nasjonalturbase.BaseObjects.Link;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Area extends BasicData implements Serializable {

  @JsonProperty(TurbaseConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(TurbaseConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(TurbaseConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(TurbaseConstants.PARENTS_AREAS)
  private final String[] parentsAreasID;
  @JsonProperty(TurbaseConstants.GROUPS)
  private final String[] groupsID;
  @JsonProperty(TurbaseConstants.PICTURES)
  private final String[] picturesID;
  @JsonProperty(TurbaseConstants.PLACES)
  private final String[] placesID;
  @JsonProperty(TurbaseConstants.URL)
  private final String url;
  @JsonProperty(TurbaseConstants.MAP)
  private final String map;
  @JsonProperty(TurbaseConstants.HIKE_MAP)
  private final String hikeMap;
  @JsonProperty(TurbaseConstants.GEO_JSON)
  private PolygonGeoJSON polygonGeoJson;
  // This value is computed from the GeoJson polygon
  private final Location center;

  public String[] getMunicipalities() {
    return municipalities;
  }

  public String[] getCounties() {
    return counties;
  }

  public List<Link> getLinks() {
    return links;
  }

  public String[] getParentsAreasID() {
    return parentsAreasID;
  }

  public String[] getGroupsID() {
    return groupsID;
  }

  public String[] getPicturesID() {
    return picturesID;
  }

  public String[] getPlacesID() {
    return placesID;
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

  public PolygonGeoJSON getPolygonGeoJson() {
    return polygonGeoJson;
  }

  public Location getCenter() {
    return center;
  }

  @JsonCreator
  public Area(
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
      @JsonProperty(value = TurbaseConstants.MUNICIPALITIES, required = false) final String[] municipalities,
      @JsonProperty(value = TurbaseConstants.COUNTIES, required = false) final String[] counties,
      @JsonProperty(value = TurbaseConstants.LINKS, required = false) final List<Link> links,
      @JsonProperty(value = TurbaseConstants.PARENTS_AREAS, required = false) final String[] parentsAreasID,
      @JsonProperty(value = TurbaseConstants.GROUPS, required = false) final String[] groupsID,
      @JsonProperty(value = TurbaseConstants.PICTURES, required = true) final String[] picturesID,
      @JsonProperty(value = TurbaseConstants.PLACES, required = false) final String[] placesID,
      @JsonProperty(value = TurbaseConstants.URL, required = false) final String url,
      @JsonProperty(value = TurbaseConstants.MAP, required = false) final String map,
      @JsonProperty(value = TurbaseConstants.HIKE_MAP, required = false) final String hikeMap
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
    this.polygonGeoJson = polygonGeoJson;
    this.municipalities = municipalities;
    this.counties = counties;
    this.links = links;
    this.parentsAreasID = parentsAreasID;
    this.groupsID = groupsID;
    this.picturesID = picturesID;
    this.placesID = placesID;
    this.url = url;
    this.map = map;
    this.hikeMap = hikeMap;

    if (polygonGeoJson == null) {
      this.center = new Location(Double.MIN_VALUE, Double.MIN_VALUE);
    } else {
      this.center = computeCenterOfPolygon();
    }
  }

  private Location computeCenterOfPolygon() {
    final PolygonGeoJSON areaPolygon = this.getPolygonGeoJson();
    Double[][] polygonCoordinates = areaPolygon.getCoordinates()[0];
    Double lowestLongitude = Double.MAX_VALUE;
    Double highestLongitude = Double.MIN_VALUE;
    Double lowestLatitude = Double.MAX_VALUE;
    Double highestLatitude = Double.MIN_VALUE;
    for (int i = 0; i < polygonCoordinates.length; ++i) {
      final Double longitude = polygonCoordinates[i][0];
      final Double latitude = polygonCoordinates[i][1];
      if (longitude < lowestLongitude) {
        lowestLongitude = longitude;
      }
      if (latitude < lowestLatitude) {
        lowestLatitude = latitude;
      }
      if (highestLongitude < longitude) {
        highestLongitude = longitude;
      }
      if (highestLatitude < latitude) {
        highestLatitude = latitude;
      }
    }
    final Double longitudeOfPolygonCenter
        = lowestLongitude + ((highestLongitude - lowestLongitude) / 2);
    final Double latitudeOfPolygonCenter
        = lowestLatitude + ((highestLatitude - lowestLatitude) / 2);
    return new Location(longitudeOfPolygonCenter, latitudeOfPolygonCenter);
  }
}
