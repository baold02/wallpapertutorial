package com.example.wallpapertutorial;

import java.util.ArrayList;

public class SesrchModel {
    private ArrayList<ImageModel> photos;

    public SesrchModel(ArrayList<ImageModel> photos) {
        this.photos = photos;
    }

    public ArrayList<ImageModel> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<ImageModel> photos) {
        this.photos = photos;
    }
}
