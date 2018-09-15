package idee.models.Nasjonalturbase;

public interface TurbaseConstants {

  // Fields found in JSON replies with several items
  String DOCUMENTS = "documents";
  String COUNT = "count";
  String TOTAL = "total";
  // Basic Data fields
  // Auto-generated unique ID (objectID)
  String ID = "_id";
  // Content Provider
  String CONTENT_PROVIDER = "tilbyder";
  // Last modified (Time and date, ISO 8601)
  String LAST_MODIFIED = "endret";
  String CHECKSUM = "checksum";

  // Common fields
  String LICENSE = "lisens";
  String NAMING = "navngiving";
  // Publishing status (Drafts, Private, Public, deleted)
  String PUBLISH_STATUS = "status";
  String NAME = "navn";
  String DESCRIPTION = "beskrivelse";
  String TAGS = "tags";
  String GEO_JSON = "geojson";
  String PRIVATE = "privat";
  String GROUPS = "grupper";
  String PICTURES = "bilder";
  String PLACES = "steder";
  String URL = "url";

  // Picture fields
  String PHOTOGRAPHER = "fotograf";
  String OWNER = "eier";
  String IMG = "img";

  // Area fields
  String MUNICIPALITIES = "kommuner";
  String COUNTIES = "fylker";
  String LINKS = "lenker";
  String PARENTS_AREAS = "foreldreområder";
  String MAP = "kart";
  String HIKE_MAP = "turkart";

  // Group fields
  String COMPANY_REG = "organisasjonsnr";
  String LOGO_URL = "logo";
  String NB_OF_EMPLOYEES = "ansatte";
  String CONTACT_INFO = "kontaktinfo";
  String PARENT_GROUP = "foreldregruppe";

  // Place fields
  String ALTERNATIVE_NAME = "navn_alt";
  String SSR_ID = "ssr_id";
  String ACCESS = "adkomst";
  String FACILITIES = "fasiliteter";
  String CONSTRUCTION_YEAR = "byggeår";
  // Warning: there is an error on the norwegian key "besøksstatisikk"
  String VISITOR_STATISTICS = "besøksstatisikk";
  String SERVICE_LEVEL = "betjeningsgrad";


  // Trip fields
  String DISTANCE = "distanse";
  String DIRECTION = "retning";
  String AREAS = "områder";
  String GRADING = "gradering";
  String SUITABLE_FOR = "passer_for";
  String HANDICAP = "tilrettelagt_for";
  String SEASONS = "sesong";
  String TIME_SPENT = "tidsbruk";

  // Objects
  String COORDINATES = "coordinates";
  String WIDTH = "width";
  String HEIGHT = "height";
  String SIZE = "size";
  String PHONE = "telefon";
  String EMAIL = "epost";
  String TITLE = "tittel";
  String TYPE = "type";
  String ADRESSE1 = "adresse1";
  String ADRESSE2 = "adresse2";
  String ZIP_CODE = "postnummer";
  String POST_MAIL = "poststed";
  String MOBILE = "mobil";
  String FAX = "fax";
  String SUMMER = "sommer";
  String WINTER = "vinter";
  String NORMAL = "normal";
  String MIN = "min";
  String MAX = "max";
  String DAYS = "dager";
  String HOURS = "timer";
  String MINUTES = "minutter";
}
