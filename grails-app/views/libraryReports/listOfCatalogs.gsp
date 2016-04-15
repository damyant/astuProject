<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 10/6/14
  Time: 1:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for showing catalaogs by ISBN/Title/Author--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='bookIssue.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:form name="catalogForm" id="catalogForm">
            <table class="university-size-full-1-1 inner spinner">
                <g:if test="${params.by == "byType"}">
                %{--By Type--}%
                    <tr>

                        <td class="university-size-1-3">Type<span class="university-obligatory">*</span></td>
                        <td class="university-size-1-3">
                            <g:select name="catalogType" class="university-size-1-2" optionKey="id"
                                      optionValue="catalogTypeName" value="${catalogIns?.type?.id}"
                                      from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                                      onchange=""/>
                        </td>
                    </tr>

                </g:if>



                <g:elseif test="${params.by == "byCategory"}">
                %{--By Category--}%
                    <tr>

                        <td class="university-size-1-3">Type<span class="university-obligatory">*</span></td>
                        <td class="university-size-1-3">
                            <g:select name="catalogType" class="university-size-1-2" optionKey="id"
                                      optionValue="catalogTypeName" value="${catalogIns?.type?.id}"
                                      from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                                      onchange=""/>
                        </td>
                    </tr>

                    <tr>
                        <td class="university-size-1-3">Category<span class="university-obligatory">*</span></td>
                        <td class="university-size-1-3">
                            <g:select name="catalogCatagory" class="university-size-1-2" optionKey="id"
                                      optionValue="catalogCatagoryName" value="${catalogIns?.catagory?.id}"
                                      from="${catalogCatagoryList}" noSelection="['': ' Select Catalog Catagory']"
                                      onchange=""/>
                        </td>
                    </tr>
                </g:elseif>
                <g:elseif test="${params.by == "byIsbn"}">
                %{--By ISBN--}%
                    <tr>

                        <td class="university-size-1-3">ISBN</td>
                        <td class="university-size-1-3"><input type="text" name="catalogIsbn"
                                                               class="university-size-1-2" value="${catalogIns?.isbn}">
                        </td>
                    </tr>
                </g:elseif>
                <g:elseif test="${params.by == "byTitle"}">
                    <tr>

                        <td class="university-size-1-3">Title</td>
                        <td class="university-size-1-3"><input type="text" name="catalogTitle"
                                                               class="university-size-1-2" value="${catalogIns?.title}">
                        </td>
                    </tr>
                </g:elseif>
                <g:elseif test="${params.by == "byAuthor"}">
                %{--By Author--}%
                    <tr>

                        <td class="university-size-1-3">Author</td>
                        <td class="university-size-1-3"><input type="text" name="catalogAuthor"
                                                               class="university-size-1-2"
                                                               value="${catalogIns?.author}"></td>
                    </tr>
                </g:elseif>

                <tr>
                    <td></td>
                    <td>
                        <input type="button" class="university-size-1-2 university-button" value="Show"
                               onclick="showCatalogList()"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:form>


        <table class="university-size-full-1-1 inner spinner" id="bookList">
            <div id="errorMsg" class="university-status-message"></div></table>


    </fieldset>
</div>

</body>
</html>