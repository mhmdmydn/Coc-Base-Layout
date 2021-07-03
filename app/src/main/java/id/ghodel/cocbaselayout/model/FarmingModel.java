package id.ghodel.cocbaselayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmingModel {

    @SerializedName("Layout")
    @Expose
    private Layout__2 layout;

    public Layout__2 getLayout() {
        return layout;
    }

    public void setLayout(Layout__2 layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "FarmingModel{" +
                "layout=" + layout +
                '}';
    }
}
