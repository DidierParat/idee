package idee.Nasjonalturbase.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.TurbaseConstants;

/**
 * Created by didier on 20.01.17.
 */
public class Img {
    @JsonProperty(TurbaseConstants.URL)
    private final String url;
    @JsonProperty(TurbaseConstants.WIDTH)
    private final int width;
    @JsonProperty(TurbaseConstants.HEIGHT)
    private final int height;
    @JsonProperty(TurbaseConstants.SIZE)
    private final int size;

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    @JsonCreator
    public Img(
            @JsonProperty(value = TurbaseConstants.URL, required = true)
            final String url,
            @JsonProperty(value = TurbaseConstants.WIDTH, required = true)
            final int width,
            @JsonProperty(value = TurbaseConstants.HEIGHT, required = true)
            final int height,
            @JsonProperty(value = TurbaseConstants.SIZE, required = true)
            final int size
    ) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.size = size;
    }
}
