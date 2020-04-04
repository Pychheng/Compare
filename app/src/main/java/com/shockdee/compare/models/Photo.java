package com.shockdee.compare.models;

public class Photo {

    public int photoId;
    public String photoName;
    public String photoProductName;
    public String photoProductType;
    public String photoBrand;
    public String photoStore;
    public String photoDate;
    public Double photoPrice;
    public String photoDescription;
    public String photoPath;

    public Photo() {
    }

    public Photo(String photoName, String photoProductName, String photoProductType, String photoBrand, String photoStore, String photoDate, Double photoPrice, String photoDescription, String photoPath) {
        this.photoName = photoName;
        this.photoProductName = photoProductName;
        this.photoProductType = photoProductType;
        this.photoBrand = photoBrand;
        this.photoStore = photoStore;
        this.photoDate = photoDate;
        this.photoPrice = photoPrice;
        this.photoDescription = photoDescription;
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoProductName() {
        return photoProductName;
    }

    public void setPhotoProductName(String photoProductName) {
        this.photoProductName = photoProductName;
    }

    public String getPhotoProductType() {
        return photoProductType;
    }

    public void setPhotoProductType(String photoProductType) {
        this.photoProductType = photoProductType;
    }

    public String getPhotoBrand() {
        return photoBrand;
    }

    public void setPhotoBrand(String photoBrand) {
        this.photoBrand = photoBrand;
    }

    public String getPhotoStore() {
        return photoStore;
    }

    public void setPhotoStore(String photoStore) {
        this.photoStore = photoStore;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public Double getPhotoPrice() {
        return photoPrice;
    }

    public void setPhotoPrice(Double photoPrice) {
        this.photoPrice = photoPrice;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public void setPhotoDescription(String photoDescription) {
        this.photoDescription = photoDescription;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return
                "Titre : " + photoProductName + '\n' +
                "Type : " + photoProductType + '\n' +
                "Marque : " + photoBrand + '\n' +
                "Enseigne : " + photoStore + '\n' +
                "Date : " + photoDate + '\n' +
                "Prix : " + photoPrice +" €"+ '\n' +
                "Description : " + photoDescription;
    }
}
