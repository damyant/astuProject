<%--
  Created by Damyant Software Pvt Ltd.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
%{--Page for enrolling in project--}%
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='registerPage.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <g:if test="${!authourity}">
        <fieldset class="form">
            <div class='body'>
                <div class='errors'>
                    <div class="university-not-authorized">
                        <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                                style="margin: auto;"/></p>

                        <p>You are not Authorized to view this Page.</p>
                    </div>
                </div>
            </div>
        </fieldset>
    </g:if>
    <g:else>
        <g:if test="${!status}">
            <fieldset class="form">
                <div class='body'>
                    <div class='errors'>
                        <div class="university-not-authorized">
                            <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                                    style="margin: auto;"/></p>

                            <p>You don't Have any Project Subject.</p>
                        </div>
                    </div>
                </div>
            </fieldset>
        </g:if>
        <g:else>
            <fieldset class="form">
                <g:form controller="student" action="submitProjectEnrollment" method='post' id="submitProjectEnrollment" name="submitProjectEnrollment">
                    <h3>Project Enrollment</h3>
                    <g:if test="${flash.message}">
                        <div class="message"><div class="university-status-message">${flash.message}<label
                                id="statusMessage"></label></div></div>
                    </g:if>
                    <div style="margin-left: 10px;"><label><h6>All [<span
                            class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
                    <table class="inner university-size-full-1-1">
                        <tr>
                            <td class="university-size-1-3">
                                <label>Roll Number<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="rollNo" required class="university-size-1-2" value="${student?.rollNo}" readonly>
                            </td>
                        </tr>
                         <tr>
                            <td class="university-size-1-3">
                                <label>Name<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="name" required class="university-size-1-2" value="${student?.firstName} ${student?.middleName} ${student?.lastName}">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Branch<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="hidden" name="branchId" value="${student.programBranch.id}">
                                <input type="text" name="branch" required class="university-size-1-2" value="${student?.programBranch?.name}" onchange="loadGuideList(this)">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Academic Session<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="session" required class="university-size-1-2" value="${academicSession}">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Phone Number<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="phno" required class="university-size-1-2" maxlength="10" onkeypress="return isNumber(event)" value="${student?.mobileNo}">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>email<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="email" name="emailStudent" required class="university-size-1-2" maxlength="100" value="${student?.email}">
                            </td>
                        </tr>
                        <g:each in="${subList}" var="sub" status="inde">
                            <tr><td colspan="2"> For ${sub.subjectName} [${sub.subjectCode}]<input type="hidden" name="subjectCode" value="${sub.subjectCode}"> </td></tr>
                         <tr>
                            <td class="university-size-1-3">
                                <label>Title of the Project<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <g:textArea name="projectTitle" required="required" class="university-size-1-2"></g:textArea>
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Domain<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <g:select name="domain" from="${domainList}" optionKey="id" optionValue="domainName" noSelection="['': 'Choose Domain']" required="required"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Name of Institution<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="nameOfInstitution" required class="university-size-1-2">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Guide Name<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <select name="guideName"  required class="university-size-1-2 guideName" onchange="loadGuideDetails(this, ${inde})">
                                    <option>Select Guide</option>
                                    <g:each in="${nameList}" var="name" status="inde1">
                                        <option value="${idList[inde1]}">${name}</option>
                                    </g:each>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Designation<span class="university-obligatory">*</span></label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="designation" id="designation${inde}" required class="university-size-1-2">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Guide Phone Number</label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="text" name="phnoGuide" id="phnoGuide${inde}"  class="university-size-1-2" maxlength="10" onkeypress="return isNumber(event)">
                            </td>
                        </tr>
                        <tr>
                            <td class="university-size-1-3">
                                <label>Guide email</label>
                            </td>
                            <td class="university-size-2-3">
                                <input type="email" name="emailGuide" id="emailGuide${inde}"  class="university-size-1-2" maxlength="100">
                            </td>
                        </tr>
                        </g:each>
                        <tr>
                            <td class="university-size-1-3">
                            </td>
                            <td class="university-size-2-3">
                                <input type="submit" value="Submit" class="university-size-1-2"/>
                            </td>
                        </tr>
                    </table>

                </g:form>
            </fieldset>
        </g:else>
    </g:else>
</div>
</body>
</html>





