package com.kilometer.domain.item;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;
    private String urlDB;

    protected UploadFile() {
    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile updateDB(String urlDB) {
        this.urlDB = urlDB;
        return this;
    }

}
