{{# partial "content" }}
    <!--  메뉴  -->
    <div class="container-fluid">
        <div class="col-sm-12">
            <div class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" data-linkval="searchText" class="form-control" placeholder="Search">
                </div>
                <span class="form-control btn btn-primary" data-link="/wiki/search" data-linktype="search">
                    위키검색
                </span>
            </div>
            <div class="btn-group pull-right">
                <a class="form-control btn btn-primary" href="/wiki/write">
                    위키생성
                </a>
            </div>
        </div>


        <!-- contents -->
        <div class="col-sm-12 top-buffer wiki-main">
            <!-- 하단 컨텐츠 -->
            <div class="col-md-12 col-lg-12">
                <!-- 전체 키워드 선택 -->
                <div class="col-md-2" style="padding-left: 0px;" id="categorizeKeywordListArea">
                    <div class="well">전체 키워드 선택</div>
                    <ul class="nav nav-pills nav-stacked" >

                    </ul>
                </div>
                <!--// 전체 키워드 선택 -->

                <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">

                    <div class="nav-tabs-header">{{listTitle}}</div>

                    {{#each listWiki}}
                        <div class="media qna-item">
                            <a class="pull-left" style="cursor: pointer">
                                <div class="user-profile">
                                    <img src="/imageView?path={{user.userImage}}" class="profile-image" alt="avatar" onerror='this.src="/resource/images/avatar.png"'>
                                </div>
                            </a>
                            <div class="row">
                                <div class="col-xs-9 col-sm-9 col-md-9">
                                    <div class="media-body">
                                        <div class="media-heading">
                                            <strong><a class="form" href="/wiki/{{wiki.wikiId}}">{{wiki.title}}</a></strong>
                                            <span class="my-qna-writer pull-right">
                                                <div class="user-profile-xs">
                                                    <span class="nickname"><i class="fa fa-pencil-square-o"></i> {{user.insertUserNick}}</span>
                                                </div>
                                            </span>
                                            <br>
                                            {{#each keywords}}
                                                {{#keywordBadge keywordName}}{{/keywordBadge}}
                                            {{/each}}
                                        </div>
                                        <div>
                                            {{{subString (htmlDelete wiki.contents) 0 80 }}}
                                            <span class="pull-right"><i class="fa fa-clock-o"></i> {{formatDate wiki.insertDate "yyyy.MM.dd HH:mm"}}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-1 col-sm-1 col-md-1">
                                    <ul class="nav nav-pills nav-stacked ">
                                        <li><button class="btn btn-sm btn-default btn-sm-fixed">댓글 {{wiki.replyCount }}</button></li>
                                        <li><button class="btn btn-sm btn-default btn-sm-fixed">추천 {{wiki.likeCount }}</button></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    {{/each}}

                    {{#if pageIdx}}
                    <div class="text-center">
                        <ul class="pagination">
                            {{#ifIntCont page ">=" 6 }}
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            {{/ifIntCont}}
                            {{#each pageIdx}}
                                <li><a href="#">{{this}}</a></li>
                            {{/each}}
                            {{#ifIntCont pages "<" allPage }}
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            {{/ifIntCont}}
                        </ul>
                    </div>
                    {{/if}}
                </div>
                <!--// 전체 공간 -->
            </div>
        </div>
        <!--// contents -->
    </div>
    <input type="hidden" id="page" value="{{page}}" />
{{/partial}}

{{#partial "script-page"}}
    {{embedded "common/_keywordList"}}
    <script type="text/javascript">
        $(document).ready(function() {
            Wiki.renderKeywordList('{{selectKeywordName}}');
        });
        var Wiki = {
            renderKeywordList : function(keywordName){
                $.ajax({
                    url: "/common/findKeywordList",
                    type: "POST",
                    data: { keywordType : 'WIKI', keywordName : Wiki.selectedKeyword(keywordName) },
                    success : function(data){
                        if(data.resultCode == 1) {
                            var source = $('#common-_keywordList-hbs').html();
                            var template = Handlebars.compile(source);

                            var html = template(data);
                            $('#categorizeKeywordListArea').html(html);
                            Wiki.bindKeywordListEvent();
                        }
                    },
                    error : function(req){
                        // Handle upload error
                        alert('req : ' + req);
                        console.log('req : ' + req.status);
                        console.log('req : ' + req.readyState);

                        alert('에러가 발생하였습니다. 에러코드 [' + req.status + ']');
                    }
                });
            },
            selectedKeyword : function(keywordName){
                if(!keywordName){
                    keywordName = '';
                }
                return keywordName;
            },
            bindKeywordListEvent : function(){
                $('#categorizeKeywordListArea').find('li').click(function(){
                    Wiki.renderKeywordList(this.getAttribute('value'));
                });
            }
        }
    </script>
{{/partial}}

{{> template/base}}
