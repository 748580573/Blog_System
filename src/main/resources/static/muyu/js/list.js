Vue.use(VueLazyload,{
    preLoad: 1.3,
    error: '/blog_system/muyu/img/error.jpg',
    loading: '/blog_system/muyu/img/jiazai.png',
    attempt: 1
});

var list = new Vue({
    el:"#list",
    data:{
        blogs:null
    }
});

var tag = new Vue({
    el:"#tags",
    data:{tags:null}
});


var rank = new Vue({
    el:"#rank",
    data:{
        blogs: null
    }
});


$(function () {
    initPage();

    $("#key").keydown(function(event) {
        if (event.keyCode == 13) {
            var key = $("#key").val();
            window.location = "/blog_system/page/html/list.html?key="+key;
        }
    })
    loadList();
});

var initPage = function () {
    var key = getUrlParam("key");
    var pageNumber = getUrlParam("pageNumber");
    if (pageNumber == null || pageNumber == ""){
        pageNumber = 1;
    }

    $.ajax({
        url:"/blog_system/blog/fuzzy",
        data:{"key":key,"pageNumber":pageNumber},
        type:"POST",
        success:function (result) {
            var pageNumber = result.pageNumber;
            var pageTotal = result.pageTotal;
            var data = result.data;
            list.blogs = data;
            $("#pageNumber").attr("data-page",pageNumber);
            $("#pageNumber").attr("data-page",pageNumber);
            $("#pageNumber").attr("data-total",pageTotal);
        }
    });

    $.ajax({
        url:"/blog_system/blog/tags",
        type:"GET",
        success:function (result) {
            var data = result.data;
            tag.tags = data;
        }
    })

    $.ajax({
        url:"/blog_system/blog/rank",
        type:"GET",
        success:function (result) {
            var data = result.data;
            rank.blogs = data;
        }
    })
};

var loadList = function () {
    var loading = false;
    $(window).scroll(function () {
        var h=$(document.body).height();//网页文档的高度
        var c = $(document).scrollTop();//滚动条距离网页顶部的高度
        var wh = $(window).height(); //页面可视化区域高度
        if (Math.ceil(wh+c)>= (h - h / 12)){
            var p = parseInt($("#pageNumber").attr("data-page"));
            var t = parseInt($("#pageNumber").attr("data-total"));
            if (p == t) {
                $("#load").css("display","none");
                $("#end").show();
                return;
            }
            if (!loading){
                loading = true;
                var page = parseInt($("#pageNumber").attr("data-page")) + 1;
                $("#load").show();
                $.ajax({
                    url:"/blog_system/blog/fuzzy",
                    type:"POST",
                    data:{"pageNumber":page,"pageSize":9},
                    success:function (result) {
                        $("#load").css("display","none");
                        var data = result.data;
                        var pageNumber = result.pageNumber;
                        var pageTotal = result.pageTotal;
                        list.blogs = list.blogs.concat(data);
                        $("#pageNumber").attr("data-page",pageNumber);
                        $("#pageNumber").attr("data-page",pageNumber);
                        $("#pageNumber").attr("data-total",pageTotal);
                        loading = false;
                    }
                })
            }
        }
    });
};

var look = function () {
    var key = $("#key").val();
    window.location = "/blog_system/muyu/list.html?key="+key;
};

var lookTag = function (obj) {
    var key = $(obj).attr("value");
    window.location = "/blog_system/muyu/list.html?key="+key;
};
