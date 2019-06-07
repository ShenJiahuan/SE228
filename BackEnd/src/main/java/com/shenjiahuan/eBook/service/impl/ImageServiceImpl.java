package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.ImageDao;
import com.shenjiahuan.eBook.entity.BookSnapshot;
import com.shenjiahuan.eBook.entity.Image;
import com.shenjiahuan.eBook.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDao imageDao;

    @Override
    public Image findByBookId(int bookId) {
        return imageDao.findImageById(bookId);
    }

    @Override
    public String createImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = DigestUtils.md5DigestAsHex((fileName + new Date().getTime()).getBytes()) + ".jpg";
        File f = null;
        f = File.createTempFile("tmp", null);
        file.transferTo(f);
        f.deleteOnExit();
        byte[] fileContent = FileUtils.readFileToByteArray(f);
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);

        Image image = new Image();
        image.setImgBase64(encodedString);
        image.setTmpFilename(fileName);
        imageDao.createImage(image);
        return fileName;
    }

    @Override
    public void updateImage(BookSnapshot bookSnapshot) {
        Image image = imageDao.findImageByTmpFilename(bookSnapshot.getImgFileName());
        image.setBookId(bookSnapshot.getBookId());
        image.setTmpFilename(null);
        imageDao.deleteImageById(bookSnapshot.getBookId());
        imageDao.deleteImageByTmpFilename(bookSnapshot.getImgFileName());
        imageDao.createImage(image);
    }
}
