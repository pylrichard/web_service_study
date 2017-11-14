package com.bd.roncoo.book.shop.db.domain;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PrintBook extends Book {
    private Date printDate;

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }
}
