<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    //전달받은 메시지
    String alert_title = CmmUtil.nvl((String)request.getAttribute("alert_title"));
    String alert_contents = CmmUtil.nvl((String)request.getAttribute("alert_contents"));
    String alert_state = CmmUtil.nvl((String)request.getAttribute("alert_state"));
    String alert_aftersending = CmmUtil.nvl((String)request.getAttribute("alert_aftersending"));
%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>COMG</title>
    <script src="/js/jquery-3.6.0.min.js"></script>


</head>
<body>

</body>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
    swal('<%=alert_title%>', "<%=alert_contents%>", '<%=alert_state%>')
        .then(function(){
            location.href="/<%=alert_aftersending%>";
        });
</script>
</html>