package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByHotDesc(Pageable pageable);

    List<Book> findAllByOrderByScoreDesc(Pageable pageable);

    List<Book> findBooksByTitleContaining(String keyword);
}
