<%--
  Created by Damyant Software Pvt Ltd.
  User: chauhan611
  Date: 1/13/15
  Time: 10:12 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<fieldset class="form">
    <h3>Faculty Advisor </h3>
    <g:form  controller="hod" action="saveFacultyAdvisor">

        <div style="margin-left: 10px;"><label><h6>All [<span
                class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
        <table class="inner university-size-full-1-1">
            <tr>
                <g:hiddenField name="employeeId" value="${employeeObj?.id}" />
                <td class="university-size-1-3">Name<span class="university-obligatory">*</span></td>
                <td class="university-size-1-3"><input type="text"  name="name" onkeypress="return onlyAlphabets(event);"
                                                       maxlength="50" class="university-size-1-2" value="" placeholder=" Name"/> </td>
            </tr>
            <tr>
                <td class="university-size-1-3">Department<span class="university-obligatory">*</span></td>
                <td class="university-size-2-3">
                    <g:select name="department" id="programId" optionKey="id" class="university-size-1-2" value=" "
                              optionValue="name" from="${depList}" noSelection="['': ' Select Department']"/>
                    <label id="errorMsg" class="error1"></label>
                </td>
            </tr>
            <tr>
                <td>E-mail <span class="university-obligatory">*</span></td>
                <td>
                    <input type="text" name="email"  PLACEHOLDER="e-mail" value=""
                           class="university-size-1-2" id="email">
                </td>
            </tr>
            <tr>
                <td>Username <span class="university-obligatory">*</span></td>
                <td>
                    <input type="text" name="username"  PLACEHOLDER="Username" value=""
                           class="university-size-1-2" id="username">
                </td>
            </tr>
            <tr>
                <td>Password<span class="university-obligatory">*</span></td>
                <td>
                    <input type="password" name="password"  PLACEHOLDER="Password" value=""
                           class="university-size-1-2" id="password">
                </td>
            </tr>
            <tr>
                <td>Confirm Password<span class="university-obligatory">*</span></td>
                <td>
                    <input type="password" name="cnfpassword"  PLACEHOLDER="Password" value=""
                           class="university-size-1-2" id="cnfpassword">

                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="SUBMIT" value="Submit" id="" class="university-button">
                </td>
            </tr>
        </table>
    </g:form>
</fieldset>
</div>
</body>

</body>
</html>