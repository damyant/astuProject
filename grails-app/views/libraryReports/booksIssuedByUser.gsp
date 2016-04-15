<%--
  Created by IntelliJ IDEA.
  User: chandan's
  Date: 27-05-2015
  Time: 17:45
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--View details of Issued books(issued to,issued date,catalog, catalog type)--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of Department</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-1-2" id="courseTable" style="margin: auto;">
            <thead>
            <tr>
                <th >Issued To</th>
                <th >Issued Date</th>
                <th >Catalog</th>
                <th >Catalog Type</th>
                <th >Issuer name</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${bookIssueList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${listInst.issuingPersonId}</td>
                    <td>${listInst.issuedDate}</td>
                    <td>${listInst.catalog.title}</td>
                    <td>${listInst.catalogType.catalogTypeName}</td>
                    <td>${listInst.user.username}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $('#courseTable').dataTable( {
            "columnDefs": [
                {
                }
            ]
        } );
    } );

</script>

</body>
</html>