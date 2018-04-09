package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
public class PrintBook extends Book {
    private Date printDate;
}
