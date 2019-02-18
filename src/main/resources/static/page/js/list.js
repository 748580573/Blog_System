$(document).ready(function () {
    var key = getUrlParam("key");
    var pageNo = getUrlParam("pageNo");
    if (pageNo == null || pageNo == ""){
        pageNo = 1;
    }
    $.ajax({
        url:"/blog_system/blog/fuzzy",
        data:{"key":key,"pageNo":pageNo},
        type:"POST",
        success:function (result) {
            var data = result.data;
            var total = result.total;
            init_pageNumber(total);
            var data  = new Vue({
                el:"#list",
                data:{
                    blogs:data
                }
            });
        }
    })
});

/**
 * 初始化分页
 */
var init_pageNumber = function (number) {
    var html = "";
    for (var i = 1;i <= number;i++){
        html +=  "<button type=\"button\" class=\"btn btn-default\" onclick='turnPage(this)'>" + i + "</button>\n"
    }
    $("#pageNumber").html(html);
};

var turnPage = function (obj) {
    var key = getUrlParam("key");
    var pageNo = $(obj).text();
    window.location = "/blog_system/page/html/list.html?key="+key+"&pageNo="+pageNo;
};