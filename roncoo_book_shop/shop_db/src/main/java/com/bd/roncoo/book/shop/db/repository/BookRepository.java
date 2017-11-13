package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository(T, ID)中泛型T代表操作的数据库表，ID代表主键类型
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
