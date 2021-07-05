package id.ghodel.cocbaselayout.model.builderhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Mayudin on 05-Jul-21.
 */
public class BuilderBase {

    @SerializedName("id")
    @Expose
    private String baseId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("layout_link")
    @Expose
    private String layoutLink;
    @SerializedName("hall")
    @Expose
    private String hall;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("download")
    @Expose
    private String download;
    @SerializedName("view")
    @Expose
    private String view;
    @SerializedName("like")
    @Expose
    private String like;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
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

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "BuilderBase{" +
                "id='" + baseId + '\'' +
                ", image='" + image + '\'' +
                ", layoutLink='" + layoutLink + '\'' +
                ", hall='" + hall + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", download='" + download + '\'' +
                ", view='" + view + '\'' +
                ", like='" + like + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                '}';
    }
}