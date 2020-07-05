package com.halil.ozel.photosapp.data;

import java.util.List;

public class Photos {

    private String perpage;

    private String total;

    private String pages;

    private List<Photo> photo;

    private String page;

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
