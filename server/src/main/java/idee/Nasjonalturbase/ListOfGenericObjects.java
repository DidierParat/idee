package idee.Nasjonalturbase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import idee.Nasjonalturbase.BaseObjects.GenericObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListOfGenericObjects {
    @JsonProperty(TurbaseConstants.DOCUMENTS)
    private final GenericObject[] documents;
    @JsonProperty(TurbaseConstants.COUNT)
    private final Integer count;
    @JsonProperty(TurbaseConstants.TOTAL)
    private final Integer total;

    public GenericObject[] getDocuments() {
        return documents;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getTotal() {
        return total;
    }

    @JsonCreator
    public ListOfGenericObjects(
            @JsonProperty(value = TurbaseConstants.DOCUMENTS, required = true)
            final GenericObject[] documents,
            @JsonProperty(value = TurbaseConstants.COUNT, required = true)
            final Integer count,
            @JsonProperty(value = TurbaseConstants.TOTAL, required = true)
            final Integer total)
    {
        this.documents = documents;
        this.count = count;
        this.total = total;
    }
}
