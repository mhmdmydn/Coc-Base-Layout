package id.ghodel.cocbaselayout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HybridModel {

    @SerializedName("Layout")
    @Expose
    private Layout__3 layout;

    public Layout__3 getLayout() {
        return layout;
    }

    public void setLayout(Layout__3 layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "HybridModel{" +
                "layout=" + layout +
                '}';
    }
}
