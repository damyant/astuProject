<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/19/14
  Time: 1:18 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{--This page is for adding a new employee--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Employee Page</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <g:javascript src='validate.js'/>
    <g:javascript src='validation.js'/>
    <g:javascript src='admin.js'/>
    <g:javascript src="postExamination.js"/>
    <script type="text/javascript">


        %{--$('#document').ready(function () {--}%

        %{--if(${params.id}){--}%
        %{--var gender = "${employeeObj?.gender}"--}%
        %{--$("input[name='gender'][value=" + gender + "]").attr('checked', 'checked');--}%
        %{--}--}%

        %{--});--}%
        function copyAllAdress() {
            /*function for copying current address to permanent address*/

            if ($('#sameAsPermanent').prop('checked')) {
                $('#permanentAddress').val($('#currentAddress').val())
                $('#permanentAddressCity').val($('#currentAddressCity').val())
                $('#permanentAddressDistrict').val($('#currentAddressDistrict').val())
                $('#permanentStateAddress').val($('#currentStateAddress').val())
                $('#permanentPincode').val($('#currentPincode').val())

            }
            else {
                $('#permanentAddress').val('')
                $('#permanentAddressCity').val('')
                $('#permanentAddressDistrict').val('')
                $('#permanentStateAddress').val('')
                $('#permanentPincode').val('')

            }

        }
    </script>

    <script>


        var i = 0;
        var j = 0;
        function showOther() {
            if ($("#qualification").val() == "Others") {
                $("#hid").show();
            }
            else {
                $("#hid").hide();
            }
        }


        function addRow() {
            /*function for adding row for designation and date of joining of employee*/
            if (i == 0) {
                if(($('#row').find("input.ii").val()!="")&&($('#row').find("input.jj").val()!=""))
                {
                $('#tab tbody').append('<tr id="row'+i+'"><td><input type="text" class="ii" name="designation"></td>' +
                        '<td class="t"><input type="text" class="datepicker4 jj" name="joiningDate" PLACEHOLDER="DD/MM/YYYY" value=' + "<g:formatDate format="dd/MM/yyyy" date="${empDesObj?.joiningDate}"/>" + '></td>' +
                        '<td><img id ="im'+i+'" style="width:30px;float:right" src="${resource(dir: 'images', file: 'addButton.png')}" onclick="addRow()"></td></tr>');

                $('#im').remove();
                i++;
                }
                else
                {
                    alert("Please Enter Designation & Date of Joining");
                }

            }

            else {
                if(($('#row'+(i-1)).find("input.ii").val()!="")&&($('#row'+(i-1)).find("input.jj").val()!=""))
                {
//                    debugger;

                $('#tab tbody').append('<tr id="row'+i+'"><td class="t"><input type="text" class="ii" name="designation"></td>' +
                        '<td class="t"><input type="text" class="datepicker4 jj" name="joiningDate" PLACEHOLDER="DD/MM/YYYY" value=' + "<g:formatDate format="dd/MM/yyyy" date="${empDesObj?.joiningDate}"/>" +'></td>' +
                        '<td><img id ="im'+i+'" style="width:30px;float:right" src="${resource(dir: 'images', file: 'addButton.png')}" onclick="addRow()"></td></tr>');

                $('#im' + (i - 1)).remove();
                i++;


            }
                else
                {
                    alert("Please Enter Details");
                }
            }
            $(".datepicker4").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        }

        function addRowQualification() {
            /*function for adding row for qualification details*/
            if (j == 0) {
                if(($('#roww').find("input.degree").val()!="")&&($('#roww').find("input.university").val()!="")&&($('#roww').find("input.subject").val()!="")&&($('#roww').find("input.year").val()!=""))
                {
                $('#tab2 tbody').append('<tr id="roww'+j+'"><td><input type="text" class="degree"  name="degree"></td>' +
                        '<td><input type="text" class="university" name="university"></td> <td><input type="text" class="subject" name="subject"></td>' +
                        '</td> <td><input type="text" class="year" name="year"></td>' +
                        '<td><img id ="imi'+j+'" style="width:30px;float:right" src="${resource(dir: 'images', file: 'addButton.png')}" onclick="addRowQualification()"></td></tr>');
                $('#imi').remove();
                j++;
                }
                else{
                    alert("Please enter details")
                }
            }
            else {
                if(($('#roww'+(j-1)).find("input.degree").val()!="")&&($('#roww'+(j-1)).find("input.university").val()!="")&&($('#roww'+(j-1)).find("input.subject").val()!="")&&($('#roww'+(j-1)).find("input.year").val()!=""))
                {
                $('#tab2 tbody').append('<tr id="roww'+j+'"><td><input type="text"class="degree" name="degree"></td>' +
                        '<td><input type="text" class="university" name="university"></td> <td><input type="text" class="subject" name="subject"></td>' +
                        '</td> <td><input type="text" class="year" name="year"></td>' +
                        '<td><img id ="imi'+j+'" style="width:30px;" src="${resource(dir: 'images', file: 'addButton.png')}" onclick="addRowQualification()"></td></tr>');
                $('#imi' + (j - 1)).remove();
                j++;
            }
                else{
                    alert("Please enter details")
                }
        }
        }



    </script>
</head>

<body>

<div id="main">
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<fieldset class="form">
<h3>Employee Information Sheet</h3>
%{--form for entering employee information--}%
<g:uploadForm controller="employe" action="saveEmployee" method='post' enctype="multipart/form-data"
              id="employe1" name="employe1">
<div style="margin-left: 10px;"><label><h6>All [<span
        class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

<table class="inner university-size-full-1-1">
<tr>
    <g:hiddenField name="employeeId" value="${employeeObj?.id}"/>
    <td class="university-size-1-3">Name of the Employee <span class="university-obligatory">*</span></td>
    <td class="university-size-2-3">
        <table class="inner university-table-1-3 university-size-full-1-1" style="vertical-align: top;">
            <tr>
                <td style="width: auto">
                    <g:select name="salutation"  class="university-size-1-1" id="salutation" tabindex="0"
                              from="${salutation}" noSelection="['': ' Select One']"/>
                </td>
                <td style="width: auto">
                    <input type="text" tabindex="1" name="firstName"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${employeeObj?.firstName}"
                           placeholder="First Name"/>

                </td>
                <td style="vertical-align: top;width: auto">
                    <input type="text" tabindex="2" name="middleName"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${employeeObj?.middleName}"
                           placeholder="Middle Name"/>
                </td>
                <td>
                    <input type="text" tabindex="3" name="lastName"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${employeeObj?.lastName}"
                           placeholder="Last Name"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td class="university-size-1-3">Designation & Joining Date</td>
    <td>
        <table id="tab" class="inner university-size-full-1-1">

            <thead><tr>
                <td>Designation</td>
                <td>Date of Joining</td>
            </tr></thead>

            <tbody><tr id="row"><td ><input type="text"class="ii" name="designation" tabindex="4" maxlength="50"></td>
                <td class="t"><input type="text" name="joiningDate" PLACEHOLDER="DD/MM/YYYY" tabindex="5"
                       value="<g:formatDate format="dd/MM/yyyy" date="${empDesObj?.joiningDate}"/>"
                       class="datepicker3 jj">

            </td>
                <td><img id="im" style="width:30px;float:right;" src="${resource(dir: 'images', file: 'addButton.png')}"
                         class="window" onclick="addRow()"></td>

            </tr></tbody>
        </table>
    </td>
</tr>
%{--</g:else>--}%

<tr>
    <td class="university-size-1-3">Employee Code <span class="university-obligatory">*</span></td>
    <td class="university-size-2-3">
        <input type="text" name="employeeCode" id="employeeCode" tabindex="6" value="${employeeObj?.employeeCode}"
               onkeypress="return alphanumericWithSplChar(event)" onblur="validEmpCode()" maxlength="20" class="university-size-1-2"/>
        <label id="errorMsg" class="error1"></label>
    </td>
</tr>


<tr>
    <td class="university-size-1-3">Department/Office<span class="university-obligatory">*</span></td>
    <g:if test="${employeeObj?.department?.id}">
        <g:select name="department" class="university-size-1-2" id="departmentName" tabindex="7"
                  value="${employeeObj?.department?.id}" optionKey="id"
                  optionValue="name" from="${departList}" noSelection="['': ' Select Department']"/>
    </g:if>
    <g:else>
        <td class="university-size-2-3">
            <g:select name="department" class="university-size-1-2" id="departmentName" optionKey="id" tabindex="7"
                      optionValue="name" from="${departList}" noSelection="['': ' Select Department']"/>
        </td>

    </g:else></tr>

<tr>
    <td class="university-size-1-3">Qualification</td>
    <td>
        <table id="tab2" class="inner university-size-full-1-1">
            <thead><tr><td>Degree</td><td>Institution</td><td>Subject</td><td>Year</td>

            </tr></thead>
            <tbody>
            <tr><td><input type="text" name="degree" maxlength="50" tabindex="8" value="Post Doctorate" readonly></td>
                <td><input type="text" name="university" maxlength="50" tabindex="9"></td>
                <td>
                    <input type="text" name="subject" maxlength="50" tabindex="10">

                </td>
                <td><input type="text" name="year" tabindex="11"></td></tr>
            <tr><td><input type="text" name="degree" maxlength="50" value="Ph.D" tabindex="12" readonly></td>
                <td><input type="text" name="university" maxlength="50" tabindex="13"></td>
                <td>
                    <input type="text" name="subject" maxlength="50" tabindex="14">

                </td>
                <td><input type="text" name="year" tabindex="15"></td></tr>
            <tr id="roww"><td><input type="text" class="degree" name="degree" maxlength="50" tabindex="16"></td>
                <td><input type="text" class="university" name="university" maxlength="50" tabindex="17"></td>
                <td>
                    <input type="text" class="subject" name="subject" maxlength="50" tabindex="18">

                </td>
                <td><input type="text" class="year" name="year" tabindex="19"></td>
                <td><img id="imi" style="width:30px;" src="${resource(dir: 'images', file: 'addButton.png')}"
                         class="window" onclick="addRowQualification()"></td>
            </tr></tbody>
        </table>
    </td>
</tr>
<tr>
    <td>E-mail 1<span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="firstEmail" class="university-size-1-2" tabindex="20" value="${employeeObj?.firstEmail}">
    </td>
</tr>
<tr>
    <td>E-mail 2 <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="secondEmail" class="university-size-1-2" tabindex="21" value="${employeeObj?.secondEmail}">
    </td>
</tr>

<tr>
    <td>Date of Birth <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="dob" maxlength="10" tabindex="22" PLACEHOLDER="DD/MM/YYYY"
               value="<g:formatDate format="MM/dd/yyyy" date="${employeeObj?.dob}"/>"
               class="university-size-1-2" id="datepicker2">

    </td>
</tr>
<tr>
    <td>Gender <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Male</span><input type="radio" tabindex="23" name="gender" value="Male"
                                           class="radioInput"/></label>
            <label><span>Female</span><input type="radio" tabindex="24" name="gender" value="Female" class="radioInput"/>
            </label>
            <label><span>Other</span><input type="radio" tabindex="25" name="gender" value="Other" class="radioInput"/>
            </label>
        </div>
    </td>
</tr>
<tr>
    <td class="university-size-1-3">Father's Name <span class="university-obligatory">*</span></td>
    <td class="university-size-2-3">
        <input type="text" name="fatherName" id="fatherName" value="${employeeObj?.fatherName}" tabindex="26"
               onkeypress="return onlyAlphabets(event)" maxlength="100" class="university-size-1-2"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-3">Mother's Name <span class="university-obligatory">*</span></td>
    <td class="university-size-2-3">
        <input type="text" name="motherName" id="motherName" value="${employeeObj?.motherName}" tabindex="27"
               onkeypress="return onlyAlphabets(event)" maxlength="100" class="university-size-1-2"/>

    </td>
</tr>
<tr>
    <td class="university-size-1-3">Marital Status<span class="university-obligatory">*</span></td>
    <g:if test=""><td class="university-size-2-3">

        <g:select name="maritalStatus" class="university-size-1-2" id="departmentName" tabindex="28"
                  value="${employeeObj?.maritalStatus}"
                  from="${marital}" noSelection="['': ' Select Marital Status']"/>
    </td></g:if>


    <g:else><td class="university-size-2-3">
        <g:select name="maritalStatus" class="university-size-1-2" id="departmentName" tabindex="29"
                  value="${employeeObj?.maritalStatus}"
                  from="${marital}" noSelection="['': ' Select Marital Status']"/>
    </td></g:else>
</tr>

<tr>
    <td class="university-size-1-3">Spouse's Name</td>
    <td class="university-size-2-3">
        <input type="text" name="spouseName" id="spouseName" tabindex="30" value="${employeeObj?.spouseName}"
               onkeypress="return onlyAlphabets(event)" maxlength="100" class="university-size-1-2"/>

    </td>
</tr>
<tr>
    <td>Address<span class="university-obligatory">*</span><br/><br/><br/></td>
    <td>
        <table style="width: 100%" id="examCenterAddress">
            <tr>
                <td class="university-size-1-2">Current Address</td>
                <td class="university-size-1-2"><div>Permanent Address</div>

                    <div style="margin-top: 10px;">
                        <label>
                            <input type="checkbox" tabindex="36" id="sameAsPermanent" onchange="copyAllAdress()">
                            Same As Current Address
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td><g:textArea class="university-size-1-1" value="${employeeObj?.currentAddress}" rows="5" tabindex="31"
                                id="currentAddress" name="currentAddress" placeholder="Address"></g:textArea></td>
                <td><g:textArea class="university-size-1-1" tabindex="37" value="${employeeObj?.permanentAddress}" rows="5"
                                name="permanentAddress" id="permanentAddress" placeholder="Address"></g:textArea></td>
            </tr>

            <tr>
                <td>
                    <input type="text" name="currentAddressCity" placeholder="City/Town" tabindex="32"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.currentAddressCity}"
                           id="currentAddressCity" style="width: 44%"/>
                    <input type="text" name="currentAddressDistrict" placeholder="District" tabindex="33"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.currentAddressDistrict}"
                           id="currentAddressDistrict" style="width: 44%"/>

                </td>

                <td>
                    <input type="text" name="permanentAddressCity" tabindex="38" placeholder="City/Town"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.permanentAddressCity}"
                           id="permanentAddressCity" style="width: 44%"/>
                    <input type="text" name="permanentAddressDistrict" tabindex="39" placeholder="District"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.permanentAddressDistrict}"
                           id="permanentAddressDistrict" style="width: 44%"/>

                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="currentStateAddress" placeholder="State" tabindex="34"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.currentStateAddress}"
                           maxlength="30" id="currentStateAddress" style="width: 44%"/>
                    <input type="text" tabindex="35" name="currentPincode" id="currentPincode" value="${employeeObj?.currentPincode}"
                           placeholder="Pincode" maxlength="6" style="width: 36%" onkeypress="return isNumber(event)"/>

                </td>
                <td>
                    <input type="text" name="permanentStateAddress" tabindex="40" placeholder="State"
                           onkeypress="return onlyAlphabets(event);" value="${employeeObj?.permanentStateAddress}"
                           maxlength="30" id="permanentStateAddress" style="width: 44%"/>
                    <input type="text" name="permanentPincode" id="permanentPincode" tabindex="41"
                           value="${employeeObj?.permanentPincode}" placeholder="Pincode" maxlength="6"
                           style="width: 36%" onkeypress="return isNumber(event)"/>

                </td>

            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>Contact Number 1 <span class="university-obligatory">*</span></td>
    <td><input type="text" style="width: 25px" maxlength="3" value="+91" readonly> -
        <input type="text" id="firstMobileNo" class="university-size-1-3" tabindex="42" name="firstMobileNo" maxlength="10"
               value="${employeeObj?.firstMobileNo}"
               onkeypress="return isNumber(event)"/>

    </td>
</tr>
<tr>
    <td>Contact Number 2 <span class="university-obligatory">*</span></td>
    <td><input type="text" style="width: 25px" maxlength="3" value="+91" readonly> -
        <input type="text" id="secondMobileNo" class="university-size-1-3" tabindex="43" name="secondMobileNo" maxlength="10"
               value="${employeeObj?.secondMobileNo}"
               onkeypress="return isNumber(event)"/>

    </td>
</tr>


<tr>
    <td>Emergency Contact No <span class="university-obligatory">*</span></td>
    <td><input type="text" style="width: 25px" maxlength="3" value="+91" readonly> -
        <input type="text" id="emergencyContactNo" class="university-size-1-3" name="emergencyContactNo" maxlength="10"
                tabindex="44" value="${employeeObj?.emergencyContactNo}"
                onkeypress="return isNumber(event)"/>
    </td>
</tr>





<tr>
    <td>PAN Card Number <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="panNumber" maxlength="13" tabindex="45"
               class="university-size-1-2" id="panNumber" value="${employeeObj?.panNumber}">

    </td>
</tr>
<tr>
    <td>Bank Account Number <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="accountNo" maxlength="50" tabindex="46"
               class="university-size-1-2" id="accountNo" value="${employeeObj?.accountNo}">

    </td>
</tr>
<tr>
    <td>Bank Name <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="bankName" maxlength="50" tabindex="47"
               class="university-size-1-2" id="bankName" value="${employeeObj?.bankName}">

    </td>
</tr>

<tr>
    <td>
        <label style="line-height: 18px;">Upload recent Passport size Photograph (Size: Less then 50KB )<span
            class="university-obligatory">*</span></label>
    </td>
    <td>
        <g:if test="${employeeObj}">
            <img src="${createLink(controller: 'employe', action: 'show', id: employeeObj?.id
                    , mime: 'image/jpeg')}" class="university-registration-photo" id="picture1"/>
            <input type='file' id="profileImage"  tabindex="48" onchange="readURL(this, 'picture1');" class="university-button"
                   name="photograph"/>
        </g:if>
        <g:else>
            <div id="profile-image"><img src="" alt="Space for Photograph "
                                         class="university-registration-photo" id="picture2"/></div>
            <input type='file' id="profileImage" tabindex="48" onchange="readURL(this, 'picture2');" class="university-button"
                   name="photograph"/>
        </g:else>
        <input type="text" id="imageValidate" name="imageValidate" value=""
               style="width: 1px;height: 1px;border: 0px;"/>

    </td>
</tr>

<tr>
    <td></td>
    <td>

        <input type="button" value="Submit" onclick="checkValidation()" id="submitButton" tabindex="49"
               class="university-button">
        <input type="reset" value="Reset" tabindex="50" onclick="resetImage()" class="university-button">
    </td>
</tr>
</table>
</g:uploadForm>

</fieldset>
%{--</g:else>--}%

%{--</div>--}%

</div>
<script type="text/javascript">
    $(function () {
        $(function () {
            $(".datepicker4").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $("#datepicker2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $(".datepicker3").datepicker({
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