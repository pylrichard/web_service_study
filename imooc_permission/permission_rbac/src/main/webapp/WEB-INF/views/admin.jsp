<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default">
    <div class="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <div class="navbar-header pull-left">
            <a href="/admin/page.do" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    管理员控制台
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <span class="user-info">
                            <small>欢迎,</small>
                            Admin
                        </span>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#"> <i class="ace-icon fa fa-cog"></i>
                                设置
                            </a>
                        </li>
                        <li>
                            <a href="profile.html"> <i class="ace-icon fa fa-user"></i>
                                个人资料
                            </a>
                        </li>

                        <li class="divider"></li>
                        <li>
                            <a href="/logout.page"> <i class="ace-icon fa fa-power-off"></i>
                                注销
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div id="sidebar" class="sidebar responsive">
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'fixed')
            } catch (e) {
            }
        </script>


        <ul class="nav nav-list">
            <li class="active">
                <a class="popstyle" href="/sys/user/page.do" target="_blank">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text"> 首页 </span>
                </a>
                <b class="arrow"></b>
            </li>
            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text"> 权限管理 </span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a class="popstyle" href="/sys/dept/dept.page" target="_blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            用户管理
                        </a>
                        <b class="arrow"></b>
                    </li>
                    <li class="">
                        <a class="popstyle" href="/sys/role/role.page" target="_blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            角色管理
                        </a>
                        <b class="arrow"></b>
                    </li>
                    <li class="">
                        <a class="popstyle" href="/sys/aclModule/acl.page" target="_blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            权限管理
                        </a>
                        <b class="arrow"></b>
                    </li>
                    <li class="">
                        <a class="popstyle" href="/sys/log/log.page" target="_blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            权限更新记录
                        </a>
                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>

            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text"> 系统管理 </span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>

                <b class="arrow"></b>

                <ul class="submenu">
                    <li class="">
                        <a class="popstyle" href="/config/config.page" target="_blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            全局配置管理
                        </a>
                        <b class="arrow"></b>
                    </li>
                </ul>
            </li>
        </ul>

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
               data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>

        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'collapsed')
            } catch (e) {
            }
        </script>
    </div>

    <div class="main-content">
        <iframe id="innerFrame" src="/sys/dept/dept.page" width="99%" style="min-height: 768px;"></iframe>
    </div>

    <a href="index.html#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse display">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>

<script>
    $(document).ready(function () {
        $(".popstyle").removeAttr("target");
        $(".popstyle").each(function () {
            var $this = $(this)
            tmp = $this.attr("href");
            $this.attr("data", tmp);
            $this.attr("href", "javascript:void(0)");
        })

        $(".popstyle").click(function () {
            var $this = $(this)
            $("iframe").attr(
                'src',
                $this.attr("data")
            );
        });

        $(".direct").click(function () {
            var $this = $(this)
            $("iframe").attr(
                'src',
                $this.attr("data-value")
            );
        });
    });
</script>
</body>
</html>

