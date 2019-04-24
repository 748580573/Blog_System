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


var serializeFormAddParam = function (url, key, val) {
    url += "&"+key+"="+val;
    return url;
};

