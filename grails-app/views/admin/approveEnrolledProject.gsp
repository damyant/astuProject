<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page import="examinationproject.ProjectDomain; examinationproject.GuideDetails" contentType="text/html;charset=UTF-8" %>
%{--for List of projects which is not approved(unapproved project)--}%
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
        <h3>List of UnApproved Project Details</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1  university-table-text-left" id="courseTable">
            <thead>
            <tr>
                <th style="width: 15%">Student Name</th>
                <th style="width: 50%">Student Roll No</th>
                <th style="width: 10%">Project Title</th>
                <th style="width: 15%">Domain</th>
                <th style="width: 15%">Institute</th>
                <th style="width: 15%">Guide Name</th>
                <th style="width: 15%">Subject Name</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${projectDetailsList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td>${listInst.student.firstName} ${listInst.student.middleName ? listInst.student.middleName : "" } ${listInst.student.lastName}</td>

                    <td>${listInst.student.rollNo}</td>
                    <td>${listInst.projectTitle}</td>
                    %{--<td>${listInst.domain}</td>--}%
                    <td>${examinationproject.ProjectDomain.findById(Long.parseLong(listInst.domain)).domainName}</td>
                    <td>${listInst.nameOfInstitution}</td>
                    <td>${nameList[i]}</td>
                    <td>${listInst.subject.subjectName}</td>
                    <td><g:link controller="admin" action="approveProjectDetails"  id="${listInst.id}" params="">Approve</g:link> </td>
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