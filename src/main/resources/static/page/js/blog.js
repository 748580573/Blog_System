
$(document).ready(function () {
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

            var blogContent = new Vue({
                el:"#blog",
                data:{
                    tags:tags,
                    blogCode:blogCode,
                    blogTilte:blogTilte,
                    blogDesc:blogDesc,
                    createDate:createDate
                }
            });
        }
    })
});

/**
 * 获取url中的参数
 * @param paraName
 * @returns {string}
 * @constructor
 */
function getUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");

            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}