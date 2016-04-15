<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing list of guide(provide button for deleting particular guide)--}%
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
        <h3>List of Guide</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1  university-table-text-left" id="courseTable">
            <thead>
            <tr>
                <th style="width: 20%">Branch</th>
                <th style="width: 20%">Academic Session</th>
                <th style="width: 20%">Guide Name</th>
                <th style="width: 20%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${guideList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${listInst.programBranch.name}</td>
                    <td>${listInst.academicSession}</td>
                    <td>${listInst.employee.firstName} ${listInst.employee.middleName?listInst.employee.middleName:''} ${listInst.employee.lastName}</td>
                    <td><g:link controller="admin" action="deleteGuide" id="${listInst.id}"  onclick="return confirm('Are you sure you want to Delete?');">Delete</g:link> </td>
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
                    "targets": [ 3 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>

</body>
</html>