package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Image;

public interface ImageDao {

    Image findImageById(int bookId);

    Image findImageByTmpFilename(String tmpFilename);

    void createImage(Image image);

    void deleteImageById(int bookId);

    void deleteImageByTmpFilename(String tmpFilename);
}
