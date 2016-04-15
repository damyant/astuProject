<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 11/17/14
  Time: 3:38 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='validation.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Student Login</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"> <label class="error">${flash.message}</label></div>
        </g:if>
        <g:form name="stuLogin" id="stuLogin" controller="student" action="validateStudent">
            <div class="" style="width: 70%;margin:40px auto 10px; text-align: center;">


                <div class="input university-bottom-margin">
                    <label for="username" class="university-right-margin">Username</label><input type="text"
                     maxlength="8" name="rollNo" id="username" class="university-size-1-11" style="margin-left: 4px" onkeypress="return isNumber(event)" placeholder="Roll Number"/><label id="rollMsg" class="error"></label>
                </div>
                <div class="input university-bottom-margin">
                    <label for="password" class="university-right-margin">Password</label><input type="password" maxlength="10" class="university-size-1-11" name="password" id="password"  placeholder="Password" /><label id="dobMsg"></label>
                </div>
                <input type="Submit"  value="Submit" class="ui-button"  onclick="validate()" />
            </div>
        </g:form>
    </fieldset>
</div>
<script>
    $(function () {
        $(function () {
            $("#dob").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
    });
</script>
</body>
</html>