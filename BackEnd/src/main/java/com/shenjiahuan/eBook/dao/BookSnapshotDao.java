package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.BookSnapshot;

import java.io.IOException;

public interface BookSnapshotDao {
    void createOrUpdateBookSnapshot(BookSnapshot bookSnapshot) throws IOException;
}
