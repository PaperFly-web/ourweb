var rexPwd=false;
//注册验证密码强度
$("#password").blur(function () {
    var alertMsg=$("#alertMsg");
    var pwd=$("#password").val();
    var rex=/^\S{6,}$/;
    if(!rex.test(pwd)){
        $("#modifyPwdBtn").attr("disabled","disabled");
        rexPwd=false;
        if(pwd!=''){
            alertMsg.text("密码不符合规定!")
        }

    }else {
        // alert("密码符合规范")
        rexPwd=true;
        alertMsg.text("")
    }
});
//注册验证密码是否一致
$("#re-password").blur(function () {
    var pwd=$("#password").val();
    var repwd=$("#re-password").val();
    var alertMsg=$("#alertMsg");
    if(pwd!=repwd){
        if(repwd!=''){
            alertMsg.text("密码不一致!")
        }
        $("#modifyPwdBtn").attr("disabled","disabled");
    }else {
        if(rexPwd){
            $("#modifyPwdBtn").removeAttr("disabled")
        }
        alertMsg.text("")
    }
});
$("#modifyPwdBtn").click(function () {
    var pwd=$("#password").val();
    var alertMsg=$("#alertMsg");
    $.post(
        "modifyUserPassword",
        "pwd="+pwd,
        function (data) {
            alertMsg.text(data);
            $("#modifyPwdBtn").attr("disabled","disabled");
        },
        "text"
    )
});
$(function () {
    var noFlag=false;
    var modifyNoBtn=$("#modifyNoBtn");
    var getCodeBtn=$("#getCode");
//注册验证手机号
    $("#no").blur(function () {
        var no=$("#no").val();
        var rex=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
        var flag = rex.test(no);
        var alertMsg=$("#alertMsg");
        if(!flag){
            getCodeBtn.attr("disabled","disabled");
            // alertMsg.removeAttr("visibility");
            noFlag=false;
            if(no!=''){
                alertMsg.text("您输入的不是手机号!");
            }
        }else {
            noFlag=true;
            alertMsg.text("");
            getCodeBtn.removeAttr("disabled");
            modifyNoBtn.removeAttr("disabled");
        }
    });
//获取验证码
    $("#getCode").click(function () {
        var alertMsg=$("#alertMsg");
        getCodeBtn.attr("disabled","disabled");
        if(noFlag){
            var no=$("#no").val();
            $.ajax({
                url:"/modifyNoSend",
                data:"no="+no,
                datatype:"text",
                success:function(data){
                    second=60;//初始化倒计时总时间
                    if(data=="成功"){
                        alertMsg.text("恭喜您验证码发送成功!");
                        getCodeBtn.attr("disabled","disabled");
                        setInterval(countDown,1000);
                    }else {
                        noFlag=false;
                        alertMsg.text(data);
                    }
                }
            })
        }else {
            alertMsg.text("请先填写正确的手机号!")
        }
    });
    function countDown() {
        if(second==0){
            getCodeBtn.removeAttr("disabled");
            getCodeBtn.text("获取验证码");
        }else {
            getCodeBtn.attr("disabled","disabled");
            getCodeBtn.text("重新获取("+second--+"秒)");
        }
    }
    modifyNoBtn.click(function () {
        var rex=/^[A-Z a-z 0-9 +]{6}$/;
        var no=$("#no").val();
        var code=$("#code").val();
        var alertMsg=$("#alertMsg");
        if(!rex.test(code)){
            alertMsg.text("验证码格式不正确!")
        }else {
            $.post(
                "/modifyNo",
                "newNo="+no+"&code="+code,
                function (data) {
                    alertMsg.text(data);
                    modifyNoBtn.attr("disabled","disabled");
                    getCodeBtn.attr("disabled","disabled");
                },
                "text"
            )
        }
    });
});
$(function () {
   var modifyUserNameBtn=$("#modifyUserNameBtn");
   var userName=$("#userName");
   var alertMsg=$("#alertMsg");
    modifyUserNameBtn.click(function () {
        if(userName.val()==''){
            alertMsg.text("用户名不能为空!")
        }else {
            $.post(
                "/modifyUserName",
                "userName="+encodeURIComponent(userName.val()),
                function (data) {
                    alertMsg.text(data);
                    if(data='恭喜您,用户名修改成功!'){
                        $("#showUserName").text("您好啊:"+userName.val())
                    }
                },
                "text"
            )
        }
    });
    userName.focus(function () {
        alertMsg.text("");
    });
});