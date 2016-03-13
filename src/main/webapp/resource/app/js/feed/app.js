var Feed = {
    'init' : function() {
        this.bindSave();
        this.bindFileAttachment();
        this.bindFileUploadModal();
        this.bindImageGallery();
    },
    'loadRecentlyList' : function() {
        FeedList.call("/feed/recentlyList", function(html, itemSize) {
            $('#feedList').html(html);
            FeedPager.init(itemSize, Feed.moreList);
        });
    },
    'moreList' : function() {
        FeedList.call("/feed/list?lastId=" + FeedList.getLastId(), function(html, itemSize) {
            $('#feedList').append(html);
            FeedPager.loaded(itemSize);
        });
    },
    'getId': function($target) {
        return $target.closest('.thread').data('feedThreadId');
    },
    "notHasAction": function($target) {
        return !$target.hasClass('hasActor');
    },
    'bindSave' : function() {
        var me = this;
        var $frm = $('#feedForm');
        $('#feedSave').click(function(){
            if(!me.checkValidate($frm)) {
                return;
            }
            $.post('/feed/save', $frm.serialize())
                .done(function(response){
                    if(FeedUtil.alertResponse(response)) {
                        return;
                    }
                    me.loadRecentlyList();
                    me.clearForm();
                    FeedFile.clear();
                }).fail(function(){
                    alert('Error saving feed');
                });
        });
    },
    'clearForm' : function() {
        $('#feedForm').find('textarea').val('');
    },
    'checkValidate' : function($frm) {
        if (!$frm.find('textarea[name=feedContent]').val().trim().length) {
            FeedUtil.alert('feed를 입력해주세요!');
            return false;
        }
        return true;
    },
    "bindFileAttachment": function () {
        $('#uploadfile').change(function(){
            $('#fileAttachmentInput').val($('#uploadfile').val());
            var $feedFileForm = $("#feedFileForm");
            $.ajax({
                url: "/common/uploadFile",
                type: "POST",
                data: new FormData($feedFileForm[0]),
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false,
                success: function (req) {
                    FeedFile.add(req.data);
                    $('#fileUploadModal').modal('hide');
                },
                error: function () {
                   FeedUtil.alert('업로드 중 에러가 발생했습니다. 파일 용량이 허용범위를 초과 했거나 올바르지 않은 파일 입니다.');
                }
            });
        });
    },
    'bindFileUploadModal': function() {
        $('#fileUploadModal').on('show.bs.modal', function () {
            $('#fileAttachmentInput').val('');
        });
    },
    "getFeedThreadIdByPopOver": function(el) {
        var popoverId = $(el).closest('.popover').attr('id');
        var $popOverBody =  $('.thread .config').find('[aria-describedby='+ popoverId +']');
        return this.getId($popOverBody);
    },
    "getFeedBodyByFeedThreadId": function(feedThreadId) {
        return $('#feedList li.thread[data-feed-thread-id="' + feedThreadId + '"]');
    },
    'removeItem': function(el, redirectUrl) {
        var me = this;
        var feedThreadId = me.getFeedThreadIdByPopOver(el);

        FeedUtil.confirm('FEED를 삭제하시겠습니까?', function(){
            var $feedBody = me.getFeedBodyByFeedThreadId(feedThreadId);
            $.post('/feed/' + feedThreadId + '/delete')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    FeedUtil.hidePopOver();
                    $feedBody.remove();

                    if(redirectUrl) {
                        location.href = redirectUrl;
                    }
                }).fail(function () {
                    alert('Error delete feed');
                });
        });
    },
    'modifyItem' : function(el) {
        var me = this;
        var feedThreadId =  me.getFeedThreadIdByPopOver(el);
        var $feedBody = me.getFeedBodyByFeedThreadId(feedThreadId);
        var $feedContents = $feedBody.find('.feed-contents-box');
        var $modify = $feedBody.find('.feed-modify');
        var $frm = $modify.find('.feed-modify-form');

        $modify.find('.modifyBtn').off('click').on('click', function () {
            if(!me.checkValidate($frm)) {
                return;
            }
            $.post('/feed/modify', $frm.serialize())
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    $modify.hide();
                    var contents = response.data.feedContent.replace(/(\r\n|\n|\r)/gm, '<br>');
                    $feedContents.html(contents).show();
                }).fail(function () {
                    alert('Error modify feed');
                });
        }).end().find('.cancelBtn').off('click').on('click', function () {
            $modify.hide();
            $feedContents.show();
        });

        $modify.show();
        $feedContents.hide();
        FeedUtil.hidePopOver();
    },
    'like': function(el) {
        var feedThreadId = this.getId($(el));
        var notHasAction = this.notHasAction($(el));
        var message = notHasAction ? '이 글을 좋아하시나요?' : '좋아요를 취소하시겠습니까?';

        FeedUtil.confirm(message, function(){
            $.post('/feed/' + feedThreadId + '/like')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    FeedUtil.toggleCount($(el), response.data.count, response.data.hasActor);
                }).fail(function () {
                    alert('Error like feed');
                });

        });
    },
    'claim': function(el) {
        var feedThreadId = this.getId($(el));
        var notHasAction = this.notHasAction($(el));
        var message = notHasAction ? '이 글을 신고하시겠습니까?' : '신고를 취소하시겠습니까?';

        FeedUtil.confirm(message, function(){
            $.post('/feed/' + feedThreadId + '/claim')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    FeedUtil.toggleCount($(el), response.data.count, response.data.hasActor);
                }).fail(function () {
                    alert('Error claim feed');
                });
        });
    },
    'bindImageGallery': function(){
        $('#feedList').off('click').delegate('*[data-toggle="lightbox"]', 'click', function(e) {
            e.preventDefault();
            return $(this).ekkoLightbox({
                always_show_close: true,
                loadingMessage: '<img src="/resource/images/loading-spinner.svg" class="feed-image-loader" />'
            });
        });
    }
};

var FeedFile = {
    'add': function(fileObj) {
        this.addPreviewToForm(fileObj);
        this.addHiddenInputToForm(fileObj);
    },
    'clear': function(){
        this.removePreviewInForm();
        this.removeHiddenTagsInForm();
    },
    'addPreviewToForm': function (obj) {
        var fullPath = '/download?path=' +obj.filePath + '/' + obj.savedName;

        if(obj.fileType == 'FILE') {
            var $file = ' <a href="' + fullPath + '" download><i class="fa fa-file-o"></i> ' + obj.realName + '</a>';
            $('#feedForm .attachmentArea .files').append($file);
        } else {
            var $image = ' <img width="190" class="img-thumbnail" src="' + fullPath + '">';
            $('#feedForm .attachmentArea .images').append($image);
        }
    },
    'addHiddenInputToForm': function (obj) {
        var idx = $('#feedForm input[name$=fileType]').size();
        var $input = $('<input />').attr('type', 'hidden');
        $('#feedForm').append($input.clone().attr('name', "feedFiles["+ idx + "].realName").val(obj.realName))
                .append($input.clone().attr('name', "feedFiles["+ idx + "].savedName").val(obj.savedName))
                .append($input.clone().attr('name', "feedFiles["+ idx + "].filePath").val(obj.filePath))
                .append($input.clone().attr('name', "feedFiles["+ idx + "].fileSize").val(obj.fileSize))
                .append($input.clone().attr('name', "feedFiles["+ idx + "].fileType").val(obj.fileType));
    },
    'removePreviewInForm': function() {
        $('#feedForm .attachmentArea .files').empty();
        $('#feedForm .attachmentArea .images').empty();
        $('#feedForm input[name^=feedFiles]').remove();
    },
    'removeHiddenTagsInForm': function() {
        $('#feedForm input[name^=feedFiles]').remove();
    }
};

var FeedReply = {
    'init' : function(){
        this.bindSave();
    },
    'getId' : function($target){
        return $target.closest('.reply-thread').data('feedReplyId');
    },
    'notHasAction': function($target) {
        return !$target.hasClass('hasActor');
    },
    'bindSave' : function() {
        var me = this;
        $('button[name=saveReply]').off('click').on('click',function () {
            var $frm = $(this).closest('form');
            var $repliesBody = $(this).closest('.thread').find('.feed-replies');

            if(!me.checkValidate($frm)) {
                return;
            }
            $.post('/feed/reply/save', $frm.serialize())
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    me.clearForm($frm);
                    me.loadItem($repliesBody, response.data);
                }).fail(function () {
                    alert('Error saving feed Reply');
                });
        });
    },
    'loadItem': function($target, data){
        var source = $('#feed-template-reply-hbs').html();
        var template = Handlebars.compile(source);
        var html = template(data);
        $target.append(html);
    },
    'checkValidate': function($frm) {
        if (!$frm.find('input[name=feedReplyContent]').val().trim().length) {
            FeedUtil.alert('댓글을 입력해주세요!');
            return false;
        }
        return true;
    },
    'clearForm': function($frm) {
        $frm.find('input[name=feedReplyContent]').val('');
    },
    'removeItem' : function(el) {
        var feedReplyId = this.getId($(el));
        var $target = $(el).closest('.reply-thread');

        FeedUtil.confirm('댓글을 삭제하시겠습니까?', function(){
            $.post('/feed/reply/' + feedReplyId + '/delete')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    $target.remove();
                }).fail(function () {
                    alert('Error delete feed Reply');
                });
        });
    },
    'like' : function(el) {
        var feedReplyId = this.getId($(el));
        var notHasAction = this.notHasAction($(el));
        var message = notHasAction ? '이 댓글을 좋아하시나요?' : '좋아요를 취소하시겠습니까?';

        FeedUtil.confirm(message, function(){
            $.post('/feed/reply/' + feedReplyId + '/like')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    FeedUtil.toggleCount($(el), response.data.count, response.data.hasActor);
                }).fail(function () {
                alert('Error like feed');
            });
        });
    },
    'claim' : function(el) {
        var feedReplyId = this.getId($(el));
        var notHasAction = this.notHasAction($(el));
        var message = notHasAction ? '이 댓글을 신고하시겠습니까?' : '신고를 취소하시겠습니까?';

        FeedUtil.confirm(message, function(){
            $.post('/feed/reply/' + feedReplyId + '/claim')
                .done(function (response) {
                    if (FeedUtil.alertResponse(response)) {
                        return;
                    }
                    FeedUtil.toggleCount($(el), response.data.count, response.data.hasActor);
                }).fail(function () {
                    alert('Error claim feed');
                });
        });
    }
};

var FeedUtil = {
    'bindPopOver': function() {
        $('.popover-config').popover({
            trigger: 'click',
            html: true,
            content: function () {
                return $('#popover-config-content').html();
            }
        });

        $('body').off('click').on('click', function (e) {
            $('[data-toggle="popover"]').each(function () {
                var isInsidePopover = !$(this).is(e.target)
                    && $(this).has(e.target).length === 0
                    && $('.popover').has(e.target).length === 0;
                if (isInsidePopover) {
                    $(this).popover('hide');
                }
            });
        });        
    },
    'hidePopOver': function() {
        $('.popover-config').popover('hide');
    },
    'toggleCount': function(el, count, hasActor) {
        el.find('span').html('(' + count + ')');
        if (hasActor) {
            el.addClass('hasActor');
        } else {
            el.removeClass('hasActor');
        }
    },
    'alert': function(message, confirmCallback) {
        $.alert({
            title: '확인',
            content: message,
            confirmButton: 'OK',
            confirmButtonClass: 'btn-primary',
            icon: 'fa fa-info',
            animation: 'zoom',
            confirm: confirmCallback
        });
    },
    'alertResponse': function(response) {
        var isErrorOccur = (response.resultCode != 1);
        if(isErrorOccur) {
            this.alert(response.comment, function(){
                if(response.resultCode == 10) {
                    location.href = '/loginPage';
                }
            });
        }

        return isErrorOccur;
    },
    'confirm': function(message, confirmCallback) {
        $.confirm({
            title: '확인',
            content: message,
            confirmButton: '확인',
            cancelButton: '취소',
            confirmButtonClass: 'btn-info',
            confirm: confirmCallback
        });
    }
};

var FeedPager = {
    'PAGE_SIZE': 5,
    '$button': $('#feedMoreBtn'),
    '$emptyFeedMore': $('#emptyFeedMore'),
    'init': function(itemSize, callback) {
        if(itemSize > 0) {
            this.showBtn();
            this.bindLoading(callback);
            this.$emptyFeedMore.hide();
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

var FeedList = {
    'call' : function(url, resultCallback) {
        $.get(url, function (response) {
            if(FeedUtil.alertResponse(response)) {
                return;
            }
            var source = $('#feed-template-list-hbs').html();
            var template = Handlebars.compile(source);
            var html = template(response);
            resultCallback(html, response.data.length);
        });
    },
    'getLastId': function() {
        return $('#feedList li.thread').last().data('feedThreadId');
    },
    'getSize': function(){
        return $('#feedList li.thread').size();
    }
};

var MyFeed = {
    'moreList' : function() {
        FeedList.call("/feed/myList?lastId=" + FeedList.getLastId(), function(html, itemSize) {
            $('#feedList').append(html);
            FeedPager.loaded(itemSize);
        });
    }
};