<%--
  Created by Damyant Software Pvt Ltd.
  User: chauhan611
  Date: 1/13/15
  Time: 11:17 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Students for Semester Approval</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1" id="courseTable">
            <thead>
            <tr>
                <th>Student Name</th><th>Roll No</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${studentList}" status="i" var="student">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <g:if test="${student.middleName}">
                        <td><label>${student.firstName} </label><label>${student.middleName} </label><label>${student.lastName}</label></td>
                    </g:if><g:else>
                    <td><label>${student.firstName}</label><label> ${student.lastName}</label></td>
                </g:else>
                    <td>${student.rollNo}</td>
                    <td> <g:link controller="student" action="registration" params="[studentID:student.id]" class="university-text-decoration-none"><button class="university-button">View</button></g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>

    </fieldset>
</div>

</body>
</html>