<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing list of subjects and provide option to edit the subject details--}%
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
        <h3>List of Courses</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1  university-table-text-left" id="courseTable">
            <thead>
            <tr>
                <th style="width: 15%">Course Code</th>
                <th style="width: 50%">Course Name</th>
                <th style="width: 10%">Credit</th>
                <th style="width: 15%">Session Of Course</th>
                <th style="width: 10%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${subSessionList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${listInst.subjectId.subjectCode}</td>
                    <td>${listInst.subjectId.subjectName}</td>
                    <td>${listInst.subjectId.creditPoints}</td>
                    <td>${listInst.sessionOfSubject}</td>
                    <td><g:link controller="admin" action="addCourses" target="_blank" id="${listInst.id}" params="">Edit</g:link> </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $('#courseTable').dataTable( {
            "columnDefs": [
                {
                    "targets": [ 2,3,4 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>

</body>
</html>