<%--
  Created by IntelliJ IDEA.
  User: Chandan
  Date: 11/6/14
  Time: 1:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Single Marks Sheet Generation</title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<script>
    $(document).ajaxStart(function(){
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
</script>

<div id="main">
    <fieldset class="form">
        <h3>Download Mark Sheet of Semester</h3>
        <g:form name="singleMarksheet" id="singleMarksheet" controller="postExamination" action="downloadSingleMarksheet">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">
                <tr>
                    <td>Enter Roll Number<span class="university-obligatory">*</span></td>
                    <td class="university-size-3-4" style="text-align: left">
                        <input type="text" class="university-size-1-2" required id="rollNo" name="rollNo" maxlength="10" onchange="loadSemesterFromRollNo(this)" onkeypress="return isNumber(event)"/>
                    </td>
                </tr>
                <tr>
                    <td>Enter Semester<span class="university-obligatory">*</span></td>
                    <td class="university-size-3-4" style="text-align: left">
                        <g:select name="semester" required="required" id="semester" optionKey="id" class="university-size-1-2"
                                  optionValue="" from="" noSelection="['': ' Select Semester']"/>
                    </td>
                </tr>
                %{--<sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_EXAM_ADMIN">--}%
                    %{--<tr>--}%
                        %{--<td>Enter Date<span class="university-obligatory">*</span></td>--}%
                        %{--<td class="university-size-3-4" style="text-align: left">--}%
                            %{--<input type="text" name="marksheetDate" maxlength="10" PLACEHOLDER="DD/MM/YYYY" class="university-size-1-2"--}%
                                   %{--id="datepicker">--}%
                        %{--</td>--}%
                    %{--</tr>--}%
                %{--</sec:ifAnyGranted>--}%
            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="submit" value="Download Marks Sheet" class="ui-button university-size-1-4" style="margin: auto;">
                <input type="reset" value="Cancel" onclick="resetImage()" class="university-button">
            </div>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
<script>
    $(function () {
        $(function () {
            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd-M-yy",
                maxDate: 60
            });
        });
    });
</script>
</body>
</html>