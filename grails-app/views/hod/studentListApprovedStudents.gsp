<%--
  Created by IntelliJ IDEA.
  User: chauhan611
  Date: 1/20/15
  Time: 3:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing list of students (whose registration has been approved)--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of Approved Students</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1" id="courseTable">
            %{--<input type="text" name="searchStudent" id="searchStudent" placeholder="Search by Roll No" size="21" maxlength="120"><input type="button" onclick="searchApprovedStudent()" value="search">--}%
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
                        <td>${student.firstName} ${student.middleName} ${student.lastName}</td>
                    </g:if>
                    <g:else>
                        <td>${student.firstName} ${student.lastName}</td>
                    </g:else>
                    <td>${student.rollNo}</td>
                    <td> <g:link controller="student" action="registration" params="[studentID:student.id, view:'view']" class="university-text-decoration-none"><button class="university-button">View</button></g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('#courseTable').dataTable({
            "columnDefs": [
                {
                    "targets": [ 2],
                    "searchable": false
                }
            ]
        });
    });

</script></body>
</html>