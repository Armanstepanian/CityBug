package org.citybugs.citybugs_new;

public class Model {
    String name, titl,profilePick,picture,context,text_id;
    int like,comm,share;


    public Model(String name, String titl, String profilePick, String picture,String context, int like, int comm, int share,String text_id) {
        this.name = name;
        this.context = context;
        this.titl = titl;
        this.profilePick = profilePick;
        this.picture = picture;
        this.like = like;
        this.comm = comm;
        this.share = share;
        this.text_id = text_id;
    }

    public String getText_id() {
        return text_id;
    }

    public void setText_id(String text_id) {
        this.text_id = text_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitl() {
        return titl;
    }

    public void setTitl(String titl) {
        this.titl = titl;
    }

    public String getProfilePick() {
        return profilePick;
    }

    public void setProfilePick(String profilePick) {
        this.profilePick = profilePick;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComm() {
        return comm;
    }

    public void setComm(int comm) {
        this.comm = comm;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
