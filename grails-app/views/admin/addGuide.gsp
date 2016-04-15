<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/27/14
  Time: 2:32 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for adding project guide(assigning project guide)--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src="admin.js"/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Add Guide</h3>

        <g:form controller="admin" action="saveGuide"  id="saveGuide"  name="saveGuide" >
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <table class="university-size-full-1-1 inner spinner" style="margin-top: 40px;">
                <tr>

                    <td class="university-size-1-3">Select branch<span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <g:select name="programBranch" class="university-size-1-2" optionKey="id"
                                  optionValue="name"  value="${catalogIns?.type?.id}"
                                  from="${branchList}" noSelection="['': ' Select Branch']"
                                  onchange="loadAllEmployee(this)"/>


                    </td>
                </tr>
                 <tr>

                    <td class="university-size-1-3">Academic Session <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3"><input type="text" name="academicSession" class="university-size-1-2" value="${catalogIns?.isbn}"></td>
                </tr>
                <tr>

                    <td class="university-size-1-3">Employee<span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3"><select name="employee"  id="employee" class="university-size-1-2"><option value=""> Select Employee</option></select></td>
                </tr>

                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Submit"/>
                    </td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>




</body>
</html>