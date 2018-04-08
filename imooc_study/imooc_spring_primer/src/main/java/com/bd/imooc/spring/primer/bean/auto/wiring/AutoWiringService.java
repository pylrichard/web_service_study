package com.bd.imooc.spring.primer.bean.auto.wiring;

public class AutoWiringService {
    private AutoWiringDAO autoWiringDAO;

    /**
     * 对应spring-auto-wiring.xml的default-autowire="constructor"
     */
    public AutoWiringService(AutoWiringDAO autoWiringDAO) {
        System.out.println("AutoWiringService");
        this.autoWiringDAO = autoWiringDAO;
    }

    /**
     * 对应spring-auto-wiring.xml的default-autowire="byName/byType"
     */
    public void setAutoWiringDAO(AutoWiringDAO autoWiringDAO) {
        System.out.println("setAutoWiringDAO");
        this.autoWiringDAO = autoWiringDAO;
    }

    public void say(String word) {
        this.autoWiringDAO.say(word);
    }
}
