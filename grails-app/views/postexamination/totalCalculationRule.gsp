<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for creating rule for exam type--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create Total Calculation Rule</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if><div style="text-align: center"><label class="error1" id="symbolError"></label></div>

        <div style="text-align: center;font-size: 13px;font-weight: bold;margin-bottom: 30px;color: yellow;background-color: #0000FF;padding: 5px;">Please Maintain one space between each character in Rule expression and use Brackets (use only parentheses).</div>
        <g:form controller="admin" action="saveNewTotalRule" name="saveNewTotalRule" id="saveNewTotalRule">
            <table class="university-size-full-1-1 inner" id="totalCalculation">
                <thead>
                <tr>
                    <th></th>
                    <th>Select One</th>
                    <th>Variable for Rule Result</th>
                    <th>Rule Expression</th>
                    <th></th>
                </tr>
                </thead><tbody>
            <tr>
                <td>Rule For Total</td>
                <td><g:select name="forTotalOf" from="${examTypeList}" optionKey="id" optionValue="examTypeName" noSelection="['': ' Select Exam Type']" class="university-size-1-1 forTotalOf"></g:select></td>
                <td><input type="text" name="ruleVariable" placeholder="Enter Variable" maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                           class="university-size-1-3 ruleVariable"/></td>
                <td><input type="text" name="ruleExpression" placeholder="Enter Rule Expression"
                           class="university-size-1-1 ruleExpression"/></td>
                <td><input type="button" class="ui-button" value="+" onclick="addMoreSubExamType()"/></td>
            </tr>

            </tbody>
            </table>
            <table class="inner">
                <tr>
                    <td class="university-size-1-2"></td>
                    <td><input type="submit" value="Submit" id="submitButton" onclick="validate()" class="university-button"></td>
                </tr>
            </table>
        </g:form>

    </fieldset>
</div>
</body>
</html>
