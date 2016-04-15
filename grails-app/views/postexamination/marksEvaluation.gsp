<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 9/6/14
  Time: 3:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for evaluating marks according to rule--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='validation.js'/>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='admin.js'/>
</head>

<body>
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

<div id="main">
    <fieldset class="form">
        <h3>Marks Evaluation</h3>

        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>

        <div style="margin-left: 10px;"><label><h6>All [<span
                class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
        <g:form controller="postExamination" action="performMarksEvaluation" name="performMarksEvaluation" id="performMarksEvaluation">
            <table class="inner" style="margin: auto; width: 100%">
                <tr>
                    <td class="university-size-1-3"><p>Enter Course Code <span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-1-3">
                        <input type="text" class="university-size-2-3" name="subjectCode"
                               id="subjectCode"/>

                    </td>
                    <td class="university-size-1-3">
                        <input type="button" value="Load Deatils" class="university-button"
                               onclick="loadSubjectDetails()"/><label id="loadError"></label>
                    </td>
                </tr>
            </table>
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-3"><p>Course Name</p></td>
                    <td class="university-size-2-3">
                        <input type="text" class="university-size-1-2" name="subjectName" readonly
                               id="SubjectName"/>
                        <input type="hidden" id="theory" value=""/>
                        <input type="hidden" id="practical" value=""/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Other Details<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">

                        <table class="inner university-size-1-3" id="ltpc">

                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><p>Academic Session<span class="university-obligatory">*</span></p>
                    </td><td>
                    <select name="academicSession" id="academicSession" disabled="true" class="university-size-1-3" onchange="">
                        <option value="">Select Academic Session</option>

                    </select>
                </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" class="university-button university-size-1-4"onclick="validate()" value="Evaluate"/></td>
                </tr>
            </table>
        </g:form>
        <div id="msgDiv"></div>
    </fieldset>
</div>
</body>
</html>