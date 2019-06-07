package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.entity.BookSnapshot;
import com.shenjiahuan.eBook.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image findByBookId(int bookId);

    String createImage(MultipartFile file) throws IOException;

    void updateImage(BookSnapshot bookSnapshot);
}
