package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ImageRepository extends MongoRepository<Image, Integer> {

    @Query("{ 'book_id' : ?0 }")
    Image findByBookId(int bookId);

    @Query("{ 'tmp_filename' : ?0 }")
    Image findByTmpFilename(String tmpFilename);

    @Query(value="{ 'book_id' : ?0 }", delete = true)
    void deleteImagesByBookId(int bookId);

    @Query(value="{ 'tmp_filename' : ?0 }", delete = true)
    void deleteImagesByTmpFilename(String tmpFileName);
}
