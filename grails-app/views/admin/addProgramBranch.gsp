<%--
  Created by IntelliJ IDEA.
  User: chandan's
  Date: 27-05-2015
  Time: 17:42
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for Adding Program Branch & Assigning department to new branch--}%
<html>
<head>
    <title>Add Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Add Program Branch</h3>
        <g:form controller="admin" action="saveProgramBranch" id="addCoursesFrmId" name="addCoursesFrmId" >
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="inner" style="margin: auto; width: 100%">
                <tr>
                    <td class="university-size-1-3"><p>Program Branch Name <span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3"><g:textField name="programBranchName" required="required" id="programBranchName" maxlength="150" value=""
                                                                 class="university-size-1-2"/>

                    </td>
                </tr>
                <tr>
                    <td>Branch Code <span class="university-obligatory">*</span></td>
                    <td><input type="text" name="branchCode" maxlength="2" required class="university-size-1-2"></td>
                </tr>
                <tr>
                    <td>Department<span class="university-obligatory">*</span></td>
                    <td><g:select name="department" class="university-size-1-2" optionKey="id" required="required"
                                   optionValue="name"
                                   from="${deptList}" noSelection="['': ' Select Department']"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Save" class="university-button"></td>
                </tr>
            </table>

        </g:form>

    </fieldset>
</div>
</body>
</html>