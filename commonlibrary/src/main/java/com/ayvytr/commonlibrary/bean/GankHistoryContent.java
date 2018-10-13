package com.ayvytr.commonlibrary.bean;

import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 */
public class GankHistoryContent {
    private boolean error;
    private List<String> category;
    private HashMap<String, List<Gank>> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public HashMap<String, List<Gank>> getResults() {
        return results;
    }

    public void setResults(HashMap<String, List<Gank>> results) {
        this.results = results;
    }
}
