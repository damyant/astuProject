<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 4/29/2014
  Time: 11:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for creating new program type(and program type code)--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='bank.js'/>
    <g:javascript src='postExamination.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Enter New Program Type</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div class="university-size-1-1" style="margin-left: 10px;">
            <label>
                <h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory</h6>
            </label>
        </div>
        <g:form controller="category" action='saveNewCategory'  method='post'  name='crateNewCategory' id='crateNewCategory'>
            <input type="hidden" name="categoryId" value="${programType?.id}"/>
            <table class="inner">

                <tr>
                    <td class="university-size-1-4">
                        <label>Program Type Name <span class="university-obligatory">*</span></label>
                    </td>
                <g:if test="${programType}">
                    <td class="university-size-3-4">
                        <input type="text" class="university-size-1-4" id="categoryName" value="${programType?.type}" name="categoryName" />

                    </td>
                    </g:if>
                    <g:else>
                        <td class="university-size-3-4">
                            <input type="text" class="university-size-1-4" id="categoryName" value="" name="categoryName" onblur="validName()"/>

                        </td>

                    </g:else>
                    </tr>

                <tr>
                    <td class="university-size-1-4">
                        <label>Program Code <span class="university-obligatory">*</span></label>
                    </td>
                     <g:if test="${programType}">
                    <td class="university-size-3-4">
                        <input type="text" class="university-size-1-4" id="programCode" value="${programType?.code}" readonly name="programCode"  onkeypress="return isNumber(event)" onblur="validCode()"/>

                    </td>
                     </g:if>
                    <g:else>
                        <td class="university-size-3-4">
                            <input type="text" class="university-size-1-4" id="programCode" value=" " name="programCode"  onkeypress="return isNumber(event)" onblur="validCode()"/>

                        </td>

                    </g:else>
                </tr>
                <tr>
                    <td></td>
                    <td> <input type="submit" id="submitButton" value="Submit" onclick="validate()"  onkeypress="return onlyAlphabets(event);"  class="university-size-1-4" style="height: 30px;font-weight: bolder;"></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>