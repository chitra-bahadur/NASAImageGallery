package obvious.assignment.nasaimagegallery.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "image_details")
public class ImageDetails {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "explanation")
    String explanation;

    @ColumnInfo(name = "hd_url")
    String hdUrl;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "url")
    String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public void setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
