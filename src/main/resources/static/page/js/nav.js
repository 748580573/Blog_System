var nav = new Vue({
    el: "#nav",
    data: {
        titles: [
            {name: "网站首页",url:"/blog_system/page/html/index.html", sub: []},
            {name: "技术博客", url:"#",sub: []},
            {name: "技术专栏",url:"#", sub: [{name: "Java",url:"/blog_system/page/html/list.html?key=Java"}, {name: "知识总结",url:"/blog_system/page/html/list.html?key=总结"}, {name: "数据库",url:"/blog_system/page/html/list.html?key=Linux"}]},
            {name: "资源分享",url:"#", sub: []}
        ]
    }
});
$(function () {
    $("#key").keydown(function(event) {
        if (event.keyCode == 13) {
            var key = $("#key").val();
            window.location = "/blog_system/page/html/list.html?key="+key;
        }
    })
});

var search = function () {
    var key = $("#key").val();
    window.location = "/blog_system/page/html/list.html?key="+key;
};