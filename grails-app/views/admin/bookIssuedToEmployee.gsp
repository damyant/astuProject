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
    <h3>Book Issue Details</h3>
    <table>

      <thead>
      <tr>
        <g:sortableColumn property="title" title="Type" class="university-size-1-6"/>
        <g:sortableColumn property="title" title="Category" class="university-size-1-6"/>
        <g:sortableColumn property="title" title="Title" class="university-size-1-6"/>
        <g:sortableColumn property="title" title="Author" class="university-size-1-6"/>
        <g:sortableColumn property="title" title="Publisher" class="university-size-1-6"/>
        <g:sortableColumn property="title" title="Year" class="university-size-1-4"/>
        <g:sortableColumn property="title" title="Issue Date" class="university-size-1-2"/>
      </tr>
      </thead>
      <tbody>
      <g:each in="${issuedBookList}" status="i" var="catalogInst">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          <td>${catalogInst.catalog.type.catalogTypeName}</td>
          <td>${catalogInst.catalog.department.name}</td>
          <td>${catalogInst.catalog.title}</td>
          <td>${catalogInst.catalog.author}</td>
          <td>${catalogInst.catalog.publisher}</td>
          <td>${catalogInst.catalog.year}</td>
          <td><g:formatDate format="dd/MM/yyyy" date="${catalogInst?.issuedDate}"/></td>
        </tr>
      </g:each>
      </tbody>
    </table>



  </fieldset>
</div>

</body>
</html>