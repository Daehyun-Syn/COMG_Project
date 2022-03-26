<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%

    //전달받은 메시지
    String msg = CmmUtil.nvl((String)request.getAttribute("msg"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SeeSay</title>

    <script src="${pageContext.request.contextPath}/static/resource/js/jquery-3.6.0.min.js"></script>

</head>
<body>

</body>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
    swal({
        title: "Wow!",
        text: "Message!",
        type: "success"
    }).then(function() {
        window.location = "/notice/NoticeList";
    });
</script>
</html>