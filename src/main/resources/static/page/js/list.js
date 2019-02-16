$(document).ready(function () {
    var key = getUrlParam("key");
    $.ajax({
        url:"/blog_system/blog/fuzzy",
        data:{"key":key},
        type:"POST",
        success:function (result) {
            var data = result.data;
            var data  = new Vue({
                el:"#list",
                data:{
                    blogs:data
                }
            })
        }
    })
});