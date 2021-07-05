package id.ghodel.cocbaselayout.model.builderhall;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Mayudin on 05-Jul-21.
 */
public class Basis {

    @SerializedName("BuilderBase")
    @Expose
    private BuilderBase builderBase;

    public BuilderBase getBuilderBase() {
        return builderBase;
    }

    public void setBuilderBase(BuilderBase builderBase) {
        this.builderBase = builderBase;
    }

    @Override
    public String toString() {
        return "Basis{" +
                "builderBase=" + builderBase +
                '}';
    }
}
