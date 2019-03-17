var dataList= new Vue({
    el:"#blogList",
    data:{
        blogs:null
    }
});

$(function () {
    var pageNumber = getUrlParam("pageNumber");
    if (pageNumber == null || pageNumber == ""){
        pageNumber = 1;
    }
    listBlog(pageNumber);
});

var listBlog = function (pageNumber) {
    $.ajax({
        url:"/blog_system/blog/blogList",
        data:{"pageNumber":pageNumber},
        type:"POST",
        success:function (result) {
            var data = result.data;
            var total = result.total;
            var pageHtml = "";
            dataList.blogs = data;
            for (var i = 1;i <= total;i++ ){
                pageHtml += "<li><a href=\"javascript:void(0)\" onclick=\"turnPage(this)\"> " + i+"</a></li>";
            }
            $("#page").html(pageHtml);
        }
    })
};

var turnPage = function (obj) {
    var pageNumber = $(obj).text();
    listBlog(pageNumber);
};