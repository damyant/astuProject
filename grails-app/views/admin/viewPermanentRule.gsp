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
        <h3>Permanent Rule</h3>
        <table class="inner" cellpadding="5" cellspacing="10">
            <tr>
                <td class="university-size-1-3" style="text-align: right;">Class Test Weightage:</td>
                <td class="university-size-2-3" style="font-weight: bolder;line-height: 20px;">${classTestWeightage}</td>
            </tr>
            <tr>
                <td class="university-size-1-3" style="text-align: right;">Class Test Total:</td>
                <td class="university-size-2-3" style="font-weight: bolder;line-height: 20px;">${classTestTotal}</td>
            </tr>
            <tr>
                <td class="university-size-1-3" style="text-align: right;">Theory Marks:</td>
                <td class="university-size-2-3" style="font-weight: bolder;line-height: 20px;">${theoryMarksFormula}</td>
            </tr>
            <tr>
                <td class="university-size-1-3" style="text-align: right;">Practical Marks:</td>
                <td class="university-size-2-3" style="font-weight: bolder;line-height: 20px;">${practicalMarksFormula}</td>
            </tr>
            <tr>
                <td class="university-size-1-3" style="text-align: right;">Total Marks:</td>
                <td class="university-size-2-3" style="font-weight: bolder;line-height: 20px;">${totalMarksSecuredFormula}</td>
            </tr>
        </table>
    </fieldset>
</div>

</body>
</html>