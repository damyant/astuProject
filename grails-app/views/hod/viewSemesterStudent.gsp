<%--
  Created by Damyant Software Pvt Ltd.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import=" java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
%{--Page for showing semester registration details--}%
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
        $(document).ready(function () {
            var studentSubject = ${studentSubject};
            var total=0

            for (var i = 0; i < studentSubject.length; i++) {
                $('#studentSubjects' + studentSubject[i]).attr("checked", true)
                $('#studentSubjects' + studentSubject[i]).parents("tr:first").css('background-color', 'yellow');
                var listId = ($('#studentSubjects' + studentSubject[i]).val())
                var newCredit = $('#creditPoints' + listId).text()
                var total = parseInt(total) + parseInt(newCredit)
            }
            $('#totalSemCredit').text(total)

            $(".dialog").dialog({
                autoOpen: false,
                draggable: false,
                position: ['center', 0],
                width: 850,
                resizable: false,
                height: 750,
                modal: true,
                title: 'Subject Selection',
                close: function (ev, ui) {
                    $.unblockUI();
                }
            });

        });
    </script>
</head>

<body>
<div id="main">
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>
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
<h3>STUDENT SEMESTER REGISTRATION SHEET</h3>

<table align="center" cellpadding="10" class="inner" style="width: 100%;margin: auto;">

    <g:if test="${studInstance}">

        <g:hiddenField name="studentId" value="${studInstance?.id}"/>
    </g:if>
    <!----- First Name ---------------------------------------------------------->
    <g:if test="${noReject}">
        <input type="hidden" name="approvedUpdate" value="${noReject}"/>
    </g:if>
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
            <td>Roll Number</td>
            <td>
                <input type="text" name="rollNo" readonly
                       class="university-size-1-1" value="${studInstance?.rollNo}"/>
            </td>
        </tr>
    </g:if>

    <g:if test="${studInstance?.dob}">
        <tr>
            <td>Date Of Birth</td>
            <td>
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
        <td>Semester</td>
        %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
        <td>
            <input name="studentSemester" readonly id="studentSemester" class="university-size-1-2"
                   value="${studInstance.semester}">
        </td>
    </tr>
    <sec:ifAnyGranted roles="ROLE_STUDENT">
        <tr>
        <td>Status of Registration<span class="university-obligatory">*</span></td>
    %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
        <td>
            <g:if test="${studInstance.semesterRegistration == 1}">
                <div class="message"><div class="university-status-message"
                                          style="text-align: left">Approval Under Process</div></div>
            </g:if>
            <g:if test="${studInstance.semesterRegistration == 2}">
                <div class="message"><div class="university-status-message"
                                          style="text-align: left">Semester Registration Rejected</div>
                </div>
            </g:if>
            <g:if test="${studInstance.semesterRegistration == 3}">
                <div class="message"><div class="university-status-message"
                                          style="text-align: left">Semester Registration Approved</div>
                </div>
            </g:if>

        </td>
    </sec:ifAnyGranted>
    <tr>
        <!-----Father's Name---------------------------------------------------------->
        <td>Father's Name</td>
        <td>${studInstance?.fatherName}</td>
    </tr>
    <tr>
        <!----- Mother's Name ---------------------------------------------------------->
        <td>Mother's Name</td>
        <td>${studInstance?.motherName}</td>
    </tr>
    <tr>
        <td colspan="2">
            <table class="university-size-full-1-1">
                <tr>
                    <!----- Guardian's Name ---------------------------------------------------------->
                    <td class="university-size-1-3">Guardian's Name</td>
                    <td class="university-size-2-3">
                        ${studInstance?.guardianName}
                    </td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>
                        <input type="text" id="email" name="email" class="university-size-1-3" readonly maxlength="50"
                               value="${studInstance?.email}"/>
                    </td>
                </tr>
                <tr class="viewAddress">
                    <td>Hostel</td>
                    <td>
                        <g:if test="${studInstance?.stayInHostel}">
                            Yes
                        </g:if><g:else>No</g:else>
                    </td>
                </tr>
                <g:if test="${studInstance?.stayInHostel}">
                    <tr class="viewAddress">
                        <td>Hostel Name</td>
                        <td>
                            ${studInstance?.hostelName}
                        </td>
                    </tr>
                    <tr class="viewAddress">
                        <td>Hostel  Room Number</td>
                        <td>
                            ${studInstance?.hostelRoomNo}
                        </td>
                    </tr>
                </g:if>
                <tr>
                    <td>Address<br/><br/><br/></td>
                    <td>
                        <table style="width: 100%" id="examCenterAddress">
                            <tr>
                                <th class="university-size-1-2">Permanent Address</th>
                                <th class="university-size-1-2">Mailing Address</th>
                            </tr>

                            <tr class="viewAddress">
                                <td class="university-size-1-2">
                                    <div style="line-height: 18px;">
                                        <label>${studInstance?.permanent_Address}</label><br/>
                                        <label>${studInstance?.permanentAddressDistrict}, ${studInstance?.permanentStateAddress}</label><br/>
                                        <label>${studInstance?.permanentPincode}, +91-${studInstance?.permanentMobileNo}</label>
                                    </div>
                                </td>
                                <td class="university-size-1-2">
                                    <div style="line-height: 18px;">
                                        <label>${studInstance?.mailing_Address}</label><br/>
                                        <label>${studInstance?.mailingAddressDistrict}, ${studInstance?.mailingStateAddress}</label><br/>
                                        <label>${studInstance?.mailingPincode}, +91-${studInstance?.mailingMobileNo}</label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
    <tr><td colspan="2">
        <table class="university-size-full-1-1">
            <tr>
                <td><b>Fee Details</b></td>
                <td><label onclick="enableFeeEdit()"><img id="im"
                                                       style="width:15px;margin-left: 30px;"
                                                       alt="Edit"
                                                       src="${resource(dir: 'images', file: 'edit.ico')}"
                                                       class="window"> Edit
                </label></td></tr><tr>
                <td style="line-height: 20px;" colspan="2"><table class="inner">
                    <g:each in="${semFeeInst}" var="feeInst"><tr>
                        <td class="university-size-1-3"><label style="font-weight: bold;">GU Receipt Number:</label></td><td  class="university-size-2-3"><input type="text" PLACEHOLDER="GU Receipt No" class="guReceiptNo university-size-1-4"
                                                                                                        name="guReceiptNo" value=" ${feeInst.guReceiptNo}"
                                                                                                        style="margin-bottom: 10px;" readonly
                                                                                                        maxlength="15" onkeypress="return isAlphaNumeric(event);"></td></tr>

                       <tr> <td><label style="font-weight: bold;">Amount:</label> </td><td><input type="text" PLACEHOLDER="Amount" class="guReceiptAmount university-size-1-4"
                                                                                              name="guReceiptAmount" value="${feeInst.guReceiptAmount}"
                                                                                              style="margin-bottom: 10px;" readonly
                                                                                              maxlength="15" onkeypress="return isNumber(event);"></td></tr>

                       <tr> <td><label style="font-weight: bold;">Date:</label></td><td> <input type="text" name="guReceiptDate" maxlength="10" PLACEHOLDER="DD/MM/YYYY" readonly
                                                                                            class="guReceiptDate university-size-1-4" onkeypress="disableKeyInput(this)"
                                                                                            value="<g:formatDate format="dd-MMM-yyyy"
                                                                                                                 date="${feeInst.guReceiptDate}"/>">
                        </td></tr>
                    </tr>

                    </g:each>
                </table>


                </td>
            </tr>
        </table>
    </td>

</table>

<table id="studentCourses" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
       style="margin: 5px  auto;"><thead>
<tr>
    <th style="width: 20%">Course Code</th>
    <th style="width: 40%">Core Course Name</th>
    <th style="width: 5%"><label>L</label></th>
    <th style="width: 5%"><label>T</label></th>
    <th style="width: 5%"><label>P</label></th>
    <th style="width: 5%"><label>C</label></th>
    <th style="width: 15%"><label>Contact Hours</label></th>
    <th style="width: 5%"></th>
</tr>
<tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;" colspan="8">Mandatory Papers</td></tr>
</thead>
    <tbody>
    <g:each in="${totalList}" status="lst" var="list">
        <tr>
            <td>${list.subjectId.subjectCode}</td>
            <td>${list.subjectId.subjectName}</td>
            <td>${list.subjectId.lecture}</td>
            <td>${list.subjectId.tutorial}</td>
            <td>${list.subjectId.practical}</td>
            <td><label id="creditPoints${list.id}">${list.subjectId.creditPoints}</label></td>
            <td>${list.subjectId.contactHours}</td>
            <g:if test="${!viewStatus}">
                <td>
                    <input name='studentSubjects' id='studentSubjects${list.id}' class="studentSubjects, checkbox"
                           type='checkbox' value="${list.id}">
                </td>
            </g:if>

        </tr>
    </g:each>
    </tbody>
</table>
<g:if test="${optionalList.size() > 0}">
    <table id="studentOptionalSubjects" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
           style="margin: 5px  auto;">
        <thead>
        <tr>
            <th style="width: 20%">Course Code</th>
            <th style="width: 40%">Optional Course Name</th>
            <th style="width: 5%"><label>L</label></th>
            <th style="width: 5%"><label>T</label></th>
            <th style="width: 5%"><label>P</label></th>
            <th style="width: 5%"><label>C</label></th>
            <th style="width: 15%"><label>Contact Hours</label></th>
            <th style="width: 5%"></th>
        </tr>
        <tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;" colspan="8">Elective Papers</td></tr>

        </thead>
        <tbody>
        <g:each in="${optionalList}" status="lst" var="list">
            <tr>
                <td>${list.subjectCode}</td>
                <td>${list.subjectName}</td>
                <td>${list.lecture}</td>
                <td>${list.tutorial}</td>
                <td>${list.practical}</td>
                <td><label id="creditPoints${list.id}">${list.subjectCredit}</label></td>
                <td>${list.contactHours}</td>
            <g:if test="${!viewStatus}">
                <td>
                    <input name='studentSubjects' id='studentSubjects${list.id}' class="studentSubjects , checkbox"
                           type='checkbox' onclick="addTotalCreditPoints(${list.subjectCredit})"
                           value="${list.id}">
                </td>
            </g:if>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>
<g:if test="${totalList1.size() > 0}">
    <table id="studentExtraCourses" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
           style="margin: 5px auto;">
        <thead>
        <tr>
            <th style="width: 20%">Course Code</th>
            <th style="width: 40%">Extra Course Name</th>
            <th style="width: 5%"><label>L</label></th>
            <th style="width: 5%"><label>T</label></th>
            <th style="width: 5%"><label>P</label></th>
            <th style="width: 5%"><label>C</label></th>
            <th style="width: 15%"><label>Contact Hours</label></th>
            <th style="width: 5%"></th>
        </tr>
        <tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;" colspan="8">Back Papers</td></tr>
        </thead>
        <tbody>
        <g:each in="${totalList1}" status="lst" var="list">
            <tr>
                <td>${list.subjectId.subjectCode}</td>
                <td>${list.subjectId.subjectName}</td>
                <td>${list.subjectId.lecture}</td>
                <td>${list.subjectId.tutorial}</td>
                <td>${list.subjectId.practical}</td>
                <td><label id="creditPoints${list.id}">${list.subjectId.creditPoints}</label></td>
                <td>${list.subjectId.contactHours}</td>
            <g:if test="${!viewStatus}">
                <td>
                    <input name='studentExtraSubjects' id='studentSubjects${list.id}' class="studentSubjects, checkbox"
                           type='checkbox' onclick="addTotalCreditPoints(${list.subjectId.creditPoints})"
                           value="${list.id}">
                </td>
            </g:if>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:if>
<g:if test="${!noReject}">
    <div style="width: 100%; margin: 10px auto;text-align: center; font-size: 16px;font-weight: bolder;">
        TOTAL SEMESTER CREDIT: <label id="totalSemCredit">0</label>
    </div>
</g:if>

%{--<g:submitButton name="submit">Submit</g:submitButton>--}%
<sec:ifAnyGranted roles="ROLE_HOD,ROLE_ADMIN,ROLE_FACULTYADVISOR">
    <g:if test="${params.view == null}">
        <table><tr>
            <td colspan="2" align="center">
                <g:submitButton name="Update & Approve"
                                class="university-button">Update & Approve</g:submitButton>
        <g:if test="${noReject}"></g:if><g:else> <button class="university-button" onclick="rejectSemesterRegistration();
                return false;">Reject</button></g:else>
            </td>
        </tr></table>
    </g:if>
</sec:ifAnyGranted>
</g:form>
</fieldset>
<table hidden="hidden" id="rejectionFormTable">
    <g:form controller="hod" action="rejectSemesterStudent">
        <tr>
            <td>
                <g:hiddenField name="studentId" value="${studInstance?.id}"/>
                %{--<label for="reasonForRejection">Enter Reason</label>--}%
                <textArea id="reasonForRejection" rows="4" cols="32" name="reasonForRejection" PLACEHOLDER="Reason For Reject">

                </textArea>
            </td>
            <td>

                <g:submitButton name="Reject">Reject</g:submitButton>
            </td>
        </tr>
    </g:form>
</table>
%{--</g:else>--}%
</div>
<script>

    $(document).ready(function () {
        $(".guReceiptDate").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            maxDate: 0
        });
    });
    function copyAllAdress() {
//            alert('djfdkjfjdhfjhdjfhd')
        if ($('#sameAsPermanent').prop('checked')) {
            $('#mailing_Address').val($('#permanent_Address').val())
            $('#mailingAddressDistrict').val($('#permanentAddressDistrict').val())
            $('#mailingStateAddress').val($('#permanentStateAddress').val())
            $('#mailingPincode').val($('#permanentPincode').val())
            $('#mailingMobileNo').val($('#permanentMobileNo').val())
        }
        else {
            $('#mailing_Address').val('')
            $('#mailingAddressDistrict').val('')
            $('#mailingStateAddress').val('')
            $('#mailingPincode').val('')
            $('#mailingMobileNo').val('')
        }

    }
    $(".checkbox").change(function () {
        var listId = ($(this).val())
        var prevCredit = $('#totalSemCredit').text()
        var newCredit = $('#creditPoints' + listId).text()
        if ($(this).is(":checked")) {
            var total = parseInt(prevCredit) + parseInt(newCredit)
            $('#totalSemCredit').text(total)
            $(this).parents("tr:first").css('background-color','yellow');
        }
        else if ($(this).is(":checked") == false) {
            var total = parseInt(prevCredit) - parseInt(newCredit)
            $('#totalSemCredit').text(total)
            $(this).parents("tr:first").css('background-color','white');

        }
    });

    function rejectSemesterRegistration() {
        $('#rejectionFormTable').prop('hidden', false)
    }

</script>
</body>
</html>





