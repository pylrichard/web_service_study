package com.bd.imooc.spring.bean.annotation.jsr;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 注解@Named与@Component等效，见55~62.png
 */
//@Service
@Named
public class JsrService {
    //	@Resource
//	@Inject
    private JsrDAO jsrDAO;

    /**
     * JavaEE提供@Resource，适用于多实现，基于byName
     * Spring提供@Autowired，适用于单实现，基于byType
     * 注解@Inject等效于@Autowired，见59.png
     * 假如IoC容器中有多个JsrDAO，通过@Named指定
     */
//	@Resource
    //引入javax.inject包
    @Inject
    public void setJsrDAO(@Named("jsrDAO") JsrDAO jsrDAO) {
        this.jsrDAO = jsrDAO;
    }

    /**
     * 初始化回调和销毁回调不常使用，见57.png
     */
    @PostConstruct
    public void init() {
        System.out.println("JsrService init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("JsrService destroy.");
    }

    public void save() {
        jsrDAO.save();
    }
}
