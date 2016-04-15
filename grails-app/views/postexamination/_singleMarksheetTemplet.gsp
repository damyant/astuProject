<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 10/20/2014
  Time: 3:04 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>

    <g:resource dir="css" file="gu_stylesheet.css"/>
    <style type="text/css">

    .marksheet > tr > td {
        border: 1px solid black;
    }

    @page {
        size: 210mm 297mm;
    }

    div.break {
        page-break-after: always;
    }

    table {
        border-collapse: collapse;
    }

    table, th, td {
        text-align: center;
        font-family: Cambria, Georgia, serif;
    }

    .marksheet td {
        height: 40px;
        vertical-align: middle;
    }
    </style>
</head>

<body>
<div style="width: 100%;border: 0px; margin: auto;">
    <table style="width: 100%;border: 0px; margin-top: 10px;" cellpadding="0px" cellspacing="0px">
        %{--<tr>--}%
            %{--<td colspan="3" style="text-align: left"><label>Date :</label>${resultDate}</td>--}%
        %{--</tr>--}%
        %{--<tr>--}%
            %{--<td style="width: 44%; text-align: right; font-size: 30px;font-weight: bolder;">Gauhati</td>--}%
            %{--<td style="width: 10%;text-align: center;"><rendering:inlineJpeg bytes="${logo}"--}%
                                                                             %{--class="university-registration-photo"--}%
                                                                             %{--style="margin:auto; width: 45px;"/></td>--}%
            %{--<td style="width: 46%;text-align: left;font-size: 30px;font-weight: bolder;">University</td>--}%
        %{--<td colspan="3" style="text-align: center;font-size: 36px;font-weight: bold;">--}%
            %{--<label style="letter-spacing: 15px;">Symphonie</label>--}%
        %{--</td>--}%
        %{--</tr>--}%
        <tr>
            <td colspan="3" style="text-align: center;font-size: 36px;font-weight: bold;">
                <label style="letter-spacing: 15px;">Assam Science and Technology University</label>
            </td>
        </tr>
    </table>

    <table style="width: 100%;border: 0px; margin-top: 10px;"
           cellpadding="0px" cellspacing="0px">
        <tr>
            <td colspan="7"></td>
            <td colspan="4" style="width:80%;text-align: center;font-size: 17px;font-weight: bold;">
                <label>Marks/Grades Sheet</label>
            </td>
            <td rowspan="4" style="width:10%">
                <g:if test="${student?.getStudentImage()}">
                    <rendering:inlineJpeg bytes="${student?.getStudentImage()}" style="margin:auto; height: 150px;width: 117px;border: 1px solid black;    display: block;
                                                  background-position: bottom; background-size: 100%; text-align: center;"/>
                </g:if>
                <g:else>
                    <div style="margin:auto; height: 120px;width:110px;text-align: center ; vertical-align:middle;border: 1px solid;">
                        <div style="margin: 20px 0px;">
                            Affix Passport Size Photo
                        </div>
                    </div>
                </g:else>
            </td>

        </tr>
        <tr>
            <td colspan="7"></td>
            <td colspan="4" style="text-align: center;font-size: 14px;">
                <label>The following is the statement of marks obtained by</label>
            </td>
        </tr>
        <tr>
            <td colspan="7"></td>
            <td colspan="4" style="text-align: center;font-size: 15px;">
                <label style="font-weight: bold;font-style: italic; text-transform: uppercase;">${student?.firstName} ${student?.middleName} ${student?.lastName}</label><label>, bearing Roll No:</label><label
                    style="font-weight: bold;">${student?.rollNo}</label>
            </td>
        </tr>
        <tr>
            <td colspan="7"></td>
            <td colspan="4" style="text-align: center">
                <label>in the ${student?.program.courseName}</label>
            </td>
        </tr>

    </table>


    <table class="marksheet"
           style="margin-top: 20px;margin-bottom: 40px; width: 85%;margin-left:auto;margin-right: auto;"
           cellspacing="50px;">
        <tr>
            <td colspan="3" style="text-align: center;font-size: 15px; font-weight: bold;">
                SEMESTER
                <g:if test="${semester == 1}">
                    <label>I</label>
                </g:if>
                <g:elseif test="${semester == 2}">
                    <label>II</label>
                </g:elseif>
                <g:elseif test="${semester == 3}">
                    <label>III</label>
                </g:elseif>
                <g:elseif test="${semester == 4}">
                    <label>IV</label>
                </g:elseif>
                <g:elseif test="${semester == 5}">
                    <label>V</label>
                </g:elseif>
                <g:elseif test="${semester == 6}">
                    <label>VI</label>
                </g:elseif>
                <g:elseif test="${semester == 7}">
                    <label>VII</label>
                </g:elseif>
                <g:elseif test="${semester == 8}">
                    <label>VIII</label>
                </g:elseif>
            </td>
        </tr>
        <tr>
            <td style="width:50%;">Courses Offered</td>
            <td style="width:26%;">Evaluated Marks</td>
            <td style="width:24%;">Grade</td>
            %{--<td style="width:12%;">Credit Earned</td>--}%
        </tr>
        <g:each in="${marksList}" var="subject" status="i">
            <tr>
                <td>${subjects[i]}</td>
                <td>${evalMarks[i]}</td>
                %{--<td  style="width:11%;">${marksObtained[i]}</td>--}%
                %{--<td  style="width:13%;">${internalMarks[i]}</td>--}%
                <td>${grades[i]}</td>
            </tr>
        </g:each>
        %{--<tr>--}%
            %{--<td style="border: 0px;">Total</td>--}%
            %{--<td>${fullTotal}</td>--}%
            %{--<td colspan="2"><g:if test="${isAllPass}">${totalTheory + totalInternal}</g:if></td>--}%
            %{--<td>${totalCredit}</td>--}%
        %{--</tr>--}%
        %{--<g:if test="${passSemList}">--}%
            %{--<tr>--}%
                %{--<td colspan="4" style="border: 0px;"></td>--}%
            %{--</tr>--}%
            %{--<g:each in="${passSemList}" var="sem" status="i">--}%
                %{--<tr>--}%
                    %{--<td style="text-align: center;font-size: 15px; font-weight: bold;">${sem}</td>--}%
                    %{--<td>${totalSemFullMarksList[i]}</td>--}%
                    %{--<td colspan="2">${totalSemMarksList[i]}</td>--}%
                    %{--<td>${totalSemCreditList[i]}</td>--}%
                %{--</tr>--}%
            %{--</g:each>--}%
        %{--</g:if>--}%
        %{--<tr>--}%
            %{--<td colspan="4" style="border: 0px;"></td>--}%
        %{--</tr>--}%
        %{--<tr>--}%
            %{--<td style="border: 0px;"></td>--}%
            %{--<td colspan="4" style="border: 0px;"><li--}%
                    %{--style="font-size: 15px;font-weight: bold;line-height: 40px; vertical-align: middle;"><ul--}%
                        %{--type="circle">Result ${finalResult}</ul></li></td>--}%
        %{--</tr>--}%
    </table>
    %{--<ul style="font-size: 15px;">--}%
        %{--<li><strong>Minimum Pass Marks :</strong>[Theory 35%] and [Practical 40%] per paper</li>--}%
        %{--<li><strong>Class I :</strong> 60% or above</li>--}%
        %{--<li><strong>Class II :</strong> 45% or above but less than 60%</li>--}%
        %{--<li><strong>Simple Pass :</strong> 35% or above but less than 45%</li>--}%
        %{--<li><strong>NC :</strong> Not Cleared</li>--}%
    %{--</ul>--}%

    <div style="text-align: center;font-size:15px;margin-top: 50px">(Computer generated Statement of Marks does not require a signature.)</div>
</div>
</body>
</html>