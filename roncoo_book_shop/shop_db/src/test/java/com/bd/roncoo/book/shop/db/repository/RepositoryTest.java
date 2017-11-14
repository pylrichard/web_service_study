package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.BaseTest;
import com.bd.roncoo.book.shop.db.domain.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest extends BaseTest {
    /**
     * 依赖注入，BookRepository继承Repository接口，Spring会生成bookRepository代理对象
     * CrudRepository的@NoRepositoryBean通知Spring不要生成代理对象
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * BookRepository继承CrudRepository
     */
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
    public void testFindAll1() {
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

    /**
     * BookRepository继承PagingAndSortingRepository
     */
    @Test
    public void testFindAll2() {
        /*
            SQL语句包含order by子句
            bookRepository.findAll(new Sort(Direction.DESC, "name", "id"));
            按name降序排，按id升序排，Order(String property)中DEFAULT_DIRECTION默认是Direction.ASC，可以不写
        */
        bookRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "name"), new Sort.Order("id")));
    }

    @Test
    public void testFindAll3() {
        /*
            Open Type Hierarchy查看继承树，使用实现类PageRequest创建对象
            从第1页开始，每页取10条记录
        */
        Pageable req = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "name"));
        //返回值Page<Book> books的getContent()返回List<Book>，比如表中共有15条记录，每页取10条记录，查第1页List有10条，查第2页List有5条
        bookRepository.findAll(req);
    }

    /**
     * BookRepository继承JpaRepository
     */
    @Test
    public void testFindAll4() {
        Pageable req = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "name"));
        Book book = new Book();
        book.setName("战争与和平");
        Example<Book> example = Example.of(book);
        bookRepository.findAll(example, req);
    }

    @Test
    public void testFindAll5() {
        Pageable req = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "name"));
        Book book = new Book();
        book.setName("战争");
        /*
            生成的SQL语句包含like
            不支持group by，or，日期大于小于，between
        */
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Book> example = Example.of(book, matcher);
        bookRepository.findAll(example, req);
    }

    @Test
    public void testFindByNameAndCategoryName() {
        bookRepository.findByNameAndCategoryName("战争与和平", "世界名著");
    }

    @Test
    public void testFindByNameLike() {
        bookRepository.findByNameLike("战争%");
    }

    @Test
    public void testFindBooks() {
        bookRepository.findBooks("战争%", "世界名著", new PageRequest(0, 10));
    }

    @Test
    public void testUpdateBookInfo() {
        bookRepository.updateBookInfo("战争", 1L);
    }

    @Test
    public void testSpecification() {
        Specification<Book> spec = new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                /*
                    Predicate实现类似组合模式，自身既是容器，也是实体
                    比如p1和p2是实体，通过and封装在p3中，p3相当于是一个容器
                    "战争与和平"和"世界名著"在应用中由前端传入
                    构建动态查询时根据相应参数是否有值来决定是否生成相应过滤条件
                 */
                Predicate p1 = cb.equal(root.get("name"), "战争与和平");
                Predicate p2 = cb.equal(root.get("category").get("name"), "世界名著");
                //生成的SQL语句关联bs_book表和bs_category表
                Predicate p3 = cb.and(p1, p2);

                //指定查询结果包含bs_category表记录和join类型
                root.fetch("category", JoinType.LEFT);

                return p3;
            }
        };

        bookRepository.findOne(spec);
    }
}