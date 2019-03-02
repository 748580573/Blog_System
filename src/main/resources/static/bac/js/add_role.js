var permissions = new Vue({
    el:"#permissions",
    data:{
        permissions:null
    }
});

$(function () {
    init_permissions();
})

var init_permissions = function () {
    $.ajax({
        url:"/blog_system/permissions",
        type:"POST",
        success:function (result) {
            var code = result.code;
            if (code == 201){
                var data = result.data;
                permissions.permissions = data;
            } else {
                alert(result.msg)
            }
        }
    })
};
