package id.ghodel.cocbaselayout.model.townhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmingModel {

    @SerializedName("Layout")
    @Expose
    private Layout layout;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "FarmingModel{" +
                "layout=" + layout +
                '}';
    }
}
