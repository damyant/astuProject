<%--
  Created by Damyant Software Pvt Ltd.
  User: shweta
  Date: 3/26/14
  Time: 10:24 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Generate Fee Voucher</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript">

    </script>
    %{--${params}--}%
    <script type="text/javascript">
        $(function () {
            $("#rollNo").blur(function () {
                var rollNo = $("#rollNo").val()
                var feeType = 0
                var url = "${createLink(controller:'admin', action:'checkFeeByRollNo')}"
                $.getJSON(url, {rollNo: rollNo, feeType: feeType}, function (json) {
//                        alert(json.feeStatus)
                    if (json.feeStatus) {

                        $("#submit").prop('disabled', false);
//                            $("#feeType").prop('disabled', false);
                        $("#feeError").prop('hidden', true);

                    } else {
                        $('#errorMsg').show();
                    }
                });
//                }
            })
        });
    </script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Generate Duplicate Pay-In-Slip(Unapproved)</h3>
        <g:if test="${params.rollNo == 'generated'}">
            <div class="message"><div class="university-status-message" id=""><g:message
                    code="rollNo.Generated.message"/></div>
            </div>
        </g:if>
        <label id="statusError"></label>

        <div class="university-status-message" id="errorMsg" hidden="hidden">Roll Number does not belongs to IDOL</div>



        <g:form id="postExamFee" name="postExamFee">
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="inner" style="margin: auto;text-align: center; width: 100%">
                <tr>
                    <td class="university-size-1-3">
                        <p>Enter Roll Number:<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                        <g:textField name="rollNumberInput" id="rollNumberInput" class="university-size-1-3"
                                     onblur="searchByRollNumber()"
                                     onkeypress="return isNumber(event)"/><label id="feeError" class="error"></label>

                    </td>
                </tr>

                <tr><td>
                    <p><label for="feeType">
                        <g:message code="programFee.programDetail.label" default="Program Name"/>:<span
                                class="university-obligatory">*</span>
                    </label></p>
                </td>
                    <td>
                        <g:select name="postFeeType" id="postFeeType" from="" class="many-to-one university-size-1-3"
                                  disabled="true" onchange="clearSelectBox()"></g:select>

                    </td>

                </tr>
                <tr>
                    <td>
                        <p><label for="semester">
                            <g:message code="programFee.term.label" default="Semester"/>:<span
                                    class="university-obligatory">*</span>
                        </label></p>
                    </td>
                    <td>
                        <g:select name="semester" id="semester" disabled="true" onchange="enableDuplicateButton()" from="" class="many-to-one university-size-1-3"></g:select>
                    </td>
                </tr>

                <tr><td colspan="2" style="text-align: center; ">
                    <input type="button" name="submit" id="submit" class="university-button"
                           onclick="generateDuplicateChallan()" value="Submit"
                           style="margin-top: 15px;"/>
                </td>
                </tr>
            </table>

        </g:form>
    </fieldset>
</div>

<div id="challanDiv" class="dialog" style="width: 320px;margin:auto;">
    <input type="button" id="print" value="Print" onclick="printFeeChallan('#feeChallanDiv')"
           style="text-align: center;">

    <div id="feeChallanDiv"
         style="font-family: Times New Roman, Times, serif;border:0px;font-style: normal;height: 300px;margin: auto;font-weight: bold;">
        %{--<div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">----------------------------------------BANK COPY----------------------------------------</div>--}%

        <div style="border: 0px solid;">
            <div style="width:80%;margin:15px auto;font-size: 11px;font-weight: bold;letter-spacing:2px;">
                <div style="float: right;">
                    <lable>Challan. No.</lable>
                    <label id="challanNo" style=""></label>
                </div>
            </div>

            <div class="university-clear-both"></div>

            <div style="text-align: center;text-transform:capitalize;font-size: 9px;letter-spacing:2px;">
                <div>&nbsp;</div>

                <div>&nbsp;</div>

                <div style="text-transform:none;font-size:11px;">State Bank of India</div>
                %{--<div style="text-transform:uppercase;font-size: 11px;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>--}%
                <div style="text-transform:none;font-size: 11px;margin-top: 1px;margin-bottom: 1px;"><label
                        style="border: 1px solid;padding-left: 5px;padding-right: 5px;">SBI A/C No. <g:message
                            code="default.Bank.AcNo"/></label><label></label></div>

                <div style="text-transform:none;font-size: 11px;">Institute of Science and Technology</div>

                <div style="text-transform:capitalize;font-size: 11px;">Gauhati University</div>
            </div>

            <div style="clear: both; margin-bottom: 10px;"></div>
            <table style="width:80%;margin:auto;text-transform:capitalize;border: solid 0px black;letter-spacing:1px;font-family: Times New Roman, Times, serif;font-style: normal;font-weight: bold;">
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Name:</lable></td><td
                        style="width: 60%;font-size: 10px;"><label id="studentName"></label></td></tr>
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Roll No:</lable></td><td
                        style="width: 60%;font-size: 10px;font-weight: bolder;letter-spacing:2px;"><label
                            id="studentRollNo"></label></td></tr>
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;">Type Of Fee:</td><td
                        style="width: 60%;font-size: 10px;"><label id="feeType"></label></td></tr>
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;">Term/Semester:</td><td
                        style="width: 60%;font-size: 10px;"><label id="term"></label></td></tr>
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Amount:</lable></td><td
                        style="width: 60%;font-size: 11px;font-weight: bolder;letter-spacing:2px;"><label
                            id="amount"></label>
                    <label style="display: block" id="feeInWord"></label><label style="display: block"
                                                                                id="lateFee"></label>
                </td></tr>
                <tr><td style="height:60px">&nbsp;</td><td style="height:60px">&nbsp;</td></tr>
                <tr><td style="vertical-align: bottom;width: 40%;font-size: 9px;padding-left: 5px;">${new Date()}</td>
                    <td style="vertical-align: bottom;">
                        <div style="width:95%;margin:auto;text-align: right; bottom: 2px;font-size: 9px;"><label
                                style="float:right;">Cashier's Signature</label></div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title: 'Fee Voucher',
            close: function (ev, ui) {
                $.unblockUI();
            }

        });
    });
</script>
</body>
</html>