<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 6/3/2014
  Time: 2:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Interface which provide option for deleting semester registration of particular student--}%
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
            Delete Semester Registration
        </h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <br/>
        %{--${rootImageFolder}--}%
        <g:form controller="admin" action="deleteCurrentSemRegDetails" id="individualStudentUpdate" name="individualStudentUpdate">
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
                      <input type="button" name="view" id="rollNo" value="View Semester Details" class="university-button" onclick="viewCurrentSemDetailsByRollNo()" disabled/>
                    </td>

                    <td class="university-size-1-4">

                    </td>
                    <td class="university-size-1-4"></td>
                </tr>

            </table>
        <table class="inner" id="feeDetailsTable"  style="visibility: hidden;">
            <thead>
            <th>Academic Session</th>
            <th>Semester</th>
            <th>Status</th>
            <th>Receipt No</th>
            <th>Amount</th>
            <th>Date</th>
            </thead>
            <tbody>

            </tbody>
        </table>
        <table class="inner" id="subjectDetailsTable" style="visibility: hidden;">
            <thead>
            <th>Course Name</th>
            <th>Semester</th>
            <th>Status</th>
            </thead>
            <tbody>

            </tbody>
        </table>
            <div style="width: 100%;text-align: center;visibility: hidden" id="submitButton"><input type="submit" value="Delete" class="university-button"/></div>
        </g:form>
    </fieldset>
</div>
</body>
</html>