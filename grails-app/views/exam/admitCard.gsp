<%--
  Created by Damyant Software Pvt Ltd.
  User: IDOL_2
  Date: 7/2/14
  Time: 6:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Generate Admit Card</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"><label class="error">${flash.message}</label></div>
        </g:if>
        <g:form controller="exam" action="generateSingleAdmitCard" name="generateSingleAdmitCard" id="generateSingleAdmitCard">
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-4">Enter Roll Number</td>
                    <td class="university-size-1-3"><input type="text" class="university-size-1-3" maxlength="10"
                                                           onkeypress="return isNumber(event)" name="rollNoForFeeStatus"
                                                           id="rollNoForFeeStatus"/></td>

                </tr>
            %{--</table>--}%

            %{--<div class="university-size-full-1-1" id="showStatusForRollNo"></div>--}%
            %{--<table class="inner university-size-full-1-1">--}%
                %{--<tr>--}%
                    %{--<td class="university-size-1-4"><label>Fee Exempt</label></td>--}%
                    %{--<td class="university-size-3-4" style="padding-left: 25px;"><input type="checkbox" name="feeExempt"--}%
                                                                                       %{--id="feeExempt"/></td>--}%
                %{--</tr>--}%
                %{--<tr>--}%
                    %{--<td class="university-size-1-4">Select Semester/Term</td>--}%
                    %{--<td class="university-size-1-3">--}%
                        %{--<select name="semesterList" id="semesterList" class="university-size-1-3">--}%
                            %{--<option value="">Select Semester</option>--}%
                        %{--</select>--}%
                    %{--</td>--}%
                %{--</tr>--}%
                <tr>
                    <td class="university-size-1-4"><label>Select Exam Year</label></td>
                    <td class="university-size-1-4">
                        <g:select name="examYear" id="examYear" class="university-size-1-3"
                                  from="${yearList}" noSelection="['': ' Select Year']"/>

                    </td>
                </tr>
                %{--<tr>--}%
                    %{--<td class="university-size-1-4><label for="semesterList">Select Examination Venue</label></td>--}%
                    %{--<td style="padding-left: 25px;">--}%
                        %{--<g:select name="examinationVenue" class="university-size-1-4" id="examCenterList" from=""--}%
                                  %{--onchange="showExamVenueCapacity(),enableShowCandidate()"--}%
                                  %{--noSelection="['': ' Select Exam Venue']"/>--}%
                    %{--</td>--}%
                %{--</tr>--}%
                <tr>
                    %{--<td>--}%
                        %{--<div id="maxCapacityBox" hidden="">--}%
                            %{--<b><label style="font-size: 10px;">Maximum Capacity</label><input type="text" class="university-size-1-3"--}%
                                                                                              %{--id="totalCapacity" style="text-align: center;font-size: 10px;" readonly/>--}%
                            %{--</b>--}%
                        %{--</div>--}%
                        %{--<div id="remainingCapacityBox" hidden="">--}%
                            %{--<b><label style="font-size: 10px;">Available Capacity</label><input type="text" class="university-size-1-3"--}%
                                                                                                %{--id="remainingCapacity" style="text-align: center;font-size: 10px;"--}%
                                                                                                %{--readonly/>--}%
                            %{--</b>--}%

                        %{--</div>--}%


                    %{--</td>--}%
                    <td style="padding-left: 25px;"><input type="submit" value="Generate Admit Card" class="university-button" onclick="validateProgramFee()"/>
                    </td>
                </tr>

            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>