
$(function () {
    var cno=$("#articleCno").val();
    var no=$("#currentNo").val();
    $.post(
        "/selectArticleByCno",
        "cno="+cno,
        function (data) {
            $("#articlesTable").html("")
            $.each(data, function (i, n) {
                if (no == n.no) {
                    $("#articlesTable").append(
                        "<tr><td>&nbsp;</td></tr>" +
                        "<tr>" +
                        "   <td align='center'><span class='spanCurrStuNum'>" + n.userName + "</span>&nbsp;&nbsp;</td>" +
                        "   <td><span class='spanArticles'><div class='alert alert-success' role='alert'>" + n.article + "</div><span class='spanTime'>" + n.articleCreateTime + "</span></span></td>"
                    )
                } else {
                    $("#articlesTable").append(
                        "<tr><td>&nbsp;</td></tr>" +
                        "<tr>" +
                        "   <td align='center'><span class='spanStuNum'>" + n.userName + "</span>&nbsp;&nbsp;</td>" +
                        "   <td><span class='spanArticles'><div class='alert alert-success' role='alert'>" + n.article + "</div><span class='spanTime'>" + n.articleCreateTime + "</span></span></td>"
                    )
                }
            })
        },
        "json"
    );
    $("#btn").click(function () {
        $.post(
            "addArticle",
            "article=" + $("#textarea").val()+"&cno="+cno,
            function (data) {
                $("#articlesTable").html("")
                $("#textarea").val("");
                $.each(data, function (i, n) {
                    if (no == n.no) {
                        $("#articlesTable").append(
                            "<tr><td>&nbsp;</td></tr>" +
                            "<tr>" +
                            "   <td align='center'><span class='spanCurrStuNum'>" + n.userName + "</span>&nbsp;&nbsp;</td>" +
                            "   <td><span class='spanArticles'><div class='alert alert-success' role='alert'>" + n.article + "</div><span class='spanTime'>" + n.articleCreateTime + "</span></span></td>"
                        )
                    } else {
                        $("#articlesTable").append(
                            "<tr><td>&nbsp;</td></tr>" +
                            "<tr>" +
                            "   <td align='center'><span class='spanStuNum'>" + n.userName + "</span>&nbsp;&nbsp;</td>" +
                            "   <td><span class='spanArticles'><div class='alert alert-success' role='alert'>" + n.article + "</div><span class='spanTime'>" + n.articleCreateTime + "</span></span></td>"
                        )
                    }

                })
            },
            "json"
        )
    })

})