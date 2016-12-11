
var summertnote_sub = (function(){
    var summertnote_sub = {};
    summertnote_sub.test = function(){
      alert("test");
    };
    summertnote_sub.sendImage = function(file, el){
        var form_data = new FormData();
        form_data.append('uploadfile', file);
        $.ajax({
            url: "/common/uploadFile",
            method: "POST",
            type: "POST",
            data: form_data,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function (req) {
                var url  = req.data.filePath+"/"+req.data.savedName;
                url = "/imageView?path="+url;
                $(el).summernote('editor.insertImage', url);
            },
            error: function (e) {
                console.info(e);
                alert('업로드 중 에러가 발생했습니다. 파일 용량이 허용범위를 초과 했거나 올바르지 않은 파일 입니다.');
            }
        });
    };
    return summertnote_sub;
});
