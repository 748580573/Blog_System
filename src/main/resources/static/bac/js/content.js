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
            dataList.blogs = data;
            generatePage(result.pageNumber,result.total);
        }
    })
};


var generatePage = function (pageNumber,total) {
    var html = "";
    if (pageNumber <= 1){
        html += "<li class='disabled'><a href=\"javascript:void(0)\" data-page=\"" + pageNumber + "\">上一页</a></li>";
    }else {
        html += "<li><a href=\"javascript:void(0)\" data-page=\"" + pageNumber + "\" onclick='previousPage(this)'>上一页</a></li>";
    }

    for (var i = 0; i < 5 && pageNumber + i <= total;i++){
        html += "<li><a href=\"javascript:void(0)\" data-page=\" " + (pageNumber + i) + "\" onclick='turnPage(this)'> " + (pageNumber + i) +"</li>";
    }

    if (pageNumber >= total){
        html += "<li class='disabled'><a href=\"javascript:void(0)\" data-page=\"" + pageNumber + "\">下一页</a></li>";
    }else {
        html += "<li><a href=\"javascript:void(0)\" data-page=\"" + pageNumber + "\" onclick='nextPage(this)'>下一页</a></li>";
    }
    $("#pagination").html(html);
};

var previousPage = function (obj) {
    var pageNumber = parseInt($(obj).attr("data-page")) - 1;
    $.ajax({
        url:"/blog_system/blog/blogList",
        data:{"pageNumber":pageNumber,"pageSize":10},
        type:"POST",
        success:function (result) {
            var msg = result.msg;
            dataList.blogs = result.data;
            var pageNumber = result.pageNumber;
            var total = result.total;
            generatePage(pageNumber,total);
        }
    })
};

var nextPage = function (obj) {
    var pageNumber = parseInt($(obj).attr("data-page")) + 1;
    $.ajax({
        url:"/blog_system/blog/blogList",
        data:{"pageNumber":pageNumber,"pageSize":10},
        type:"POST",
        success:function (result) {
            var msg = result.msg;
            dataList.blogs = result.data;
            var pageNumber = result.pageNumber;
            var total = result.total;
            generatePage(pageNumber,total);
        }
    })
};

var turnPage = function (obj) {
    var pageNumber = parseInt($(obj).attr("data-page")) ;
    $.ajax({
        url:"/blog_system/blog/blogList",
        data:{"pageNumber":pageNumber,"pageSize":10},
        type:"POST",
        success:function (result) {
            var msg = result.msg;
            dataList.blogs = result.data;
            var pageNumber = result.pageNumber;
            var total = result.total;
            generatePage(pageNumber,total);
        }
    })
};

var fuzzyByDB = function () {
    var form =  $("#query").serialize();
    form = serializeFormAddParam(form,"pageNumber",1);
    form = serializeFormAddParam(form,"pageSize",10);
    $.ajax({
        url:"/blog_system/blog/fuzzyByDB",
        data:form,
        type: 'POST',
        success:function (result) {
            if (result.code == 200){
                $("#queryParam").attr();
            }
        }
    })
};

var view_blog = function (obj) {
    var blog_id = $(obj).attr("data-code");
    var href = "/blog_system/muyu/blog.html?blogCode="+blog_id;
    window.open(href,"_blank")
};

//todo 完成ajax请求后的流程  替换符文布
var dele_blog = function (obj) {
    var blog_code = $(obj).attr("data-code");
    $.ajax({
        url:"/blog_system/blog/delete",
        data:{"blog_code":blog_code},
        type:"POST",
        success:function (result) {
            var code = result.code;
            if (code ==  201){
                $(obj).parents("tr").remove();
            } else {
                alert("删除失败," + result.msg);
            }
        }
    })
};