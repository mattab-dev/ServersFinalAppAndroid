package pl.example.apimoviegetter;

import com.google.gson.annotations.SerializedName;

public class Movie {

    private String title;
    private String plot;

    @SerializedName("id")
    public String id;

    @Override
    public String toString() {
        return("Title " + title + ", plot: " + plot);
    }

    public Movie(String title, String plot, String id) {
        this.title = title;
        this.plot = plot;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
