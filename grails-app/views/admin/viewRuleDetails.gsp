<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/29/14
  Time: 12:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>View Rule</h3>
        <table class="inner" cellpadding="15" cellspacing="10" style="line-height: 20px;">
            <tr>
                <td class="university-size-2-3" style="text-align: right;vertical-align: top;">
                    <table class="inner" style="margin-top: 0px;">
                        <tr>
                            <td style="font-weight: bolder;font-size: 18px;"><label style="padding: 20px;">${ruleInst.variableOfResult}</label> <label style="padding: 20px;">=</label><label style="padding: 20px;">${ruleInst.expression}</label> </td>
                        </tr>
                    </table>
                </td>
                <td class="university-size-1-3">
                    <table class="inner"  style="font-weight: bold; font-size: 11px;">
                        <tr><th colspan="2">Symbol Definition</th></tr>
                        <g:each in="${symbolTextList}" var="symInst" status="inde">
                            <tr>
                                <td>${symbolViewList[inde]}</td>
                                <td>${symInst}</td>

                            </tr>
                        </g:each>

                    </table>
                </td>
            </tr>

        </table>
    </fieldset>
</div>

</body>
</html>