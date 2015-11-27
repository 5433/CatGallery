package com.catgallery;

import android.graphics.Bitmap;

/**
 * Created by Anmar Hindi on 11/27/15.
 */
public class ImageItem {
    private Bitmap image;
    private String title;

    //represents the image
    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
