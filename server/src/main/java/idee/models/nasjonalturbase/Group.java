package idee.models.nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.models.nasjonalturbase.baseobjects.ContactInfo;
import idee.models.nasjonalturbase.baseobjects.Link;
import idee.models.nasjonalturbase.baseobjects.PolygonGeoJson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends BasicData {

  @JsonProperty(TurbaseConstants.AREAS)
  private final String[] areasId;
  @JsonProperty(TurbaseConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(TurbaseConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(TurbaseConstants.COMPANY_REG)
  private final String companyReg;
  @JsonProperty(TurbaseConstants.LOGO_URL)
  private final String logoUrl;
  @JsonProperty(TurbaseConstants.NB_OF_EMPLOYEES)
  private final int nbOfEmployees;
  @JsonProperty(TurbaseConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(TurbaseConstants.CONTACT_INFO)
  private final List<ContactInfo> contactInfos;
  @JsonProperty(TurbaseConstants.PARENT_GROUP)
  private final String parentGroup;
  @JsonProperty(TurbaseConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(TurbaseConstants.PICTURES)
  private final String[] picturesId;
  @JsonProperty(TurbaseConstants.PLACES)
  private final String[] placesId;
  @JsonProperty(TurbaseConstants.URL)
  private final String url;

  public String[] getAreasId() {
    return areasId;
  }

  public String[] getMunicipalities() {
    return municipalities;
  }

  public String[] getCounties() {
    return counties;
  }

  public String getCompanyReg() {
    return companyReg;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public int getNbOfEmployees() {
    return nbOfEmployees;
  }

  public List<Link> getLinks() {
    return links;
  }

  public List<ContactInfo> getContactInfos() {
    return contactInfos;
  }

  public String getParentGroup() {
    return parentGroup;
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

  @JsonCreator
  public Group(
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
      @JsonProperty(value = TurbaseConstants.AREAS, required = false)
      final String[] areasId,
      @JsonProperty(value = TurbaseConstants.MUNICIPALITIES, required = false)
      final String[] municipalities,
      @JsonProperty(value = TurbaseConstants.COUNTIES, required = true)
      final String[] counties,
      @JsonProperty(value = TurbaseConstants.COMPANY_REG, required = false)
      final String companyReg,
      @JsonProperty(value = TurbaseConstants.LOGO_URL, required = false)
      final String logoUrl,
      @JsonProperty(value = TurbaseConstants.NB_OF_EMPLOYEES, required = false)
      final int nbOfEmployees,
      @JsonProperty(value = TurbaseConstants.LINKS, required = false)
      final List<Link> links,
      @JsonProperty(value = TurbaseConstants.CONTACT_INFO, required = false)
      final List<ContactInfo> contactInfos,
      @JsonProperty(value = TurbaseConstants.PARENT_GROUP, required = false)
      final String parentGroup,
      @JsonProperty(value = TurbaseConstants.GROUPS, required = false)
      final String[] groupsId,
      @JsonProperty(value = TurbaseConstants.PICTURES, required = true)
      final String[] picturesId,
      @JsonProperty(value = TurbaseConstants.PLACES, required = false)
      final String[] placesId,
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
    this.areasId = areasId;
    this.municipalities = municipalities;
    this.counties = counties;
    this.companyReg = companyReg;
    this.logoUrl = logoUrl;
    this.nbOfEmployees = nbOfEmployees;
    this.links = links;
    this.contactInfos = contactInfos;
    this.parentGroup = parentGroup;
    this.groupsId = groupsId;
    this.picturesId = picturesId;
    this.placesId = placesId;
    this.url = url;
  }
}
