package idee.provider.trip.dnt.location.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.model.Location;
import idee.provider.common.dnt.BasicData;
import idee.provider.common.dnt.DntConstants;
import idee.provider.common.dnt.baseobjects.Link;
import idee.provider.common.dnt.baseobjects.PolygonGeoJson;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Area extends BasicData implements Serializable {

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
  @JsonProperty(DntConstants.PLACES)
  private final String[] placesId;
  @JsonProperty(DntConstants.URL)
  private final String url;
  @JsonProperty(DntConstants.MAP)
  private final String map;
  @JsonProperty(DntConstants.HIKE_MAP)
  private final String hikeMap;
  @JsonProperty(DntConstants.GEO_JSON)
  private PolygonGeoJson polygonGeoJson;
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

  public String[] getParentsAreasId() {
    return parentsAreasId;
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

  public String getMap() {
    return map;
  }

  public String getHikeMap() {
    return hikeMap;
  }

  public PolygonGeoJson getPolygonGeoJson() {
    return polygonGeoJson;
  }

  public Location getCenter() {
    return center;
  }

  @JsonCreator
  public Area(
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
      @JsonProperty(value = DntConstants.PLACES)
      final String[] placesId,
      @JsonProperty(value = DntConstants.URL)
      final String url,
      @JsonProperty(value = DntConstants.MAP)
      final String map,
      @JsonProperty(value = DntConstants.HIKE_MAP)
      final String hikeMap
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
    this.parentsAreasId = parentsAreasId;
    this.groupsId = groupsId;
    this.picturesId = picturesId;
    this.placesId = placesId;
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
    final PolygonGeoJson areaPolygon = this.getPolygonGeoJson();
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
