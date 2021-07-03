package id.ghodel.cocbaselayout.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Layout__3 {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("layout_link")
    @Expose
    private String layoutLink;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("hall")
    @Expose
    private String hall;
    @SerializedName("download")
    @Expose
    private String download;
    @SerializedName("view")
    @Expose
    private String view;
    @SerializedName("like")
    @Expose
    private String like;
    @SerializedName("base_strength")
    @Expose
    private String baseStrength;
    @SerializedName("cc_troops")
    @Expose
    private String ccTroops;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLayoutLink() {
        return layoutLink;
    }

    public void setLayoutLink(String layoutLink) {
        this.layoutLink = layoutLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(String baseStrength) {
        this.baseStrength = baseStrength;
    }

    public String getCcTroops() {
        return ccTroops;
    }

    public void setCcTroops(String ccTroops) {
        this.ccTroops = ccTroops;
    }

    @Override
    public String toString() {
        return "Layout{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", layoutLink='" + layoutLink + '\'' +
                ", type='" + type + '\'' +
                ", hall='" + hall + '\'' +
                ", download='" + download + '\'' +
                ", view='" + view + '\'' +
                ", like='" + like + '\'' +
                ", baseStrength='" + baseStrength + '\'' +
                ", ccTroops='" + ccTroops + '\'' +
                '}';
    }

}
