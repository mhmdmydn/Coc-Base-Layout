package id.ghodel.cocbaselayout.model;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarModel {

    @SerializedName("Layout")
    @Expose
    private Layout__1 layout;

    public Layout__1 getLayout() {
        return layout;
    }

    public void setLayout(Layout__1 layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "WarModel{" +
                "layout=" + layout +
                '}';
    }
}
