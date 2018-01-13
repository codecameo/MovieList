package com.example.hp.assessment.network.models;

import java.util.ArrayList;

/**
 * Created by Md. Sifat-Ul Haque on 1/13/2018.
 */

public class MovieListApiModel {
    private int page, total_results, total_pages;
    private ArrayList<MovieApiModel> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieApiModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieApiModel> results) {
        this.results = results;
    }
}
