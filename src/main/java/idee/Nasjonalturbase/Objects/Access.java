package idee.Nasjonalturbase.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

/**
 * Created by didier on 20.01.17.
 */
public class Access {
    @JsonProperty(TurbaseConstants.SUMMER)
    private final String summer;
    @JsonProperty(TurbaseConstants.WINTER)
    private final String winter;

    public String getSummer() {
        return summer;
    }

    public String getWinter() {
        return winter;
    }

    public Access(
            @JsonProperty(value = TurbaseConstants.SUMMER, required = true)
            final String summer,
            @JsonProperty(value = TurbaseConstants.WINTER, required = true)
            final String winter
            ) {
        this.summer = summer;
        this.winter = winter;
    }
}
