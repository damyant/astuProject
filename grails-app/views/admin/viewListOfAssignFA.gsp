<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing list of assigned faculty advisor and provide option to delete particular faculty advisor--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    %{--<script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>--}%

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>

    <style type="text/css" class="init"></style>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of Faculty Advisors</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1" id="courseTable">
            <thead>
            <tr>
                <th style="width: 15%">Username</th>
                <th style="width: 10%">Employee Code</th>
                <th style="width: 25%">Program</th>
                <th style="width: 10%">Semester</th>
                <th style="width: 15%">Academic Session</th>
                <th style="width: 15%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${facultyAdvisorList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${listInst.user.username}</td>
                    <td>${listInst.user.employeeCode}</td>
                    <td>${listInst.programDetail.courseName}</td>
                    <td>${listInst.semester.semesterNo}</td>
                    <td>${listInst.academicSession}</td>
                    <td><g:link controller="admin" action="deleteAssignedFA" id="${listInst.id}" params=""><button onclick="return confirm('Are you sure you want to delete this?');"class="university-button">Delete</button></g:link> </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        %{--<div class="paginateButtons">--}%
            %{--<g:paginate controller="admin" action="viewListOfAssignFA" total="${facultyAdvisorTotal}"/>--}%
        %{--</div>--}%

    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $('#courseTable').dataTable( {
            "columnDefs": [
                {
                    "targets": [ 5 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>
</body>
</html>