<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 9/6/14
  Time: 3:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<script>
    $(document).ajaxStart(function(){
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
        <h3>Marks Evaluation</h3>
        <g:form name="studentMarksForm" id="studentMarksForm">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="inner" style="width: 100%;margin: auto;" border="0">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td class="university-size-1-3">Program<span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                               optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"
                                />
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Academic Session<span class="university-obligatory">*</span></p>
                    </td><td>
                            <select name="academicSession" id="academicSession" class="university-size-1-3" onchange="loadSessionSemester(this)">
                                <option value="">Select Academic Session</option>
                                <g:each in="${academicSession}" var="year">
                                    <option value="${year}">${year}</option>
                                </g:each>
                            </select>
                    </td>
                </tr>
                <!----------------------------------------- Session Name --------------------------------------------->
                %{--<tr>--}%
                    %{--<td>Student Admission Year<span class="university-obligatory">*</span></td>--}%
                    %{--<td>--}%
                        %{--<select name="SessionList" id="SessionList" class="university-size-1-3">--}%
                            %{--<option value="">Select Admission Year</option>--}%
                            %{--<g:each in="${admissionYear}" var="year">--}%
                                %{--<option value="${year}">${year}</option>--}%
                            %{--</g:each>--}%
                        %{--</select>--}%
                    %{--</td>--}%
                %{--</tr>--}%


                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td>Semester<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-3" disabled="true"
                             value="" optionValue="" from="" noSelection="['': ' Select Semester']" onchange="loadProgramCourseByAcademic(this)" />
                    </td>
                </tr>
                <!----------------------------------------- Course Name --------------------------------------------->
                <tr>
                    <td>Course<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="courseCode" id="courseName" optionKey="id" class="university-size-1-3"
                                  value="" optionValue="courseCode" from="" noSelection="['': ' Select Course']" disabled="true" onchange="loadExaminationBySession(this)"/>
                    </td>
                </tr>

                <tr>
                    <td class="university-size-1-3"><p>Examination<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                            <g:select name="examination" id="examination" disabled="true" optionKey="id" class="university-size-1-3" value=""
                                      optionValue="" from=""
                                      noSelection="['': ' Select Examination']" onchange="loadTotalMarks(this)"/>


                    </td>
                </tr>
                %{--<tr>--}%
                    %{--<td class="university-size-1-3"><p>Examination Sub Type<span class="university-obligatory">*</span>--}%
                    %{--</p></td>--}%
                    %{--<td class="university-size-2-3">--}%

                            %{--<g:select name="examSubType" id="examSubType" disabled="true" optionKey="" class="university-size-1-2" value=""--}%
                                      %{--optionValue="" from="" noSelection="['': ' Select Exam Sub Type']" onchange=""/>--}%

                        %{--</td>--}%
                %{--</tr>--}%

            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="button" value="Show Students" id="showButton" onclick="populateStudentListForMarks()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Set" id="setButton" onclick="disableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Reset" id="resetButton" onclick="enableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
            </div>

            <table class="inner" id="dataTable" style="visibility: hidden">
                <tr>
                    <td>List of Roll Numbers</td>
                    <td style="text-align: center">
                        <g:select name="rollNoList" id="rollNoList" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  onchange="loadTabulatorMarks()"/>
                    </td>
                </tr>

                <tr>
                    <td>Enter Marks</td>
                    <td class="university-size-3-4" style="text-align: center">
                        <input type="text" class="university-size-1-3" id="marksValue" name="marksValue" maxlength="3" onkeypress="return isNumber(event)" onchange="checkMarks(this)"/>
                        <input type="hidden" id="totalMarks" value=""/><br/><label id="marksError" class="error1" style="margin-top: 10px;"></label>
                    </td>
                </tr>
            </table>

            <div style="text-align: center; margin: 10px auto;visibility: hidden;" id="buttonDiv" class="university-size-full-1-1">
                <input type="button" value="Save Marks" id="Save_Marks" disabled class="ui-button university-size-1-4" style="margin: auto; " onclick="saveMarks()">
            </div>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>