package id.ghodel.cocbaselayout.model.townhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseModel {
    @SerializedName("trophy")
    @Expose
    private List<TrophyModel> thropy = null;
    @SerializedName("war")
    @Expose
    private List<WarModel> war = null;
    @SerializedName("farming")
    @Expose
    private List<FarmingModel> farming = null;
    @SerializedName("hybrid")
    @Expose
    private List<HybridModel> hybrid = null;

    public List<TrophyModel> getThropy() {
        return thropy;
    }

    public void setThropy(List<TrophyModel> thropy) {
        this.thropy = thropy;
    }

    public List<WarModel> getWar() {
        return war;
    }

    public void setWar(List<WarModel> war) {
        this.war = war;
    }

    public List<FarmingModel> getFarming() {
        return farming;
    }

    public void setFarming(List<FarmingModel> farming) {
        this.farming = farming;
    }

    public List<HybridModel> getHybrid() {
        return hybrid;
    }

    public void setHybrid(List<HybridModel> hybrid) {
        this.hybrid = hybrid;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "thropy=" + thropy +
                ", war=" + war +
                ", farming=" + farming +
                ", hybrid=" + hybrid +
                '}';
    }
}
