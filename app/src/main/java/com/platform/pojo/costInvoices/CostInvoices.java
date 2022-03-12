
package com.platform.pojo.costInvoices;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CostInvoices {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("columnValues")
    @Expose
    private Object columnValues;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Object getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Object columnValues) {
        this.columnValues = columnValues;
    }

}
