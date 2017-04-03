<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>头条资讯</title>
    <meta name="viewport"
          content="width=device-width, minimum-scale=1.0, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" type="text/css" href="/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/styles/font-awesome.min.css">

    <link rel="stylesheet" media="all" href="/styles/style.css">

</head>
<body class="welcome_index">

<header class="navbar navbar-default navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                    data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a href="http://nowcoder.com/" class="navbar-brand logo">
                <h1>头条资讯</h1>
                <h3>你关心的才是头条</h3>
            </a>
        </div>

        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">

            <ul class="nav navbar-nav navbar-right">
                <li class=""><a href="http://nowcoder.com/explore">发现</a></li>

                <#if user??>
                    <li class="js-login"><a href="/logout">${user.name}</a></li>
                <#else>
                    <li class="js-login"><a href="javascript:void(0);">登陆</a></li>
                </#if>

            </ul>

        </nav>
    </div>
</header>


<div id="main">


    <div class="container" id="daily">
        <div class="jscroll-inner">
            <div class="daily">
        <#assign cur_date="">
        <#list vos as vo>
        <#if cur_date !=  vo.news.createdDate?string("yyyy-MM-dd")>
            <#if (vo_index > 0)>
                </div>
            </#if>
            <#assign cur_date = vo.news.createdDate?string('yyyy-MM-dd')>

            <h3 class="date">
                <i class="fa icon-calendar"></i>

                <span>头条资讯 &nbsp;  ${vo.news.createdDate?string("yyyy-MM-dd")}</span>
            </h3>

        <div class="posts">
        </#if>
            <div class="post">
                <div class="votebar">
                    <button class="click-like up" aria-pressed="false" title="赞同"><i class="vote-arrow"></i><span
                            class="count">${vo.news.likeCount}</span></button>
                    <button class="click-dislike down" aria-pressed="true" title="反对"><i class="vote-arrow"></i>
                    </button>
                </div>
                <div class="content" data-url="${vo.news.link}">
                    <div>
                        <img class="content-img" src="${vo.news.image}" alt="">
                    </div>
                    <div class="content-main">
                        <h3 class="title">
                            <a target="_blank" rel="external nofollow" href="${vo.news.link}">${vo.news.title}</a>
                        </h3>
                        <div class="meta">
                        ${vo.news.link}
                            <span>
                                            <i class="fa icon-comment"></i> ${vo.news.commentCount}
                                        </span>
                        </div>
                    </div>
                </div>
                <div class="user-info">
                    <div class="user-avatar">
                        <a href="/user/${vo.user.id}/"><img width="32" class="img-circle" src="${vo.user.headUrl}"></a>
                    </div>
                </div>

                <div class="subject-name">来自 <a href="/user/${vo.user.id}/">${vo.user.name}</a></div>
            </div>
        </#list>



        </div>
        </div>
    </div>
</div>

<#include "footer.html">
</body>
</html>