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
            var hotBlog = new Vue({
                el: "#rank",
                data: {
                    blogs: data
                }
            })
        }
    })
};

var init_rankBlog = function () {
    $.ajax({
        url:"/blog_system/blog/rank",
        type:"POST",
        success:function (result) {
            var data = result.data;
            var rankBlog = new Vue({
                el:"#hot",
                data:{
                    blogs:data
                }
            })
        }
    })
};