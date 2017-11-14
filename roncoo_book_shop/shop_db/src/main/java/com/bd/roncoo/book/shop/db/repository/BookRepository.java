package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.support.BookShopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository(T, ID)中泛型T代表操作的数据库表，ID代表主键类型
 * Specification<T>只有一个方法
 * Root<T>是对泛型T的封装，CriteriaQuery<?>抽象整个SQL语句，组合各个段(select段，where段)
 * CriteriaBuilder创建Predicate(封装过滤条件，相当于where子句)
 * Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
 */
public interface BookRepository extends BookShopRepository<Book> {
    /**
     * 声明静态查询很方便，但方法名会很长
     * 不能声明update、delete、count、sum
     * findBy+关键字，属性名字要大写
     * 注解@Query("from Book b left join b.category where b.name = ?1")
     * 注解@EntityGraph(attributePaths = {"category"})，如果有多个类似findByName的方法有同样的这种注解，业务逻辑一但变化需要多次修改
     * 采用如下方法，只需要在Book类修改注解即可
     */
    @EntityGraph(value = "Book.fetch.category.and.author")
    Book findByName(String name);

    List<Book> findByNameAndCategoryName(String bookName, String categoryName);

    List<Book> findByNameLike(String name);

    /**
     * JPQL会被编译成针对具体DB的SQL，屏蔽不同DB的差异，针对具体类(Book)编写，不是针对数据库表(bs_book)
     * 没有写join，但b.category.name表示要进行关联
     * 注解@Query("from Book b where b.name like ?1 and b.category.name = ?2 order by b.name desc")
     * 也可以显式进行关联，并指定查询得到的字段，这里指定bs_book表的字段
     * 注解@Query("select b from Book b left join b.category c where b.name like ?1 and c.name = ?2 order by b.name desc")
     * Page<Book> findBooks(String bookName, String categoryName, Pageable sort);
     * 可以指定count，返回值为long编译会报错
     */
    @Query("select count(b.id) from Book b left join b.category c where b.name like ?1 and c.name = ?2 order by b.name desc")
    Page<Book> findBooks(String bookName, String categoryName, Pageable sort);

    /**
     * 注解@Query(value = "update Book b set b.name = ?1 where b.id = ?2", nativeQuery = false)
     * nativeQuery为true表示原生SQL
     */
    @Query(value = "select * from bs_book where bs_id = ?2", nativeQuery = true)
    @Modifying
    int updateBookInfo(String bookName, Long bookId);
}
