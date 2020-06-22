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
})