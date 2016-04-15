<%--
  Created by Damyant Software Pvt Ltd.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    %{--Page for semester registration--}%
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='registerPage.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript">
        $(document).ready(function () {

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
            $(".guReceiptDate").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $(".checkbox").change(function () {
                var listId = ($(this).val())
                var prevCredit = $('#totalSemCredit').text()
                var newCredit = $('#creditPoints' + listId).text()
                if ($(this).is(":checked")) {
                    var total = parseInt(prevCredit) + parseInt(newCredit)
                    $('#totalSemCredit').text(total)
                    if ($("#subjectSelected").val() == '') {
                        $("#subjectSelected").val(listId)
                    }
                }
                else if ($(this).is(":checked") == false) {
                    var total = parseInt(prevCredit) - parseInt(newCredit)
                    $('#totalSemCredit').text(total)
                }
                if ($('input[type=checkbox]:checked').size() == 0) {
                    $("#subjectSelected").val('')
                }
            });
            $("input[name='semContinuation']").change(function () {
                var status = ($(this).val())
                if (status == 'yes') {
                    $(".checkbox").each(function () {
                        $(this).prop('disabled', true)
                        $('#subjectSelected').prop('disabled', true)
                        $('#studentCourses').prop('hidden', true)
                        $('#studentExtraCourses').prop('hidden', true)
                        $('#studentOptionalSubjects').prop('hidden', true)
                    });
                }
                else {
                    $(".checkbox").each(function () {
                        $(this).prop('disabled', false)
                        $('#subjectSelected').prop('disabled', false)
                        $('#studentCourses').prop('hidden', false)
                        $('#studentExtraCourses').prop('hidden', false)
                        $('#studentOptionalSubjects').prop('hidden', false)
                    });
                }
            });
            $("input:checkbox").click(function () {
                if ($(this).is(':checked')) {
                    $(this).parents("tr:first").data('prevColor', $(this).parents("tr:first").css('background-color'));
                    $(this).parents("tr:first").css('background-color', 'yellow');
                }
                else {
                    $(this).parents("tr:first").css('background-color', $(this).parents("tr:first").data('prevColor'));
                }
            });

        });
        function copyAllAdress() {
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
        function addFeeRow() {
            var guReceiptNo = true, guReceiptAmount = true, guReceiptDate = true;
            $("input[name='guReceiptNo']").each(function () {
                if ($(this).val() == '') {
                    guReceiptNo = false
                }
            });
            $("input[name='guReceiptAmount']").each(function () {
//                alert($(this).val())
                if ($(this).val() == '') {
                    guReceiptAmount = false
                }
            });
            $("input[name='guReceiptDate']").each(function () {
                if ($(this).val() == '') {
                    guReceiptDate = false
                }
            });
            if (guReceiptDate == true && guReceiptAmount == true && guReceiptNo == true) {
                $('#feeTable').append('<tr id="feeRow' + i + '"><td></td><td><div><input type="text" PLACEHOLDER="GU Receipt No" required class="guReceiptNo university-size-2-3 gReceipt" name="guReceiptNo"style="margin-bottom: 10px;" maxlength="15" onkeypress="return isAlphaNumeric(event);"></div>' +
                        '<div><input type="text" PLACEHOLDER="Amount" class="guReceiptAmount university-size-2-3 gAmount" name="guReceiptAmount" required style="margin-bottom: 10px;" maxlength="15" onkeypress="return isNumber(event);"></div>' +
                        '<div><input type="text" name="guReceiptDate" maxlength="10"  onkeydown="disableKeyInput(this)" required PLACEHOLDER="DD/MM/YYYY"class="guReceiptDate university-size-2-3 gDate" value=""></div></td>' +
                        '<td></td></tr>');

                $('#b').remove();
                i++;
                $(".guReceiptDate").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "dd/mm/yy",
                    maxDate: 0
                });
            }
            else {
                alert("Please Fill the previous fields first");
            }

        }
    </script>

</head>

<body>
<div id="main">
<g:if test="${status}">
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>
                <g:if test="${status == 'Not Registered'}"><p>You Have Not Enrolled Yet!</p></g:if>
                <g:elseif test="${status == 'No Approved'}"><p>Your Enrollment is not Approved Yet.</p></g:elseif>
                <g:elseif
                        test="${status == 'Admission Date'}"><p>"Please Ask Authorized person to Assign Admission Date"</p></g:elseif>
                <g:elseif
                        test="${status == 'Reg Expired'}"><p>Registration Period Expired. Please Ask Authorized Person.</p></g:elseif>
                <g:elseif
                        test="${status == 'Prev Not Approved'}"><p>Your Previous Semester is not Approved Yet. Please Ask Authorized Person.</p></g:elseif>
                <g:elseif test="${status != 0 && sem != 0}">
                    <p>Semester Registration for Semester ${sem} is Completed</p>
                </g:elseif>
                <g:else>
                    <p>Please Ask Authorized person to Assign Registration Date</p>
                </g:else>
            </div></div>
        </div>
    </fieldset>
</g:if>
<g:else>
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>
<g:if test="${studInstance}">
<fieldset class="form">
<g:form controller="student" action="submitSemesterRegistration" method='post'
        enctype="multipart/form-data"
        id="studentRegister" name="studentRegister">
<h3>STUDENT SEMESTER REGISTRATION SHEET</h3>

<div style="margin-left: 10px;"><label><h6>All [<span
        class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
<table align="center" cellpadding="10" class="inner"
       style="width: 100%;margin: auto;">
<g:if test="${studInstance}">

    <g:hiddenField name="studentId" value="${studInstance?.id}"/>
</g:if>
<g:if test="${studInstance.semesterRejectionReason}">
    <tr>
        <td colspan="2" style="text-align: center;font-weight: bold;"><label
                class="error">Reason for Rejection:::  ${studInstance?.semesterRejectionReason}</label>
        </td>
    </tr>
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
<tr>
    <td>Programme</td>
    <td>
        <input type="hidden" name="programId" id="programId" value="${studInstance?.program?.id}"/>
        <input type="text" readonly id="programName" class="university-size-1-1"
               value="${studInstance?.program?.courseName}"/>
    </td>
</tr>

<tr>
    <td>Session</td>
    <td>
        <input name="session" readonly id="session" class="university-size-1-2"
               value="${session}">
    </td>
</tr>
<tr>
    <td>Semester</td>
    <td>
        <input name="studentSemester" readonly id="studentSemester" class="university-size-1-2"
               value="${nextSemester}">
    </td>
</tr>
<tr>
    <td>Father's Name</td>
    <td>${studInstance?.fatherName}</td>
</tr>
<tr>
    <td>Mother's Name</td>
    <td>${studInstance?.motherName}</td>
</tr>
<tr>
    <td colspan="2">
        <table class="university-size-full-1-1">
            <tr>
                <!----- Guardian's Name ---------------------------------------------------------->
                <td class="university-size-1-3">Guardian's Name<span class="university-obligatory">*</span></td>
                <td class="university-size-2-3">
                    <table class="inner" style="width:100%;margin-top: 0px;">
                        <tr>
                            <td class="university-size-3-4"><input type="text" id="guardianName"
                                                                   style="margin-left: -10px;"
                                                                   class="university-size-1-2" name="guardianName"
                                                                   maxlength="100" readonly
                                                                   value="${studInstance?.guardianName}"
                                                                   onkeypress="return onlyAlphabets(event);"/></td>
                            <td class="university-size-1-4"><label onclick="enableEdit()"><img id="im"
                                                                                               style="width:15px;margin-left: 30px;"
                                                                                               alt="Edit"
                                                                                               src="${resource(dir: 'images', file: 'edit.ico')}"
                                                                                               class="window"> Edit
                            </label></td>
                        </tr>
                    </table>

                </td>
            </tr>
            <tr>
                <td>Email<span class="university-obligatory">*</span></td>
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
            <tr class="editAddress" style="display: none;">
                <td>Hostel<span class="university-obligatory">*</span></td>
                <td>
                    <div class="radio_options">
                        <label><span>Yes</span><input type="radio" name="stayInHostel" id="stayInHostelYes" value="Yes"
                                                      class="radioInput" onchange="enableHostel()"/></label>
                        <label><span>No</span><input type="radio" name="stayInHostel" id="stayInHostelNo" value="No"
                                                     class="radioInput" onchange="enableHostel()" checked/>
                        </label>
                    </div>
                </td>
            </tr>
            <tr class="editAddress" style="display: none;">
                <td>Hostel Name<span class="university-obligatory">*</span></td>
                <td>
                    <input type="text" id="hostelName" name="hostelName" disabled class="university-size-1-3"
                           maxlength="50"
                           value="${studInstance?.hostelName}"/>
                </td>
            </tr>
            <tr class="editAddress" style="display: none;">
                <td>Hostel Room Number<span class="university-obligatory">*</span></td>
                <td>
                    <input type="text" id="hostelRoomNo" name="hostelRoomNo" disabled class="university-size-1-3"
                           maxlength="50"
                           value="${studInstance?.hostelRoomNo}"/>
                </td>
            </tr>
            <tr>
                <td>Address<span class="university-obligatory">*</span><br/><br/><br/></td>
                <td>
                    <table style="width: 100%" id="examCenterAddress">
                        <tr>
                            <th class="university-size-1-2">Permanent Address</th>
                            <th class="university-size-1-2">Mailing Address</th>
                        </tr>
                        <tr style="display: none; width: 100%" class="editAddress">
                            <td class="university-size-1-2"></td>
                            <td class="university-size-1-2">
                                <label>
                                    <input type="checkbox" id="sameAsPermanent" onchange="copyAllAdress()">
                                    Same As Permanent Adress
                                </label>
                            </td>
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
                        <tr class="editAddress" style="display: none;">
                            <td class="university-size-1-2">

                                <g:textArea class="university-size-1-1"
                                            value="${studInstance?.permanent_Address}" rows="5"
                                            id="permanent_Address" name="permanent_Address"
                                            placeholder="Address"></g:textArea></td>
                            <td class="university-size-1-2">

                                <g:textArea class="university-size-1-1" value="${studInstance?.mailing_Address}"
                                            rows="5" name="mailing_Address" id="mailing_Address"
                                            placeholder="Address"></g:textArea></td>
                        </tr>

                        <tr style="display: none;" id="district_state" class="editAddress">
                            <td>
                                <input type="text" name="permanentAddressDistrict"
                                       placeholder="District" disabled
                                       onkeypress="return onlyAlphabets(event);"
                                       value="${studInstance?.permanentAddressDistrict}"
                                       id="permanentAddressDistrict" style="width: 44%;"/>
                                <input type="text" name="permanentStateAddress"
                                       placeholder="State"
                                       onkeypress="return onlyAlphabets(event);"
                                       value="${studInstance?.permanentStateAddress}" maxlength="30"
                                       id="permanentStateAddress" style="width: 44%;"/>
                            </td>

                            <td>
                                <input type="text" name="mailingAddressDistrict"
                                       placeholder="District"
                                       onkeypress="return onlyAlphabets(event);"
                                       value="${studInstance?.mailingAddressDistrict}"
                                       id="mailingAddressDistrict" style="width: 44%;"/>
                                <input type="text" name="mailingStateAddress"
                                       placeholder="State"
                                       onkeypress="return onlyAlphabets(event);"
                                       value="${studInstance?.mailingStateAddress}" maxlength="30"
                                       id="mailingStateAddress" style="width: 44%;"/>
                            </td>
                        </tr>
                        <tr style="display: none;" id="pin_MobileNo" class="editAddress">
                            <td>
                                <input type="text" name="permanentPincode" id="permanentPincode"
                                       value="${studInstance?.permanentPincode}" placeholder="Pincode"
                                       maxlength="6" style="width: 36%" onkeypress="return isNumber(event)"/>
                                <input type="text" id="permanentCntryCode" name="permanentCntryCode"
                                       style="width: 25px" maxlength="3" value="+91" readonly> - <input
                                    type="text" id="permanentMobileNo" name="permanentMobileNo"
                                    value="${studInstance?.permanentMobileNo}" placeholder="Phone No"
                                    maxlength="10" style="width: 40%" onkeypress="return isNumber(event)"/>
                            </td>
                            <td>
                                <input type="text" name="mailingPincode" id="mailingPincode"
                                       value="${studInstance?.mailingPincode}" placeholder="Pincode"
                                       maxlength="6" style="width: 36%" onkeypress="return isNumber(event)"/>
                                <input type="text" id="mailingCntryCode" name="mailingCntryCode"
                                       style="width: 25px" maxlength="3" value="+91" readonly> - <input
                                    type="text" id="mailingMobileNo" name="mailingMobileNo"
                                    value="${studInstance?.mailingMobileNo}" placeholder="Phone No"
                                    maxlength="10" style="width: 40%" onkeypress="return isNumber(event)"/>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>

        </table>
    </td>
</tr>
<tr><td colspan="2">
    <table class="university-size-full-1-1" id="feeTable">
        <tr>
            <td class="university-size-1-3">Fee Details<span class="university-obligatory">*</span><br/><br/><br/></td>
            <td class="university-size-1-3">
                <div><input type="text" PLACEHOLDER="GU Receipt No" required class="guReceiptNo university-size-2-3"
                            name="guReceiptNo"
                            style="margin-bottom: 10px;"
                            maxlength="15" onkeypress="return isAlphaNumeric(event);"></div>

                <div><input type="text" PLACEHOLDER="Amount" required class="guReceiptAmount university-size-2-3"
                            name="guReceiptAmount"
                            style="margin-bottom: 10px;"
                            maxlength="15" onkeypress="return isNumber(event);"></div>

                <div><input type="text" name="guReceiptDate" required maxlength="10" PLACEHOLDER="DD/MM/YYYY"
                            class="guReceiptDate university-size-2-3" onkeydown="disableKeyInput(this)"
                            value=""></div>
            </td>
            <td class="university-size-1-3"><input type="button" value="Add More GU Receipt" onclick="addFeeRow()"/>
            </td>
        </tr>
    </table>
</td>
</tr>
<tr>
    <td>Apply For Semester Drop</td>
    <td><label style="padding: 3px 20px;"><input type="radio" value="yes" name="semContinuation">Yes
    </label><label><input type="radio" value="No" name="semContinuation" checked>No</label></td>
</tr>
</table>

<div id="semTegTable" class="university-size-full-1-1">
    <g:if test="${totalList.size() > 0}">
        <table id="studentCourses" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
               style="margin: 5px auto;"><thead>
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
        <tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;"
                colspan="8">Mandatory Papers</td></tr>
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
                    <td>
                        <input name='studentSubjects' class="studentSubjects, checkbox" type='checkbox'
                               value="${list.id}">
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>

    <g:if test="${optionalList.size() > 0}">
        <table id="studentOptionalSubjects" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
               style="margin: auto;">
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
            <tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;"
                    colspan="8">Elective Papers</td></tr>

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
                    <td>
                        <input name='studentSubjects' class="studentSubjects , checkbox" type='checkbox'
                               value="${list.id}">
                    </td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
    <g:if test="${totalList1.size() > 0}">
        <table id="studentExtraCourses" class="university-size-full-1-1" cellspacing="0" cellpadding="0"
               style="margin: auto;">
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
            <tr><td style="background-color: bisque;text-align: center;font-size: 16px;line-height: 18px;font-weight: bold;"
                    colspan="8">Back Papers</td></tr>
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
                    <td>
                        <input name='studentExtraSubjects' class="studentSubjects, checkbox" type='checkbox' value="${list.id}">
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>
</div>

<div style="width: 100%; margin: 10px auto;text-align: center; font-size: 16px;font-weight: bolder;">
    TOTAL SEMESTER CREDIT: <label id="totalSemCredit">0</label>
</div>
<input type="text" name="subjectSelected" id="subjectSelected" value=""
       style="width: 0px;height: 0px;visibility: hidden;"/>

<div style="width: 100%; margin: 10px auto;text-align: center;">
    <input type="submit" name="submit" value="Submit" onclick="validate()" class="university-button" id="semRegButton"/>
</div>
</g:form>
</fieldset>
</g:if>
<g:else>
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>

                <g:if test="${flash.message}">
                    <div class="message"><div class="university-status-message">${flash.message}</div></div>
                </g:if>
            </div></div>
        </div>
    </fieldset>

</g:else>
</g:else>
</div>
<script>

    function getSubjectForSemester() {
        var programId = $("#programId").val()
        var studentSemester = $("#studentSemester").val()
        if (programId && studentSemester) {
            $.ajax({
                type: "post",
                url: url('student', 'getCourseForStudent', ''),
                data: {programId: programId, studentSemester: studentSemester},
                success: function (data) {
                    $('#studentCourses tr:gt(0)').empty()
                    if (data.totalList.length > 0)
                        $('#studentCourses').prop('hidden', false)
                    if (data.totalList1.length > 0)
                        $('#studentExtraCourses').prop('hidden', false)
                    if (data.optionalList.length > 0)
                        $('#studentOptionalSubjects').prop('hidden', false)
                    $('#semRegButton').css("visibility", "visible");
                    $('#studentCourses tbody').empty().append()
                    for (var i = 0; i < data.totalList.length; i++) {
                        $('#studentCourses  tbody').append("<tr> " +
                                "<td>" + data.totalList[i].subjectName + "</td>" +
                                "<td>" + data.totalList[i].subjectCredit + "</td>" +
                                "<td><input name='studentSubjects' type='checkbox' value=" + data.totalList[i].id + "></td>" +
                                "</tr>")
                    }
                    $('#studentOptionalSubjects tbody').empty().append()
                    for (var i = 0; i < data.optionalList.length; i++) {
                        $('#studentOptionalSubjects tbody').append("<tr> " +
                                "<td>" + data.optionalList[i].subjectName + "</td>" +
                                "<td>" + data.optionalList[i].subjectCredit + "</td>" +
                                "<td><input name='studentSubjects' type='checkbox' value=" + data.optionalList[i].id + "></td>" +
                                "</tr>")
                    }
                    $('#studentExtraCourses tbody').empty().append()
                    for (var i = 0; i < data.totalList1.length; i++) {
                        $('#studentExtraCourses tbody').append("<tr> " +
                                "<td>" + data.totalList1[i].subjectName + "</td>" +
                                "<td>" + data.totalList1[i].subjectCredit + "</td>" +
                                "<td><input name='studentExtraSubjects' type='checkbox' value=" + data.totalList1[i].id + "></td>" +
                                "</tr>")
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }

    }
</script>

</body>
</html>





