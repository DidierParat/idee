package idee.Nasjonalturbase.BaseObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolygonGeoJSON implements Serializable {
    @JsonProperty(TurbaseConstants.COORDINATES)
    private final Double[][][] coordinates;
    @JsonProperty(TurbaseConstants.TYPE)
    private final String type;

    public Double[][][] getCoordinates() {
        return coordinates;
    }

    public PolygonGeoJSON(
            @JsonProperty(value = TurbaseConstants.COORDINATES, required = true)
            final Double[][][] coordinates,
            @JsonProperty(value = TurbaseConstants.TYPE, required = true)
            final String type
            ) {
        this.coordinates = coordinates;
        this.type = type;
    }
}
