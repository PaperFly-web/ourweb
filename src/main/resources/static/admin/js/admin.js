var alertMsg;
var rexPhnoe=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
var re_rexPwd=false;
var rexpwd=false;
var rexNo=false;
$(function () {
    alertMsg=$("#alertMsg");
    modifyUserMsg();
    modifyUserPermAndRole();
    modifyClassMsg();
    modifyBlogMsg();
    registerUser();
    updataTemplateCode();
});
function modifyUserMsg() {
    $("#password").blur(function () {
        var rex=/^[A-Z a-z 0-9 +]{6}$/;
        var pwd=$("#password").val();
        if(!rex.test(pwd)){
            alertMsg.text("密码不符合规定!");
            rexpwd=false;
        }else {
            rexpwd=true;
            alertMsg.text("")
        }
    });
    //注册验证密码是否一致
    $("#re-password").blur(function () {
        var pwd=$("#password").val();
        var repwd=$("#re-password").val();
        if(pwd!=repwd){
            alertMsg.text("密码不一致!");
            re_rexPwd=false;
        }else {
            re_rexPwd=true;
            alertMsg.text("")
        }
    });
    $("#no").blur(function () {
        var no=$("#no").val();
        if(!rexPhnoe.test(no)){
            alertMsg.text("手机号格式不正确!");
            rexNo=false;
        }else {
            rexNo=true;
            alertMsg.text("")
        }
    });
    $("#permNo").blur(function () {
        var no=$("#permNo").val();
        if(!rexPhnoe.test(no)){
            alertMsg.text("手机号格式不正确!");
            rexNo=false;
        }else {
            rexNo=true;
            alertMsg.text("")
        }
    });
    $("#roleNo").blur(function () {
        var no=$("#roleNo").val();
        if(!rexPhnoe.test(no)){
            alertMsg.text("手机号格式不正确!");
            rexNo=false;
        }else {
            rexNo=true;
            alertMsg.text("")
        }
    });
    $("#re-password").focus(function () {
        alertMsg.text("");
    });
    $("#password").focus(function () {
        alertMsg.text("")
    });
    $("#no").focus(function () {
        alertMsg.text("");
    });
    $("#userPerm").focus(function () {
        alertMsg.text("");
    });
    $("#userRole").focus(function () {
        alertMsg.text("");
    });
    $("#permNo").focus(function () {
        alertMsg.text("");
    });
    $("#roleNo").focus(function () {
        alertMsg.text("");
    });
    $("#modifyPwdBtn").click(function () {
        if(!(re_rexPwd&&rexpwd)) {
            alertMsg.text("不能修改,信息有误!")
        }
        if(re_rexPwd&&rexpwd){
            var pwd=$("#password").val();
            var no=$("#no").val();
            $.post(
                "adminModifyUserPassword",
                "pwd="+pwd+"&no="+no,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }


    });

}
function modifyUserPermAndRole() {
    $("#modifyUserRoleBtn").click(function () {
        var userRole=$("#userRole").val();
        var roleNo=$("#roleNo").val();
        if(userRole==""){
            alertMsg.text("角色不能为空!");
        }else if(!rexPhnoe.test(roleNo)){
            alertMsg.text("手机号格式不正确!");
        } else {
            $.post(
                "modifyUserRole",
                "no="+roleNo+"&role="+userRole,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
    });
    $("#modifyUserPermBtn").click(function () {
        var userPerm=$("#userPerm").val();
        var permNo=$("#permNo").val();
        if(userPerm==""){
            alertMsg.text("权限不能为空!");
        }else if(!rexPhnoe.test(permNo)){
            alertMsg.text("手机号格式不正确!");
        } else {
            $.post(
                "modifyUserPerm",
                "no="+permNo+"&perm="+userPerm,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
    });
}
function modifyClassMsg() {

    $("#modifyfileStartPathBtn").click(function () {
        var filePath=$("#modifyFileStartPath").val();
        if(filePath==""){
            alertMsg.text("文件地址不能为空!");
        }else {
            $.post(
                "adminModifySystemPropertites",
                "fileStartSavePath="+filePath+"&fileMaxZize="+-1,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
    });
    $("#modifyMaxFileSizeBtn").click(function () {
        var fileSize=$("#maxFileSize").val();
        if(fileSize<=0||fileSize==''){
            alertMsg.text("最低尺寸不能小于等于0!");
        }else {
            $.post(
                "adminModifySystemPropertites",
                "fileStartSavePath="+"-1"+"&fileMaxZize="+fileSize,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
        $("#modifyFileStartPath").focus(function () {
            alertMsg.text("");
        });
        $("#maxFileSize").focus(function () {
            alertMsg.text("");
        });
    });
}
function modifyBlogMsg() {
    $("#modifyBlogfileStartPathBtn").click(function () {
        var filePath=$("#modifyBlogFileStartPath").val();
        if(filePath==""){
            alertMsg.text("文件地址不能为空!");
        }else {
            $.post(
                "adminModifyBlogPropertites",
                "blogStartFilePath="+filePath+"&blogFileMaxZize="+-1,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
    });
    $("#modifyBlogMaxFileSizeBtn").click(function () {
        var fileSize=$("#maxBlogFileSize").val();
        if(fileSize<=0||fileSize==''){
            alertMsg.text("最低尺寸不能小于等于0!");
        }else {
            $.post(
                "adminModifyBlogPropertites",
                "blogStartFilePath="+"-1"+"&blogFileMaxSize="+fileSize,
                function (data) {
                    alertMsg.text(data);
                },
                "text"
            )
        }
        $("#modifyBlogFileStartPath").focus(function () {
            alertMsg.text("");
        });
        $("#maxBlogFileSize").focus(function () {
            alertMsg.text("");
        });
    });
}
function registerUser() {
    var noIsRight=false;
    var no=$("#registerUserNo").val();
    $("#registerUserNo").focus(function () {
        alertMsg.text("");
    });
    $("#registerUserNo").blur(function () {
        no=$("#registerUserNo").val();
        if(no!=''){
            if(rexPhnoe.test(no)){
                noIsRight=true;
            }else {
                noIsRight=false;
                alertMsg.text("手机号不正确!");
            }
        }
    });
    $("#registerUserBtn").click(function () {
        // alert("注册");
        if(noIsRight){
            $.ajax({
                url:"adminRegist",
                type:"post",
                data:"no="+no+"&password=123456&userName=paperfly",
                dataType:"text",
                success:function (data) {
                    alertMsg.text(data);
                }
            })
        }else {
            alertMsg("请填写正确的手机号!");
        }

    })
}
function updataTemplateCode() {
    var isRight=false;
    var code=$("#updataTemplateCode").val();
    $("#updataTemplateCode").focus(function () {
        alertMsg.text("");
    });
    $("#updataTemplateCode").blur(function () {
        code=$("#updataTemplateCode").val();
        if(code!=''){
            isRight=true;
        }else {
            isRight=false;
        }
    });
    $("#updataTemplateCodeBtn").click(function () {
        if(isRight){
            $.ajax({
                url:"/updataTemplateCode",
                type:"post",
                data:"templateCode="+code,
                dataType:"text",
                success:function (data) {
                    alertMsg.text(data);
                }
            })
        }else {
            alertMsg.text("模板代码不能为空!");
        }

    })
}