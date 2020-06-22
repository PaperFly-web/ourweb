$("#pdf").click(function () {
    window.open("/pdfjs/web/viewer.html?file=" +encodeURIComponent("http://localhost:8083/displayPDF"));
});