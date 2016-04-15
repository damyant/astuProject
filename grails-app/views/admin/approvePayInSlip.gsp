<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 5/5/2014
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
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
        <h3>Approve Pay-In-Slip</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <g:form id="approvePayInSlip" name="approvePayInSlip" controller="admin" action="approveFeeForStudents">

        <table class="inner university-size-full-1-1">
            <tr>
                  <td class="university-size-1-4">Pay-In-Slip Challan No</td>
                <td class="university-size-1-4"><input type="text" class="university-size-1-1" name="payInSlipNo" id="payInSlipNo"></td>
                <td class="university-size-1-2"><input type="button" value="Get Details" onclick="populateChallanDetail()"/>  <div id="error" class="error5" hidden="hidden">No Student Found </div></td>
            </tr>

        </table>

          <table id="allStudentList" class="inner" style="margin: 40px auto;" cellpadding="10" cellspacing="6">
            <tbody>

            </tbody>


        </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>