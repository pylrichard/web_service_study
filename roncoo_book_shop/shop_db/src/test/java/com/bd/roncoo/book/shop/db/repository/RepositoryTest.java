package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.BaseTest;
import com.bd.roncoo.book.shop.db.domain.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RepositoryTest extends BaseTest {
    /**
     * 依赖注入，BookRepository继承Repository接口，Spring会生成bookRepository代理对象
     * CrudRepository的@NoRepositoryBean通知Spring不要生成代理对象
     */
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByName() {
        System.out.println(bookRepository.getClass().getName());
        bookRepository.findByName("战争与和平");
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("战争与和平");
        /*
            save根据Book对象Id(主键)是否有值决定执行insert还是update
            BaseTest的@Transactional保证测试用例生成的SQL语句不会commit
        */
        bookRepository.save(book);
    }

    @Test
    public void testFindOne() {
        //Book与Category是多对一关系，生成的SQL语句中会与bs_category表进行关联
        bookRepository.findOne(1L);
    }

    @Test
    public void testExists() {
        System.out.println(bookRepository.exists(1L));
    }

    @Test
    public void testFindAll() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        //将ids放在生成的SQL语句的in子句中
        bookRepository.findAll(ids);
    }

    @Test
    public void testCount() {
        System.out.println(bookRepository.count());
    }
}