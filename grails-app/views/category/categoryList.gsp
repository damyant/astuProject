<%--
  Created by Damyant Software Pvt Ltd.
  User: user
  Date: 5/2/2014
  Time: 1:18 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing program type list(provide option for edit and delete)--}%
<html>
<head>
    <meta name="layout" content="main">
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'bank.js')}'></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>View Program Type List</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <table class="university-size-1-1 university-text-align-centre" style="margin: auto;border: 1px solid beige">
            <tr>
                <th>Program Type Name</th>
                <th>&nbsp;</th>
            </tr>
            <g:each in="${programTypeList}" var="typeInstance">
                <tr>
                    <td>${fieldValue(bean: typeInstance, field: "type")}</td>
                    <td>
                        <div class="university-text-align-centre">
                            <input type="submit" value="Update" class="university-button"
                                   onclick="updateCategoryType(${typeInstance.id})"/>
                            <input type="button" onclick="deleteCategoryType(${typeInstance.id})" value="Delete"
                                   class="university-button"/>
                        </div>
                    </td>
                </tr>
            </g:each>
        </table>

    </fieldset>
</div>
<script>
    function updateCategoryType(id){
    categoryId = id
    window.location.href = '/Symphonie/category/createNewCategory?categoryId=' + categoryId;
    }

    function deleteCategoryType(id){
        categoryId = id
    //    alert(bankId)
    window.location.href = '/Symphonie/category/deleteCategory?categoryId='+ categoryId;
    }
</script>
</body>
</html>
