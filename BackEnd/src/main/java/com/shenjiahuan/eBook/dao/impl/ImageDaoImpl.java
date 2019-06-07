package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.ImageDao;
import com.shenjiahuan.eBook.entity.Image;
import com.shenjiahuan.eBook.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image findImageById(int bookId) {
        return imageRepository.findByBookId(bookId);
    }

    @Override
    public Image findImageByTmpFilename(String tmpFilename) {
        return imageRepository.findByTmpFilename(tmpFilename);
    }

    @Override
    public void createImage(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void deleteImageById(int bookId) {
        imageRepository.deleteImagesByBookId(bookId);
    }

    @Override
    public void deleteImageByTmpFilename(String tmpFilename) {
        imageRepository.deleteImagesByTmpFilename(tmpFilename);
    }
}
