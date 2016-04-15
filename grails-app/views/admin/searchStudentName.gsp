<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 6/11/2014
  Time: 3:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for searching student by name--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Search Student By Name</h3>
        <br/>
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-7">Search By Name</td>
                <td class="university-size-1-4"><input type="search"  class="university-size-3-4" id="searchStudent" placeholder=" First / Middle / Last Name"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7">Admission Year</td>
                <td class="university-size-1-4">
                    <select id="session" name="programSession" class="university-size-3-4">
                        <option value="">Select Admission Year</option>
                        <g:each in="${sessionList}" var="session">
                            <option value="${session}">${session}</option>
                        </g:each>
                    </select>
                </td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7"></td>
                <td class="university-size-1-4"><input type="button" class="university-button" onclick="searchStudentList()" value="Search"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
        </table>
        <table class="university-size-full-1-1 inner" id="studentListTable" style="visibility:hidden;">
            <thead>
                    <tr>
                        <th  style="width: 25%;">Name</th><th  style="width: 15%;">Roll Number</th><th  style="width: 30%;">Programme</th><th style="width: 10%;">Student Details</th><th style="width: 20%;">Semester Registration Details</th>
                    </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
        <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
            <br/>

            <div class="pagination">
                <a href="#" class="first" data-action="first">&laquo;</a>
                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                <input type="text" readonly="readonly"/>
                <a href="#" class="next" data-action="next">&rsaquo;</a>
                <a href="#" class="last" data-action="last">&raquo;</a>
            </div>
        </div>
    </fieldset>
</div>
</body>
</html>