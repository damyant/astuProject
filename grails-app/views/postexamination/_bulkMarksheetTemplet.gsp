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

<g:each in="${studentList}" var="student" status="index">

<div style="width: 100%;border: 0px; margin: auto;">
    <table style="width: 100%;border: 0px; margin-top: 10px;" cellpadding="0px" cellspacing="0px">
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
                <g:if test="${studentList[index].getStudentImage()}">
                    <rendering:inlineJpeg bytes="${studentList[index].getStudentImage()}" style="margin:auto; height: 150px;width: 117px;border: 1px solid black;    display: block;
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
                <label style="font-weight: bold;font-style: italic; text-transform: uppercase;">${studentList[index].firstName} ${studentList[index].middleName} ${studentList[index].lastName}</label><label>, bearing Roll No:</label><label
                    style="font-weight: bold;">${studentList[index].rollNo}</label>
            </td>
        </tr>
        <tr>
            <td colspan="7"></td>
            <td colspan="4" style="text-align: center">
                <label>in the ${progName}</label>
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
        </tr>
        <g:each in="${totalMarkList[index]}" var="subject" status="i">
            %{--<g:each in="${0..marksLength[index] - 1}" var="i">--}%
                %{--<g:if test="${i > 0}">--}%
                     %{--<tr>--}%
                         %{--<td>${totalMarkList[index][i]}</td>--}%
                         %{--<td>${totalMarkList[index][i]}</td>--}%
                         %{--<td>${totalMarkList[index][i]}</td>--}%
                    %{--</tr>--}%
                %{--</g:if>--}%
                %{--<g:else>--}%
                    <tr>
                        <td>${totalMarkList[index][i]}</td>
                        <td>${totalMarkList[index][i]}</td>
                        <td>${totalMarkList[index][i]}</td>
                    </tr>
                %{--</g:else>--}%
        </g:each>

    </table>

    <div style="text-align: center;font-size:15px;margin-top: 50px">(Computer generated Statement of Marks does not require a signature.)</div>
</div>

    <g:if test="${index != studentList.size()-1}">
        <div class="break"></div>
    </g:if>

</g:each>
</body>
</html>