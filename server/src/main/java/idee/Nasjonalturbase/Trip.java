package idee.Nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.BaseObjects.LineStringGeoJSON;
import idee.Nasjonalturbase.BaseObjects.Link;
import idee.Nasjonalturbase.BaseObjects.TimeSpent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip extends BasicData {
    @JsonProperty(TurbaseConstants.DISTANCE)
    private final int distance;
    @JsonProperty(TurbaseConstants.DIRECTION)
    private final String direction;
    @JsonProperty(TurbaseConstants.AREAS)
    private final String[] areasID;
    @JsonProperty(TurbaseConstants.MUNICIPALITIES)
    private final String[] municipalities;
    @JsonProperty(TurbaseConstants.COUNTIES)
    private final String[] counties;
    @JsonProperty(TurbaseConstants.ACCESS)
    private final String access;
    @JsonProperty(TurbaseConstants.LINKS)
    private final List<Link> links;
    @JsonProperty(TurbaseConstants.GRADING)
    private final String grading;
    @JsonProperty(TurbaseConstants.SUITABLE_FOR)
    private final String[] suitableFor;
    @JsonProperty(TurbaseConstants.HANDICAP)
    private final String[] handicap;
    @JsonProperty(TurbaseConstants.SEASONS)
    private final String[] seasons;
    @JsonProperty(TurbaseConstants.TIME_SPENT)
    private final TimeSpent timeSpent;
    @JsonProperty(TurbaseConstants.GROUPS)
    private final String[] groupsID;
    @JsonProperty(TurbaseConstants.PICTURES)
    private final String[] picturesID;
    @JsonProperty(TurbaseConstants.PLACES)
    private final String[] placesID;
    @JsonProperty(TurbaseConstants.URL)
    private final String url;
    @JsonProperty(TurbaseConstants.GEO_JSON)
    private final LineStringGeoJSON lineStringGeoJSON;

    public int getDistance() {
        return distance;
    }

    public String getDirection() {
        return direction;
    }

    public String[] getAreasID() {
        return areasID;
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

    public LineStringGeoJSON getLineStringGeoJSON() {
        return lineStringGeoJSON;
    }

    @JsonCreator
    public Trip(
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
            final LineStringGeoJSON lineStringGeoJSON,
            @JsonProperty(value = TurbaseConstants.DISTANCE, required = false)
            final int distance,
            @JsonProperty(value = TurbaseConstants.DIRECTION, required = false)
            final String direction,
            @JsonProperty(value = TurbaseConstants.AREAS, required = false)
            final String[] areasID,
            @JsonProperty(value = TurbaseConstants.MUNICIPALITIES, required = false)
            final String[] municipalities,
            @JsonProperty(value = TurbaseConstants.COUNTIES, required = true)
            final String[] counties,
            @JsonProperty(value = TurbaseConstants.ACCESS, required = false)
            final String access,
            @JsonProperty(value = TurbaseConstants.LINKS, required = false)
            final List<Link> links,
            @JsonProperty(value = TurbaseConstants.GRADING, required = true)
            final String grading,
            @JsonProperty(value = TurbaseConstants.SUITABLE_FOR, required = false)
            final String[] suitableFor,
            @JsonProperty(value = TurbaseConstants.HANDICAP, required = false)
            final String[] handicap,
            @JsonProperty(value = TurbaseConstants.SEASONS, required = true)
            final String[] seasons,
            @JsonProperty(value = TurbaseConstants.TIME_SPENT, required = false)
            final TimeSpent timeSpent,
            @JsonProperty(value = TurbaseConstants.GROUPS, required = false)
            final String[] groupsID,
            @JsonProperty(value = TurbaseConstants.PICTURES, required = true)
            final String[] picturesID,
            @JsonProperty(value = TurbaseConstants.PLACES, required = false)
            final String[] placesID,
            @JsonProperty(value = TurbaseConstants.URL, required = false)
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
        this.lineStringGeoJSON = lineStringGeoJSON;
        this.distance = distance;
        this.direction = direction;
        this.areasID = areasID;
        this.municipalities = municipalities;
        this.counties = counties;
        this.access = access;
        this.links = links;
        this.grading = grading;
        this.suitableFor = suitableFor;
        this.handicap = handicap;
        this.seasons = seasons;
        this.timeSpent = timeSpent;
        this.groupsID = groupsID;
        this.picturesID = picturesID;
        this.placesID = placesID;
        this.url = url;
    }
}
