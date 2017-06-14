package idee.Nasjonalturbase.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

import java.io.Serializable;

/**
 * Created by didier on 20.01.17.
 */
public class LineStringGeoJSON implements Serializable {
    @JsonProperty(TurbaseConstants.COORDINATES)
    private final Double[][] coordinates;
    @JsonProperty(TurbaseConstants.TYPE)
    private final String type;

    public Double[][] getCoordinates() {
        return coordinates;
    }

    public LineStringGeoJSON(
            @JsonProperty(value = TurbaseConstants.COORDINATES, required = true)
            final Double[][] coordinates,
            @JsonProperty(value = TurbaseConstants.TYPE, required = true)
            final String type
            ) {
        this.coordinates = coordinates;
        this.type = type;
    }
}
