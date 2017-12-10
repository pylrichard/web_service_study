# 自定义JSP标签自动完成对页面按钮做权限拦截处理

## 问题

系统已经支持了指定请求的权限控制，能否在页面加载时对无权限访问的按钮或者菜单进行隐藏，每次点击后提示无权限操作，用户体验不好

## 解决方案

自定义一套jsp页面的标签，校验当前用户是否有某个权限点的访问权限作为标签的后台处理逻辑

以下是核心代码，如获取当前用户id、判断一个用户是否有权访问某个权限点等需要自行实现

首先定义一个后台处理标签渲染逻辑的类，继承RequestContextAwareTag类，RequestContextAwareTag继承了TagSupport，增加获取RequestContext的类方便获取当前request等内容

```java
package com.xxx.permission.tag;

import com.google.common.base.Splitter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import java.util.List;

public class AclCheckTag extends RequestContextAwareTag {
    /**
     * 权限标识码
     */
    @Getter
    @Setter
    private String code;
    
    public AclCheckTag() {
        super();
    }
    
    @Override
    protected int doStartTagInternal() throws Exception {
        if(needShowButton()) {
            //Tag的关键字，代表将标签里的内容输出到存在的输出流中
            return EVAL_BODY_INCLUDE;
            
        }
        //Tag的关键字，代表跳过开始和结束标签之间的代码
        return SKIP_BODY;
        
        /**
         * 其他关键字说明
         * SKIP_PAGE：忽略剩下的页面
         * EVAL_PAGE：继续执行下面的页
         *
         * 对于控制按钮是否有权限展示，只需要EVAL_BODY_INCLUDE和SKIP_BODY即可
         */
    }
    
    private boolean needShowButton() {
        Integer currentUserId = getCurrentLoginUserId();
        if (currentUserId == null) {
            //获取不到登录用户，没有权限，不展示按钮
            return false;
        }
        if (StringUtils.isEmpty(code)) {
            //没有实际传入权限点，按照有权限来处理，也可以根据实际需要不允许访问
            return true;
        }
        //支持逗号分隔多个权限点
        List<String> codeList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(code);
        for (String aclCode : codeList) {
            if (hasAcl(aclCode, currentUserId)) {
                /*
                    这里假设拥有一个权限就代表有权限
                    也可以调整为需要拥有所有权限才代表有权限
                */
                return true;
            }
        }
      	
        return false;
    }
    
    private Integer getCurrentLoginUserId() {
        //TODO 从上下文中获取当前登录的用户ID
        return 1;
    }
    
    private boolean hasAcl(String aclCode, Integer userId) {
        //TODO 获取Spring上下文，判断userId对应的用户是否可访问aclCode对应的权限点
        return true;
    }
    
    @Override
    public void release() {
        super.release();
        code = null;
    }
}
```

定义JSP页面使用的标签acl-taglib.tld，存放在/WEB-INF/tld路径下，核心是指出标签逻辑处理的类及参数说明

```xml
<?xml version="1.0" encoding="UTF-8"?>
<taglib xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
        http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"
        version="2.0">
    
    <description>权限校验</description>
    <tlib-version>1.0</tlib-version>
    <short-name>acl</short-name>
    <uri>http://xxx.com</uri>
    <tag>
        <description>判断某个用户是否具有某个权限的按钮或链接</description>
        <name>checkPermission</name>
        <tag-class>com.xxx.tag.AclCheckTag</tag-class>
        <body-content>JSP</body-content>
        <attribute>
            <description>对应权限点的标识</description>
            <name>code</name>
            <required>true</required>
            <rtexprvalue>true</rtexprvalue>
            <type>java.lang.String</type>
        </attribute>
    </tag>
</taglib>
```

在项目的web.xml里进行taglib的配置，注意taglib-location要和实际放置的位置一致

```xml
<jsp-config>
    <taglib>
        <taglib-uri>/acl</taglib-uri>
        <taglib-location>/WEB-INF/tld/acl-taglib.tld</taglib-location>
    </taglib>
</jsp-config>
```

## 使用

使用标签时，JSP页面会要求引入对应的taglib，这里需要注意uri的对应

给指定的按钮配置标签和权限点，这里假设按钮对应的权限点标识是ACL0001

```jsp
<%@taglib prefix="acl" uri="/acl" %>

<acl:checkPermission code="ACL0001">
    <input type="submit" name="submit">更新</input>
</acl:checkPermission>
```

除了可以放置按钮外，任何HTML的片段都可以放到标签里，这里后台逻辑只关心当前用户是否有权访问配置的权限点，有权访问时才会输出标签里的HTML片段。使用时注意别把HTML片段对应的权限点标识搞错就可以了

## 延伸

如今越来越多的系统采用前后端分离的架构，这时该如何在页面渲染时不展示无权操作的按钮和链接呢

首先后台提供一个根据权限点标识(要支持多个权限点标识一起校验)检查当前用户是否有权访问的接口

前台对于需要做权限校验的按钮先设置为不展示，然后在页面加载时发送请求到后台判断相关的权限标识是否可访问

前台拿到结果后对于有权访问的按钮和菜单进行展示，无权访问的直接移除对应的dom元素

这样效果就和权限标签做的事情是一样的，只是多了个后台请求，这个请求即使被使用的人发现修改也没什么影响，因为每个请求都会权限系统拦截