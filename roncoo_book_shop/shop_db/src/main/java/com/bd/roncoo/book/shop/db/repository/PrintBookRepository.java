package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.domain.PrintBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrintBookRepository extends JpaRepository<PrintBook, Long>, JpaSpecificationExecutor<PrintBook> {}
