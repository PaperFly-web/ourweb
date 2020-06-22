$(function () {
    var page=0;
    var fileMaxSize=$("#fileMaxSize").val();
    var cno=$("#createCnoValue").val();
    var cname=$("#createCnameValue").val();
    var classTaskId=$("#classTaskIdValue").val();
    var taskName=$("#taskNameValue").val();
    var userName=$("#createNameValue").val();
    var createNo=$("#createNoValue").val();
    $("#uploadFile").click(function () {
        $("#showFile").html("")
        $("#showFile").html("<div class=\"agile-its\">\n" +
            "                    <h2>上传文件</h2>\n" +
            "                    <div class=\"w3layouts\">\n" +
            "                        <p>文件不超过"+fileMaxSize+"M</p>\n" +
            "                        <div class=\"photos-upload-view\">\n" +
            "                            <form id=\"upload\" action=\"/upload\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
            "                                <div class=\"agileinfo\" id='agileinfo'>\n" +
            "                                    <input type=\"submit\" class=\"choose\" value=\"选择文件\">\n" +
            "                                    <input type=\"hidden\" name=\"cno\" value=\""+cno+"\">\n" +
            "                                    <input type=\"hidden\" name=\"classTaskId\" value=\""+classTaskId+"\">\n" +
            "                                    <input type=\"hidden\" name=\"taskName\" value=\""+taskName+"\">\n" +
            "                                    <input type=\"hidden\" name=\"userName\" value=\""+userName+"\">\n" +
            "                                    <input type=\"hidden\" name=\"no\" value=\""+createNo+"\">\n" +
            "                                    <input type=\"file\" id=\"fileselect\" name=\"file\">\n" +
            "                                </div>\n" +
            "                                <br>\n" +
            "                                <div class=\"form-group\">\n" +
            "                                    <input type=\"submit\" value=\"提交\" class=\"btn btn-success\" disabled>\n" +
            "                                </div>\n" +
            "                                <div id=\"messages\">\n" +
            "                                    <p></p>\n" +
            "                                </div>\n" +
            "                            </form>\n" +
            "                        </div>\n" +
            "                        <div class=\"clearfix\"></div>\n" +
            "                    </div>\n" +
            "                </div>")
    });
    $("#showFile").on("change","#fileselect",function () {
        var file = document.getElementById("fileselect").files[0];
        var fileSize=(file.size/1024)/1024;
        fileSize=fileSize.toFixed(2);//保留俩位小数
        $("#messages").html("<p>名称: <strong>" + file.name +
            "<br>"+
            "</strong> 类型: <strong>" + file.type +
            "<br>"+
            "</strong> 大小: <strong>" + fileSize +
            "</strong> M</p>")
        if(file.size>fileMaxSize*1024*1024){
            $(".btn-success").attr("disabled","disabled");
        }else {
            $(".btn-success").removeAttr("disabled");
        }
    });
    $("#fileselect").change(function () {
        var file = document.getElementById("fileselect").files[0];
        var fileSize=(file.size/1024)/1024;
        fileSize=fileSize.toFixed(2);//保留俩位小数
        $("#messages").html("<p>名称: <strong>" + file.name +
            "<br>"+
            "</strong> 类型: <strong>" + file.type +
            "<br>"+
            "</strong> 大小: <strong>" + fileSize +
            "</strong> M</p>")
        if(file.size>fileMaxSize*1024*1024){
            $(".btn-success").attr("disabled","disabled");
        }else {
            $(".btn-success").removeAttr("disabled");
        }
    });
    $("#myFile").click(function () {
        $.ajax({
            url:"/selectThisClassFile",
            data:"cno="+cno+"&classTaskId="+classTaskId+"&page=0",
            dataType:"json",
            success:function(data){
                if(data==''){
                    $("#showFile").html("")
                    $("#showFile").append("<div class=\"alert alert-success\" role=\"alert\" id='news'>您还未在此任务上传过文件!</div>");
                }else {
                    $("#showFile").html("")
                    $.each(data,function (i,n) {
                        $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                            "<h4>"+n.fileName+"</h4>"+
                            "<p>"+n.createTime+"</p>"+
                            "   <form action=\"/download\" method=\"post\">\n" +
                            "       <input type=\"hidden\" value=\""+n.filePath+"\" name=\"filePath\" >\n" +
                            "       <input type=\"hidden\" value=\""+n.fileName+"\" name=\"fileName\">\n" +
                            "       <button type=\"submit\"  class=\"myFile btn-success btn\">下载</button>" +
                            "   </form>\n" +
                            "   <a href='/deleteOneFile/?filePath="+n.filePath+"&no="+createNo+"&cno="+cno+"&cname="+cname+"&userName="+userName+"&classTaskId="+classTaskId+"'>删除</a>\n" +
                            " </div>");
                    });
                    $("#showFile").append("<nav style='position: fixed;left: 43%;top: 85%' aria-label=\"Page navigation\">\n" +
                        "                            <ul class=\"pagination\">\n" +
                        "                            <li>\n" +
                        "                            <a href=\"#\" aria-label=\"Previous\" class='page'>\n" +
                        "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                        "                        </a>\n" +
                        "                        </li>\n" +
                        "                        <li><a href=\"#\" class='page'>1</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>2</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>3</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>4</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>5</a></li>\n" +
                        "                        <li>\n" +
                        "                        <a href=\"#\" aria-label=\"Next\" class='page'>\n" +
                        "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                        "                        </a>\n" +
                        "                        </li>\n" +
                        "                        </ul>\n" +
                        "                        </nav>")
                }
            }
        })
    });
    $("#showFile").on("click",".page",function () {

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
            url:"/selectThisClassFile",
            data:"cno="+cno+"&classTaskId="+classTaskId+"&page="+page,
            dataType:"json",
            success:function(data){
                if(data==''){
                    $("#showFile").html("")
                    $("#showFile").append("<div class=\"alert alert-success\" role=\"alert\" id='news'>已经是最后了!</div>" +
                        "<nav aria-label=\"Page navigation\"  style='position: fixed;left: 43%;top: 85%'>\n" +
                        "  <ul class=\"pagination\">\n" +
                        "    <li>\n" +
                        "      <a href=\"#\" aria-label=\"Previous\" class='page'>\n" +
                        "        <span aria-hidden=\"true\">&laquo;</span>\n" +
                        "      </a>\n" +
                        "    </li>\n" +
                        "    <li><a href=\"#\" class='page'>1</a></li>\n" +
                        "    <li><a href=\"#\" class='page'>2</a></li>\n" +
                        "    <li><a href=\"#\" class='page'>3</a></li>\n" +
                        "    <li><a href=\"#\" class='page'>4</a></li>\n" +
                        "    <li><a href=\"#\" class='page'>5</a></li>\n" +
                        "    <li>\n" +
                        "      <a href=\"#\" aria-label=\"Next\" class='page'>\n" +
                        "        <span aria-hidden=\"true\">&raquo;</span>\n" +
                        "      </a>\n" +
                        "    </li>\n" +
                        "  </ul>\n" +
                        "</nav>");
                }else {
                    $("#showFile").html("")
                    $.each(data,function (i,n) {
                        $("#showFile").append("<div class='newsbox'  overflow='hidden'>\n" +
                            "<h4>"+n.fileName+"</h4>"+
                            "<p>"+n.createTime+"</p>"+
                            "   <form action=\"/download\" method=\"post\">\n" +
                            "       <input type=\"hidden\" value=\""+n.filePath+"\" name=\"filePath\" >\n" +
                            "       <input type=\"hidden\" value=\""+n.fileName+"\" name=\"fileName\">\n" +
                            "       <button type=\"submit\" value=\""+n.fileName+"\" class=\"myFile btn-success btn\">下载</button>" +
                            "   </form>\n" +
                            "   <a href='/deleteOneFile/?filePath="+encodeURIComponent(n.filePath)+"&no="+createNo+"&cno="+cno+"&cname="+cname+"&userName="+userName+"&classTaskId="+classTaskId+"'>删除</a>\n" +
                            " </div>");
                    });
                    $("#showFile").append("<nav style='position: fixed;left: 43%;top: 85%' aria-label=\"Page navigation\">\n" +
                        "                            <ul class=\"pagination\">\n" +
                        "                            <li>\n" +
                        "                            <a href=\"#\" aria-label=\"Previous\" class='page'>\n" +
                        "                            <span aria-hidden=\"true\">&laquo;</span>\n" +
                        "                        </a>\n" +
                        "                        </li>\n" +
                        "                        <li><a href=\"#\" class='page'>1</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>2</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>3</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>4</a></li>\n" +
                        "                        <li><a href=\"#\" class='page'>5</a></li>\n" +
                        "                        <li>\n" +
                        "                        <a href=\"#\" aria-label=\"Next\" class='page'>\n" +
                        "                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                        "                        </a>\n" +
                        "                        </li>\n" +
                        "                        </ul>\n" +
                        "                        </nav>")
                }
            }
        })



    });
});



