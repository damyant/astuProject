<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='validation.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create Examination Type</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        <g:form controller="admin" action="saveExamType" name="saveExamType" id="saveExamType">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td class="university-size-1-3">Examination Type Name</td>
                    <td class="university-size-2-3">
                        <input type="text" class="university-size-1-2" name="examSubName" id="examSubName"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Required Total Marks</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="3" class="university-size-1-2" name="requiredTotalMarks" id="requiredTotalMarks" onkeypress="return isNumber(event);"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"   style="line-height: 15px;text-transform: capitalize;">Symbol for Marks Secured By Student In the Examination</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="marksSecuredSymbol" id="marksSecuredSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"   style="line-height: 15px;text-transform: capitalize;">Symbol for Total marks of the Examination</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="totalMarksSymbol" id="totalMarksSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"  style="line-height: 15px;text-transform: capitalize;"> Symbol for Total Marks student Secured in the Exam after Applying Rule</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="examSubTypeTotalSymbol" id="examSubTypeTotalSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Submit" id="submitButton" onclick="validate()" class="university-button"></td>
                </tr>
            </table>

        </g:form>
    </fieldset>
</div>
</body>
</html>
