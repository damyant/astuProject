<%--
  Created by Damyant Software Pvt Ltd.
  User: Damyant Software Pvt Ltd
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="java.text.SimpleDateFormat;javax.validation.constraints.Null;examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Student Details</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%
    <style type="text/css">
    </style>
    <script>
        $(function () {
            var icons = {
                header: "ui-icon-circle-arrow-e",
                activeHeader: "ui-icon-circle-arrow-s"
            };
            $("#accordion").accordion({
                icons: icons,
                heightStyle: "content"
            });
        });
    </script>

</head>

<body>
<div id="main">
<fieldset class="form">
<h3>STUDENT INFORMATION</h3><br/>

<div id="accordion">
<h3>Personal Details</h3>

<div style="min-height: 360px;">
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-2-3">
                <div class="main-Content">
                    <g:if test="${studInstance?.firstName}">
                        <div class="label-header">Name</div>

                        <div class="label-content">${studInstance?.firstName} ${studInstance?.middleName} ${studInstance?.lastName}</div>
                    </g:if>
                    <g:if test="${studInstance?.dob}">
                        <div class="label-header">Date of Birth</div>

                        <div class="label-content"><g:formatDate format="dd-MMM-yyyy"
                                                                 date="${studInstance?.dob}"/></div>
                    </g:if>
                    <g:if test="${studInstance?.mobileNo}">
                        <div class="label-header">Mobile Number</div>

                        <div class="label-content">${studInstance?.mobileNo}</div>
                    </g:if>
                    <g:if test="${studInstance?.category}">
                        <div class="label-header">Catagory</div>

                        <div class="label-content">${studInstance?.category}</div>
                    </g:if>
                    <g:if test="${studInstance?.nationality}">
                        <div class="label-header">Nationality</div>

                        <div class="label-content">${studInstance?.nationality}</div>
                    </g:if>
                    <g:if test="${studInstance?.gender}">
                        <div class="label-header">Gender</div>

                        <div class="label-content">${studInstance?.gender}</div>
                    </g:if>
                    <g:if test="${studInstance?.state}">
                        <div class="label-header">State of Domicile</div>

                        <div class="label-content">${studInstance?.state}</div>
                    </g:if>
                    <g:if test="${studInstance?.fatherName}">
                        <div class="label-header">Father's Name</div>

                        <div class="label-content">${studInstance?.fatherName}</div>
                    </g:if>
                    <g:if test="${studInstance?.mailing_Address}">
                        <div class="label-header">Mailing Address</div>

                        <div class="label-content" style="line-height: 20px;">
                            ${studInstance?.mailing_Address}, ${studInstance?.mailingAddressDistrict}, <br/>
                            ${studInstance?.mailingStateAddress}-${studInstance?.mailingPincode}<br/>${studInstance?.mailingMobileNo}
                        </div>
                    </g:if>
                </div>
            </td>
            <td class="university-size-1-3">
                <g:if test="${studInstance?.studentImage}">
                    <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id, mime: 'image/jpeg')}"
                         class="university-registration-photo" id="picture1"/>
                </g:if>
            </td>
        </tr>
    </table>
</p>
</div>

<h3>Programme Details</h3>

<div  style="min-height: 200px;">
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-1-1">
                <div class="main-Content">
                    <g:if test="${studInstance?.program}">
                        <div class="label-header">Programme</div>

                        <div class="label-content">${studInstance?.program?.courseName}</div>
</g:if>
                        <div class="label-header">Roll Number</div>

                        <div class="label-content"><g:if
                                test="${studInstance?.rollNo}">${studInstance?.rollNo}</g:if><g:else>Not Generated</g:else></div>

                        <div class="label-header">Current Semester</div>

                        <div class="label-content">${studInstance?.semester}</div>

                        <div class="label-header">Admission Year</div>

                        <div class="label-content">${studInstance?.registrationYear}</div>
                </div>
            </td>
        </tr>
    </table>
</p>
</div>
<h3>Fees Details</h3>
<div  style="min-height: 200px;">
<p>
    <g:if test="${feeDetails?.size() > 0}">
        <table class="inner" style="text-align: center;">
            <tr><th style="text-align: center;">Semester No</th><th style="text-align: center;">Academic Session</th><th style="text-align: center;">Receipt No</th><th style="text-align: center;"> Receipt Date</th><th style="text-align: center;">Amount</th>
            </tr>
            <g:each in="${feeDetails}" var="feeInst">
                <tr><td style="text-align: center;">${feeInst.semester.semesterNo}</td><td style="text-align: center;">${feeInst.academicSession}</td><td style="text-align: center;">${feeInst.guReceiptNo}</td><td style="text-align: center;"><g:if
                        test="${feeInst?.guReceiptDate}"><g:formatDate format="dd-MMM-yyyy"
                                                                     date="${feeInst?.guReceiptDate}"/></g:if></td><td style="text-align: center;">${feeInst?.guReceiptAmount}</td>
                </tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <div class="university-status-message">No Fees Paid</div>
    </g:else>
</p>
</div>

<h3>Examination Details</h3>

<div  style="min-height: 200px;">
    <g:if test="${studentMarksList}">
        <table class="inner" style="text-align: center;">
            <tr><th style="text-align: center;">Academic Session</th><th style="text-align: center;">Subject</th><th style="text-align: center;">Grade</th><g:if test="${!showType}"><th style="text-align: center;"> Evaluated Marks</th></g:if>
            </tr>
            <g:each in="${studentMarksList}" var="marks">
                <tr><td style="text-align: center;">${marks.academicSession}</td><td style="text-align: center;">${marks.subjectId.subjectName}</td><td style="text-align: center;">${marks.grade}</td><g:if test="${!showType}"><td style="text-align: center;">${marks.marksSecuredAfterRule}</td></g:if>
                </tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <p>Not Available</p>
    </g:else>
</div>
</div>
</fieldset>

</div>

</body>
</html>