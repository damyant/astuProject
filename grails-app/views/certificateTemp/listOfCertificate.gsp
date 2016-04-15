<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{-- Page for showing list of certificates, their related user roles,format availability ,template availability etc--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <title></title>
</head>

<body>

<div id="main">
    <fieldset class="form">
        <h3>List of Certificates</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <div class="university-status-message" id="errorMsg"></div>
        <table class="inner university-size-full-1-1 tableData university-table-text-left" id="courseTable" >
            <thead>
            <tr>
                <th style="width: 5%">Sl No.</th>
                <th style="width: 25%">Certificate Name</th>
                <th style="width: 25%">Role</th>
                <th style="width: 15%"></th>
                <th style="width: 15%"></th>
                <th style="width: 15%"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${certificateList}" status="i" var="listInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${(i + 1)}</td>
                    <td>${listInst.nameOfCertificate}</td>
                    <td>
                        <g:each in="${roleList[i]}" var="inst">
                            <div style="padding: 3px;">
                                ${inst.role.authority}
                            </div>
                        </g:each>
                    </td>
                    <td>
                        <g:if test="${templateList[i]!='NA'}">
                            <g:link controller="certificateTemp" action="createCertificate" target="_blank" id="${templateList[i]}"><button class="btn btn-default">Edit Certificate</button></g:link>
                        </g:if>
                        <g:else>
                            <g:if test="${tempAvailable[i]=='N'}">
                                Template Not Available
                            </g:if>
                            <g:else>
                                Template Not Created
                            </g:else>
                        </g:else>
                    </td>
                    <td><g:if test="${templateList[i]!='NA'}">
                        <g:link controller="certificateTemp" action="createCertificateTemplate" target="_blank" id="${templateList[i]}"><button class="btn btn-default">Edit Template</button></g:link>
                    </g:if>
                        <g:else>
                            <g:if test="${tempAvailable[i]=='N'}">
                                Template Not Available
                            </g:if>
                            <g:else>
                                Template Not Created
                            </g:else>
                        </g:else>
                    </td>
                    <td><g:if test="${certificateTemplateId[i]!='NA'}">
                        <g:link controller="certificateTemp" action="deleteCertificate" id="${certificateTemplateId[i]}"><button class="btn btn-default">Delete</button></g:link>
                    </g:if>
                    <g:else>
                        <g:if test="${tempAvailable[i]=='N'}">
                            Template Not Available
                        </g:if>
                        <g:else>
                            Template Not Created
                        </g:else>
                    </g:else>
                    </td>
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
                    "targets": [ 2,3,4 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>
</body>
</html>