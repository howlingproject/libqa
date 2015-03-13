<%--
  Created by IntelliJ IDEA.
  User: yong
  Date: 2014. 9. 22.
  Time: 오후 10:57
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:render template="/include/indicator"/>
<!-- qna top contents -->
<div class="container qna-top-search">
    <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
        </div>
        <a href="#" class="form-control btn btn-primary">검색</a>
    </form>

    <div class="btn-group pull-right" data-toggle="buttons">
        <a href="#" class="btn btn-primary">총 질문(123)</a>
        <a href="#" class="btn btn-primary">총 답변(123)</a>
        <a href="#" class="btn btn-primary">총 키워드(123)</a>
        <a href="#" class="btn btn-primary">답변을 기다리는 Q&A(123)</a>
        <g:link controller="qa" action="create" class="btn btn-primary">질문하기</g:link>
    </div>
</div>
<!--// qna top contents -->

<!-- 메뉴 -->
<div class="container contents-container top-buffer">
    <!-- keyword -->
    <div class="col-md-2">
        <div class="nav-tabs-header">분류키워드</div>
        <!-- 분류키워드 list start -->
        <div id="categorizeKeywordListArea"></div>
        <!-- 분류키워드 list end -->
    </div>
    <!--// lnb -->

    <div class="col-md-10">
        <!-- 내가 작성한 Q&A -->
        <div class="nav-tabs-header">내가 작성한 Q&A</div>

        <!-- 내가 작성한 Q&A list start -->
        <div id="myWriteQaListArea"></div>
        <!-- 내가 작성한 Q&A list end -->
        <div class="my-qna-item">
            <a class="pull-left" href="#">
                <div class="user-profile">
                    <img alt="avatar" class="profile-image" src="../images/avatar.png"/>
                </div>
            </a>

            <div class="row">
                <div class="col-xs-9 col-sm-9 col-md-9 col-lg-10">
                    <div class="media-body">
                        <div class="media-heading">
                            <span>2014-04-05 12:12</span>
                            <span class="my-qna-writer">
                                <div class="user-profile-xs">
                                    <span class="nickname">Yion</span>
                                </div>
                            </span>

                            <div class="pull-right">
                                <span class="label label-primary">Spring</span>
                                <span class="label label-success">JAVA</span>
                            </div>

                            <div class="qna-title">
                                <span>Spring 정복하기!</span>
                            </div>

                            <div>
                                Spring 서버사이드 공간 > Spring AOP 위키에 질문을 올렸습니다.- 내용이 잘 이해가...
                                Spring 서버사이드 공간 > Spring AOP 위키에 질문을 올렸습니다.- 내용이 잘 이해가...
                                <span class="badge">6</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 my-qna-rating pull-right">
                    <button class="btn btn-sm btn-default btn-sm-fixed pull-right">조회 33</button>
                    <button class="btn btn-sm btn-default btn-sm-fixed pull-right">추천 1</button>
                </div>
            </div>
        </div>
        <!--// 내가 작성한 Q&A -->

        <div class="top-buffer">&nbsp;</div>

        <!-- 내가 답변한 Q&A -->
        <div class="nav-tabs-header">내가 답변한 Q&A</div>

            <!-- 내가 답변한 Q&A list start -->
            <div id="myReplyQaListArea"></div>
            <!-- 내가  답변한 Q&A list end -->
            <div class="my-qna-item">
            <a class="pull-left" href="#">
                <div class="user-profile">
                    <img alt="avatar" class="profile-image" src="../images/avatar.png"/>
                </div>
            </a>

            <div class="row">
                <div class="col-xs-9 col-sm-9 col-md-9 col-lg-10">
                    <div class="media-body">
                        <div class="media-heading">
                            <span class="media">2014-04-05 12:12</span>
                            <span class="my-qna-writer">
                                <div class="user-profile-xs">
                                    <span class="nickname">Yion</span>
                                </div>
                            </span>

                            <div class="pull-right">
                                <span class="label label-primary">Spring</span>
                                <span class="label label-success">JAVA</span>
                            </div>

                            <div class="qna-title">
                                <span>Spring 정복하기!</span>
                            </div>

                            <div>
                                Spring 서버사이드 공간 > Spring AOP 위키에 질문을 올렸습니다.- 내용이 잘 이해가...
                                Spring 서버사이드 공간 > Spring AOP 위키에 질문을 올렸습니다.- 내용이 잘 이해가...
                                <span class="badge">6</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 my-qna-rating pull-right">
                    <button class="btn btn-sm btn-default btn-sm-fixed pull-right">조회 33</button>
                    <button class="btn btn-sm btn-default btn-sm-fixed pull-right">추천 1</button>
                </div>
            </div>
        </div>

    <!--// 내가 답변한 Q&A -->
</div>

<script type="text/javascript">
    $(function(){
        Qa.renderKeywordList();
        Qa.renderMyWriteQaList();
        Qa.renderMyReplyQaList();
    });

    var Qa = {
        renderKeywordList : function(){
            $.ajax({
                url: '${createLink(controller:'keywordList',action:'categorizeKeywordList')}',
                data : {keywordType : 'QA'},
                failure: function(){ },
                success: function(response) {
                    $('#categorizeKeywordListArea').html(response);
                }
            });
        },
        renderMyWriteQaList : function(){
//            var test = $('#categorizeKeywordListArea').find('li.active').attr('value');
            $.ajax({
                url: '${createLink(controller:'qa',action:'renderMyQaList')}',
                data : {keywordType : 'QA', keywordName : 'JAVA', viewType : 'myWrite'},
                failure: function(){ },
                success: function(response) {
                    $('#myWriteQaListArea').html(response);
                }
            });
        },
        renderMyReplyQaList : function(){
            $.ajax({
                url: '${createLink(controller:'qa',action:'renderMyQaList')}',
                data : {keywordType : 'QA', keywordName : 'JAVA', viewType : 'myReply'},
                failure: function(){ },
                success: function(response) {
                    $('#myReplyQaListArea').html(response);
                }
            });
        }
    };
</script>
