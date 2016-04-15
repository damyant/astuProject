<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/29/14
  Time: 12:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for viewing catalog list--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Catalogs</h3>
        <table>

            <thead>
            <tr>
                <g:sortableColumn property="title" title="Type" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Category" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="ISBN" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Title" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Author" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Publisher" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Year" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Quantity" class="university-size-1-2"/>
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
                </tr>
            </g:each>
            </tbody>
        </table>



    </fieldset>
</div>

</body>
</html>