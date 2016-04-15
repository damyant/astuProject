<%--
  Created by IntelliJ IDEA.
  User: chauhan611
  Date: 2/9/15
  Time: 10:50 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='validation.js'/>
    <g:javascript src='admin.js'/>
    <title></title>
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Change Your Password</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="user" action="savePassword"  enctype="multipart/form-data"
                id="changePwd" name="changePwd">

            <table class="university-size-full-1-1 inner">

            <tr>
                <td class="university-size-1-3"><label for="pwd">Enter New Password<span class="university-obligatory">*</span></label></td>
                <td class="university-size-2-3 ">
                    <input type="password" class="university-size-1-3" name="pwd" id="pwd"/>
                     %{--onchange="confirmMatchPassword()"/>--}%

                </td>
            </tr>
            <tr>
                <td class="university-size-1-3"><label for="cpwd">Confirm New Password<span class="university-obligatory">*</span></label></td>
                <td class="university-size-2-3 ">
                    <input type="password" class="university-size-1-3" name="cpwd" id="cpwd"/>
                     %{--onchange="confirmMatchPassword1()"/>--}%

                </td>
            </tr>
            <tr>
                <td class="university-size-1-3 ">&nbsp;</td>
                <td class="university-size-2-3 ">
                    <input type="submit" value="Submit" onclick="validate()" id="saveChangePassword" class="university-size-1-4 ui-button"/></td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>