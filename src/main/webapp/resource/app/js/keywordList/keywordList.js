var KeywordList = {
    renderKeywordList : function(){
        var me = this;
        $.ajax({
            url: "/common/findKeywordList",
            type: "POST",
            data: { keywordType : 'QA', keywordName : me.selectedKeywordName() },
            success : function(data){
                if(data.resultCode == 1) {
                    var source = $('#common-_keywordList-hbs').html();
                    var template = Handlebars.compile(source);

                    var html = template(data);
                    $('#categorizeKeywordListArea').html(html);
                    me.bindKeywordListEvent();
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
    selectedKeywordName : function(){
        var keywordName = $('#categorizeKeywordListArea').find('li.active').attr('data-keywordList-name');
        return keywordName;
    },
    bindKeywordListEvent : function(){
        var me = this;
        $('#categorizeKeywordListArea').find('li').click(function(){
            me.findKeywordList(this);
        });
    },
    findKeywordList : function(keywordListObj){
        var ulObj = $(keywordListObj).parent();
        ulObj.find('li').removeClass('active');
        var keywordListValue = keywordListObj.getAttribute('data-keywordList-name');
        if(keywordListValue == ''){
            ulObj.find('li[data-keywordList-name=""]').addClass('active');
        } else {
            ulObj.find('li[data-keywordList-name=' + keywordListValue + ']').addClass('active');
        }
        // keyword 해당되는 질문 전체 목록 List up
        // 화면 분할? 같이? 고민
        this.bindKeywordListImplEvent();
    },
    // 각 영역별 필요한 event 구현
    bindKeywordListImplEvent : function () {

    }
}