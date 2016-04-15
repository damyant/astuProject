<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/29/14
  Time: 12:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Interface for requesting certificate--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src="admin.js"/>
    <g:javascript src="postExamination.js"/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:form controller="student" action="submitCertificateRequest" id="certificate" name="certificate">
            <h3>Request Certificate</h3>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="inner" style="margin-top: 30px;">
                <tr>
                    <td>Select Certificate Type</td>
                    <td>
                        <select name="certificate" id="certificate" class="university-size-1-2" onchange="loadRequiredFields(this)">
                            <option value>Select Certificate</option>

                            <g:each in="${certificateList}" status="i" var="listInst">
                                <option value="${listInst.id}">${listInst.nameOfCertificate}</option>
                            </g:each>
                        </select>
                    </td>
                </tr>
                <tr class="experiance" style="display: none;">
                    <td>Date of release</td>
                    <td>
                        <input type="text" class="university-size-1-2" name="dateOfRelease" id="dateOfRelease">
                    </td>
                </tr>
                <tr class="internship" style="display: none;">
                    <td>Add Roll No</td>
                    <td>
                        <input type="text" class="col-sm-5"  id="rollNo">
                        <input type="button" class="col-sm-2 btn" value="Add" onclick="addRollNO()">
                        <input type="text" class="col-sm-5" readonly name="rollNoList" id="rollNoList">
                    </td>
                </tr>

                <tr>
                    <td>Remarks</td>
                    <td>
                        <g:textArea name="certificateRemarks" class="university-size-1-2"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <g:submitButton name="submit" class="university-button" onclick="validate()"/>
                    </td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $(function () {
            $("#dateOfRelease").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd-mm-yy",
                minDate: -1
            });
        });
    } );

</script>
</body>
</html>