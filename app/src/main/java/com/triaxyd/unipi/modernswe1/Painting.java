package com.triaxyd.unipi.modernswe1;


public final class Painting {
    private final String img_reference, img_description;

    public Painting(String img_reference, String img_description) {
        this.img_reference = img_reference;
        this.img_description = img_description;
    }

    public String getImg_reference() {
        return img_reference;
    }

    public String getImg_description() {
        return img_description;
    }
}
