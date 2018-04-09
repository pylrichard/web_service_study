package com.bd.roncoo.book.shop.db.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 注解@MappedSuperclass标记的父类被子类继承，修改父类的成员，子类对应的表也随之修改
 */
@MappedSuperclass
@Getter
@Setter
public class DomainImpl {
    /**
     * 注解@Id表示主键
     *
     * 注解@GeneratedValue指定主键生成策略
     * GeneratedValue.strategy()默认为GenerationType.AUTO，根据数据库在TABLE/SEQUENCE/IDENTITY之间选择策略
     * MySQL就使用IDENTITY，既是Auto Increment
     * JPA会检查数据库对应的表/字段是否存在，如果存在则新设置不会生效，生效则需要在数据库中删除对应的表/字段
     * Long在表中转换为BIGINT
     */
    @Id
    @GeneratedValue(generator = "sequenceGenerator")
    //此继承策略适用于继承体系小(子类数量少)，父类和子类差异大的情况
    @GenericGenerator(name = "sequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    //指定sequence名称，生成全局主键，保证各张表的主键不冲突
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "ID_SEQUENCE"),
                    //指定起始数字
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000"),
                    //指定增长步长
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
                    //指定额外参数，进行池化，提高性能
                    @Parameter(name = SequenceStyleGenerator.OPT_PARAM, value = "pooled"),
            }
    )
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime = new Date();
}
