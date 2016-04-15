<%--
  Created by IntelliJ IDEA.
  User: chandan's
  Date: 27-05-2015
  Time: 17:45
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--For showing list of Department and provide button for deleting department--}%
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
        <h3>List of Department</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-1-2" id="courseTable" style="margin: auto;">
            <thead>
            <tr>
                <th style="width: 75%">Department Name</th>
                <th style="width: 25%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${programBranchList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${listInst.name}</td>
                    <td><g:link controller="admin" action="deleteDepartment" id="${listInst.id}" onclick="return confirm('Are you sure you want to Delete?');"><button class="university-button">delete</button></g:link> </td>
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
                    "targets": [ 1 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>

</body>
</html>