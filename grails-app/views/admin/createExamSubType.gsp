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
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create Examination Sub Type</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if><div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        <g:form controller="admin" action="saveSubExamType" name="saveSubExamType" id="saveSubExamType">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td class="university-size-1-3">Select Examination Type</td>
                    <td class="university-size-2-3">
                        <g:select name="exmType" class="university-size-1-2" id="exmType" optionKey="id"
                                  optionValue="examTypeName"
                                  from="${examTypeList}" noSelection="['': ' Select Examination Type']"/>

                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Sub Examination Type Name</td>
                    <td class="university-size-2-3">
                        <input type="text" class="university-size-1-2" name="examSubName" id="examSubName"/>

                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"  style="line-height: 15px; text-transform: capitalize;">Symbol for Marks Secured By Student In the Examination</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="marksSecuredSymbol" id="marksSecuredSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"  style="line-height: 15px;text-transform: capitalize;">Symbol for Total marks of the Examination</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="totalMarksSymbol" id="totalMarksSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"  style="line-height: 15px;text-transform: capitalize;">Symbol for Maximum marks among All Students</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="maxInAllStudentsSymbol" id="maxInAllStudentsSymbol"onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr><tr>
                <td class="university-size-1-3"  style="line-height: 15px;text-transform: capitalize;">Symbol for Total Marks student Secured in the Exam after Applying Rule</td>
                <td class="university-size-2-3">
                    <input type="text" maxlength="1" class="university-size-1-2" name="examSubTypeTotalSymbol" id="examSubTypeTotalSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                </td>
            </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" id="submitButton" onclick="validate()" value="Submit" class="university-button"></td>
                </tr>
            </table>

        </g:form>
    </fieldset>
</div>
</body>
</html>
