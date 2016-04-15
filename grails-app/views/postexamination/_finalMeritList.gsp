<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 8/8/2014
  Time: 7:10 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>
<script type="text/css">
    table, th, td{
        border: 1px solid #b2b2b2;
    }
    table {
        border-collapse: collapse;
    }
    @page {
        size: 210mm 297mm; // A4 format
    }

    div.break {
        page-break-after:always;
    }
</script>

<body>
<div style="width: 100%;text-align: center;font-size: 18px;"><b>Gauhati University Institute of Science and Technology</b>
</div>
<div style="width: 100%;text-align: center;font-size: 15px;text-decoration: underline">Result of ${courseName} for Academic Session ${academicSession}</div>
<div style="width: 100%;text-align: center;font-size: 15px;"><b>Current Status: ${status}</b></div>
<g:if test="${studentRollNoList}">
    <table style="width: 100%;">
        <tr>
            <th>Sr. No.</th>
            <th>Name</th>
            <th>Roll No</th>
            <th>Marks</th>
            <th>Grade</th>
        </tr>
        <g:each in="${studentRollNoList}" var="stuInst" status="inde">
            <tr>
                <td>${inde + 1}</td>
                <td>${studentNameList[inde]}</td>
                <td>${stuInst}</td>
                <td>${marks[inde]}</td>
                <td>${gradeList[inde]}</td>
            </tr>
        </g:each>
    </table>
</g:if>
<g:else>
    NO STUDENT CLEARED IN ALL SUBJECTS
</g:else>
</body>
</html>