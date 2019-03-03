package com.github.didierparat.idee.provider.common.dnt;

public final class DntConstants {

  private DntConstants() {}

  public static final String MAX_OBJECTS_PER_REQUEST = "50";
  public static final String OBJECT_TYPE_TUR = "turer/";
  public static final String OBJECT_TYPE_AREAS = "omr%C3%A5der/";

  // Query Parameters name
  public static final String QUERY_PARAM_LIMIT = "limit";
  public static final String QUERY_PARAM_API_KEY = "api_key";
  public static final String QUERY_PARAM_AREAS = "områder[]";

  // Fields found in JSON replies with several items
  public static final String DOCUMENTS = "documents";
  public static final String COUNT = "count";
  public static final String TOTAL = "total";
  // Basic Data fields
  // Auto-generated unique ID (objectID)
  public static final String ID = "_id";
  // Content Provider
  public static final String CONTENT_PROVIDER = "tilbyder";
  // Last modified (Time and date, ISO 8601)
  public static final String LAST_MODIFIED = "endret";
  public static final String CHECKSUM = "checksum";

  // Common fields
  public static final String LICENSE = "lisens";
  public static final String NAMING = "navngiving";
  // Publishing status (Drafts, Private, Public, deleted)
  public static final String PUBLISH_STATUS = "status";
  public static final String NAME = "navn";
  public static final String DESCRIPTION = "beskrivelse";
  public static final String TAGS = "tags";
  public static final String GEO_JSON = "geojson";
  public static final String PRIVATE = "privat";
  public static final String GROUPS = "grupper";
  public static final String PICTURES = "bilder";
  public static final String PLACES = "steder";
  public static final String URL = "url";

  // Picture fields
  public static final String PHOTOGRAPHER = "fotograf";
  public static final String OWNER = "eier";
  public static final String IMG = "img";

  // Area fields
  public static final String MUNICIPALITIES = "kommuner";
  public static final String COUNTIES = "fylker";
  public static final String LINKS = "lenker";
  public static final String PARENTS_AREAS = "foreldreområder";
  public static final String MAP = "kart";
  public static final String HIKE_MAP = "turkart";

  // Group fields
  public static final String COMPANY_REG = "organisasjonsnr";
  public static final String LOGO_URL = "logo";
  public static final String NB_OF_EMPLOYEES = "ansatte";
  public static final String CONTACT_INFO = "kontaktinfo";
  public static final String PARENT_GROUP = "foreldregruppe";

  // Place fields
  public static final String ALTERNATIVE_NAME = "navn_alt";
  public static final String SSR_ID = "ssr_id";
  public static final String ACCESS = "adkomst";
  public static final String FACILITIES = "fasiliteter";
  public static final String CONSTRUCTION_YEAR = "byggeår";
  // Warning: there is an error on the norwegian key "besøksstatisikk"
  public static final String VISITOR_STATISTICS = "besøksstatisikk";
  public static final String SERVICE_LEVEL = "betjeningsgrad";


  // DntTrip fields
  public static final String DISTANCE = "distanse";
  public static final String DIRECTION = "retning";
  public static final String AREAS = "områder";
  public static final String GRADING = "gradering";
  public static final String SUITABLE_FOR = "passer_for";
  public static final String HANDICAP = "tilrettelagt_for";
  public static final String SEASONS = "sesong";
  public static final String TIME_SPENT = "tidsbruk";

  // Objects
  public static final String COORDINATES = "coordinates";
  public static final String WIDTH = "width";
  public static final String HEIGHT = "height";
  public static final String SIZE = "size";
  public static final String PHONE = "telefon";
  public static final String EMAIL = "epost";
  public static final String TITLE = "tittel";
  public static final String TYPE = "type";
  public static final String ADRESSE1 = "adresse1";
  public static final String ADRESSE2 = "adresse2";
  public static final String ZIP_CODE = "postnummer";
  public static final String POST_MAIL = "poststed";
  public static final String MOBILE = "mobil";
  public static final String FAX = "fax";
  public static final String SUMMER = "sommer";
  public static final String WINTER = "vinter";
  public static final String NORMAL = "normal";
  public static final String MIN = "min";
  public static final String MAX = "max";
  public static final String DAYS = "dager";
  public static final String HOURS = "timer";
  public static final String MINUTES = "minutter";
}
