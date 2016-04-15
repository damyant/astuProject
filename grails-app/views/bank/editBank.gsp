

<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 5/2/2014
  Time: 12:12 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='validation.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <g:hasErrors bean="${bankInstance}">
        <div class="errors">
            <g:renderErrors bean="${bankInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <fieldset class="form">
        <h3>EDIT BANK</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="bank" action="updateBank" method='post'  enctype="multipart/form-data" id="bankForm" name="bankForm">
            <div style="margin-left: 10px;margin-top: 20px;""><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="university-size-full-1-1 inner">
            <tr>
            <td class="university-size-1-3"><label for="bankName">Bank Name <span class="university-obligatory">*</span></label></td>
            <td class="university-size-2-3 ">
                <input type="text" class="university-size-1-2" value="${bankInstance?.bankName}" name="bankName" id="bankName"/>
                <input type="hidden" value="${bankInstance?.id}" name="bankId"/>
            </td>
        </tr>
        <tr>
                <td class="university-size-1-3 ">&nbsp;</td>
                <td class="university-size-2-3 "><input type="submit" value="Submit" onclick="validate()" class="university-size-1-4 ui-button"/></td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>
