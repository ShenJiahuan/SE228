package com.shenjiahuan.eBook.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "image")
public class Image {

    @Id
    @Field("book_id")
    private Integer bookId;

    @Field("img_base64")
    private String imgBase64;

    @Field("tmp_filename")
    private String tmpFilename;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getTmpFilename() {
        return tmpFilename;
    }

    public void setTmpFilename(String tmpFilename) {
        this.tmpFilename = tmpFilename;
    }
}
