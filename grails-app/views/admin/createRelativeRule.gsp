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
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Create Relative Rules</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="admin" action="saveRelativeRule" name="saveRelativeRule" id="saveRelativeRule">
            <table class="university-size-full-1-1 inner">
                <tr>
                    <td class="university-size-2-3"   style="vertical-align: top;margin-top: 10px;">
                        <table class="university-size-full-1-1 inner" style="padding: 20px;">
                            <tr>
                                <td class="university-size-1-3">Relative Rule Required</td>
                                <td  class="university-size-2-3"> <label><input type="radio" name="relativeIsRequired" value="yes">Required</label><label><input type="radio" name="relativeIsRequired" value="no">Not Required</label></td>
                            </tr>
                            <tr>
                                <td class="university-size-1-3">Relative Marks</td>
                                <td  class="university-size-2-3"><input type="text" class="university-size-1-2" onkeypress="return isRuleFormula(event)" name="relativeMarks"/></td>
                            </tr>

                            <tr>
                                <td></td>
                                <td><input type="submit" value="Submit" class="university-button"></td>
                            </tr>

                        </table>

                    </td>
                    <td class="university-size-1-3">
                        <div style="vertical-align: middle;margin: auto;line-height: 20px;font-size: 12px;">
                            <strong>NOTE:</strong>
                            <ul>
                                <li><strong>A</strong> : Class Test Total</li>
                                <li><strong>B</strong> : End Semester Theory Marks Secured</li>
                                <li><strong>D</strong> : End Semester Practical Marks Secured</li>
                                <li><strong>E</strong> : Maximum Practical Marks</li>
                                <li><strong>L</strong> : Lecture</li>
                                <li><strong>T</strong> : Tutorial</li>
                                <li><strong>P</strong> : Practical</li>
                                <li><strong>C</strong> : Credit</li>
                                <li><strong>F</strong> : Theory Marks</li>
                                <li><strong>G</strong> : Practical Marks</li>
                                <li><strong>H</strong> : Maximum marks Secured among all Students</li>
                                <li><strong>I</strong> : Total Marks Secured from Permanent Rule</li>
                                <li><strong>+</strong> : Plus</li>
                                <li><strong>-</strong> : Minus</li>
                                <li><strong>*</strong> : Multiply</li>
                                <li><strong>/</strong> : Divide</li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>
