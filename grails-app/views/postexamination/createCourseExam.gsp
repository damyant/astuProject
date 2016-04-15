<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 19/5/14
  Time: 6:08 PM
--%>

<%@ page import="postexamination.ExamType" contentType="text/html;charset=UTF-8" %>
%{--Page for creating new examination for particular subject in selected session--}%
<html>
<head>
    <title>Add Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

</head>
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
<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${examInst}">
            <h3>Update Examination</h3>

        </g:if>
        <g:else>
            <h3>Add Examination</h3>
        </g:else>

        <g:form controller="postExamination" action="saveCourseExam" id="saveCourseExam" name="saveCourseExam">
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="inner" style="margin: auto; width: 100%">
                <tr>
                    <td class="university-size-1-3"><p>Enter Course Code <span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-1-3">
                        <g:if test="${examInst}">
                            <input type="hidden" name="updateExamInst" value="${examInst.id}">
                            <input type="text" class="university-size-2-3" readonly name="subjectCode" value="${examInst.subject.subjectCode}"
                                   id="subjectCode"/>
                        </g:if>
                        <g:else>
                            <input type="text" class="university-size-2-3" name="subjectCode"
                                   id="subjectCode"/></g:else>

                    </td>
                    <td class="university-size-1-3">
                        <g:if test="${examInst}"></g:if>
                        <g:else>
                            <input type="button" value="Load Deatils" class="university-button"
                                   onclick="loadSubjectDetails()"/><label id="loadError"></label>
                        </g:else></td>
                </tr>
            </table>
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-3"><p>Course Name</p></td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="text" class="university-size-1-2" name="subjectName" readonly
                                   value="${examInst.subject.subjectName}"
                                   id="SubjectName"/>
                            <input type="hidden" id="theory"
                                   value="${examInst.subject.lecture + examInst.subject.tutorial}"/>
                            <input type="hidden" id="practical" value="${examInst.subject.practical}"/>
                        </g:if>
                        <g:else>
                            <input type="text" class="university-size-1-2" name="subjectName" readonly
                                   id="SubjectName"/>
                            <input type="hidden" id="theory" value=""/>
                            <input type="hidden" id="practical" value=""/>
                        </g:else>

                    </td>
                </tr>
                <g:if test="${examInst}">

                </g:if>
                <g:else>
                    <tr>
                        <td class="university-size-1-3"><p>Other Details<span class="university-obligatory">*</span></p>
                        </td>
                        <td class="university-size-2-3">

                            <table class="inner university-size-1-3" id="ltpc">

                            </table>
                        </td>
                    </tr>
                </g:else>

                <tr>
                    <td class="university-size-1-3"><p>Academic Session<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="text"  name="academicSession" id="academicSession" readonly value="${examInst.academicSession}"/>

                        </g:if>
                        <g:else>
                            <select name="academicSession" id="academicSession" class="university-size-1-2">
                                <option value="">Select Academic Session</option>
                                <g:each in="${academicSession}" var="year">
                                    <option value="${year}">${year}</option>
                                </g:each>
                            </select>
                        </g:else>

                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Examination Type<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="hidden" name="examType" id="examType" value="${examInst.examTypes}"/>
                            <input type="text" readonly value="${postexamination.ExamType.findById(Long.parseLong(examInst.examTypes)).examTypeName}"/>
                        </g:if>
                        <g:else>
                            <g:select name="examType" id="examType" optionKey="id" class="university-size-1-2" value=""
                                      optionValue="examTypeName" from="${examTypeList}"
                                      noSelection="['': ' Select Exam Type']" onchange="loadExamSubType(this)"/>
                        </g:else>

                    </td>
                </tr>
                <tr id="examSubTypeTr" style="display: none;">
                    <td class="university-size-1-3"><p>Examination Sub Type<span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="hidden"  name="examSubType" id="examSubType" value="${examInst.examSubType}">
                            <input type="text" readonly value="${postexamination.ExamSubType.findById(Long.parseLong(examInst.examSubType)).examSubTypeName}"/>
                        </g:if>
                        <g:else>
                            <g:select name="examSubType" id="examSubType" optionKey="" class="university-size-1-2" value=""
                                      optionValue="" from="" noSelection="['': ' Select Exam Sub Type']" onchange="checkExamSubType(this)"/>

                        </g:else></td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Total Marks<span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="text" value="${examInst.totalMarks}" class="university-size-1-2"
                                   name="assignedTotalMarks" id="assignedTotalMarks" maxlength="3"  onchange="checkRemainingMarks(this)" onkeypress="return isNumber(event)"/>

                        </g:if>
                        <g:else>
                            <input type="text" class="university-size-1-2" name="assignedTotalMarks"
                                   id="assignedTotalMarks" maxlength="3" onchange="checkRemainingMarks(this)" onkeypress="return isNumber(event)"/>

                        </g:else>
                        <input type="text" class="university-size-1-2" readonly id="examTypeReqMarks">
                        <input type="hidden" id="examTypeReqMarksHidden" value="0">
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"></td>
                    <td class="university-size-2-3">
                        <g:if test="${examInst}">
                            <input type="submit" class="university-button" value="Update" id="assignedTotalMarkSubmit"/>

                        </g:if>
                        <g:else>
                            <input type="submit" class="university-button" onclick="validate()" value="Save" id="assignedTotalMarkSubmit"/>

                        </g:else>
                    </td>
                </tr>

            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>