Vue.use(VueLazyload,{
    preLoad: 1.3,
    error: '/blog_system/muyu/img/error.jpg',
    loading: '/blog_system/muyu/img/jiazai.png',
    attempt: 1
});
var newList = new Vue({
    el:"#newList",
    data:{
        blogs:null
    }
});

var rankBlog = new Vue({
    el:"#rank",
    data:{
        blogs:null
    }
});

$(function () {
    loadList();
    initPage();

    $("#key").keydown(function(event) {
        if (event.keyCode == 13) {
            var key = $("#key").val();
            window.location = "/blog_system/muyu/list.html?key="+key;
        }
    })
});

var initPage = function () {
    $.ajax({
        url:"/blog_system/blog/searchRecommendBlog",
        type:"POST",
        success:function (result) {
            var data = result.data;
            newList.blogs = data;
            var pageNumber = result.pageNumber;
            var pageTotal = result.pageTotal;
            $("#pageNumber").attr("data-page",pageNumber);
            $("#pageNumber").attr("data-total",pageTotal);
        }
    });

    $.ajax({
        url:"/blog_system/blog/rank",
        type:"POST",
        success:function (result) {
            var data = result.data;
            rankBlog.blogs = data;
        }
    });
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
                $("#end").show();
                return;
            }
            if (!loading){
                loading = true;
                var page = parseInt($("#pageNumber").attr("data-page")) + 1;
                $("#load").show();
                $.ajax({
                    url:"/blog_system/blog/searchRecommendBlog",
                    type:"POST",
                    data:{"pageNumber":page,"pageSize":6},
                    success:function (result) {
                        $("#load").css("display","none");
                        var data = result.data;
                        var pageNumber = result.pageNumber;
                        var pageTotal = result.pageTotal;
                        newList.blogs = newList.blogs.concat(data);
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
