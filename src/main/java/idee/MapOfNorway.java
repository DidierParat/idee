package idee;

import idee.Clients.DntClient;
import idee.Nasjonalturbase.Area;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by didier on 19.01.17.
 */
public class MapOfNorway {
    private static final String SERIALIZED_HASHMAP_NAME = "areaHashmap.ser";
    private static final Logger LOGGER = Logger.getLogger(MapOfNorway.class.getName());

    private HashMap<String, Area> areaMap;

    public Integer distanceInKm(final Location locationA, final Location locationB) {
        final Double longitudeA = locationA.getLongitude();
        final Double latitudeA = locationA.getLatitude();
        final Double longitudeB = locationB.getLongitude();
        final Double latitudeB = locationB.getLatitude();

        Integer earthRadius = 6371; //km
        Double dLat = Math.toRadians(latitudeB-latitudeA);
        Double dLng = Math.toRadians(longitudeB-longitudeA);
        Double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(latitudeA))
                * Math.cos(Math.toRadians(latitudeB))
                * Math.sin(dLng/2)
                * Math.sin(dLng/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double dist = (earthRadius * c);
        return dist.intValue();
    }

    public MapOfNorway(final DntClient dntClient) {
        File f = new File(SERIALIZED_HASHMAP_NAME);
        if(f.exists() && !f.isDirectory()) {
            try {
                FileInputStream fis = new FileInputStream(SERIALIZED_HASHMAP_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.areaMap = (HashMap) ois.readObject();
                ois.close();
                fis.close();
            } catch(Exception e) {
                LOGGER.severe(
                        "Could not deserialize "
                        + SERIALIZED_HASHMAP_NAME
                        + ". Caught exception: " + e);
            }
        } else {
            // has to be populated from DNT
            this.areaMap = new HashMap<>();
            try {
                String[] areasIds = dntClient.getAreasIds();
                for (int i = 0; i < areasIds.length; ++i) {
                    String areaId = areasIds[i];
                    Area area = dntClient.getArea(areaId);
                    areaMap.put(area.getId(), area);
                }
                FileOutputStream fos = new FileOutputStream(SERIALIZED_HASHMAP_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(areaMap);
                oos.close();
                fos.close();
            } catch (Exception e) {
                LOGGER.severe("Could not populate MapOfNorway from DNT. Caught exception: " + e);
            }
        }
    }

    public Set<Area> getNearbyAreas(
            final Location userLocation,
            final Integer searchRadiusKm) {
        final Set<Area> areasNearby = new HashSet<>();
        final Collection<Area> areas = areaMap.values();
       for (final Area area:areas) {
            final Integer distanceBetweenAreaAndUser = distanceInKm(userLocation, area.getCenter());
            LOGGER.fine(
                    "Area: "
                    + area.getName()
                    + ". Center of area: "
                    + area.getCenter());
            LOGGER.fine("Distance: " + distanceBetweenAreaAndUser);
            if (distanceBetweenAreaAndUser <= searchRadiusKm) {
                areasNearby.add(area);
            }
        }
        return areasNearby;
    }
}
