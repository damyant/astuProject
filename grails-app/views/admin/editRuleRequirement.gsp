<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for editing name and requirement status of main rule--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
</head>
<body>
<div id="main">
    <fieldset class="form">
        <h3>Edit Rule Requirement</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if><div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        <g:form controller="admin" action="saveEditRuleRequirement" name="editRuleRequirement" id="editRuleRequirement">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td class="university-size-1-3">Rule Name</td>
                    <td class="university-size-2-3">
                        <g:select name="ruleId" from="${ruleNameList}" optionKey="id" optionValue="rule" value="${ruleNameInst?.id}" noSelection="['':'Select Rule Name']"></g:select>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Rule Status</td>
                    <td class="university-size-2-3">
                        <label><input type="radio" name="ruleRequired" value="yes">Required </label>
                        <label><input type="radio" name="ruleRequired" value="no">Not Required </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" id="submitButton"  value="Submit" class="university-button"></td>
                </tr>
            </table>

        </g:form>
    </fieldset>
</div>
<g:if test="${ruleNameInst}">

    <script type="text/javascript">

        $(document).ready(function () {
            if(${ruleNameInst.ruleRequired})  {
                $("input[name='ruleRequired'][value='yes']").attr('checked', 'checked');

            }
            else{
                $("input[name='ruleRequired'][value='no']").attr('checked', 'checked');
            }
        })
    </script>
</g:if>
</body>
</html>
