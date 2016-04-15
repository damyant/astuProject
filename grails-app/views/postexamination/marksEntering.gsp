<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 9/6/14
  Time: 3:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for entering marks for particular subject in particular session --}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marks Entering Interface</title>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='admin.js'/>
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
        <h3>Marks Entering Interface</h3>
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
                                      noSelection="['': ' Select Examination']" onchange="loadTotalMarks(this)"/>


                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Entry Type<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                           <label class="university-size-1-3" style="padding: 2px 30px;"><input type="radio" name="entryType" value="add" checked>Add</label>
                           <label class="university-size-1-3"><input type="radio" name="entryType" value="edit">Edit</label>
                           <label class="university-size-1-3"><input type="radio" name="entryType" value="rejected">Edit Rejected</label>
                    </td>
                </tr>
            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="button" value="Show Students" id="showButton" onclick="populateStudentListForMarks()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Set" id="setButton" onclick="disableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Reset" id="resetButton" onclick="enableAllSelectBox()" class="university-button university-size-1-4" style="margin: auto;" disabled="true">
            </div>

            <table class="inner" id="dataTable" style="visibility: hidden">
                <tr>
                    <td class="university-size-1-3">List of Roll Numbers</td>
                    <td style="text-align: center"  class="university-size-1-3">
                        <g:select name="rollNoList" id="rollNoList" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  onchange="loadTabulatorMarks()"/>
                    </td  class="university-size-1-3">
                    <td rowspan="2"><select id="alreadyEnteredMarks" multiple="multiple" class="university-size-1-2"></select></td>
                </tr>

                <tr>
                    <td>Enter Marks</td>
                    <td style="text-align: center">
                        <input type="text" class="university-size-1-3" id="marksValue" name="marksValue" maxlength="3" onkeypress="return isMarks(event)" onchange="checkMarks(this)"/>
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