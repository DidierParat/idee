package idee.Nasjonalturbase.BaseObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSpent {
    @JsonProperty(TurbaseConstants.NORMAL)
    private final EstimatedTime normal;
    @JsonProperty(TurbaseConstants.MIN)
    private final EstimatedTime min;
    @JsonProperty(TurbaseConstants.MAX)
    private final EstimatedTime max;

    public TimeSpent(
            @JsonProperty(value = TurbaseConstants.NORMAL, required = true)
            final EstimatedTime normal,
            @JsonProperty(value = TurbaseConstants.MIN, required = true)
            final EstimatedTime min,
            @JsonProperty(value = TurbaseConstants.MAX, required = true)
            final EstimatedTime max
    ) {
        this.normal = normal;
        this.max = max;
        this.min = min;
    }

    public EstimatedTime getNormal() {
        return normal;
    }

    public EstimatedTime getMin() {
        return min;
    }

    public EstimatedTime getMax() {
        return max;
    }
}