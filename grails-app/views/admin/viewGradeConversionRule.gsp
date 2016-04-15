<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for showing details of grade conversion rule--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>View Grade Conversion Rules</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${toList.size()>0}">
            <table class="inner university-size-1-2" style="margin: auto; font-size: 15px; line-height: 25px;text-align: center">
                <tr>
                    <th class="university-size-1-3">From</th><th class="university-size-1-3">To</th><th class="university-size-1-3">Grade</th>
                </tr>
                <g:each in="${gradeList}" var="grade" status="indx">
                    <tr><td>${fromList[indx]}</td><td>${toList[indx]}</td><td>${grade}</td></tr>
                </g:each>
            </table>
        </g:if>
        <g:else>
            <div style="text-align: center">No Role Available</div>
        </g:else>

    </fieldset>
</div>
</body>
</html>
