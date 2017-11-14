package com.bd.roncoo.book.shop.db.repository;

import com.bd.roncoo.book.shop.db.BaseTest;
import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.domain.EBook;
import com.bd.roncoo.book.shop.db.domain.PrintBook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private PrintBookRepository printBookRepository;

    /**
     * BookRepository继承CrudRepository
     */
    @Test
    public void testFindByName() {
        //findOne是@ManyToOne的方法，在获取Book对象信息的同时获取Category对象信息
        System.out.println(bookRepository.getClass().getName());
        /*
            findByName是自定义方法，此处会生成2个SQL语句
            在findByName添加@Query注解/@EntityGraph注解
         */
        Book book = bookRepository.findByName("战争与和平");
        System.out.println(book.getCategory().getName());
    }

    /**
     * 添加BookShopRepositoryImpl自定义实现
     */
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

    @Test
    public void testPersistenceContext1() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        /*
            Book与Category是多对一关系，生成的SQL语句中会与bs_category表进行关联
        	在bs_book表中添加(bs_id = 1, bs_name = "战争与和平", bs_category_bs_id = 1)
    		在bs_category表中添加(bs_id = 1, bs_name = "世界名著")
            持久化上下文可以理解为一个Map，在事务开始时创建，事务结束时销毁
            事务中可以把Domain对象关联到持久化上下文中
            如findOne查询得到的Book对象，与持久化上下文关联起来了，对开发者不可见
            事务提交时，JPA会执行脏检查，检查持久化上下文中保存的Domain对象数据与数据库表记录是否一致，不一致会更新数据库表记录
            事务回滚，不会执行上述操作
            事务中进行查询，优先在持久化上下文中查找，持久化上下文是一级缓存
            此处BaseTest设置了@Transactional，进行事务回滚，所以save不会生成update语句
        */
        Book book = bookRepository.findOne(1L);
        book.setName("美女与野兽");
        //saveAndFlush生成update语句，不是在commit时生成
        bookRepository.saveAndFlush(book);
        //在commit时生成update语句
        //bookRepository.save(book);
        //System.out.println("save success");
        transactionManager.commit(status);
    }

    @Test
    public void testPersistenceContext2() {
        /*
            findOne查询得到的对象会缓存在持久化上下文中(实现一级缓存功能)
            再次执行findOne会到持久化上下文中查询
            此处生成一个select语句
        */
        //bookRepository.findOne(1L);
        //bookRepository.findOne(1L);

        //此处生成2个select语句
        bookRepository.findAll();
        bookRepository.findAll();
    }

    @Test
    public void testHierarchy() {
        PrintBook printBook = new PrintBook();
        printBook.setName("1");
        bookRepository.save(printBook);

        EBook eBook = new EBook();
        eBook.setName("2");
        bookRepository.save(eBook);

        List<Book> books = bookRepository.findAll();
        books.stream().forEach(book -> System.out.println(book.getClass().getSimpleName()));

        List<PrintBook> printBooks = printBookRepository.findAll();
        printBooks.stream().forEach(book -> System.out.println(book.getClass().getSimpleName()));
    }
}