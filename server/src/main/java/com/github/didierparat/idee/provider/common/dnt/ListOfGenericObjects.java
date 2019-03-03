package com.github.didierparat.idee.provider.common.dnt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.GenericObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListOfGenericObjects {

  @JsonProperty(DntConstants.DOCUMENTS)
  private final GenericObject[] documents;
  @JsonProperty(DntConstants.COUNT)
  private final Integer count;
  @JsonProperty(DntConstants.TOTAL)
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
      @JsonProperty(value = DntConstants.DOCUMENTS)
      final GenericObject[] documents,
      @JsonProperty(value = DntConstants.COUNT)
      final Integer count,
      @JsonProperty(value = DntConstants.TOTAL)
      final Integer total) {
    this.documents = documents;
    this.count = count;
    this.total = total;
  }
}
