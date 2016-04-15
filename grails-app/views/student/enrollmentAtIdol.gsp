<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 4/23/2014
  Time: 5:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Enrollment At Idol</title>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.ui.datepicker.js')}"></script>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%

</head>
<body>
<div id="main">
<g:if test="${!studyCentre}">
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>

                <p><g:message code="registration.denied.message"/></p>
            </div></div>
        </div>
    </fieldset>
</g:if>
<g:elseif test="${!programList}">
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>

                <p><g:message code="Admission.denied.message"/></p>
            </div></div>
        </div>
    </fieldset>
</g:elseif>
<g:else>
%{--<g:set var="index" value="1"/>--}%
<fieldset class="form">
   <h3>Student Enrollment</h3>
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<div class="university-status-message"><div id="errorMessage"></div></div>

<form id="tempEnrollment">
    <div style="margin-left: 10px;"><label><h6>All [<span
            class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
    <table class="inner university-size-full-1-1">
        <tr>
            <td class="university-size-1-3">Name of the applicant <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
                    <tr>
                        <td>
                            <input type="text" tabindex="1" name="firstName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="" placeholder="First Name"/>

                        </td>
                        <td style="vertical-align: top;">
                            <input type="text" tabindex="2" name="middleName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="" placeholder="Middle Name"/>
                        </td>
                        <td>
                            <input type="text" tabindex="3" name="lastName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="" placeholder="Last Name"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>Programme<span class="university-obligatory">*</span></td>
            <td>
                <g:select name="programId" id="programId" optionKey="id" tabindex="4"
                          onchange="loadProgramFeeAmountAtIdol(this),enableApplicationNo(),checkCourseCodeLength(this)" class="university-size-1-2"
                          optionValue="courseName" from="${programList}" noSelection="['': ' Select Programme']"/>
                <g:hiddenField name="idol" value="idol"/>
                <label id="ProgrammeNotExist" class="error"></label>
                <label id="courseCodeLength" class="error"></label>
            </td>
        </tr>
        <tr>
            <td class="university-size-1-3">Application Number <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <input type="text" name="applicationNo" id="applicationNo" tabindex="5" onchange="return checkApplicationNumber(this)"
                       onkeypress="return isNumber(event)" maxlength="10" class="university-size-1-2" disabled/>
                <label id="errorMsg" class="error1"></label>
            </td>
        </tr>

        <tr>
            <td>Date of Birth <span class="university-obligatory">*</span></td>
            <td>
                %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
                <input type="text" name="d_o_b" maxlength="10" tabindex="6" PLACEHOLDER="DD/MM/YYYY"
                       class="university-size-1-2" id="datepicker2">

            </td>
        </tr>
        <tr>
            <!----- Contact centre/study centre ---------------------------------------------------------->
            <td>Study centre <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" name="studyCentre" class="university-size-1-2" value="${studyCentre?.name}"
                       readonly/>
                <input type="hidden" name="studyCentreCode" value="${studyCentre?.centerCode}">
            </td>
        </tr>
        <tr>
            <td>Category <span class="university-obligatory">*</span></td>
            <td>
                <div class="radio_options">
                    <label><span>General</span><input type="radio" tabindex="7" name="category" value="General"
                                                      class="radioInput"/></label>

                    <label><span>MOBC</span><input type="radio" name="category" value="MOBC" class="radioInput"/>
                    </label>

                    <label><span>OBC</span><input type="radio" name="category" value="OBC" class="radioInput"/></label>

                    <label><span>SC</span><input type="radio" name="category" value=" SC" class="radioInput" style=""/>
                    </label>

                    <label><span>ST</span><input type="radio" name="category" value="S.T" class="radioInput"/></label>

                    <label><span>MINORITY</span><input type="radio" name="category" value="MINORITY COMMUNITY"
                                                       class="radioInput"/>
                    </label>
                </div>
            </td>
        </tr>
        <tr>
            <td>Gender <span class="university-obligatory">*</span></td>
            <td>
                <div class="radio_options">
                    <label><span>Male</span><input type="radio" tabindex="8" name="gender" value="Male"
                                                   class="radioInput"/></label>
                    <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/>
                    </label>
                    <label><span>Other</span><input type="radio" name="gender" value="Other" class="radioInput"/>
                    </label>
                </div>
            </td>
        </tr>
        <tr>
            <td>Mobile Number <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" id="mobileNoCntryCode" name="mobileNoCntryCode" maxlength="3" value="+91"
                       readonly> - <input
                    type="text" id="mobileNo" name="mobileNo" maxlength="10" tabindex="9"
                    onkeypress="return isNumber(event)"/>
            </td>
        </tr>
        <tr>
            <td>Select District<span class="university-obligatory">*</span></td>
            <td>
                <g:select name="examDistrict" id="district" tabindex="10" optionKey="id"
                          value=""
                          class="university-size-1-2"
                          onChange="showExamCenterList()" optionValue="districtName"
                          from="${districtList}" noSelection="['': ' Select District']"/>
            </td>
        </tr>
        <tr>
            <td>Select Preference of examination Centre <span class="university-obligatory">*</span></td>
            <td>
                <g:select name="examinationCentre" tabindex="11" id="examinationCentre" class="university-size-1-2"
                          from=" " noSelection="['': 'Select Examination Centre']"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>

                <input type="button" value="Submit" id="idolSubmitButton" tabindex="12"
                       onclick="submitTempRegistration()" class="university-button">
                <input type="reset" value="Reset" tabindex="13" onclick="resetImage()" class="university-button">
            </td>
        </tr>
    </table>
</form>


<div id="challanDiv" class="dialog" style="width: 320px;margin:auto;">
    <input type="button" id="print" value="Print" onclick="printFeeChallan('#feeChallanDiv')" style="text-align: center;">
<div id="feeChallanDiv" style="letter-spacing:2pt;border:0px;font-family:Arial, Gadget, sans-serif;font-style: normal;height: 300px;margin: auto;">
%{--<div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">----------------------------------------BANK COPY----------------------------------------</div>--}%

<div style="border: 0px solid;">
    <div style="width:80%;margin:15px auto;letter-spacing:2pt;font-size: 11px;font-weight: normal;">
        <div style="float: right;">
            <lable>Challan. No.</lable>
            <label id="challanNo" style=""></label>
        </div>
    </div>
    <div class="university-clear-both"></div>
    <div style="width: 100%;">&nbsp;</div>
    <div style="width:100%;text-align: center;letter-spacing:2pt;text-transform:capitalize;font-size: 11px;font-weight: 100;">
        <div>&nbsp;</div><div>&nbsp;</div>
        <div style="text-transform:none;font-size:11px;">State Bank of India    /    United Bank of India</div>
        %{--<div style="text-transform:uppercase;font-size: 11px;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>--}%
        <div style="text-transform:none;font-size: 11px;margin-top: 1px;margin-bottom: 1px;"><label style="border: 1px solid;padding-left: 5px;padding-right: 5px;">SBI A/C No. <g:message code="default.Bank.AcNo"/></label><label>     </label><label style="border: 1px solid;padding-left: 5px;padding-right: 5px;">UBI A/C No. <g:message code="default.Bank.UBI.AcNo"/></label></div>
        <div style="text-transform:none;font-size: 11px;">Institute of Distance and Open Learning</div>
        <div style="text-transform:capitalize;font-size: 11px;">Gauhati University</div>
    </div>

    <div style="clear: both; margin-bottom: 10px;"></div>
    <table  style="width:80%;margin:auto;letter-spacing:2pt;text-transform: capitalize;border: solid 0px black;font-weight: 100;">
        <tr><td style="width: 45%;font-size: 10px;padding-left: 5px;"><lable>Name</lable></td><td style="width: 55%;font-size: 11px;"><label id="studentName"></label></td></tr>
        <tr><td  style="font-size: 10px;padding-left: 5px;"><lable>Roll No</lable></td><td style="font-size: 11px;letter-spacing:2px;"><label id="studentRollNo"></label></td></tr>
        <tr><td  style="font-size: 10px;padding-left: 5px;">Type of Fee</td><td style="font-size: 11px;"><label id="feeType"></label></td></tr>
        <tr><td  style="font-size: 10px;padding-left: 5px;"><lable>Amount</lable></td><td style="font-size: 11px;letter-spacing:2px;"><label id="amount"></label>
            <label style="display: block" id="feeInWord"></label><label style="display: block" id="lateFee"></label>
        </td></tr>
        <tr><td  style="height:60px">&nbsp;</td><td  style="height:60px">&nbsp;</td></tr>
        <tr><td style="vertical-align: bottom;width: 40%;font-size: 10px;text-transform: capitalize;padding-left: 5px;"><g:formatDate format="dd/MM/yyyy" date="${new Date()}"/></td>
            <td style="vertical-align: bottom;">
                <div style="width:95%;margin:auto;text-align: right; bottom: 2px;font-size: 10px;"><label style="float:right;">Signature</label></div>
            </td>
        </tr>
    </table>
</div>
</fieldset>
</g:else>

</div>
<script type="text/javascript">
    $(function () {
        $(function () {
            $("#datepicker2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
//        maxWidth:600,
//        maxHeight: 500,
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title: 'Fee Voucher',
            close: function (ev, ui) {
                $.unblockUI();
//            getStudentsList()
            }

        });
    });

</script>

</body>
</html>