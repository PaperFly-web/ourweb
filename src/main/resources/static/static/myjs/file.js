$(function () {
    var cno=$("#createCnoValue").val();
    var createName=$("#createNameValue").val();
    var createNo=$("#createNoValue").val();
    var createCname=$("#createCnameValue").val();
    $.ajax({
        url:"/selectCreateTask",
        data:"cno="+cno,
        dataType:"json",
        success:function(data){
            $("#task").html("");
            $.each(data,function (i,n) {
                $("#task").append(
                    "<dd><a href='/myTask/?cno="+n.cno+"&classTaskId="+n.classTaskId+"&userName="+encodeURIComponent(createName)+"&no="+createNo+"&cname="+createCname+"&taskName="+encodeURIComponent(n.taskName)+"' class='task'>"+n.taskName+"</a></dd>");
            })
        }
    });
});

