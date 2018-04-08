<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html lang="zh-CN">
<%
    String ret = request.getParameter("ret");
    if (StringUtils.isNotBlank(ret)) {
        ret = URLEncoder.encode(ret);
    } else {
        ret = "";
    }
%>
<head>
    <meta charset="utf-8">
    <title>登录</title>
</head>

<body>
<div class="container">
    <form class="form-signin" action="/login.page?ret=<%=ret%>" method="post">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputUserName" class="sr-only">邮箱/手机号</label>
        <input type="text" id="inputUserName" class="form-control" placeholder="Email/Telephone" name="username"
               required autofocus value="${username}">
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required>
        <div class="checkbox" style="color: red;">${error}</div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>
</div>
</body>
</html>
