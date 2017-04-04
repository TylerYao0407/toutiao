<#include "header.ftl">
 <div id="main">
        <div class="container">
            <div class="post detail">

                <div class="votebar">
                    <button class="click-like up" aria-pressed="false" title="赞同"><i class="vote-arrow"></i><span class="count">${news.likeCount}</span></button>
                    <button class="click-dislike down" aria-pressed="true" title="反对"><i class="vote-arrow"></i>
                    </button>
                </div>

                <div class="content" data-url="http://nowcoder.com/posts/5l3hjr">
                      <div class="content-img">
                          <img src="${news.image}?imageView2/1/w/100/h/80/" alt="">
                      </div>
                      <div class="content-main">
                          <h3 class="title">
                              <a target="_blank" rel="external nofollow" href="${news.link}">${news.title}</a>
                          </h3>
                          <div class="meta">
                              ${news.link}
                              <span>
                                  <i class="fa icon-comment"></i> ${news.commentCount}
                              </span>
                          </div>
                      </div>
                  </div>
            <div class="user-info">
                <div class="user-avatar">
                        <a href="http://nowcoder.com/u/125701"><img width="32" class="img-circle" src="${owner.headUrl}"></a>
                </div>
                </div>

                <div class="subject-name">来自 <a href="/user/${owner.id}">${owner.name}</a></div>
            </div>

            <div class="post-comment-form">
                <#if user??>
                <span>评论 (${news.commentCount})</span>
                <form method="post" action="/addComment">
                  <div class="form-group text required comment_content">
                      <label class="text required sr-only">
                          <abbr title="required">*</abbr> 评论
                      </label>
                      <input id="newsId" type="hidden" name="newsId" value="${news.id}"/>
                      <textarea rows="5" class="text required comment-content form-control" name="content" id="content"></textarea>
                  </div>
                  <div class="text-right">
                    <input type="submit" name="commit" value="提 交" class="btn btn-default btn-info">
                  </div>
                </form>
                <#else >
                <div class="login-actions">
                    <a class="btn btn-success" href="/?pop=1">登录后评论</a>
                </div>
                </#if>
            </div>

            <div id="comments" class="comments">
                <#list comments as commentvo>
                <div class="media">
                    <a class="media-left" href="/user/${commentvo.user.id}">
                        <img src="${commentvo.user.headUrl}">
                    </a>
                    <input id="commentId" type="hidden" name="commentId" value="${commentvo.comment.id}"/>
                    <div class="media-body">
                        <h4 class="media-heading"> <small class="date">${commentvo.comment.createdDate?string("yyyy-MM-dd")})</small></h4>
                        <div>${commentvo.comment.content}</div>
                        <#assign localId = localUserId>
                        <#if localId == commentvo.user.id >
                            <input class="pos_right btn btn-default btn-danger" id="delete" type="button"  value="删 除"/>
                        </#if>
                    </div>
                </div>
                </#list>
            </div>

            <script>
                $('#delete').on('click', function() {
                    $.post("/deleteComment",
                            {
                                "newsId":$("#newsId").val(),
                                "commentId":$("#commentId").val()
                            },
                            function () {
                                window.location.reload();
                            }
                    );
                })
            </script>
        </div>
    </div>
<style type="text/css">
    .pos_right
    {
        position:relative;
        left:650px;
    }
</style>
<#include "footer.ftl">