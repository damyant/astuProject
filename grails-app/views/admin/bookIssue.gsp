<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 1/10/14
  Time: 12:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for issueing And returning books(to students and faculty both)--}%
<html>
<head>
    <title>Book Issue</title>
    <meta name="layout" content="main"/>
    <g:javascript src='bookIssue.js'/>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Book Issue & Return</h3>
        <g:form name="bookIssueForm" id="bookIssueForm">
            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <td class="new-university-size-1-3">Book Allocation To<span class="university-obligatory">*</span></td>
                    <td class="new-university-size-2-3">
                        <select class="university-size-1-2" name="type" id="type" onchange="getTotalBooks()">
                        <option value="">Select Allocation Type</option>
                        <option value="student">Student</option>
                        <option  value="faculty">Faculty</option>
                        </select>
                   </td>
                </tr>
                <tr>
                    <td>Enter RollNumber/ EmpId<span class="university-obligatory">*</span></td>
                    <td><input type="text" disabled name="id" id="issuingPersonId" class="university-size-1-2" onblur="getIssuedBooks()"></td>
                </tr>
                <tr>
            <g:if test="${department}">
                <td>Department<span class="university-obligatory">*</span></td>
                <td>
                    <select name="catalogDepartment" id="catalogDepartment" disabled class="university-size-1-2">
                        <option value>Select Department</option>
                        <option value="${department.id}">${department.name}</option>
                    </select>
                </td>
            </g:if>
            <g:else>
                <tr>
                    <td>Department<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="catalogDepartment" id="catalogDepartment" disabled="" class="university-size-1-2"
                                  optionKey="id"
                                  optionValue="name" value="${catalogIns?.department?.id}"
                                  from="${catalogDepartmentList}" noSelection="['': ' Select Catalog Department']"
                                  onchange="getSubjects()"/>
                    </td>
                </tr>
            </g:else>
            </tr>
            <tr>
                <td>Type<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="catalogType" id="catalogType" disabled="" class="university-size-1-2" optionKey="id"
                              optionValue="catalogTypeName" value="${catalogIns?.type?.id}"
                              from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                              onchange="getSubjects()"/>
                </td>
            </tr>

            <tr>
                <td>Max. Books Allocated</td>
                <td><input type="text" disabled="disabled" id="maxBooks"></td>

            </tr>
            <tr>
                <td>Books Already Issued</td>
                <td><input type="text" disabled="disabled" id="issuedBooks"></td>

            </tr>
            <tr>
                <td>Books List<span class="university-obligatory">*</span></td>
                <td>
                    <table class="university-size-full-1-1 inner">
                        <tr>
                            <td style="width: 35%;"><div style="margin-bottom: 10px;">Available Book List:</div>
                                <g:select name="allBookList" id="allBookList" class="university-size-1-1" optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from="" onchange="" multiple="true"/>
                            </td>
                            <td style="width: 10%;">
                                <div style="margin-bottom: 10px;">Quantity:</div>
                                <g:select name="quantity" id="quantity" class="university-size-1-1" optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from="" onchange="" multiple="true"/>
                            </td>
                            <td style="width: 20%;">
                                <input type="button" value="Add==>" class="university-size-1-1" style="margin-bottom: 10px;"
                                       onclick="addToList()">
                                <input type="button" value="<==Remove" class="university-size-1-1"
                                       onclick="removeFromList()">
                            </td>
                            <td style="width: 40%;"><div style="margin-bottom: 10px;">Assigned Book List:</div>
                                <g:select name="selectedBookList" id="selectedBookList" class="university-size-1-1"
                                          optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from="" onchange="" multiple="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                </td><td>
                <input type="button" disabled value="Submit" id="issuedButton" onclick="saveData()" class="university-button">
            </td>
            </tr>
            </table>
        </g:form>
    </fieldset>
</div>
<script>
    $(document).ready(function () {
        $("#issuedButton").one('click', function (event) {
            event.preventDefault();
            //do something
            $(this).prop('disabled', true);
        });
    });
</script>
</body>
</html>