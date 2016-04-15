<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for creating exam type and exam sub type--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <g:javascript src='validation.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create Examination Type</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        <g:form controller="admin" action="saveNewExamType" name="saveNewExamType" id="saveNewExamType">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td>Enter Exam Type Name</td>
                    <td><input type="text" name="examTypeName1" placeholder="Enter Exam Type Name"
                               class="university-size-2-3"/></td>
                    <td><input type="text" name="examTypeName2" placeholder="Enter Exam Type Name"
                               class="university-size-2-3"/></td>
                    <td><input type="text" name="examTypeName3" placeholder="Enter Exam Type Name"
                               class="university-size-2-3"/></td>
                </tr>
                <tr>
                    <td>Variable for Total Marks</td>
                    <td><input type="text" name="totalMarksVariable1" placeholder="Enter Variable for Total Marks" maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                               class="university-size-2-3"/></td>
                    <td><input type="text" name="totalMarksVariable2" placeholder="Enter Variable for Total Marks"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                               class="university-size-2-3"/></td>
                    <td><input type="text" name="totalMarksVariable3" placeholder="Enter Variable for Total Marks"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                               class="university-size-2-3"/></td>
                </tr><tr>
                <td>Total Marks Assigned</td>
                <td><input type="text" name="examTotalMarks1" placeholder="Enter Total Marks Assigned" onkeypress="return isNumber(event)"
                           class="university-size-2-3"/></td>
                <td><input type="text" name="examTotalMarks2" placeholder="Enter Total Marks Assigned" onkeypress="return isNumber(event)"
                           class="university-size-2-3"/></td>
                <td><input type="text" name="examTotalMarks3" placeholder="Enter Total Marks Assigned" onkeypress="return isNumber(event)"
                           class="university-size-2-3"/></td>
            </tr><tr>
                <td>Variable for Marks Secured</td>
                <td><input type="text" name="securedMarksVariable1" placeholder="Enter Variable for Marks Secured"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                           class="university-size-2-3"/></td>
                <td><input type="text" name="securedMarksVariable2" placeholder="Enter Variable for Marks Secured"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                           class="university-size-2-3"/></td>
                <td><input type="text" name="securedMarksVariable3" placeholder="Enter Variable for Marks Secured"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"
                           class="university-size-2-3"/></td>
            </tr>
                <tr>
                    <td></td>
                    <td class="text-center">
                        %{--for adding subtype--}%
                        <input type="button" class="university-button" value="Add Sub Type" onclick="AddSubType(1)"/>
                    </td>
                    <td class="text-center">
                        %{--for adding subtype--}%
                        <input type="button" class="university-button" value="Add Sub Type" onclick="AddSubType(2)"/>
                    </td>
                    <td class="text-center">
                        %{--for adding subtype--}%
                        <input type="button" class="university-button" value="Add Sub Type" onclick="AddSubType(3)"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="text-center" id="subType1" style="display: none;">
                        <table id="addSubType1" class="inner">
                            <tr>
                                <td><input type="text" name="moreSubType1"  class="moreSubType1" placeholder="Enter Sub Type Name" class="university-size-2-3"/></td>
                                <td><input type="button" class="ui-button" value="+"  onclick="addMoreSubType(1)"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="text-center" id="subType2" style="display: none;">
                        <table id="addSubType2" class="inner">
                            <tr>
                                <td><input type="text" name="moreSubType2" class="moreSubType2" placeholder="Enter Sub Type Name" class="university-size-2-3"/></td>
                                <td><input type="button" class="ui-button" value="+" onclick="addMoreSubType(2)"></td>
                            </tr>
                        </table>
                    </td>
                    <td class="text-center" id="subType3" style="display: none;">
                        <table id="addSubType3" class="inner">
                            <tr>
                                <td><input type="text" name="moreSubType3" class="moreSubType3" placeholder="Enter Sub Type Name" class="university-size-2-3"/></td>
                                <td><input type="button" class="ui-button" value="+" onclick="addMoreSubType(3)"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td style="text-align: center;"><input type="submit" class="university-button" value="Submit"/></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>
