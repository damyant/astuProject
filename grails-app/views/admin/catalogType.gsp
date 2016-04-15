<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/30/14
  Time: 1:38 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--This page is for add, view, edit and delete catalog type--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.view}">
        %{--For view ,edit and delete catalog type--}%
            <h3>Catagory Type List</h3><br/>
            <table style="margin: auto;" id="catalogEditList">
                <thead>
                <tr><th style="text-align: center">Name</th><th  style="text-align: center">Edit</th><th  style="text-align: center">Delete</th></tr>
                </thead><tbody>
                <g:each in="${catagoryTypeList}" var="catInst">
                    <tr>
                        <td style="text-align: center">${catInst.catalogTypeName}</td><td
                            style="text-align: center"><g:link controller="admin" action="catalogType"
                                                               params="[catId: catInst.id]">Edit</g:link></td><td
                            style="text-align: center"><g:link controller="admin" action="delCatalogType"
                                  params="[catId: catInst.id]" onclick="return confirm('Are you sure you want to delete this item?')">Delete</g:link></td>
                    </tr>
                </g:each>
            </tbody>
            </table>
            <script type="text/javascript" language="javascript" class="init">

                $(document).ready(function() {
                    $('#catalogEditList').dataTable( {
                        "columnDefs": [
                            {
                                "targets": [ 1,2 ],
                                "searchable": false
                            }
                        ]
                    } );
                } );

            </script>
        </g:if>
        <g:else>
        %{--To add catalog type--}%
            <h3>Catalog type</h3><br/>
            <g:form controller="admin" action="saveCatalogType" name="saveCatalogType" id="saveCatalogType">
                <g:if test="${catagoryTypeInst}">
                    <input type="hidden" name="catgoryTypeId" value="${catagoryTypeInst?.id}">
                </g:if>
                <table class="inner university-size-1-2" style="margin: auto;">
                    <tr>
                        <td class="university-size-1-3">Catalog Name</td>
                        <td class="university-size-2-3"><input type="text" class="university-size-1-1" onkeypress="return onlyAlphabets(event);"
                                                               value="${catagoryTypeInst?.catalogTypeName}"
                                                               name="catalogName"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" class="university-button" onclick="checkValidation()" value="Save"/></td>
                    </tr>
                </table>
            </g:form>
        </g:else>
    </fieldset>
</div>

</body>
</html>