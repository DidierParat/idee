package com.github.didierparat.idee.provider.common.dnt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.ContactInfo;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.Link;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.PolygonGeoJson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends BasicData {

  @JsonProperty(DntConstants.AREAS)
  private final String[] areasId;
  @JsonProperty(DntConstants.MUNICIPALITIES)
  private final String[] municipalities;
  @JsonProperty(DntConstants.COUNTIES)
  private final String[] counties;
  @JsonProperty(DntConstants.COMPANY_REG)
  private final String companyReg;
  @JsonProperty(DntConstants.LOGO_URL)
  private final String logoUrl;
  @JsonProperty(DntConstants.NB_OF_EMPLOYEES)
  private final int nbOfEmployees;
  @JsonProperty(DntConstants.LINKS)
  private final List<Link> links;
  @JsonProperty(DntConstants.CONTACT_INFO)
  private final List<ContactInfo> contactInfos;
  @JsonProperty(DntConstants.PARENT_GROUP)
  private final String parentGroup;
  @JsonProperty(DntConstants.GROUPS)
  private final String[] groupsId;
  @JsonProperty(DntConstants.PICTURES)
  private final String[] picturesId;
  @JsonProperty(DntConstants.PLACES)
  private final String[] placesId;
  @JsonProperty(DntConstants.URL)
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
      @JsonProperty(value = DntConstants.AREAS)
      final String[] areasId,
      @JsonProperty(value = DntConstants.MUNICIPALITIES)
      final String[] municipalities,
      @JsonProperty(value = DntConstants.COUNTIES)
      final String[] counties,
      @JsonProperty(value = DntConstants.COMPANY_REG)
      final String companyReg,
      @JsonProperty(value = DntConstants.LOGO_URL)
      final String logoUrl,
      @JsonProperty(value = DntConstants.NB_OF_EMPLOYEES)
      final int nbOfEmployees,
      @JsonProperty(value = DntConstants.LINKS)
      final List<Link> links,
      @JsonProperty(value = DntConstants.CONTACT_INFO)
      final List<ContactInfo> contactInfos,
      @JsonProperty(value = DntConstants.PARENT_GROUP)
      final String parentGroup,
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
