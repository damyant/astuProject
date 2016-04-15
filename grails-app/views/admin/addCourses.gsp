<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 19/5/14
  Time: 6:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    %{--Page for adding(creating) new subjects--}%
    <title>Add Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

    <script type="text/javascript">
        $(window).bind("load", function () {
            if(${updateMode==true}){
                fillCourseInfoUpdate("${courseSession?.sessionOfSubject}")
                $('#updateModes').val('true')
            }
        })
        $(document).ready(function () {
            var courseType="${courseType}";
            var projectThesis="${projectThesis}";
            if(${updateMode==true}) {
                $("input[name='courseType'][value='"+courseType+"']").attr('checked', 'checked');
                $("input[name='projectThesis'][value='"+projectThesis+"']").attr('checked', 'checked');
            }
        })
    </script>
</head>

<body>
<div id="main">
<fieldset class="form">
<h3>Add Courses</h3>
<g:form controller="course" action="saveCourses" id="addCoursesFrmId" name="addCoursesFrmId" >
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<input type="hidden" name="updateModes" id="updateModes" value="">
<table class="inner" style="margin: auto; width: 100%">
<tr>
    <td class="university-size-1-4"><p>Course Name <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-2"><g:textField name="subjectName" id="subjectName" maxlength="150" value="${courseList?.subjectName}"
                                                 class="university-size-1-3"/>
        <input type="hidden" value="${courseList?.id}" name="subjectId"/>

    </td>
    <td class="university-size-1-4"></td><td class="university-size-1-4"></td>
</tr>
    %{--field for course code which will be unique for each subject--}%
<tr>
    <td class="university-size-1-4"><p>Course Code <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="subjectCode" id="subjectCode" maxlength="6"  value="${courseList?.subjectCode}"
                                                 onchange="checkSubjectCode()" class="university-size-1-3"/>
        <label id="errorMsg" class="error1"></label>

    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Course Session <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4">
        <select id="sessionOfCourse" name="session" class="university-size-1-3">
            <option value="0">Select Session</option>
            <g:each in="${subjectSessions}" var="session">
                <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
            </g:each>
        </select>
    </td>
</tr>

<tr>
    <td class="university-size-1-4"><p>Course Type <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4">
        <g:if test="${updateMode}">
            <label style="margin-right: 20px;"><input type="radio" name="courseType" value="core">Core</label>
            <label><input type="radio" name="courseType" value="optional">Optional</label>
        </g:if><g:else>
        <label style="margin-right: 20px;"><input type="radio" name="courseType" value="core" checked>Core</label>
        <label><input type="radio" name="courseType" value="optional">Optional</label>
    </g:else>
    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Project Thesis <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4">
        <g:if test="${updateMode}">
            <label style="margin-right: 20px;"><input type="radio" name="projectThesis" value="required">Required</label>
            <label><input type="radio" name="projectThesis" value="notRequired">Not Required</label>
        </g:if><g:else>
        <label style="margin-right: 20px;"><input type="radio" name="projectThesis" value="required" >Required</label>
        <label><input type="radio" name="projectThesis" value="notRequired" checked>Not Required</label>
    </g:else>
    </td>
</tr>

<tr>
    <td class="university-size-1-4"><p>Lecture <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="lecture" id="lecture" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.lecture}"
                                                 class="university-size-1-3"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Tutorial <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="tutorial" id="tutorial" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.tutorial}"
                                                 class="university-size-1-3"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Practical <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="practical" id="practical" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.practical}"
                                                 class="university-size-1-3"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Credit Points <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="creditPoints" id="creditPoints" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.creditPoints}"
                                                 class="university-size-1-3"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-4"><p>Contact Hours <span class="university-obligatory">*</span>
    </p></td>
    <td class="university-size-1-4"><g:textField name="contactHours" id="contactHours" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.contactHours}"
                                                 class="university-size-1-3"/>

    </td>
</tr>
%{--<tr>--}%
%{--<td class="university-size-1-4"><p>Subject Pass Marks <span class="university-obligatory">*</span>--}%
%{--</p></td>--}%
%{--<g:if test="${subjectInfo != null}">--}%
%{--<td class="university-size-1-4"><g:textField name="subjPassMarks" id="subjPassMarks"--}%
%{--onkeypress="return isNumber(event)" maxlength="4"--}%
%{--value="${subjectInfo?.subjMinMarks}"--}%
%{--class="university-size-1-3"/>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<td class="university-size-1-4"><g:textField name="subjPassMarks" id="subjPassMarks"--}%
%{--onkeypress="return isNumber(event)" maxlength="4"--}%
%{--value=""--}%
%{--class="university-size-1-3"/>--}%
%{--</g:else>--}%

%{--</td>--}%
%{--</tr>--}%

%{--<g:if test="${subjectInfo != null}">--}%
%{--<tr><td colspan="4" style="background-color:grey; "><strong>Theory Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="theoryTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="theoryTotal"--}%
%{--value="${subjectInfo?.maxMarksList[0]}"/></td>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="theoryPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="theoryPass"--}%
%{--value="${subjectInfo?.minMarksList[0]}"/></td>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<tr>--}%
%{--<td colspan="4" style="background-color:grey; "><strong>Theory Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="theoryTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="theoryTotal"--}%
%{--value=""/>--}%
%{--</td>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="theoryPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="theoryPass"--}%
%{--value=""/>--}%
%{--</td>--}%
%{--</g:else>--}%

%{--</tr>--}%
%{--<tr>--}%
%{--<td colspan="4" style="background-color:grey; "><strong>Home Assignment Marks</strong></td></tr>--}%
%{--<g:if test="${subjectInfo != null}">--}%
%{--<tr><td>Total Marks</td><td><g:textField name="homeTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="homeTotal"--}%
%{--value="${subjectInfo?.maxMarksList[1]}"/>--}%

%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="homePass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="homePass"--}%
%{--value="${subjectInfo?.minMarksList[1]}"/>--}%

%{--</tr>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="homeTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="homeTotal"--}%
%{--value=""/>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="homePass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="homePass" value=""/>--}%

%{--</tr>--}%
%{--</g:else>--}%
%{--<g:if test="${subjectInfo != null}">--}%
%{--<tr><td colspan="4" style="background-color:grey; "><strong>Practical Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="practicalTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="practicalTotal"--}%
%{--value="${subjectInfo?.maxMarksList[2]}"/>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="practicalPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="practicalPass"--}%
%{--value="${subjectInfo?.minMarksList[2]}"/>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<tr><td colspan="4" style="background-color:grey; "><strong>Practical Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="practicalTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="practicalTotal"--}%
%{--value=""/>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="practicalPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="practicalPass"--}%
%{--value=""/>--}%
%{--</g:else>--}%

%{--</tr>--}%
%{--<g:if test="${subjectInfo != null}">--}%
%{--<tr>--}%
%{--<td colspan="4" style="background-color:grey; "><strong>Project Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="projectTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="projectTotal"--}%
%{--value="${subjectInfo?.maxMarksList[3]}"/>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="projectPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="projectPass"--}%
%{--value="${subjectInfo?.minMarksList[3]}"/>--}%
%{--</tr>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<tr>--}%
%{--<td colspan="4" style="background-color:grey; "><strong>Project Marks</strong></td></tr>--}%
%{--<tr><td>Total Marks</td><td><g:textField name="projectTotal" maxlength="3"--}%
%{--onkeypress="return isNumber(event)" id="projectTotal"--}%
%{--value=""/>--}%
%{--<td>Pass Marks</td><td class="university-size-2-3"><g:textField name="projectPass" maxlength="3"--}%
%{--onkeypress="return isNumber(event)"--}%
%{--id="projectPass"--}%
%{--value=""/>--}%
%{--</tr>--}%
%{--</g:else>--}%

<tr><td colspan="2" style="text-align: center; ">
    <g:submitButton name="submit" class="university-size-1-3" value="Save" onclick="validate()"
                    style="margin-top: 15px;"></g:submitButton></td>
</tr>

</table>

</g:form>

</fieldset>
</div>
</body>
</html>