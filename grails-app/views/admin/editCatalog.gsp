<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/27/14
  Time: 4:04 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for editing/deleting/viewing catalog--}%
<html>
<head>

    <meta name="layout" content="main"/>
    <g:javascript src='bookIssue.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <div style="width: 100%; text-align: center;">
        <g:form id="catalogSearch" name="catalogSearch">
            <input type="text" id="search" name="searchText" class="university-size-1-4" placeholder="   Search"
                   style="border-radius: 1px;height: 20px;line-height: 20px;">
            By <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byIsbn">ISBN</label>
            <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byTitle">Title</label>
            <input type="button" class="university-button" value="Search" onclick="searchCatalog()">
        </g:form>
    </div>
    <fieldset class="form">
        <h3>Edit Catalog</h3>
        <table id="editCatalog">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="Type" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Category" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="ISBN" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Title" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Author" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Publisher" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Year" class="university-size-1-2"/>
                <g:sortableColumn property="title" title="Quantity" class="university-size-1-2"/>
                <g:sortableColumn property="title" title="Edit" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Delete" class="university-size-1-4"/>
                <g:hiddenField name="id" value="id"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${catalogList}" status="i" var="catalogInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${catalogInst.type.catalogTypeName}</td>
                    <td>${catalogInst.department.name}</td>
                    <td>${catalogInst.isbn}</td>
                    <td>${catalogInst.title}</td>
                    <td>${catalogInst.author}</td>
                    <td>${catalogInst.publisher}</td>
                    <td>${catalogInst.year}</td>
                    <td>${catalogInst.quantity}</td>
                    <td><g:link controller="admin" action="addCatalog"
                                params="[catalogInstId: catalogInst.id]" onclick="return confirm('Are you sure you want to delete this item?')">Edit</g:link></td>

                    <td><g:link controller="admin" action="delCatalog" params="[catalogInstId: catalogInst.id]"
                                onclick="return confirm('Are you sure you want to delete?')">Delete</g:link></td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>

</body>
</html>