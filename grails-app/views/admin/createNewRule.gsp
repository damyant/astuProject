<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create New Rule</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if><div style="text-align: center"><label class="error1" id="symbolError"></label></div>
        <div style="text-align: center;font-size: 13px;font-weight: bold;margin-bottom: 30px;"> Please Maintain one space between each character in Rule expression.</div>
        <g:form controller="admin" action="saveNewRule" name="saveNewRule" id="saveNewRule">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td class="university-size-1-3">Rule Name</td>
                    <td class="university-size-2-3">
                        <input type="text" class="university-size-1-2" name="ruleName" id="ruleName"/>

                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"    style="line-height: 15px;text-transform: capitalize;">Symbol for Marks The Student Secured After Applying Rule</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="marksSecuredSymbol" id="marksSecuredSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3"  style="line-height: 15px;text-transform: capitalize;">Symbol for Maximum marks among All Students  After Applying this Rule</td>
                    <td class="university-size-2-3">
                        <input type="text" maxlength="1" class="university-size-1-2" name="maxInAllStudentsSymbol" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)"/>
                    </td>
                </tr>
                <tr>
                    <td>Add Rule Details </td>
                    <td><input type="text" id="noOfRule" placeholder="No of Rule Details" maxlength="2" onkeypress="return isNumber(event)"><input type="button" value="Add More Rule" onclick="loadMoreRow()"> </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table class="inner" id="ruleDetailTable">
                            <tr>
                                <td class="university-size-1-4">
                                    <select name="ruleFor" id="ruleFor" class="university-size-full-1-1" onchange="loadSymbol(this)">
                                        <option value="0">Select Rule For</option>
                                        <g:each in="${calculationFor}" var="type" status="inde">
                                            <option value="${typeList[inde]}">${type}</option>
                                        </g:each>
                                    </select>
                                </td>
                                %{--<td class="university-size-1-4" style="text-align: center;">--}%
                                <td class="university-size-1-4">
                                    %{--<input type="text" class="university-size-1-2" class="ruleTypeView">--}%
                                    <select name="ruleType"  class="university-size-1-2" style="display: none">
                                        <option value="0">Select</option>
                                        <g:each in="${typeTotalSymbol}" var="type" status="inde">
                                            <option value="${type}">${type}</option>
                                        </g:each>
                                    </select>
                                </td>
                                <td class="university-size-1-2">
                                    <input type="text" name="rule" id="rule" class="university-size-3-4" placeholder="Enter the Rule expression"/>
                                </td>
                            </tr>
                        </table>



                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" id="submitButton"  value="Submit" onclick="validate()" class="university-button"></td>
                </tr>
            </table>

        </g:form>

    </fieldset>
</div>
</body>
</html>
