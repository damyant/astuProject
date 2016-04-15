<%--
  Created by IntelliJ IDEA.
  User: chauhan611
  Date: 1/20/15
  Time: 3:29 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script>
        function reloadPage(val){
            console.log(val.id)
            var rowNo= val.id
            $('.'+rowNo).prop('hidden', true)
//            location.reload();
        }
    </script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List for Certificate Request</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1" id="courseTable">
            <thead>
            <tr>
                <th>Student Name</th><th>Roll No</th><th>Certificate Type</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${requestedCertificateList}" status="i" var="requestedCertificate">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}  ${i+1} ">
                    <g:if test="${requestedCertificate.student.middleName}">
                        <td>${requestedCertificate.student.firstName} ${requestedCertificate.student.middleName} ${requestedCertificate.student.lastName}</td>
                    </g:if><g:else>
                    <td>${requestedCertificate.student.firstName} ${requestedCertificate.student.lastName} </td>
                </g:else>
                    <td>${requestedCertificate.student.rollNo}</td>
                    <td>${requestedCertificate.certificate.nameOfCertificate}</td>
                    <td> <g:link controller="hod" action="approveCertificateRequest" params="[studentID:requestedCertificate.student.id, certificateId:requestedCertificate.certificate.id]" class="university-text-decoration-none"><button class="university-button" id="${i+1}" onclick="reloadPage(this)">Approve</button></g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>

    </fieldset>
</div>


</body>
</html>