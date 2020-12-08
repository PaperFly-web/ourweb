var confirmColor='#55aaff';
$.ajax({
    url:"/selectJoinClass",
    dataType:"json",
    success:function(data){
        // alert(data)
        $("#jClass").html("");
        $.each(data,function (i,n) {
            $("#jClass").append(
                "<dd><a href='/file/"+n.cno+"/"+n.no+"/"+n.cname+"' class='uploadFile'>"+n.cname+"</a></dd>");
        })

    }
});
$.ajax({
    url:"/selectCreateClass",
    dataType:"json",
    success:function(data){
        // alert(data)
        $("#cClass").html("");
        $.each(data,function (i,n) {
            $("#cClass").append(
                "<dd><a href='/file/"+n.cno+"/"+n.cname+"' class='uploadFile'>"+n.cname+"</a></dd>");
        })

    }
});
$("#createClass").click(function () {
    var body=$(".layui-body");
    body.html("<div id='createClass' class='agile-its'>" +
        "            <h2>创建班级</h2>\n" +
        "<form action='/createClass'>\n" +
        "  <div class=\"form-group\">\n" +
        "    <input type=\"text\" class=\"form-control\" id=\"exampleInputName2\" placeholder=\"请输入班级名称\" name='cname' required>\n" +
        "  </div>\n" +
        "  <div class=\"form-group\">\n" +
        "    <input type=\"submit\" value=\"创建\" class=\"btn btn-success\">\n" +
        "  </div>\n" +
        "</form>" +
        "</div>")
});
$("#joinClass").click(function () {
    var body=$(".layui-body");
    body.html("<div id='createClass' class='agile-its'>" +
        "            <h2>加入班级</h2>\n" +
        "<form action='/joinClass'>\n" +
        "  <div class=\"form-group\">\n" +
        "    <input type=\"text\" class=\"form-control\" id=\"exampleInputName2\" placeholder=\"请输入班级号\" name='cno' required>\n" +
        "  </div>\n" +
        "  <div class=\"form-group\">\n" +
        "    <input type=\"submit\" value=\"加入\" class=\"btn btn-success\">\n" +
        "  </div>\n" +
        "</form>" +
        "</div>")
});
var signUserName=$("#signUserName");
var signTime=$("#signTime");
var toDaySignSum=0;
var toDaySignI=0;
var toDayignData;
$(function () {
    selectAllBlog();
    selectToDaySignIn();
    alertMsg();
});

function selectAllBlog() {
    $.ajax({
        url:"/selectAllBlogForSuccessView",
        type:"post",
        data:"page="+0+"&views="+1,
        dataType:"json",
        success:function(data){
            if(data==''){
                $("#showFile").html("")
                $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                    "<h4>空空如也</h4>" +
                    " </div>");
            }else {
                $("#showFile").html("");
                for (var i=(data.length-1);i>=0;i--){
                    $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                        "<h4>" + data[i].blogTitle + "</h4>" +
                        "<p>作者:" + data[i].userName + "</p>" +
                        "<p>浏览量:" + data[i].views + "</p>" +
                        "<p>" + data[i].createTime + "</p>" +
                        "   <a target='_blank' href='/pdfjs/web/viewer.html?file=/"+encodeURIComponent("displayPDF/?blogFilePath="+data[i].blogFilePath+"&blogFileName="+data[i].blogFileName+"&blogFileId="+data[i].blogFileId+"&no="+data[i].no)+"'>"+data[i].blogFileName+"</a>\n" +
                        " </div>");
                }
            }
        }
    })
}

var isSignInOk='签到成功!';
function signIn() {
    $.ajax({
        url:"/signIn",
        type:"post",
        dataType:"text",
        success:function(data){
            if(data!=''){
                isSignInOk='签到失败,今日已签过!';
                confirmColor='#55aaff'
                showModalButton1();
            }else {
                isSignInOk='签到成功!';
                confirmColor='#55aaff'
                selectToDaySignIn();
                showModalButton1();
            }
        }
    })
}
function alertMsg() {
    var msg= $("#msg").val();
    if(msg!=''){
        isSignInOk=msg;
        confirmColor='#6effb5';
        showModalButton1();
    }
}
function selectToDaySignIn() {
    $.ajax({
        url:"/selectToDaySignIn",
        type:"post",
        dataType:"json",
        success:function(data){
            toDaySignSum=data.length;
            toDayignData=data;
            signUserName.text(data[0].userName);
            signTime.text("今天:"+(data[0].createTime).substring(10)+"签到成功");
            setInterval(addSignMsg,3500);
        }
    })
}

function showModalButton1(){
    showModal({
        title: isSignInOk,  //提示的标题
        content: "",  //提示的内容
        showCancel: false,  //是否显示取消按钮，默认为 true
        cancelText: 'no',  //取消按钮的文字，默认为"取消"，最多 4 个字符
        cancelColor: "#fff",  //取消按钮的文字颜色，默认为"#000000"
        cancelBgColor: '#4c4c4c',  //取消按钮的背景颜色
        confirmText: 'OK',
        confirmColor: '#fff',
        confirmBgColor: confirmColor,
        success: function(res) {
            if (res.confirm) {
                console.log('yes');
            } else {
                console.log('no');
            }
        }
    });
}
function addSignMsg() {
    toDaySignI++;
    if(toDaySignI==toDaySignSum){
        toDaySignI=0;
    }
    signUserName.text(toDayignData[toDaySignI].userName);
    signTime.text("今天:"+(toDayignData[toDaySignI].createTime).substring(10)+"签到成功");

}
