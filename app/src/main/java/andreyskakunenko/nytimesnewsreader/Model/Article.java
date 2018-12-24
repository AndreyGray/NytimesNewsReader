package andreyskakunenko.nytimesnewsreader.Model;

import java.util.Date;
import java.util.UUID;

public class Article {
    private UUID mId;
    private Date addDate;
    private String title;
    private String author;
    private String section;
    private String date;
    private String picURL;
    private String webURL;
    private String content;

    public Article(UUID id) {
        mId = id;
        addDate = new Date();
    }

    public Article() {
        this(UUID.randomUUID());
    }

    public Article(String title, String author, String section, String date, String picURL, String webURL, String content) {
        mId = UUID.randomUUID();
        this.addDate = new Date();
        this.title = title;
        this.author = author;
        this.section = section;
        this.date = date;
        this.picURL = picURL;
        this.webURL = webURL;
        this.content = content;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setDate(Date date) {
       addDate = date;
    }

    public Date getAddDate() {
        return addDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

}
