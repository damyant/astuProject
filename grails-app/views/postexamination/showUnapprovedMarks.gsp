<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 23/4/15
  Time: 11:17 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing student's marks for particular examination which is not approved by admin or HOD--}%
%{--And provide option for approving and rejecting marks--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='validation.js'/>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='admin.js'/>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
</head>

<body>
<script>
    $(document).ajaxStart(function () {
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
</script>
<div id="main">
    <fieldset class="form">
        <h3>Unapproved Marks</h3>
        <g:form name="studentMarksForm" id="studentMarksForm">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table class="inner" style="margin: auto; width: 100%">
                <tr>
                    <td class="university-size-1-3"><p>Enter Course Code <span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-1-3">
                        <input type="text" class="university-size-2-3" name="subjectCode"
                               id="subjectCode"/>

                    </td>
                    <td class="university-size-1-3">
                        <input type="button" value="Load Deatils" class="university-button"
                               onclick="loadSubjectDetails()"/><label id="loadError"></label>
                    </td>
                </tr>
            </table>
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-3"><p>Course Name</p></td>
                    <td class="university-size-2-3">
                        <input type="text" class="university-size-1-2" name="subjectName" readonly
                               id="SubjectName"/>
                        <input type="hidden" id="theory" value=""/>
                        <input type="hidden" id="practical" value=""/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Other Details<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">

                        <table class="inner university-size-1-3" id="ltpc">

                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Academic Session<span class="university-obligatory">*</span></p>
                    </td><td>
                    <select name="academicSession" id="academicSession" disabled="true" class="university-size-1-3" onchange="loadExaminationBySession(this)">
                        <option value="">Select Academic Session</option>

                    </select>
                </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Examination<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                        <g:select name="examination" id="examination" disabled="true" optionKey="id" class="university-size-1-3" value=""
                                  optionValue="" from=""
                                  noSelection="['': ' Select Examination']" onchange="setButtonEnable(this)"/>


                    </td>
                </tr>
            </table>
            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_HOD">
            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="button" value="Show" id="showButton" onclick="loadUnapprovedStudentMarksDetails()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Set" id="setButton" onclick="disableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Reset" id="resetButton" onclick="enableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
            </div>
                </sec:ifAnyGranted>
            <sec:ifNotGranted roles="ROLE_ADMIN, ROLE_HOD">
                <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                    <input type="button" value="Show" id="setButton" onclick="loadUnapprovedStudentMarksDetails()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
            </div>
            </sec:ifNotGranted>
            <table id="StudentMarksDetails" class="inner">
            <thead></thead>
                <tbody></tbody>
            </table>
            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_HOD">
            <div class="well well-sm text-center" id="submitAll" style="display: none;margin-top: 20px;text-align: center;">
                <input type="button" value="Approve All Marks" class="university-button" onclick="approveAllStudents()">
                <input type="button" value="Reject All Marks" class="university-button" onclick="rejectAllStudents()">
            </div>
            </sec:ifAnyGranted>
        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>