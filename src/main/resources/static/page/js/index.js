var advs  = new Vue({
    el: "#adv",
    data:{
        advBlogs:null
    }
});
$.fakeLoader({
    timeToHide:4000,
    spinner:"spinner4",

});

Vue.use(VueLazyload,{
    preLoad: 1.3,
    error: '../img/error.jpg',
    loading: '../img/jiazai.png',
    attempt: 1
});

var hotBlog = new Vue({
    el: "#rank",
    data: {
        blogs: null
    }
});

var newBlog = new Vue({
    el: "#newBlog",
    data: {
        blogs: null
    }
});

var recommendBlog = new Vue({
    el:"#recommendBlog",
    data:{
        blogs:null
    }
});

var rankBlog = new Vue({
    el:"#hot",
    data:{
        blogs:null
    }
});

// var img_width;
// var img_height;
$(function () {
    compentent_reset(".Carousel_content", ".Carousel_content_li img");
        compentent_reset(".recommend_blog_ul_li", ".recommend_blog_ul_li img");
        compentent_reset(".knowledge_theme_ul_li", ".knowledge_theme_ul_li img");
        $(window).resize(function () {
            compentent_reset(".Carousel_content", ".Carousel_content_li img");
            compentent_reset(".recommend_blog_ul_li", ".recommend_blog_ul_li img");
            compentent_reset(".knowledge_theme_ul_li", ".knowledge_theme_ul_li img");

        });

        setTimeout(setInterval(carousel, 5000),0);
        setTimeout(init_hotBlog(),0);
        setTimeout(init_newBlog(),0);
        setTimeout(init_recommendBlog(),0);
        setTimeout(init_rankBlog,0);
        init_page();
    }
);

var init_page = function () {

    $.ajax({
        url:"/blog_system/blog/advs",
        type:"POST",
        success:function (result) {
            var data = result.data;
            advs.advBlogs = data;
        }
    });

    $.ajax({
        url:"/blog_system/blog/searchRecommendBlog",
        type:"POST",
        success:function (result) {
            var data = result.data;
            recommendBlog.blogs = data;
        }
    });

    $.ajax({
        url: "/blog_system/blog/searchHotBlog",
        type: "POST",
        data: {},
        success: function (result) {
            $("#rand_load").hide();
            var data = result.data;
            hotBlog.blogs = data;
        }
    });

    $.ajax({
        url: "/blog_system/blog/searchNewBlog",
        type: "POST",
        data: {},
        success: function (result) {
            var data = result.data;
            newBlog.blogs = data;
        }
    });


    $.ajax({
        url:"/blog_system/blog/rank",
        date:{"pageSize":4},
        type:"POST",
        success:function (result) {
            var data = result.data;
            rankBlog.blogs = data;
        }
    })
};

/**
 * 初始化热门博客
 */
var init_hotBlog = function () {
    $.ajax({
        url: "/blog_system/blog/searchHotBlog",
        type: "POST",
        data: {},
        success: function (result) {
            $("#rand_load").hide();
            var data = result.data;
            hotBlog.blogs = data;
        }
    })
};

var init_newBlog = function () {
    $.ajax({
        url: "/blog_system/blog/searchNewBlog",
        type: "POST",
        data: {},
        success: function (result) {
            var data = result.data;
            newBlog.blogs = data;
        }
    })
};


var init_recommendBlog = function () {
    $.ajax({
        url:"/blog_system/blog/searchRecommendBlog",
        type:"POST",
        success:function (result) {
            var data = result.data;
            recommendBlog.blogs = data;
        }
    })
};

var init_rankBlog = function () {
    $.ajax({
        url:"/blog_system/blog/rank",
        type:"POST",
        success:function (result) {
            var data = result.data;
            rankBlog.blogs = data;
        }
    })
};
/**
 * 查看某片博客
 * @param obj
 */
var view_blog = function (obj) {
    var blog_id = $(obj).attr("data-code");
    window.location = "/blog_system/muyu/blog.html?blogCode="+blog_id;
};

/**
 * 子组件根据父组件的大小进行调节
 * @param parentSelector
 * @param childSelector
 */
function compentent_reset(parentSelector, childSelector) {
    var img_width = $(parentSelector).width();
    var img_height = $(parentSelector).height();
    resetSize(childSelector, img_width, img_height);
}


/**
 * 重置组建长和宽
 * @param selector
 * @param width
 * @param height
 */
function resetSize(selector, width, height) {
    $(selector).attr("width", width);
    $(selector).attr("height", height);
}

function carousel_button(obj) {
    var index = $(obj).attr("alt");
    $(obj).children("img").hide();

    var current;
    var li_alt;

    var img_lis = $(".Carousel_content_li");
    for (var i = 0; i < img_lis.length; i++) {
        li_alt = $(img_lis[i]).attr("alt");
        if (li_alt == index) {
            current = img_lis[i];
        }
        $(img_lis[i]).fadeOut(1500);
    }
    $(current).fadeIn();
}

/**
 * 自动轮播
 */
var carousel_next = 0;

function carousel() {
    var carousel_current = carousel_next;            //当前正在轮播的图
    carousel_next++;
    carousel_next = carousel_next % 3;

    var current;
    var next;
    var img_lis = $(".Carousel_content_li");
    for (var i = 0; i < img_lis.length; i++) {
        li_alt = $(img_lis[i]).attr("alt");
        if (li_alt == carousel_current) {
            current = img_lis[i];
        }
        if (li_alt == carousel_next) {
            next = img_lis[i];
        }
    }

    $(current).fadeOut();
    $(next).fadeIn();
}

/**
 * Li标签切换
 */
var pre_li = null;     //当前被选中的li标签
var current_li = null;         //上一个被选中的li标签
function changeLi(obj) {
    pre_li = current_li;
    current_li = obj;

    if (pre_li != null) {
        if (pre_li != current_li) {
            var pre_p = $(pre_li).children(".description").get(0);
            $(pre_p).hide();
            $(pre_li).css("height", "15%");
            $(pre_li).css("background-color", "#FFF");
        }
        var current_p = $(current_li).children(".description").get(0);
        $(current_p).show();
        $(current_li).css("height", "40%");
        $(current_li).css("background-color", "#d4d4d4");
    } else {
        var current_p = $(current_li).children(".description").get(0);
        $(current_p).show();
        $(current_li).css("height", "40%");
        $(current_li).css("background-color", "#d4d4d4");
    }
}


function setFistLi() {
    $(".first_li").css("height", "40%");
    $(".first_li").css("background-color", "#d4d4d4");
}

/**
 * 选择同级中，除本标签以外的标签
 * @param selector 指定的标签
 * @param anotherSelecor 其他的同级标签
 */
function anotherTag(selector, anotherSelecor) {
    var levelTag = $(selector).prevAll(anotherSelecor);
    levelTag.push($(selector).nextAll(anotherSelecor));
    return levelTag;
}

var pre_title = null;
var cur_title = null;

function click_title(obj) {
    pre_title = cur_title;
    cur_title = obj;
    if (pre_title != null && pre_title == cur_title) {
        return;
    }
    if (pre_title != null) {
        $(pre_title).css("border-bottom", "none");
    } else {
        $($(".knowledge_theme_nav_ul").children("li").get(0)).css("border-bottom", "none");
    }
    $(cur_title).css("border-bottom", "2px solid black");
    var data_sign = $(cur_title).attr("data-sign");
    chageTitleContent(data_sign);
}

var pre_title_content = null;
var cur_title_content = null;

function chageTitleContent(data_sign) {
    pre_title_content = cur_title_content;
    var lis = $(".knowledge_theme_content_right").children("ul");
    for (var i = 0; i < lis.length; i++) {
        var value = $(lis[i]).attr("data-sign");
        if (data_sign == value) {
            cur_title_content = lis[i];
            if (cur_title_content == pre_title_content) {
                return
            }
            $(cur_title_content).show();
            if (pre_title_content != null) {
                $(pre_title_content).hide();
            } else {
                $($(".knowledge_theme_content_right").children("ul").get(0)).hide();
            }
        }
    }
}

$(function () {
    $("#key").keydown(function(event) {
        if (event.keyCode == 13) {
            var key = $("#key").val();
            window.location = "/blog_system/page/html/list.html?key="+key;
        }
    })
})

var search = function () {
    var key = $("#key").val();
    window.location = "/blog_system/page/html/list.html?key="+key;
};