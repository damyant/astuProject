<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 11/18/2014
  Time: 2:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    %{--<script src="admin.js"></script>--}%

    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Approve Registration</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"><label class="error">${flash.message}</label></div>
        </g:if>
        <g:form name="approveStudent" id="approveStudent" controller="admin" action="updateSemRegStatus">
        %{--controller="admin" action="updateSemRegStatus"--}%
            <div class="university-size-full-1-1">

                <table class="inner">
                    <tr>

                        <td class="university-size-1-4">
                            <label for="programId" class="university-right-margin">Select Programme</label>
                        </td>
                        <td>
                            <g:select name="programId" id="programId" optionKey="id" class="university-size-1-4"
                                      value=""
                                      optionValue="courseName" from="${programList}" onchange="loadProgramSession(this)"
                                      noSelection="['': ' Select Programme']"/>
                        </td>

                    </tr>
                    <tr>

                        <td class="university-size-1-4">
                            <label for="programSession" class="university-right-margin">Select Program Session</label>
                        </td>
                        <td>
                            <g:select name="programSession" id="programSession" class="university-size-1-4"
                                      value=""
                                      optionValue="" from="" onchange="loadProgramSemester(this)"
                                      noSelection="['': ' Select Programme Session']"/>
                        </td>
                    </tr>
                    <tr>

                        <td class="university-size-1-4">
                            <label for="programId" class="university-right-margin">Select Semester</label>
                        </td>
                        <td>
                            <g:select name="programSemester" id="programSemester" class="university-size-1-4"
                                      value=""
                                      optionValue="" from="" onchange="unapproveStudent(this)"
                                      noSelection="['': ' Select Semester']"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table id="studentSubLIst" class="inner"><thead></thead><tbody></tbody></table>
                            <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
                                <br/>

                                <div class="pagination">
                                    <a href="#" class="first" data-action="first">&laquo;</a>
                                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                                    <input type="text" readonly="readonly"/>
                                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                                    <a href="#" class="last" data-action="last">&raquo;</a>
                                </div>
                            </div>
                            <div id="studentListPrint"
                                 style="margin: 5px auto;width:94%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;"
                                 hidden="">

                                <label class="university-left-right-margin">
                                    Approve Range
                                </label>
                                <label class="university-left-margin" style="color: #000; font-size: 17px;">From</label>
                                <input type="text" name="studentFrom" id="studentFrom" placeholder="Enter SrNo" width="7" onclick="this.value = ''" onkeypress="return isNumber(event)"
                                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">
                                <label class="university-left-right-margin" style="color: #000;font-size: 17px;">To</label>
                                <input type="text" name="studentTo" id="studentTo" class="university-left-right-margin" placeholder="Enter SrNo" width="7" onclick="this.value = ''"
                                       onkeypress="return isNumber(event)"
                                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="margin: auto;"><table class="inner university-size-1-2" style="margin: auto;text-align: center;visibility: hidden;" id="applicationStatus"> <td><label><input type="radio" name="approvalStatus" value="Approved">Approved </label></td><td><label><input type="radio" name="approvalStatus" value="Unapproved">Unapproved </label></td></table></td>
                    </tr>
                    <tr id="unStud"><td></td><td>
                        <input type="hidden" value="" id="selectedStudents" name="selectedStudents">
                        <input type="button" value="Submit" id="checkCheckBoxes" class="ui-button" onclick="checkCheckBoxes()" disabled/></td>
                    </tr>
                </table>
            </div>
        </g:form>
    </fieldset>
</div>
</body>
</html>