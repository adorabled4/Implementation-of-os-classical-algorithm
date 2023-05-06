package com.dhx.model;

/**
 * @author adorabled4
 * @className Constant
 * @date : 2023/05/06/ 10:00
 **/
public class Constant {

    /**
     * 初始化所有的页面数
     */
    public static final Element[]elements;

    static {
        elements=new Element[11];
        for (int i = 0; i <= 10; i++) {
            elements[i]=new Element(i+1,10000+i+1);
        }
    }
}
