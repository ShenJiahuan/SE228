package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.BookSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSnapshotRepository extends JpaRepository<BookSnapshot, Integer> {
}
