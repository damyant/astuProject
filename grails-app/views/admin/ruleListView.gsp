<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for showing list of rules and provide options for viewing details of rule, editing and deleting rules --}%
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
        <h3>List Of Rule</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if><div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        %{--<g:form controller="admin" action="saveEditRuleRequirement" name="editRuleRequirement" id="editRuleRequirement">--}%
        <table class="university-size-full-1-1 inner">
            <tr>
                <th class="university-size-1-5">Rule Name</th>
                <th class="university-size-1-5">Required Status</th>
                <sec:ifAnyGranted roles="ROLE_ADMIN"><th
                        class="university-size-1-5">Edit Required Status</th></sec:ifAnyGranted>
                <th class="university-size-1-5">View Rule Details</th>
                <sec:ifAnyGranted roles="ROLE_ADMIN"><th class="university-size-1-5">Delete Rule</th></sec:ifAnyGranted>
            </tr>
            <g:each in="${ruleNameList}" var="ruleInst" status="seqNo">
                <tr>
                    <td>${ruleInst}</td>
                    <td style="text-transform: uppercase;">${statusList[seqNo]}</td>
                    <sec:ifAnyGranted roles="ROLE_ADMIN"><td><g:if test="${statusList[seqNo]!='NA'}"><g:link controller="admin" action="editRuleRequirement"
                                                                                                             id="${idList[seqNo]}"><button>Edit</button></g:link></g:if>
                    </td></sec:ifAnyGranted>
                    <td><g:link controller="admin" action="viewRuleDetails"
                                id="${ruleID[seqNo]}"><button>View</button></g:link></td>
                    <sec:ifAnyGranted roles="ROLE_ADMIN"><td><g:link controller="admin" action="deleteRuleDetails"
                                                                     id="${ruleID[seqNo]}"><button>Delete</button></g:link>
                    </td></sec:ifAnyGranted>
                </tr>
            </g:each>
        </table>

        %{--</g:form>--}%
    </fieldset>
</div>

</body>
</html>
