<%--
  Created by IntelliJ IDEA.
  User: bsnl
  Date: 2/6/2015
  Time: 1:03 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for assigning Faculty advisor--}%
<html>
<head>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='validation.js'/>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Assign Faculty Advisor</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="admin" action="saveAssignFaculty"  id="saveAssignFaculty"  name="saveAssignFaculty" >
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-3">Academic Session <span class="university-obligatory">*</span></td>
                <td class="university-size-2-3">
                    <input type="text" name="admissionSession" id="admissionSession" maxlength="9" onchange="enableAllOtherFields(this)"  class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td>Select Programme <span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="program" class="university-size-1-2" id="program" optionKey="id"
                              optionValue="courseName" disabled="true"
                              from="${programList}" noSelection="['': ' Select Programme']"
                              onchange="loadFacultyAdvisorAndSemester(this)"/>
                </td>
            </tr>
            <tr>
                <td>Select Semester <span class="university-obligatory">*</span></td>
                <td>
                   <table id="semesterTable"  class="inner university-size-1-1"></table>
                </td>
            </tr>
            <tr>
                <td>Select facultyAdvisor <span class="university-obligatory">*</span></td>
                <td>
                    <table id="facultyTable" class="inner university-size-1-1"></table>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="Submit" class="university-button" onclick="validate()"/>
                </td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>

</body>
</html>