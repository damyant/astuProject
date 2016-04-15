<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Student Registration</h3>
    </br>
        <g:form name="studentRegistration" id="studentRegistration" controller="student" action="submitStudentRegistration">
            <input type="hidden" id="subjectSelected" value="">
            <input type="hidden" id="progragramSessionId" value="${programSessionId}">
            <input type="hidden" name="studentID" value="${params.studentId}" id="studentID">
            <g:if test="${flash.message}">
                <div class="university-status-message"> <label >${flash.message}</label></div>
            </g:if>

            <table class="inner" style="width: 80%;margin-left: 20px;">

                   <tr>
                   <td class="university-size-1-4">
                       <label>Semester</label>
                   </td>
                       <td class="university-size-3-4"><g:select id="semester" name="semester" optionKey="id" class="university-size-1-5"
                                     value=""
                                     optionValue="semesterNo" onchange="getSubjectsSemesterWise(this)"
                                     from="${semesterList}" noSelection="['': 'Select Semester']"/></td>
                   </tr>

                <tr id="subjectBox"><td>
                    <label class="subjectBox"  style="visibility:hidden">Select Subjects</label>

                </td>
                <td><span id="subjectLst" class="subjectBox"  style="visibility:hidden"></span></td>
                </tr>

                <tr id="backsubjectBox"><td>
                    <label class="backsubjectBox"  style="visibility:hidden">Select Subjects</label>

                </td>
                    <td><span id="backSubjectLst" class="backSubjectBox"  style="visibility:hidden"></span></td>
                </tr>

                <tr ><td>
                    <input type="SUBMIT" id="submit" value="Submit" class="ui-button" disabled="true" />
                 </td></tr>
             </table>

        </g:form>
    </fieldset>

</div>
</body>
</html>