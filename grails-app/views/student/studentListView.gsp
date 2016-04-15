<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 4/21/2014
  Time: 11:27 AM
--%>
%{--<%@ page import="examinationproject.City; examinationproject.StudyCenter" contentType="text/html;charset=UTF-8" %>--}%
<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for showing list of students in selected program and it also provide options for view and edit student details and view their semester details--}%

<html>
<head>
    <title>Students List</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript"
            src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>
        $(document).ajaxStart(function () {
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

</head>

<body>
<div id="main">

    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <input type="hidden" value="${isFacultyAdvisor}" id="isFacultyAdvisor"/>
        <table class="university-size-full-1-1 inner" style="margin: auto; line-height: 18px;">
            <tr>
                <td class="university-size-1-5">
                    <label for="programId">Select Programme</label>
                </td>
                <td class="university-size-4-5">
                    <g:select name="programId" id="programId" class="university-size-1-2 " from="${programList}"
                              optionKey="id" optionValue="courseName" noSelection="['null': ' Select Programme ']"
                              onchange="generateStudentsList()"/>
                </td>
            </tr>
        </table>
        <table id="studentList" class="inner university-size-full-1-1 university-table-bg" style="margin-top: 30px;">
            <thead></thead>
            <tbody></tbody>
        </table>

        <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
            <br/>

            <div class="pagination" id="jqPagination">

            </div>
        </div>

        <div id="msg"></div>

        <div></div>
    </fieldset>
</div>
<script>
    $(document).ready(function () {
        $('#programId').val('')
    })
</script>
</body>
</html>