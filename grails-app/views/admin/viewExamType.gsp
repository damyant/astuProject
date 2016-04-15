<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing list of exam type--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shCore.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'shCore.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
    %{--<script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of Examination</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1" id="courseTable" style="margin: auto;">
            <thead>
            <tr>
                <th style="text-align: center">Examination</th>
                <th style="text-align: center">Required Total Marks</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${examTypeList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td style="text-align: center;">${listInst.examTypeName}</td>
                    <td style="text-align: center;">${listInst.requiredTotalMarks}</td>
                    <td style="text-align: center;"><g:link controller="admin" action="deleteExamType" id="${listInst.id}"
                                                             onclick="return confirm('Are you sure you want to delete?')"><button class="university-button">delete</button></g:link></td>
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
                    "targets": [],
                    "searchable": false
                }
            ]
        } );
    } );

</script>
</body>
</html>