package idee.Nasjonalturbase.BaseObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photographer {
    @JsonProperty(TurbaseConstants.NAME)
    private final String name;
    @JsonProperty(TurbaseConstants.PHONE)
    private final String phone;
    @JsonProperty(TurbaseConstants.EMAIL)
    private final String email;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @JsonCreator
    public Photographer(
            @JsonProperty(value = TurbaseConstants.NAME, required = true)
            final String name,
            @JsonProperty(value = TurbaseConstants.PHONE, required = true)
            final String phone,
            @JsonProperty(value = TurbaseConstants.EMAIL, required = true)
            final String email
    ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
