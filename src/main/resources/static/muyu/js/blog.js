Vue.use(VueLazyload,{
    preLoad: 1.3,
    error: '/blog_system/muyu/img/error.jpg',
    loading: '/blog_system/muyu/img/jiazai.png',
    attempt: 1
});
var blog = new Vue({
    el:"#blog",
    data:{
        tags:null,
        blogCode:null,
        blogTilte:null,
        blogDesc:null,
        createDate:null
    }
});

var rank = new Vue({
    el:"#rank",
    data:{
        blogs: null
    }
});

$(document).ready(function () {
    initPage();

    $("#key").keydown(function(event) {
        if (event.keyCode == 13) {
            var key = $("#key").val();
            window.location = "/blog_system/muyu/list.html?key="+key;
        }
    })
});

var initPage = function () {
    var blogCode =  getUrlParam("blogCode");
    $.ajax({
        url:"/blog_system//blog/searchBlog",
        data:{"blog_code":blogCode},
        type:"POST",
        success:function (result) {
            var data = result.data;
            var tagList = data.tags;
            var tags = tagList.split(",");
            var blogCode = data.blogCode;
            var blogTilte = data.blogTilte;
            var blogDesc = data.blogDesc;
            var blogContent = data.blogContent;
            var createDate = data.createDate;
            $("#blog_content").html(blogContent);
            blog.tags = tags;
            blog.blogCode = blogCode;
            blog.blogTilte = blogTilte;
            blog.blogDesc = blogDesc;
            blog.createDate = createDate;

        }
    });

    $.ajax({
        url:"/blog_system/blog/rank",
        type:"GET",
        success:function (result) {
            var data = result.data;
            rank.blogs = data;
        }
    })
};

var look = function () {
    var key = $("#key").val();
    window.location = "/blog_system/muyu/list.html?key="+key;
};

var addComment = function (obj) {
    var blogCode = getUrlParam("blogCode");
    var div = $(obj).parents(".comment-box");
    var textArea = $(div).children("textarea");
    var content = $(textArea).val();
    $.ajax({
        url:"/blog_system/comment/comment.html",
        data:{"content":content,"blogCode":blogCode},
        type:"POST",
        success:function (result) {
            if (result.code == 200){
                if (content != null && content.length > 0){
                    var li = "<li class=\"comment-content\">\n" +
                        "            <span class=\"comment-f\"></span>\n" +
                        "            <div class=\"comment-main\">\n" +
                        "                <p><a class=\"address\" href=\"http://www.muzhuangnet.com/\" rel=\"nofollow\" target=\"_blank\">比丘特之剑</a><span class=\"time\">(2019/01/28 11:41:03)</span><br>{{content}}</p>\n" +
                        "                <div class=\"reply\"><span class=\"glyphicon glyphicon-comment\" data-id='{{id}}' onclick=\"addFirstReply(this)\">回复</span></div>\n" +
                        "            </div>\n" +
                        "            <ol class=\"commentlist replyList\" style=\"margin-left: 4%\">\n" +
                        "            </ol>\n" +
                        "        </li>";
                    li = li.replace("{{content}}",content);
                    li = li.replace("{{id}}",result.data.id);
                    $("#commentList").append(li);
                }else {
                    alert("系统错误，评论失败");
                }
            }
        }
    });
};

var addFirstReply = function (obj) {
    var parDiv = $(obj).parents(".reply");
    var id = $(obj).attr("data-id");
    var replyBox = "<div class=\"comment\">\n" +
        "                    <div class=\"comment-box\">\n" +
        "                        <textarea class='commentTextArea' placeholder=\"您的评论或留言\"  cols=\"100%\" rows=\"3\" tabindex=\"3\"></textarea>\n" +
        "                        <div class=\"comment-ctrl\">\n" +
        "                            <button type=\"button\" name=\"comment-submit\" class=\"comment-submit\" tabindex=\"4\" onclick=\"removeBox(this)\">取消</button>\n" +
        "                            <button type=\"button\" name=\"comment-submit\" class=\"comment-submit\" tabindex=\"4\" data-id=\"" + id + "\" onclick=\"addFirstRelReply(this)\">评论</button>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>";
    var checkBod = $(parDiv).children(".comment");
    if (checkBod.length <= 0){
        $(parDiv).append(replyBox);
        var textArea = $(parDiv).find(".commentTextArea");
        $(textArea[0]).focus();
    }
};

var addFirstRelReply = function (obj) {
    var id = $(obj).attr("data-id");
    var parDiv = $(obj).parents(".comment-box");
    var textArea = $(parDiv).children("textarea");
    var text = $(textArea).val();
    $.ajax({
        url:"/blog_system/comment/reply.html",
        data:{"commentId":id,"content":text},
        type:"POST",
        success:function (result) {
            var reply = "<li class=\"comment-content\">\n" +
                "                    <span class=\"comment-f\"></span>\n" +
                "                    <div class=\"comment-main\">\n" +
                "                        <p><a class=\"address\" href=\"http://www.muzhuangnet.com/\" rel=\"nofollow\" target=\"_blank\">比丘特之剑</a><span class=\"time\">(2019/01/28 11:41:03)</span><br>{{content}}</p>\n" +
                "                        <div class=\"reply\"><span class=\"glyphicon glyphicon-comment\" data-id='{{id}}' onclick=\"addReply(this)\">回复</span></div>\n" +
                "                    </div>\n" +
                "                </li>";

            reply = reply.replace("{{content}}",text);
            reply = reply.replace("{{id}}",result.data.id);
            var li = $(obj).parents("li");
            var ol = $(li).children("ol");
            $(ol).append(reply);
            var div = $(obj).parents(".comment");
            if (div != null){
                $(div).remove();
            }
        }
    })
};


var addReply = function (obj) {
    var id = parseInt($(obj).attr("data-id"));
    var parDiv = $(obj).parents(".reply");
    var replyBox = "<div class=\"comment\">\n" +
        "                    <div class=\"comment-box\">\n" +
        "                        <textarea class='commentTextArea' placeholder=\"您的评论或留言\"  cols=\"100%\" rows=\"3\" tabindex=\"3\"></textarea>\n" +
        "                        <div class=\"comment-ctrl\">\n" +
        "                            <button type=\"button\" name=\"comment-submit\" class=\"comment-submit\" tabindex=\"4\" onclick=\"removeBox(this)\">取消</button>\n" +
        "                            <button type=\"button\" name=\"comment-submit\" class=\"comment-submit\" tabindex=\"4\" data-id=\"" + id + "\" onclick=\"realAddReply(this)\">评论</button>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>";
    var checkBod = $(parDiv).children(".comment");
    if (checkBod.length <= 0){
        $(parDiv).append(replyBox);
        var textArea = $(parDiv).find(".commentTextArea");
        $(textArea[0]).focus();
    }
};

//皮这一下我很开心
var realAddReply = function (obj) {
    var id = $(obj).attr("data-id");
    var parDiv = $(obj).parents(".comment-box");
    var textArea = $(parDiv).children("textarea");
    var text = $(textArea).val();
    $.ajax({
        url:"/blog_system/comment/reply.html",
        data:{"commentId":id,"content":text},
        type:"POST",
        success:function (result) {
            if (result.code == 200){
                var reply = "<li class=\"comment-content\">\n" +
                    "                    <span class=\"comment-f\"></span>\n" +
                    "                    <div class=\"comment-main\">\n" +
                    "                        <p><a class=\"address\" href=\"http://www.muzhuangnet.com/\" rel=\"nofollow\" target=\"_blank\">比丘特之剑</a><span class=\"time\">(2019/01/28 11:41:03)</span><br>{{content}}</p>\n" +
                    "                        <div class=\"reply\"><span class=\"glyphicon glyphicon-comment\" data-id={{id}} onclick=\"addReply(this)\">回复</span></div>\n" +
                    "                    </div>\n" +
                    "                </li>";

                reply = reply.replace("{{content}}",text);
                reply = reply.replace("{{id}}",result.data.id);
                var ol = $(obj).parents(".replyList");
                $(ol).append(reply);
                var div = $(obj).parents(".comment");
                if (div != null){
                    $(div).remove();
                }
            } else {
                alert("系统错误，评论失败");
            }
        }
    })
};

var removeBox = function (obj) {
    var div = $(obj).parents(".comment");
    if (div != null){
        $(div).remove();
    }
};