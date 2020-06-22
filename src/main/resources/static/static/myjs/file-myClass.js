$(function () {
    var cno=$("#cnoValue").val();
    var cname=$("#cnameValue").val();
    $("#createTask").click(function () {
        var body = $(".layui-body");
        body.html("<div id=\"create\" class=\"agile-its\">          \n" +
            "            <h2>创建班级任务</h2>\n" +
            "            <form action=\"/createTask\">\n" +
            "                <div class=\"form-group\">\n" +
            "                    <input type=\"text\" class=\"form-control\" id=\"exampleInputName2\" placeholder=\"请输入任务名称\" name=\"taskName\" required=\"\">\n" +
            "                    <input type=\"hidden\" value='"+cname+"'  name=\"cname\" required=\"\">\n" +
            "                    <input type=\"hidden\" value='"+cno+"'  name=\"cno\" required=\"\">\n" +
            "                </div>\n" +
            "                <div class=\"form-group\">\n" +
            "                    <input type=\"submit\" value=\"创建\" class=\"btn btn-alert\">\n" +
            "                </div>\n" +
            "            </form>\n" +
            "        </div>")
    });
    $(function () {
        cno=$("#cnoValue").val();
        cname=$("#cnameValue").val();
        $.ajax({
            url:"/selectCreateTask",
            dataType:"json",
            data:"cno="+cno,
            success:function(data){
                $("#task").html("");
                $.each(data,function (i,n) {
                    $("#task").append(
                        "<dd><a href='/task/?page=0&cno="+n.cno+"&classTaskId="+n.classTaskId+"&cname="+cname+"&taskName="+n.taskName+"' class='task'>"+n.taskName+"</a></dd>");
                })
            }
        });
    });
});
