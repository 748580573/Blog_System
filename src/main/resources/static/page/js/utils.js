/**
 * 获取url中的参数
 * @param paraName
 * @returns {string}
 * @constructor
 */
function getUrlParam(key) {
    // 获取参数
    var url = window.location.search;
    // 正则筛选地址栏
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    // 匹配目标参数
    var result = url.substr(1).match(reg);
    //返回参数值
    return result ? decodeURIComponent(result[2]) : null;
}

var view_blog = function (obj) {
    var blog_id = $(obj).attr("data-code");
    var href = "/blog_system/page/html/blog.html?blogCode="+blog_id;
    window.open(href,"_blank")
};

var dele_blog = function (obj) {
    var blog_code = $(obj).attr("data-code");
    //TODO 完成删除栏后天逻辑和Js
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

var modify_blog = function (obj) {
    var blog_id = $(obj).attr("data-code");
    var href = "/blog_system/bac/form_modify.html?blogCode="+blog_id;
    window.open(href,"_blank")
};

var set_text = function (selector,text) {
    $(selector).text(text);
};

var set_val =  function (selector,val) {
    $(selector).val(val);
};

var set_html = function (selector,html) {
    $(selector).html(html);
};