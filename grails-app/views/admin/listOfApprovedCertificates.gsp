<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Shows list of certificates which are approved by admin or certificate admin--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <title></title>
</head>

<body>

<div id="main">
    <fieldset class="form">
        <h3>List of Certificates</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1 tableData university-table-text-left" id="courseTable" >
            <thead>
            <tr>
                <th style="width: 5%">Sl No.</th>
                <th style="width: 20%">Requested by</th>
                <th style="width: 10%">Roll No / Employee Id</th>
                <th style="width: 10%">Semester</th>
                <th style="width: 10%">Department</th>
                <th style="width: 12%">Type of Certificate</th>
                <th style="width: 10%">Date & Time</th>
                <th style="width: 10%">IP Address</th>
                <th style="width: 10%">Remarks</th>
                <th style="width: 8%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${nameList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${(i + 1)}</td>
                    <td>${listInst}</td>
                    <td>${rollOrEid[i]}</td>
                    <td>${semester[i]}</td>
                    <td>${deptList[i]}</td>
                    <td>${certificateTypeList[i]}</td>
                    <td>${timeList[i]}</td>
                    <td>${ipAddress[i]}</td>
                    <td>${remarks[i]}</td>
                    <td>
                        <g:link controller="certificateTemp" action="loadCorrectMethod" target="_blank" id="${requestId[i]}" params="[certificate:certificateTypeIdList[i]]"><button class="btn btn-default">View</button></g:link>
                    </td>
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