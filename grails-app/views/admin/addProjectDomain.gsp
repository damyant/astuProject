<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/27/14
  Time: 2:32 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for adding project domain And assigning branch to this domain--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Add Project Domain</h3>

        <g:form controller="admin" action="saveProjectDomain"  id="saveProjectDomain"  name="saveProjectDomain" >
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <td class="university-size-1-3">Select branch<span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <g:select name="programBranch" class="university-size-1-2" optionKey="id"
                                  optionValue="name"
                                  from="${branchList}" noSelection="['': ' Select Branch']"/>


                    </td>
                </tr>
                <tr>

                    <td class="university-size-1-3">Domain Name<span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <input type="text" name="domainName" class="university-size-1-2">
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" onclick="" value="Submit"/>
                    </td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>




</body>
</html>