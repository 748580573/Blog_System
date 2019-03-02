var hotBlog = new Vue({
    el: "#rank",
    data: {
        blogs: null
    }
});

var rankBlog = new Vue({
    el:"#hot",
    data:{
        blogs:null
    }
});

$(function () {
    init_rankBlog();
    init_hotBlog();
})
/**
 * 初始化热门博客
 */
var init_hotBlog = function () {
    $.ajax({
        url: "/blog_system/blog/searchHotBlog",
        type: "POST",
        data: {},
        success: function (result) {
            var data = result.data;
            hotBlog.blogs = data;

        }
    })
};

var init_rankBlog = function () {
    $.ajax({
        url:"/blog_system/blog/rank",
        type:"POST",
        success:function (result) {
            var data = result.data;
            rankBlog.blogs = data;
        }
    })
};