package id.ghodel.cocbaselayout.model.townhall;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarModel {

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
        return "WarModel{" +
                "layout=" + layout +
                '}';
    }
}
