package com.bd.roncoo.book.shop.book.service.impl;

import com.bd.roncoo.book.shop.common.dto.BookCondition;
import com.bd.roncoo.book.shop.common.dto.BookInfo;
import com.bd.roncoo.book.shop.common.service.BookService;
import com.bd.roncoo.book.shop.db.domain.Book;
import com.bd.roncoo.book.shop.db.repository.BookRepository;
import com.bd.roncoo.book.shop.db.repository.specification.BookSpecification;
import com.bd.roncoo.book.shop.db.repository.support.AbstractDomain2InfoConverter;
import com.bd.roncoo.book.shop.db.repository.support.QueryResultConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bookService")
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookInfo getInfo(Long id) {
        Book book = bookRepository.findOne(id);
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(book, bookInfo);

        return bookInfo;
    }

    /**
     * Pageable通过RPC传输到服务提供者，需要序列化(创建新对象)，创建一个新对象需要无参构造函数
     * Pageable实现类PageRequest没有无参构造函数，拷贝Spring源码到模块目录下，自行添加无参构造函数
     * <p>
     * PageImpl是返回的一页数据
     * PageRequest是分页请求
     * Sort是分页排序策略
     */
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

    @Override
    public BookInfo create(BookInfo info) {
        Book book = new Book();
        book.setName(info.getName());
        bookRepository.save(book);
        info.setId(book.getId());

        return info;
    }

    @Override
    public BookInfo update(BookInfo info) {
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
