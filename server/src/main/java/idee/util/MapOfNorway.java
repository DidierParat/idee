package idee.util;

import idee.client.DntClient;
import idee.model.Location;
import idee.model.nasjonalturbase.Area;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MapOfNorway {

  private static final String SERIALIZED_MAP_FILE_NAME = "src/main/resource/areaMap.ser";

  private Map<String, Area> areaMap;

  private Integer distanceInKm(final Location locationA, final Location locationB) {
    final Double longitudeA = locationA.getLongitude();
    final Double latitudeA = locationA.getLatitude();
    final Double longitudeB = locationB.getLongitude();
    final Double latitudeB = locationB.getLatitude();

    Integer earthRadius = 6371; //km
    Double deltaLatitude = Math.toRadians(latitudeB - latitudeA);
    Double deltaLongitude = Math.toRadians(longitudeB - longitudeA);
    Double haversineA = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
        + Math.cos(Math.toRadians(latitudeA))
        * Math.cos(Math.toRadians(latitudeB))
        * Math.sin(deltaLongitude / 2)
        * Math.sin(deltaLongitude / 2);
    Double haversineC = 2 * Math.atan2(Math.sqrt(haversineA), Math.sqrt(1 - haversineA));
    Double dist = (earthRadius * haversineC);
    return dist.intValue();
  }

  public MapOfNorway(final DntClient dntClient) {
    File serializedMapFile = new File(SERIALIZED_MAP_FILE_NAME);
    if (serializedMapFile.exists() && !serializedMapFile.isDirectory()) {
      try {
        FileInputStream fis = new FileInputStream(SERIALIZED_MAP_FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.areaMap = (Map<String, Area>) ois.readObject();
        ois.close();
        fis.close();
      } catch (Exception exception) {
        log.error(
            "Could not deserialize "
                + SERIALIZED_MAP_FILE_NAME
                + ". Caught exception: "
                + exception);
      }
    } else {
      // save areas from DNT to avoid spamming during each request
      this.areaMap = new HashMap<>();
      try {
        final String[] areasIds = dntClient.getAreasIds();
        for (int i = 0; i < areasIds.length; ++i) {
          final String areaId = areasIds[i];
          final Area area = dntClient.getArea(areaId);
          areaMap.put(area.getId(), area);
        }
        final FileOutputStream fos = new FileOutputStream(SERIALIZED_MAP_FILE_NAME);
        final ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(areaMap);
        oos.close();
        fos.close();
      } catch (Exception exception) {
        log.error("Could not populate MapOfNorway from DNT. Caught exception: " + exception);
      }
    }
  }

  public Set<Area> getNearbyAreas(
      final Location userLocation,
      final Integer searchRadiusKm) {
    final Set<Area> areasNearby = new HashSet<>();
    final Collection<Area> areas = areaMap.values();
    for (final Area area : areas) {
      final Integer distanceBetweenAreaAndUser = distanceInKm(userLocation, area.getCenter());
      log.debug("Area: " + area.getName() + ". Center of area: " + area.getCenter());
      log.debug("Distance: " + distanceBetweenAreaAndUser);
      if (distanceBetweenAreaAndUser <= searchRadiusKm) {
        areasNearby.add(area);
      }
    }
    return areasNearby;
  }
}
