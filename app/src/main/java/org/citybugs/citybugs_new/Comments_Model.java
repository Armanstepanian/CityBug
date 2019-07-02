package org.citybugs.citybugs_new;

public class Comments_Model {
    String Com_name,comment,date,imageUrl,user_image;

    public Comments_Model(String name, String comment, String date, String imageUrl,String user_image) {
        this.user_image = user_image;
        this.Com_name = name;
        this.comment = comment;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getCom_Name() {
        return Com_name;
    }

    public void setCom_Name(String name) {
        this.Com_name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
