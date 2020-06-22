$.ajax({
    url:"/selectCreateTask",
    dataType:"json",
    success:function(data){
        $("#task").html("");
        $.each(data,function (i,n) {
            $("#task").append(
                "<dd><a href='/task/"+n.cno+"/"+n.classTaskId+"' class='task'>"+n.taskName+"</a></dd>");
        })
    }
});
$(function () {
    var page=0;
    var classTaskId=$("#classTaskIdValue").val();
    $(".page").click(function () {
        var tem=$(this).text();
        tem=tem.trim();
        if(tem=="«"){
            if(page>0){
                page=parseInt(page)-1;
            }else {
                alert("已经是第一页了!");
                return;
            }
        }else if(tem=="»"){
            page=parseInt(page)+1;
        }else {
            page=parseInt(tem)-1;
        }
        $.ajax({
            url:"/myClassTask",
            data:"classTaskId="+classTaskId+"&page="+page,
            dataType:"json",
            success:function(data){
                if(data==''){
                    $("#showFile").html("")
                    $("#showFile").append("<div class='container'>\n" +
                        "                    <div class='row'>\n" +
                        "                        <div class='col-md-4 col-md-offset-4'>\n" +
                        "                                <div class='form-group'>\n" +
                        "                                    <div class='alert alert-success' role='alert'  >已经是最后了!</div>\n" +
                        "                                </div>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>");
                }else {
                    $("#showFile").html("")
                    $.each(data,function (i,n) {
                        var size;
                        if(n.size/1024<1){
                            size=n.size+"B";
                        }else if(n.size/1024>=1 && n.size/(1024*1024)<1){
                            size=(n.size/1024).toFixed(0)+"K";
                        }else if(n.size/(1024*1024)>=1){
                            size=(n.size/(1024*1024)).toFixed(0)+'M';
                        }
                        $("#showFile").append(
                            "                <div class=\"newsbox\">\n" +
                            "                    <h4>"+n.fileName+"</h4>\n" +
                            "                        <p  >"+n.userName+":"+n.createTime+"</p>\n" +
                            "                        <p  >大小:"+size+"</p>\n" +
                            "                    <form action='/download' method=\"post\">\n" +
                            "                        <input type=\"hidden\" value='"+n.filePath+"'  name=\"filePath\" >\n" +
                            "                        <input type=\"hidden\" value='"+n.fileName+"'  name=\"fileName\">\n" +
                            "                        <button type=\"submit\" class=\"btn btn-success\" width=\"50%\" >下载</button>\n" +
                            "                    </form>\n" +
                            "                </div>\n");
                    });

                }
            }
        })



    });
});
