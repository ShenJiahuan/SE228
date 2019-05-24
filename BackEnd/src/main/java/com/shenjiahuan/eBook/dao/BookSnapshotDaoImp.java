package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.BookSnapshot;
import com.shenjiahuan.eBook.repository.BookSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class BookSnapshotDaoImp implements BookSnapshotDao {

    @Autowired
    BookSnapshotRepository bookSnapshotRepository;

    public void createOrUpdateBookSnapshot(BookSnapshot bookSnapshot) throws IOException {
        bookSnapshotRepository.save(bookSnapshot);
    }
}
