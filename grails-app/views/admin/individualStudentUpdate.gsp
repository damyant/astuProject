<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 6/3/2014
  Time: 2:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>
            Update Students
        </h3>
        <br/>
        %{--${rootImageFolder}--}%
        <form id="individualStudentUpdate">
        <div id="msgDiv" class="university-status-message"></div>
        <table class="inner" style="margin: auto;">

            <tr>
                <td class="university-size-1-5">
                    <label for="StudentRollNo">Enter Roll Number</label>
                </td>
                <td class="university-size-1-3">
                    <g:textField name="rollNo" id="StudentRollNo" maxlength="10" class="university-size-1-2" onchange="checkRollNo()" onkeypress="return isNumber(event)" />
                </td>

                <td class="university-size-1-4">
                    %{--<input type="button" name="view" id="rollNo" value="Update" class="university-size-1-2" onclick="viewStudentByRollNo()" />--}%
                </td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-5">
                </td>
                <td class="university-size-1-3">
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <input type="button" name="view" id="rollNo" value="Update" class="university-button" onclick="updateStudentByRollNo()" disabled/>
                    </sec:ifAllGranted>
                    <input type="button" class="university-button" id="view" value="View" onclick="viewStudentByRollNo()" disabled/>
                    <input type="button" class="university-button" id="viewSem" value="View Semester Registration" onclick="viewStudentSemesterByRollNo()" disabled/>
                    %{--<g:link controller="student" action="semesterDetails" params="[studentID:student.id,view:'view']" class="university-text-decoration-none"><button class="university-button">View</button></g:link>--}%
                </td>

                <td class="university-size-1-4">

                </td>
                <td class="university-size-1-4"></td>
            </tr>

        </table>
        </form>
    </fieldset>
</div>
</body>
</html>