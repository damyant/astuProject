<%--
  Created by Damyant Software Pvt Ltd.
  User: Digvijay
  Date: 9/6/14
  Time: 5:20 PM
--%>

<%--
  Damyant Software Pvt LtdtelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing defined Symbols for marks in exam,rule for exam,credit,lecture,practical and tutorial etc --}%

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Download Marks Foil Sheet</title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
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

<div id="main">
    <fieldset class="form">
        <h3>View Defined Symbols</h3>
           <table align="center" cellpadding="15" cellspacing="10" class="inner" style="width:70%;margin:auto;" border="0">
                <tr>
                    <th class="university-size-1-3">Symbol</th>
                    <th class="university-size-2-3">Description</th>
                </tr>
               <g:each in="${symbolTextList}" var="sText" status="index">
                   <tr>
                       <td>${symbolViewList[index]}</td>
                       <td>${sText}</td>
                   </tr>
               </g:each>
                <tr>
                    <td colspan="2" style="text-align: center;color:#3f4c6b;font-weight: bolder;letter-spacing: 0.1em;">[Note: Please Create the Rules in the Sequence of Use.]</td>
                </tr>
            </table>
    </fieldset>
</div>
</body>
</html>