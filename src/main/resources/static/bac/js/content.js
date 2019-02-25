$(function () {
    var pageNo = getUrlParam("pageNo");
    if (pageNo == null || pageNo == ""){
        pageNo = 1;
    }
    listBlog(pageNo);
});

var listBlog = function (pageNo) {
    $.ajax({
        url:"/blog_system/blog/blogList",
        data:{"pageNumber":pageNo},
        type:"POST",
        success:function (result) {
            var data = result.data;
            var total = result.total;
            var pageHtml = "";
            for (var i = 1;i <= total;i++ ){
                pageHtml += "<li><a href=\"#\">" + i + "</a></li>";
            }
            $("#page").html(pageHtml);
            var dataList= new Vue({
                el:"#blogList",
                data:{
                    blogs:data
                }
            })
        }
    })
};