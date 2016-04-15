<%--
  Created by Damyant Software Pvt Ltd.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
%{--Page for showing semester details--}%
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='registerPage.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript">
    </script>
    <style type="text/css">
    </style>
</head>

<body>
<div id="main">
    <g:if test="${status}">
        <fieldset class="form">
            <div class='body'>
                <div class='errors'><div class="university-not-authorized">
                    <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                            style="margin: auto;"/></p>
                    <g:if test="${status == 'Not Enrolled'}"><p>Not Enrolled Yet</p></g:if>
                    <g:elseif
                            test="${status == 'No SemRegistration'}"><p>Submit Without any Subjects in Semester Registration.</p></g:elseif>
                    <g:elseif
                            test="${status == 'rejected'}"><p>Semester Registration is Rejected.<br/>${reason}<br/>Need to Submit Again
                    </p></g:elseif>

                </div></div>
            </div>
        </fieldset>
    </g:if>
    <g:else>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:hasErrors bean="${studInstance}">
            <div class="errors">
                <g:renderErrors bean="${studInstance}" as="list"/>
            </div>
        </g:hasErrors>

    %{--<g:else>--}%
        <fieldset class="form">
            <g:form controller="hod" action="approveSemesterStudent" method='post' enctype="multipart/form-data"
                    id="studentRegister" name="studentRegister">
                <h3>STUDENT SEMESTER REGISTRATION DETAILS</h3>


            %{--<div style="margin-left: 10px;"><label><h6>All [<span--}%
            %{--class="university-obligatory">''</span>] marked fields are Mandatory.</h6></label></div>--}%
                <table align="center" cellpadding="10" class="university-table-1-2 inner"
                       style="width: 100%;margin: auto;">
                    <g:if test="${studInstance}">

                        <g:hiddenField name="studentId" value="${studInstance?.id}"/>
                    </g:if>
                    <!----- First Name ---------------------------------------------------------->
                    <tr>
                        <td class="university-size-1-3">Name of the applicant</td>
                        <td class="university-size-2-3"><label>${studInstance?.firstName}</label>
                            <g:if test="${studInstance.middleName}">
                                <label>${studInstance?.middleName}</label>
                            </g:if>
                            <label>${studInstance?.lastName}</label>
                        </td>
                    </tr>
                    <g:if test="${studInstance?.rollNo}">
                        <tr>
                            <td>Roll Number <span class="university-obligatory"></span></td>
                            <td>
                                <input type="text" name="rollNo" readonly
                                       class="university-size-1-1" value="${studInstance?.rollNo}"/>
                            </td>
                        </tr>
                    </g:if>

                    <g:if test="${studInstance?.dob}">
                        <tr>
                            <td>Date Of Birth <span class="university-obligatory"></span></td>
                            <td>
                                %{--<input type="text" name="dob" readonly--}%
                                %{--class="university-size-1-1" value="${studInstance?.dob}"/>--}%
                                <g:formatDate format="dd-MM-yyyy" date="${studInstance?.dob}"/>

                            </td>
                        </tr>
                    </g:if>
                <!----- Program Name ---------------------------------------------------------->
                    <tr>
                        <td>Programme</td>
                        %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
                        <td>
                            <input type="hidden" name="programId" id="programId" value="${studInstance?.program?.id}"/>
                            <input type="text" readonly id="programName" class="university-size-1-1"
                                   value="${studInstance?.program?.courseName}"/>

                            <label id="courseCodeLength" class="error"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>Session</td>
                        %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
                        <td>
                            <input name="session" readonly id="session" class="university-size-1-2"
                                   value="${session}">
                        </td>
                    </tr>

                    <tr>
                        <td>Current Semester</td>
                        <td>
                            <input type="text" readonly name="program" maxlength="30" class="university-size-1-2"
                                   value="${studInstance?.semester}"/>
                        </td>
                    </tr>
                </table>

                <g:if test="${finalSubjectList.size()>0}">
                    <div style="text-align: center;font-size: 15px;font-weight: bold;margin-top: 20px;">Subjects Details</div>

                    <table id="studentCourse" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
                           style=""><thead>
                    <tr>
                        <th style="width: 22%">Course Code</th>
                        <th style="width: 40%">Core Course Name</th>
                        <th style="width: 5%"><label>L</label></th>
                        <th style="width: 5%"><label>T</label></th>
                        <th style="width: 5%"><label>P</label></th>
                        <th style="width: 5%"><label>C</label></th>
                        <th style="width: 18%"><label>Contact Hours</label></th>
                    </tr>
                    </thead>
                        <tbody>
                        <g:each in="${finalSubjectList}" status="lst" var="list">
                            <tr>
                                <td colspan="7"
                                    style="text-align: center;font-weight: bold;margin: 5px auto;background-color: blanchedalmond;"><label
                                        style="margin-right: 30px;">Semester::  ${list[0].semester.semesterNo}</label>  <label
                                        style="margin-left: 30px;">Status::  ${list[0].status.status}</label></td>
                                %{--<td colspan="4" style="text-align: center;font-weight: bold;margin: 5px auto;background-color: blanchedalmond;"></td>--}%
                            </tr>
                            <g:each in="${list}" status="lst1" var="list1">
                                <tr style="border: 1px solid black">
                                    <td>${list1.subjectSession.subjectId.subjectCode}</td>
                                    <td>${list1.subjectSession.subjectId.subjectName}</td>
                                    <td>${list1.subjectSession.subjectId.lecture}</td>
                                    <td>${list1.subjectSession.subjectId.tutorial}</td>
                                    <td>${list1.subjectSession.subjectId.practical}</td>
                                    <td><label>${list1.subjectSession.subjectId.creditPoints}</label></td>
                                    <td>${list1.subjectSession.subjectId.contactHours}</td>
                                </tr>
                            </g:each>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>
                <g:if test="${finalFeeList.size()>0}">
                    <div style="text-align: center;font-size: 15px;font-weight: bold;margin-top: 20px;">Fee Details</div>
                    <table id="studentCourse" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
                           style=""><thead>
                    <tr>
                        <th style="width: 20%">GU Receipt Number</th>
                        <th style="width: 15%">Receipt Amount</th>
                        <th style="width: 15%"><label>Receipt Date</label></th>
                        <th style="width: 15%"><label>Academic Session</label></th>
                        <th style="width: 14%"><label>Semester</label></th>
                        <th style="width: 20%"><label>Status</label></th>
                    </tr>
                    </thead>
                        <tbody>
                        <g:each in="${finalFeeList}" status="lst" var="list">
                            <g:each in="${list}" status="lst1" var="list1">
                                <tr style="border: 1px solid black">
                                    <td>${list1.guReceiptNo}</td>
                                    <td>${list1.guReceiptAmount}</td>
                                    <td><g:formatDate format="dd-MMM-yyyy" date="${list1?.guReceiptDate}"/></td>
                                    <td>${list1.academicSession}</td>
                                    <td>${list1.semester.semesterNo}</td>
                                    <td>${list1.status.status}</td>
                                </tr>
                            </g:each>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>


                <g:if test="${!noButton}">
                    <sec:ifAnyGranted roles="ROLE_HOD,ROLE_FACULTYADVISOR">
                        <table><tr>
                            <td colspan="2" align="center">
                                <g:submitButton name="Update & Approve"
                                                class="university-button">Update & Approve</g:submitButton>
                                <button class="university-button" onclick="rejectSemesterRegistration();
                                return false;">Reject</button>
                            </td>
                        </tr></table>
                    </sec:ifAnyGranted>
                </g:if>

            </g:form>
        </fieldset>
        <table hidden="hidden" id="rejectionFormTable">
            <g:form controller="hod" action="rejectSemesterStudent">
                <tr>
                    <td>
                        <g:hiddenField name="studentId" value="${studInstance?.id}"/>
                        %{--<label for="reasonForRejection">Enter Reason</label>--}%
                        <textArea id="reasonForRejection" rows="4" cols="32" name="reasonForRejection">

                        </textArea>
                    </td>
                    <td>
                        <g:submitButton name="Reject">Reject</g:submitButton>
                    </td>
                </tr>
            </g:form>
        </table>
    </g:else>
</div>
<script>
    var studentSubject = "${studentSubject}"
    var array = studentSubject.split(" ,");
    debugger;
    $('.studentSubjects').each(function (i, obj) {
        console.log(' ' + array.indexOf(this.value))
        console.log(this.value + ' in loop ' + array)
//        if ($.inArray(this.value, array) > -1){
//        if (array.indexOf(this.value) > -1){
        if (studentSubject.indexOf(this.value) > -1) {
            $(this).prop('checked', true)
        }
    });

    function rejectSemesterRegistration() {
        console.log("Hello there")
        $('#rejectionFormTable').prop('hidden', false)
    }

</script>
</body>
</html>





