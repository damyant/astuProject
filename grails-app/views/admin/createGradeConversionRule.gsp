<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for adding grade conversion rule--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Grade Conversion Rule</h3>

        <div class="university-status-message"><div id="successMessage">&nbsp;</div></div>
        <g:form method='post' controller="admin" action="saveGradeConversionRule" name="saveGradeConversionRule"
                id="saveGradeConversionRule">
            <table class="inner" style="width: 70%;margin: auto;" id="gradeTable">
                <tr>
                    <td class="university-size-1-4" style="text-align: right;">
                        <select name="fromNo" id="fromNo0" class="university-size-1-1">
                            <option value="">Select From Number</option>
                            <g:each in="${100..0}" var="no">
                                <option value="${no}">${no}</option>
                            </g:each>
                        </select>
                    </td>
                    <td class="university-size-1-4" style="text-align: center;">
                        <select name="toNo" id="toNo0" class="university-size-1-1">
                            <option value="">Select to Number</option>
                            <g:each in="${100..0}" var="no">
                                <option value="${no}">${no}</option>
                            </g:each>
                        </select>
                    </td>
                    <td class="university-size-1-4" style="text-align: left;">
                        <select name="grade" id="grade0" class="university-size-1-1">
                            <option value="">Select Grade</option>
                            <g:each in="${'ABCDEFGHI'}" var="no">
                                <option value="${no}">${no}</option>
                            </g:each>
                        </select>
                    </td>
                    <td class="university-size-1-4" style="text-align: center;">
                        <label class="ui-icon-image" id="addButton0" onclick="AddGrade(0)"><img id="im"
                                                                                                style="width:20px;"
                                                                                                alt="Add"
                                                                                                src="${resource(dir: 'images', file: 'Button-Add-icon.png')}"
                                                                                                class="window"/></label>
                    </td>
                </tr>

            </table>
            <table class="inner university-size-full-1-1">
                <tr>
                    <td style="text-align: center"><input type="submit" value="Save"
                                                          class="university-button university-size-1-4"/></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>
