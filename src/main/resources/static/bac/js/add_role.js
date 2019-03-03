var permissions = new Vue({
    el:"#permissions",
    data:{
        permissions:null
    }
});

$(function () {
    init_permissions();
});

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

var addRole = function () {
    var form = new FormData();
    var name = $("#name").val();
    form.append("roleName",name);
    var obj = $("#permissions input");
    for (var i = 0;i < obj.length;i++){
        if( $(obj[i]).is(":checked") ){
            var n = $(obj[i]).attr("name");
            var v = $(obj[i]).attr("value");
            form.append(n,v);
        }
    }

    $.ajax({
        url:"/blog_system/addRole",
        type:"POST",
        data:form,
        processData: false,
        contentType: false,
        success:function (result) {
            var code = result.code;
            if (code == 201){
                var data = result.data;
                roles.roles = data;
            } else {
                alert(result.msg)
            }
        }
    });
}
