$(function () {
    var rexPhnoe=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
    var re_rexPwd=false;
    var rexpwd=false;
    var rexNo=false;
    var alertMsg=$("#alertMsg");
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
                "fileStartSavePath="+""+"&fileMaxZize="+fileSize,
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
});
