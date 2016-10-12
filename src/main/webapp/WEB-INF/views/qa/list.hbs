<!--
  Created by IntelliJ IDEA.
  User: yong
  Date: 2016. 8. 18.
  Time: 오후 10:57
-->

{{# partial "content" }}

    <!-- contents -->
    <!-- qna top contents -->
    <div class="container qna-top-search">
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
            </div>
            <a href="#" class="form-control btn btn-primary">검색</a>
        </form>
        <div class="btn-group pull-right">
            <a href="/qa/total/list" class="btn btn-primary">총 질문({{qaTotalCount}})</a>
            <a href="/qa/wait_reply/list" class="btn btn-primary">답변을 기다리는 Q&A({{qaNotReplyedCount}})</a>
            {{#if isLogin}}
            <a href="/qa" class="btn btn-primary">Q&A</a>
            <a href="/qa/form" class="btn btn-primary">질문하기</a>
            {{/if}}
        </div>
    </div>
    <!--// qna top contents -->

    <!-- 메뉴 -->
    <div class="container contents-container top-buffer">
        <!-- keyword -->
        <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
            <div class="nav-tabs-header">분류키워드</div>
            <!-- 분류키워드 list start -->
            <div id="categorizeKeywordListArea"></div>
            <!-- 분류키워드 list end -->
        </div>
        <!--// lnb -->

        <div class="qa col-xs-10 col-sm-10 col-md-10 col-lg-10">
            {{#if TOTAL}}
            <!-- 전체 Q&A -->
            <div class="nav-tabs-header">전체 Q&A</div>
            {{/if}}
            {{#if WAIT_REPLY}}
            <!-- 답변을 기다리는 Q&A -->
            <div class="nav-tabs-header">답변을 기다리는 Q&A</div>
            {{/if}}
            <!-- Q&A list start -->
            <div id="list">
                {{> qa/template/_searchList}}
            </div>
            <!-- Q&A list end -->
            <input type="hidden" name="qaSearchType" value="{{qaSearchType}}">
            <button class="btn btn-default form-control btn-qa-more" type="button" id="qaMoreBtn">more</button>
            {{#unless qaList}}
                <div class="text-center" id="emptyQaMore">
                    작성된 Q&A가 존재하지 않습니다.
                </div>
            {{/unless}}

        </div>
    </div>

{{/partial}}

{{#partial "script-page"}}
    {{embedded "common/_keywordList"}}
    {{embedded "qa/template/_noData"}}
    {{embedded "qa/template/_searchList"}}
    <script src="/resource/app/js/keywordList/keywordList.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            Qa.init();
        });

        var Qa = {
            init : function(){
                KeywordList.renderKeywordList();
                QaPager.init(this.getListCount(), this.moreList);
            },
            getListCount : function(){
                return $('#list .best-qna-item').size();
            },
            getQaList : function(){
                var me = this;
                QaPager.showBtn();
                var qaSearchType = $('[name=qaSearchType]').val();
                $.get("/qa/"+ qaSearchType +"/getList", function (response) {
                    if(response.resultCode != 1) {
                        alert(response.comment);
                        return;
                    } else {
                        $('#list').html(me.makeQaList(response));
                        QaPager.loaded(response.data.length);
                    }
                });
            },
            getQaListByKeywordList : function(){
                var me = this;
                QaPager.showBtn();
                var selectedKeywordName = KeywordList.selectedKeywordName();
                var waitReplyType;
                var qaSearchType = $('[name=qaSearchType]').val();
                if(qaSearchType == 'WAIT_REPLY'){
                    waitReplyType = 'WAIT';
                }
                if(selectedKeywordName == ''){
                    me.getQaList();
                } else {
                    $.ajax({
                        url: "/qa/"+ qaSearchType +"/getListByKeyword",
                        type: "POST",
                        data: { keywordType : 'QA', qaSearchType : qaSearchType, keywordName : selectedKeywordName, waitReplyType : waitReplyType, dayType : 'ALL' },
                        success : function(response){
                            if(response.resultCode == 1) {
                                $('#list').html(me.makeQaList(response));
                                QaPager.loaded(response.data.length);
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
                }
            },
            moreList : function() {
                var lastQaId = $('#list .best-qna-item').last().data().qaId;
                var qaSearchType = $('[name=qaSearchType]').val();
                $.get("/qa/"+ qaSearchType +"/moreList",{keywordType : 'QA', lastQaId : lastQaId, keywordName : KeywordList.selectedKeywordName()}, function (response) {
                    if(response.resultCode != 1) {
                        alert(response.comment);
                        return;
                    } else {
                        var source = $('#qa-template-_searchList-hbs').html();
                        var template = Handlebars.compile(source);
                        var html = template(response);
                        $('#list').append(html);
                        QaPager.loaded(response.data.length);
                    }
                });
            },
            makeQaList : function (jsonData) {
                var source;
                if(jsonData.data.length == 0){
                    source = $('#qa-template-_noData-hbs').html();
                } else {
                    source = $('#qa-template-_searchList-hbs').html();
                }
                var template = Handlebars.compile(source);
                var html = template(jsonData);
                return html;

            }
        };

        var QaPager = {
            'PAGE_SIZE': 5,
            '$button': $('#qaMoreBtn'),
            '$emptyQaMore': $('#emptyQaMore'),
            'init': function(itemSize, callback) {
                if(itemSize > 0) {
                    this.showBtn();
                    this.bindLoading(callback);
                    this.$emptyQaMore.hide();
                } else {
                    this.hideBtn();
                }
            },
            'showBtn' : function() {
                this.$button.show();
            },
            'hideBtn' : function() {
                this.$button.hide();
            },
            'toggleLoadingText': function (isLoading) {
                if(isLoading) {
                    this.$button
                            .prop('disabled', true)
                            .html('로딩 중...');
                } else {
                    this.$button
                            .prop('disabled', false)
                            .html('more');
                }
            },
            'bindLoading': function(callbackAfterLoading) {
                var me = this;
                this.$button.on('click', function(){
                    me.toggleLoadingText(true);
                    callbackAfterLoading();
                });
            },
            'loaded': function(itemSize) {
                this.toggleLoadingText(false);
                if(itemSize < this.PAGE_SIZE) {
                    this.hideBtn();
                }
            }
        };

        KeywordList.bindKeywordListImplEvent = function () {
            Qa.getQaListByKeywordList();
        }

    </script>
{{/partial}}

{{> template/base}}