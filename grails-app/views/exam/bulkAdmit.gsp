<%--
  Created by IntelliJ IDEA.
  User: Sanjay
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>
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
<body>
<div id="main">
    <fieldset>
        <h3>STUDENT ADMIT CARD</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"> <label class="error">${flash.message}</label></div>
        </g:if>
        <g:form controller="exam" action="generateBulkAdmit" name="admitCard" id="admitCard" >
            <g:hiddenField name="studentList" id="studentList"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">
                    <tr>
                        <td><label>Select a Programme</label></td>
                        <td>
                            <g:select name="programList" class="university-size-1-1" optionKey="id"
                                      optionValue="courseName"
                                      from="${programList}" noSelection="['': ' Select Programme']"
                                      onchange="getSemesterTerm(this)"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Term</label></td>
                        <td>
                            <select name="programTerm" class="university-size-1-1" id="semesterList">
                                <option value="">Select Semester</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Session</label></td>
                        <td>
                            <g:select name="programSession" from="" class="university-size-1-1" id="SessionList"
                                       noSelection="['': ' Select Session']"/>

                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label>Select Exam Year</label></td>
                        <td>
                            <g:select name="examYear" id="examYear" class="university-size-1-1" onchange="enableShowCandidate()"
                                      from="${yearList}" noSelection="['': ' Select Year']"/>

                        </td>
                    </tr>

                        <td>
                            <input type="submit" class="university-button" id="showCandidates" value="Submit"/>
                        </td>
                    </tr>
                </table>
            </div>

        </g:form>
    </fieldset>
</div>
</body>
</html>