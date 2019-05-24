package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.entity.BookSnapshot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderBySnapshot_HotDesc(Pageable pageable);

    List<Book> findAllByOrderBySnapshot_ScoreDesc(Pageable pageable);

    List<Book> findBooksBySnapshot_TitleContaining(String keyword);
}
