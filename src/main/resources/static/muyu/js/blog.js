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