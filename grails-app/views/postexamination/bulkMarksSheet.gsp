<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 11/6/14
  Time: 1:43 PM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Batch Marks Generation</title>
    <g:javascript src='admitCard.js'/>
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
        <h3>Batch Marks Generation</h3>
        <g:form name="" id="" controller="postExamination" action="bulkMsheetGenerate">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="left" cellpadding="10" class="university-table-1-2 inner" style="width: 70%;margin: auto;" border="0">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Program<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                                  value=""
                                  optionValue="courseName" from="${ProgramDetail.list(sort: 'courseCode')}" noSelection="['': ' Select Program']"
                                  onchange="loadSemesterFromProgram(this)"
                        />
                    </td>
                </tr>
                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td>Semester<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="semester" id="semester" optionKey="id" class="university-size-1-2"
                                  value=""
                                  optionValue="" from="" noSelection="['': ' Select Semester']"

                        />
                    </td>
                </tr>
                <!----------------------------------------- Session Name --------------------------------------------->
                <tr>
                    <td>Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="session" id="session"  class="university-size-1-2"
                                  value=""
                                   from="${academicSession}" noSelection="['': ' Select Session']"
                        />
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center">
                        <input type="submit" value="Download Marks Sheet" onclick="validate()" class="university-button">
                        <input type="reset" value="Cancel" onclick="resetImage()" class="university-button">
                    </td>
                </tr>


            </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>