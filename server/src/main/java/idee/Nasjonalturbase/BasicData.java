package idee.Nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BasicData implements Serializable {
    @JsonProperty(TurbaseConstants.ID)
    private final String id;
    @JsonProperty(TurbaseConstants.CONTENT_PROVIDER)
    private final String contentProvider;
    @JsonProperty(TurbaseConstants.LAST_MODIFIED)
    private final String lastModified;
    @JsonProperty(TurbaseConstants.CHECKSUM)
    private final String checksum;
    @JsonProperty(TurbaseConstants.LICENSE)
    private final String license;
    @JsonProperty(TurbaseConstants.NAMING)
    private final String naming;
    @JsonProperty(TurbaseConstants.PUBLISH_STATUS)
    private final String publishStatus;
    @JsonProperty(TurbaseConstants.NAME)
    private final String name;
    @JsonProperty(TurbaseConstants.DESCRIPTION)
    private final String description;
    @JsonProperty(TurbaseConstants.TAGS)
    private final String[] tags;
    // Private field, only visible for providers
    // private final String privat;

    @JsonCreator
    public BasicData(
            @JsonProperty(value = TurbaseConstants.ID, required = true)
            final String id,
            @JsonProperty(value = TurbaseConstants.CONTENT_PROVIDER, required = true)
            final String contentProvider,
            @JsonProperty(value = TurbaseConstants.LAST_MODIFIED, required = true)
            final String lastModified,
            @JsonProperty(value = TurbaseConstants.CHECKSUM, required = true)
            final String checksum,
            @JsonProperty(value = TurbaseConstants.LICENSE, required = true)
            final String license,
            @JsonProperty(value = TurbaseConstants.NAMING, required = true)
            final String naming,
            @JsonProperty(value = TurbaseConstants.PUBLISH_STATUS, required = true)
            final String publishStatus,
            @JsonProperty(value = TurbaseConstants.NAME, required = true)
            final String name,
            @JsonProperty(value = TurbaseConstants.DESCRIPTION, required = true)
            final String description,
            @JsonProperty(value = TurbaseConstants.TAGS, required = false)
            final String[] tags
    ) {
        this.id = id;
        this.contentProvider = contentProvider;
        this.lastModified = lastModified;
        this.checksum = checksum;
        this.license = license;
        this.naming = naming;
        this.publishStatus = publishStatus;
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getContentProvider() {
        return contentProvider;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getLicense() {
        return license;
    }

    public String getNaming() {
        return naming;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getTags() {
        return tags;
    }
}
