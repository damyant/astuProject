<%--
  Created by Damyant Software Pvt Ltd.
  User: sanjay
  Date: 12/24/14
  Time: 3:54 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for Adding roll no of new students--}%
<html>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
    <g:javascript src='validation.js'/>
    <g:javascript src='admin.js'/>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script>
        $(document).ready(function() {
            $('#rollNo').focus();
        });
    </script>

</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Add Student's Roll No</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="student" action="addUser" id="addStudent" name="addStudent" method='post' enctype="multipart/form-data">

            <div style="margin-left: 10px;margin-top: 20px;"><label><h5>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h5></label></div>
            <table class="university-size-full-1-1 inner">
            <tr>
                <td class="university-size-1-3"><label for="rollNo">Enter Roll No<span class="university-obligatory">*</span></label></td>
                <td class="university-size-2-3 ">
                    <input type="text" class="university-size-1-4" name="rollNo" maxlength="12" id="rollNo" onkeypress="return isNumber(event)"/>
                    %{--<input type="hidden" value="${rollInstance?.id}" name="feeId"/>--}%
                </td>
            </tr>

            <tr>
                <td class="university-size-1-3 ">&nbsp;</td>

                <td class="university-size-2-3 "><input type="submit" value="Submit"  onclick="validate()" class="university-size-1-4  university-button"/></td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>

</body>
</html>