var second=60;
$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a,.links a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});
$(function () {
    var pwdFlag=false;//判断密码是否一致
    var signFlag=false;//判断是否可以登录
    var rexPwd=false;//判断密码是否符合规范
    var register=$(".register");
    var signin=$(".sign-in");
    //注册验证密码强度
    $("#password").blur(function () {
        var alertMsg=$("#alertMsg");
        var pwd=$("#password").val();
        var rex=/^\S{6,}$/;
        if(!rex.test(pwd)){
            // alert("密码bu符合规范")
            rexPwd=false;
            if(pwd!=''){
                alertMsg.text("密码不符合规定!")
            }

        }else {
            // alert("密码符合规范")
            rexPwd=true;
            alertMsg.text("")
        }
    })
    //注册验证密码是否一致
    $("#re-password").blur(function () {
        var pwd=$("#password").val();
        var repwd=$("#re-password").val();
        var alertMsg=$("#alertMsg");
        if(pwd!=repwd){
            if(repwd!=''){
                alertMsg.text("密码不一致!")
            }
            pwdFlag=false;
        }else {
            pwdFlag=true;
            if(signFlag&&rexPwd){//如果手机号和密码都符合规范，可以注册
                register.removeAttr("disabled")
            }
            alertMsg.text("")
        }
    })
    //注册验证手机号
    $("#sign-up-no").blur(function () {
        var no=$("#sign-up-no").val();
        var rex=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
        var flag = rex.test(no);
        var alertMsg=$("#alertMsg");
        if(!flag){
            // alertMsg.removeAttr("visibility");
            signFlag=false;
            if(no!=''){
                alertMsg.text("您输入的不是手机号!");
            }

        }else {
            signFlag=true;
            alertMsg.text("");
            if(pwdFlag&&rexPwd){//如果密码验证和密码规范都正确，可以注册
                register.removeAttr("disabled")
            }

        }
    });
    //登录的验证
    $("#sign-in-no").focusout(function () {
        var no=$("#sign-in-no").val();
        var rex=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
        var flag = rex.test(no);
        var alertMsg=$("#alertMsg");
        if(!flag){//如果手机号正确可以登录
            if(no!=''){
                alertMsg.text("您输入的不是手机号!");
            }
            signin.attr("disabled","disabled");
        }else {
            signin.removeAttr("disabled");
            alertMsg.text("");
        }
    });
    $(function () {//浏览器记住密码后，直接在这里验证
        var no=$("#sign-in-no").val();
        var rex=/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
        var flag = rex.test(no);
        var alertMsg=$("#alertMsg");
        if(!flag){//如果手机号正确可以登录
            signin.attr("disabled","disabled");
        }else {
            signin.removeAttr("disabled");
            alertMsg.text("");
        }
    });
    //获取验证码
    $(".btn-success").click(function () {
        var btn=$(".btn-success");
        var alertMsg=$("#alertMsg");
        if(signFlag){
            var no=$("#sign-up-no").val();
            $.ajax({
                url:"/send",
                data:"no="+no,
                datatype:"text",
                success:function(data){
                    second=60;//初始化倒计时总时间
                    if(data=="成功"){
                        setInterval(countDown,1000);
                    }else {
                        alertMsg.text(data);
                    }
                }
            })
        }else {
            alertMsg.text("请先填写手机号!")
        }
    })
})

function countDown() {
    var btn=$(".btn-success");
    if(second==0){
        btn.removeAttr("disabled");
        btn.text("获取验证码");
    }else {
        btn.attr("disabled","disabled");
        btn.text("重新获取("+second--+"秒)");
    }

}