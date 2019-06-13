<%--
  Created by IntelliJ IDEA.
  User: Daotu
  Date: 07/06/2019
  Time: 3:09 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>I hate my teacher</title>
  </head>
  <jsp:include page="include.jsp"/>
  <body>
  <center><h3><button type="button" id="btnGoAddPage" class="btn btn-primary">Lét Sờ Tát Tỵt :))</button> </h3></center>
  </body>
<script>
  $('#btnGoAddPage').click(function () {
    location.href = '/selector';
  });
</script>
</html>
