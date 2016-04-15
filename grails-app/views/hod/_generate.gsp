<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 30/4/14
  Time: 11:18 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Printable Report</title>
    %{--<meta name="layout" content="/main">--}%
    <style type="text/css">
    table.gridtable {
        width: 90%;
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width:1px;
        border-color: #666666;
        border-collapse: collapse;
    }
    table.gridtable th {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #dedede;
    }
    table.gridtable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #ffffff;
    }
    @page {
        size: 210mm 287mm;
        margin: 30px;
    }
    </style>

</head>

<body>
<div id="main">
    %{--<div>${student.firstName}  ${student.lastName}</div>--}%
    %{--<div>${student.rollNo}  </div>--}%
    %{--<div>${certificate}</div>--}%
    %{--<div>${student.semester}</div>--}%
    %{--<div></div>--}%
</div>
</body>
</html>