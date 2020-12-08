var views='1';
var blogPage=0;
var noPage=0;
$(function () {
    var allOrMyself=true;
    $(".blogPage").click(function () {
        var blogPageVal=$(this).text();
        blogPageVal=blogPageVal.trim();
        if(blogPageVal=="«"){
            if(blogPage>0){
                blogPage=blogPage-1;
            }else {
                alert("已经是第一页了!");
                return;
            }
        }else if(blogPageVal=="»"){
            blogPage=blogPage+1;
        }else {
            blogPage=parseInt(blogPageVal)-1;
        }
        if(allOrMyself){
            selectAllBlog();
        }else {
            showMySelfBlog();
        }
    });
    $("#classNo").on("click",".noPage",function () {
       var noPageVal=$(this).text();
       noPageVal=noPageVal.trim();
       if(noPageVal=="«"){
           if(noPage>0){
               noPage=noPage-1;
           }else {
               alert("已经是第一页了")
               return;
           }
       }else if(noPageVal=="»"){
           noPage=noPage+1;
       }
       selectAllUser();
    });
    $("#byHot").click(function () {
        // alert("根据热度")
        views='1';
        if(allOrMyself){
            selectAllBlog();
        }else {
            showMySelfBlog();
        }
    });
    $("#byTime").click(function () {
        // alert("根据时间")
        views='0';
        if(allOrMyself){
            selectAllBlog();
        }else {
            showMySelfBlog();
        }
    });

    $("#showMySelfBlog").click(function () {
        allOrMyself=false;
        showMySelfBlog();
    });
    $("#showAllBlog").click(function () {
        allOrMyself=true;
        selectAllBlog();
    });
    selectAllBlog();
    selectAllUser();
});
function selectAllUser() {
    $.ajax({
        url:"/selectAllUser",
        type:"post",
        data:"page="+noPage,
        dataType:"json",
        success:function(data){
            if(data==''){
                $("#classNo").html("");
                $("#classNo").append("<div class=\"classNoBox\" overflow=\"hidden\">\n" +
                    "<a href=\"#\" aria-label=\"Previous\" class=\"noPage\">\n" +
                    "                                <span aria-hidden=\"true\">&laquo;</span>\n" +
                    "                            </a>"+
                    "            </div>");
                $("#classNo").append("<div class=\"classNoBox\" overflow=\"hidden\">\n" +
                    "<a href=\"#\" aria-label=\"Next\" class=\"noPage\">\n" +
                    "                                <span aria-hidden=\"true\">&raquo;</span>\n" +
                    "                            </a>"+
                    "            </div>")

            }else {
                $("#classNo").html("")
                $("#classNo").append("<div class=\"classNoBox\" overflow=\"hidden\">\n" +
                    "<a href=\"#\" aria-label=\"Previous\" class=\"noPage\">\n" +
                    "                                <span aria-hidden=\"true\">&laquo;</span>\n" +
                    "                            </a>"+
                    "            </div>");
                $.each(data,function (i,n) {

                    $("#classNo").append("<div class=\"classNoBox\" overflow=\"hidden\">\n" +
                        "                <a href='/personalBlog?views=1&page=0&no="+n.no+"' >"+n.userName+"</a>\n" +
                        "            </div>");
                });
                $("#classNo").append("<div class=\"classNoBox\" overflow=\"hidden\">\n" +
                    "<a href=\"#\" aria-label=\"Next\" class=\"noPage\">\n" +
                    "                                <span aria-hidden=\"true\">&raquo;</span>\n" +
                    "                            </a>"+
                    "            </div>")
            }
        }
    })
}
function selectAllBlog() {
    $.ajax({
        url:"/selectAllBlog",
        type:"post",
        data:"page="+blogPage+"&views="+views,
        dataType:"json",
        success:function(data){
            if(data==''){
                $("#showFile").html("")
                $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                    "<h4>空空如也</h4>" +
                    " </div>");
            }else {
                $("#showFile").html("")
                $.each(data,function (i,n) {
                    $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                        "<h4>" + n.blogTitle + "</h4>" +
                        "<p>作者:" + n.userName + "</p>" +
                        "<p>浏览量:" + n.views + "</p>" +
                        "<p>" + n.createTime + "</p>" +
                        "   <a target='_blank' href='/pdfjs/web/viewer.html?file=/"+encodeURIComponent("displayPDF/?blogFilePath="+n.blogFilePath+"&blogFileName="+n.blogFileName+"&blogFileId="+n.blogFileId+"&no="+n.no)+"'>"+n.blogFileName+"</a>\n" +
                        " </div>");
                })
            }
        }
    })
}
function showMySelfBlog(){
    $.ajax({
        url:"/selectMySelfBlog",
        type:"post",
        data:"page="+blogPage+"&views="+views,
        dataType:"json",
        success:function(data){
            var isOpen;
            var isOpenInfo;
            if(data==''){
                $("#showFile").html("")
                $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                    "<h4>空空如也</h4>" +
                    " </div>");
            }else {
                $("#showFile").html("")
                $.each(data,function (i,n) {
                    if(n.isOpen=='1'){
                        isOpen='已公开';
                        isOpenInfo='私有';
                    }else {
                        isOpen='未公开';
                        isOpenInfo='公开';
                    }
                    $("#showFile").append("<div class='newsbox' overflow='hidden'>\n" +
                        "<h4>" + n.blogTitle + "</h4>" +
                        "<p>作者:" + n.userName + "</p>" +
                        "<p>浏览量:" + n.views + "</p>" +
                        "<p>资源:" + isOpen + "&nbsp;&nbsp;<a href='/isOpen?blogFileId="+encodeURIComponent(n.blogFileId)+"&isOpen="+encodeURIComponent(n.isOpen)+"'>"+isOpenInfo+"</a></p>" +
                        "<p>" + n.createTime + "</p>" +
                        "   <a target='_blank' href='/pdfjs/web/viewer.html?file=/"+encodeURIComponent("displayPDF/?blogFilePath="+n.blogFilePath+"&blogFileName="+n.blogFileName+"&blogFileId="+n.blogFileId+"&no="+n.no)+"'>"+n.blogFileName+"</a>\n" +
                        "   <a style='color: #ff9281' href='/deleteOneBlogFile/?blogFilePath="+encodeURIComponent(n.blogFilePath)+"/&blogFileId="+encodeURIComponent(n.blogFileId)+"'>删除</a>\n" +
                        " </div>");
                })
            }
        }
    })
}
