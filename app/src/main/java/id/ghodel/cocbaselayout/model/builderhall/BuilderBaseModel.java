package id.ghodel.cocbaselayout.model.builderhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Mayudin on 05-Jul-21.
 */
public class BuilderBaseModel {

    @SerializedName("bases")
    @Expose
    private List<Basis> bases = null;

    public List<Basis> getBases() {
        return bases;
    }

    public void setBases(List<Basis> bases) {
        this.bases = bases;
    }

    @Override
    public String toString() {
        return "BuilderBaseModel{" +
                "bases=" + bases +
                '}';
    }
}
