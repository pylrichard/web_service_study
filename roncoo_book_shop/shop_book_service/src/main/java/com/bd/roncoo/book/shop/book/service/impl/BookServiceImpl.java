package com.bd.roncoo.book.shop.book.service.impl;

import com.bd.roncoo.book.shop.common.aspect.ServiceLog;
import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.repository.BookRepository;
import com.bd.roncoo.book.shop.db.repository.specification.BookSpecification;
import com.bd.roncoo.book.shop.db.repository.support.AbstractDomain2InfoConverter;
import com.bd.roncoo.book.shop.db.repository.support.QueryResultConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service("bookService")
//@Transactional注解的类的public方法在一个事务中执行
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public BookInfo getInfo(Long id) {
        Book book = bookRepository.findOne(id);
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(book, bookInfo);

        /*
            在同一个类中调用query()，注解@Transactional不起作用
         */
//		query(new BookCondition(), new PageRequest(0, 20));

        return bookInfo;
    }

    /**
     * Pageable通过RPC传输到服务提供者，需要序列化(创建新对象)，创建一个新对象需要无参构造函数
     * Pageable实现类PageRequest没有无参构造函数，拷贝Spring源码到模块目录下，自行添加无参构造函数
     * <p>
     * PageImpl是返回的一页数据
     * PageRequest是分页请求
     * Sort是分页排序策略
     *
     * 添加@Transactional后，抛出异常整个事务会回滚，不会插入记录
     * 注解@Transactional要起作用，需要从外部类调用query()
     */
    @ServiceLog
    @Override
    public Page<BookInfo> query(BookCondition condition, Pageable pageable) {
        /*
            不要将Domain返回给用户，避免Domain与服务的输入/输出数据绑定，此处要进行转换
         */
        Page<Book> pageData = bookRepository.findAll(new BookSpecification(condition), pageable);

        return QueryResultConverter.convert(pageData, pageable, new AbstractDomain2InfoConverter<Book, BookInfo>() {
            /*
                自定义转换逻辑
             */
            @Override
            protected void doConvert(Book domain, BookInfo info) throws Exception {
                /*
                    info.content默认为content
                 */
                info.setContent("content");
            }
        });
    }

    @Transactional
    @Override
    public BookInfo create(BookInfo info) {
        /*
            通过代码进行事务控制
         */
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(definition);
        /*
            如果create()没有注解@Transactional，以下2条update语句分别在2个事务中执行
         */
        try {
            update(new BookInfo("a"));
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        transactionManager.commit(status);
        update(new BookInfo("b"));

        /*
            分布式事务控制
            可靠消息补偿机制，优点是开发相对简单，缺点是保持数据最终一致性，如果消息队列数据量较大，数据会有一段时间是不正确的

            下订单流程：
            createOrder() 创建订单记录
            updateStock() 更新库存
            updateUserBalance() 更新用户余额
            比如发现用户余额不够，会抛出异常，如果3次调用发生在单机，位于一个事务中，会进行回滚
            3次调用的服务是分布式的，每个服务有自己的事务
            updateUserBalance()所在服务会往消息队列中发送一条消息，表示某个订单扣除用户余额失败
            updateStock()所在服务要监听这类消息，在数据库中查询相应订单是否扣除了库存，如果执行了扣除则进行回滚
         */

        Book book = new Book();
        book.setName(info.getName());
        bookRepository.save(book);
        info.setId(book.getId());

        return info;
    }

    @Override
    public BookInfo update(BookInfo info) {
        /*
            抛出异常的事务会进行回滚
         */
        if (StringUtils.equals(info.getName(), "b")) {
            throw new RuntimeException("transaction test");
        }

        Book book = bookRepository.findOne(info.getId());
        book.setName(info.getName());
        bookRepository.save(book);

        return info;
    }

    @Override
    public void delete(long id) {
        bookRepository.delete(id);
    }
}
