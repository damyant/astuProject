<%@ page import="IST.ProgramBranch; examinationproject.ProgramType; examinationproject.Subject;  examinationproject.CourseMode" %>
<%--
  Created by Damyant Software Pvt Ltd.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>


<html>
%{--Page for creating new program or updating program(program detail)--}%
<head>
    <title>Create New Programme</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'multiselectable.js')}"></script>
    <script type="text/javascript">

    </script>
    <script type="text/javascript">
        if (${updateFlag}) {
            $(window).bind("load", function () {

                updateInfo("${courseDetail}")
            })
        }
    </script>
</head><body>
<div id="main">
    <fieldset class="form">
        <g:if test="${params.courseSessionId}">
            <h3>Update Programme</h3>
        </g:if>
        <g:else>
            <h3>Add New Programme</h3>
        </g:else>

        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>

        <div id="statusMessage" style="display:none;" class="university-status-message">
            <g:if test="${params.courseSessionId}">
                <g:message code="course.updated.message"/>
            </g:if>
            <g:else>
                <g:message code="course.create.message"/>

            </g:else>
        </div>

        <g:uploadForm method="post" name="createCourse" id="createCourse" enctype="multipart/form-data">
            <g:hiddenField name="courseId" id="courseId"/>
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="university-table inner" border="0">
                <tr>
                    <td style="width: 40%"><label>Select Session :<span class="university-obligatory">*</span></label>
                    </td>
                    <td style="width: 60%">
                        <select id="session" name="session" class="university-size-1-2" >
                            <option value="0">Select Session</option>
            <g:each in="${programSessions}" var="session">
                <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
            </g:each>
            </select>
            </td>

        </tr>
            <tr>
                <td><label>Select Mode :<span class="university-obligatory">*</span></label></td>
                <td><g:select name="courseMode" id="modeName" required="required" optionKey="id" optionValue="modeName"
                               class="university-size-1-2"
                              from="${CourseMode.findAll()}" noSelection="['0': ' Select Mode']"/></td>
            </tr>
            <tr>
                <td><label>Select Programme Type :<span class="university-obligatory">*</span></label></td>
                <td><g:select name="programType" id="courseTypeName" required="required" onchange="enableNoOfSem(this)" optionKey="id" optionValue="type"

                              class="university-size-1-2" from="${ProgramType.findAll()}"
                              noSelection="['0': ' Select Programme Type']"/></td>
            </tr>

            <tr>
                <td><label>Select Programme Branch :<span class="university-obligatory">*</span></label></td>
                <td><g:select name="programBranch" required="required" id="courseTypeBranch" optionKey="id" optionValue="name"

                              class="university-size-1-2" from="${ProgramBranch.findAll()}"
                              noSelection="['0': ' Select Programme Branch']"/></td>
            </tr>
                %{--<g:if test="${studInstance?.program.programType.id==3}">--}%
                    <tr id="isPartTimeTr" style="display: none">
                        <td>Program Mode<span class="university-obligatory">*</span></td>
                        <td>
                            <label><input type="radio" checked value="fullTime" name="partTimeStatus"/> Full Time </label>
                            <label><input type="radio"  value="partTime" name="partTimeStatus"/> Part Time </label>
                        </td>
                    </tr>
                %{--</g:if>--}%

            <tr>
                <td><label>Number of Terms/Semesters :<span class="university-obligatory">*</span></label></td>
                <td>
                    <g:if test="${params.courseSessionId}">
                        <input type="text" id="noOfTerms" required="required" name="noOfTerms" maxlength="2" class="university-size-1-2"

                               onkeypress="return isNumber(event)" onblur="semesterList(this)" /></td>

                    </g:if>
                    <g:else>
                        <input type="text" id="noOfTerms" required="required" name="noOfTerms" readonly maxlength="2" class="university-size-1-2"

                               onkeypress="return isNumber(event)" onblur="semesterList(this)" /></td>


                    </g:else>

            </tr>

            <tr>
                <td><label>Number of maximum available academic year :<span class="university-obligatory">*</span>
                </label></td>
                <td><input type="text" id="noOfAcademicYears" required="required" name="noOfAcademicYears" maxlength="10"
                           class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr><tr>
                <td><label>Maximum Number of available Semester :<span class="university-obligatory">*</span>
                </label></td>
                <td><input type="text" id="finalTerm" name="finalTerm" maxlength="10"
                           class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>

            <tr>
                <td><label>Number of papers :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="noOfPapers" required="required" name="noOfPapers" maxlength="5" class="university-size-1-2"
                           onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Marks :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="totalMarks" required="required" name="totalMarks" maxlength="5" class="university-size-1-2"
                           onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Credit Points :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" required="required" id="totalCreditPoints" name="totalCreditPoints" class="university-size-1-2"
                           maxlength="5"
                           onkeypress="return isNumber(event)"/>

                </td>
            <tr><label id="worningMsg" class="university-status-message"></label></tr>

            </tr>
            <tr>
                <td colspan="2">
                    <table id="multiSelectTab" name="multiSelectTab">
                        <tr>

                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center">
                    <g:if test="${params.courseSessionId}">
                        <input type="button" id="submitForm" onclick="save();"
                               value="<g:message code="default.button.update"/>" class="university-button"/>
                        <a href="/Symphonie/course/listOfCourses?type=update"><input type="button"
                                                                                             class="university-button"
                                                                                             value="Back"/></a>
                    </g:if>
                    <g:else>
                        <input type="button" id="submitForm" onclick="save();"
                               value="<g:message code="default.button.create"/>" class="university-button">
                        <input id="clear" onclick="clearField()" type="reset"
                               value="<g:message code="default.button.clear"/>" class="university-button">
                    </g:else>
                </td>

            </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>
</body>
</html>