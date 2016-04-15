<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 5/28/2014
  Time: 2:01 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for assigning time period for semester registration(for particular program and semester) --}%
<html>
<head>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='validation.js'/>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Assign and Update Registration Date</h3>
        <g:if test="${flash.message}">
            <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="admin" action="saveAdmissionFeePeriod" name="assignAdmissionPeriod" id="assignAdmissionPeriod">
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="inner" class="university-size-full-1-1">
                <tr>
                    <td class="university-size-1-4">Academic Session <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <input type="text" name="admissionSession" maxlength="9" id="admissionSession"  onchange="enableAllOtherFields(this)"  class="university-size-1-2"/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Select Programme <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <g:select name="program" class="university-size-1-2" id="program" optionKey="id"
                                  optionValue="courseName" disabled="true"
                                  from="${programList}" noSelection="['': ' Select Programme']"
                                  onchange="loadSemesterForProgram(this)"/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Select Semester <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <g:select name="semester" class="university-size-1-2" id="semesterList" optionKey="id"
                                  optionValue="" disabled="true"
                                  from="" noSelection="['': ' Select Semester']"
                                  />
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Student Admission Year <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <select name="admissionYear" disabled id="admissionYear" class="university-size-1-2" onchange="loadAdmissionDate(this)">
                            <option value="">Select Admission Year</option>
                            <g:each in="${sessionList}" var="year">
                                <option value="${year}">${year}</option>
                            </g:each>
                        </select>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Admission Start Date <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <input type="text" name="startAdmission_D" disabled id="startAdmission_D" onkeypress="disableKeyInput(this)" maxlength="10" class="university-size-1-2" value=""/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Admission End Date <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <input type="text" name="endAdmission_D" disabled id="endAdmission_D" onkeypress="disableKeyInput(this)" maxlength="10" class="university-size-1-2" value=""/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>

                <tr>
                    <td class="university-size-1-4"></td>
                    <td class="university-size-1-2">
                        <input type="submit" value="Submit" id="admissionPeriodButton" disabled onclick="validate()" class="university-button" />
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
<script>
    $(function () {
        $(function () {
            $("#startAdmission_D").datepicker({
                constrainInput: true,
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                minDate: 0,
                onSelect: function (dateText, inst) {
                    $('#endAdmission_D').datepicker("option", "minDate", dateText);
                }
            });

            $("#endAdmission_D").datepicker({
                constrainInput: true,
                changeMonth: true,
                changeYear: true,
                minDate: 0,
                dateFormat: "dd/mm/yy",
                onSelect: function (dateText, inst) {
                    $('#startAdmission_D').datepicker("option", "maxDate", dateText);
                }
            });
        });
    });
</script>
</body>
</html>