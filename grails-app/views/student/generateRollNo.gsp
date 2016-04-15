<%--
  Created by Damyant Software Pvt Ltd.
  User: Chandan
  Date: 31/3/14
  Time: 12:56 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
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

    <fieldset class="form">
        <h3>Generate Roll No</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"> <label class="error">${flash.message}</label></div>
        </g:if>
        <g:form name="rollNumberGenerateForm" id="rollNumberGenerateForm" controller="student" action="generateRoll">
            <g:hiddenField name="studentList" id="studentList"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">

                    <tr>
                        <td><label>Select Year Of Enrollment </label></td>
                        <td>

                            <select name="year" class="university-size-2-3" id="year">
                                <option value="">Select Year</option>
                                <g:each in="${year}" var="y">
                                    <option value="${y}">${y}</option>
                                </g:each>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Program Code </label></td>
                        %{--<td>--}%
                            %{--<select name="programCode" class="university-size-2-3" id="programCode">--}%
                                %{--<option value="">Select Program Code</option>--}%
                                %{--<g:each in="${progCode}" var="prog">--}%
                                    %{--<option value="${prog}">${prog}</option>--}%
                                %{--</g:each>--}%
                            %{--</select>--}%
                        %{--</td>--}%
                        <td>
                            <g:select name="programs" class="university-size-2-3" optionKey="id"
                                      optionValue="courseName"
                                      from="${programs}" noSelection="['': ' Select Programme']"/>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td>
                        <td class="university-size-2-3 "><input type="submit" value="Generate"  class="university-size-1-4  university-button" style="padding: 0px;"/></td>
                        </td>

                    </tr>
                </table>
            </div>
            <div class="university-status-message"><label id="showErrorMessage" hidden=""></label></div>




        </g:form>
    </fieldset>


</div>
</body>
</html>











