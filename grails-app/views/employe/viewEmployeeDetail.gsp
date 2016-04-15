<%--
  Created by Damyant Software Pvt Ltd.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="java.text.SimpleDateFormat;javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
%{--This page shows all details of particular employee--}%
<html>
<head>
    <title>Employee Details</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

</head>

<body>
<div id="main">
<fieldset class="form">
<h3>Employee INFORMATION</h3><br/>

<div id="accordion">
<h2>Personal Details</h2>

<div>
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-2-3">
                <div class="main-Content">
                    <g:if test="${employeeObj?.firstName}">
                        <div class="label-header">Name</div>

                        <div class="label-content">${employeeObj?.salutation} ${employeeObj?.firstName} ${employeeObj?.middleName} ${employeeObj?.lastName}</div>

                    </g:if>
                    <g:if test="${employeeObj?.dob}">
                        <div class="label-header">Date of Birth</div>

                        <div class="label-content"><g:formatDate format="dd-MMM-yyyy"
                                                                 date="${employeeObj?.dob}"/></div>
                    </g:if>
                    <g:if test="${employeeObj?.fatherName}">
                        <div class="label-header">Father's Name</div>

                        <div class="label-content">${employeeObj?.fatherName}</div>
                    </g:if>
                    <g:if test="${employeeObj?.motherName}">
                        <div class="label-header">Mother's Name</div>

                        <div class="label-content">${employeeObj?.motherName}</div>
                    </g:if>
                    <g:if test="${employeeObj?.maritalStatus}">
                        <div class="label-header">Marital Status</div>

                        <div class="label-content">${employeeObj?.maritalStatus}</div>
                    </g:if>
                    <g:if test="${employeeObj?.spouseName}">
                        <div class="label-header">Spouse's Name</div>

                        <div class="label-content">${employeeObj?.spouseName}</div>
                    </g:if>
                %{--<g:if test="${employeeObj?.qualification}">--}%
                %{--<div class="label-header">Qualification</div>--}%

                %{--<div class="label-content">${employeeObj?.qualification}</div>--}%
                %{--</g:if>--}%
                    <g:if test="${employeeObj?.firstEmail}">
                        <div class="label-header">E-mail</div>

                        <div class="label-content">${employeeObj?.firstEmail}</div>
                    </g:if>
                    <g:if test="${employeeObj?.firstMobileNo}">
                        <div class="label-header">Mobile Number</div>

                        <div class="label-content">${employeeObj?.firstMobileNo}</div>
                    </g:if>
                    <g:if test="${employeeObj?.emergencyContactNo}">
                        <div class="label-header">Emergency Contact No.</div>

                        <div class="label-content">${employeeObj?.emergencyContactNo}</div>
                    </g:if>
                    <g:if test="${employeeObj?.gender}">
                        <div class="label-header">Gender</div>

                        <div class="label-content">${employeeObj?.gender}</div>
                    </g:if>

                </div>
            </td>
            <td class="university-size-2-3" style="vertical-align: top">
                <g:if test="${employeeObj}">
                    <img src="${createLink(controller: 'employe', action: 'show', id: employeeObj?.id
                            , mime: 'image/jpeg')}" class="university-registration-photo" id="picture1"/>
                %{--<input type='file' id="profileImage" onchange="readURL(this, 'picture1');" class="university-button"--}%
                %{--name="photograph"/>--}%
                </g:if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <g:if test="${employeeObj?.currentAddress}">
                    <div class="label-min-header">Current Address</div>

                    <div class="label-FullSize-content">
                        ${employeeObj?.currentAddress}
                    </div>


                    <g:if test="${employeeObj?.currentAddressCity}">
                        <div class="label-min-header"></div>

                        <div class="label-FullSize-content">
                            <label>${employeeObj?.currentAddressCity} ,<g:if
                                    test="${employeeObj?.currentAddressDistrict}">${employeeObj?.currentAddressDistrict}</g:if>, <g:if
                                    test="${employeeObj?.currentStateAddress}">
                                ${employeeObj?.currentStateAddress}
                            </g:if>, <g:if test="${employeeObj?.currentPincode}">
                                ${employeeObj?.currentPincode}
                            </g:if></label>
                        </div>
                    </g:if>
                </g:if>
                <div style="clear: both;"></div>
                <g:if test="${employeeObj?.permanentAddress}">

                        <div class="label-min-header">Permanent Address</div>

                        <div class="label-FullSize-content">
                            <label>${employeeObj?.permanentAddress},</label>
                        </div>
                        <g:if test="${employeeObj?.permanentAddressCity}">
                            <div class="label-min-header"></div>

                            <div class="label-FullSize-content">
                            ${employeeObj?.permanentAddressCity}, <g:if test="${employeeObj?.permanentAddressDistrict}">
                            ${employeeObj?.permanentAddressDistrict}
                        </g:if>, <g:if test="${employeeObj?.permanentStateAddress}">
                            ${employeeObj?.permanentStateAddress}
                        </g:if>, <g:if test="${employeeObj?.permanentPincode}">
                            ${employeeObj?.permanentPincode}
                        </g:if></label>
                            </div>
                        </g:if>
                </g:if>
                <div style="clear: both;"></div>
            </td>
        </tr>
    </table>
</p>
</div>
<table>
    <tr style="outline: thin solid">
        <td class="university-size-1-3">Qualification</td>
        <td><table id="tab2" class="inner university-size-full-1-1">
            <thead><tr><td style="text-decoration: underline;font-weight: bold;">Degree</td><td
                    style="text-decoration: underline;font-weight: bold;">Institution</td>
                <td style="text-decoration: underline;font-weight: bold;">Subject</td><td
                        style="text-decoration: underline;font-weight: bold;">Year</td></tr></thead>
            <tbody>
            <g:each in="${empQualObj}" status="i" var='empQualInstance'>
                <tr><td>${empQualInstance.degree}</td>
                    <td>${empQualInstance.university}</td>
                    <td>${empQualInstance.subject}</td>
                    <td>${empQualInstance.year}</td>
                </tr>
            </g:each>
            </tbody>
        </table></td>
    </tr></table>

<h2>Office Details</h2>

<div>
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-1-2">
                <div class="main-Content">
                    <table>
                        <tr style="outline: thin solid">
                            <td class="university-size-1-3">Designation & Joining Date</td>
                            <td>
                                <table id="tab" class="inner university-size-full-1-1">
                                <tr><td style="text-decoration: underline;font-weight: bold;">Designation</td><td
                                        style="text-decoration: underline;font-weight: bold;">Date of Joining</td>

                                    <g:each in="${empDesObj}" status="i" var='empDesInstance'>
                                        <tr><td>${empDesInstance.designation}</td><td><g:formatDate format="dd-MMM-yyyy"
                                                                                                    date="${empDesInstance?.joiningDate}"/>

                                        </td>
                                        </tr>
                                    </g:each>

                                </table>
                            </td>
                        </tr>
                    </table>

                    <g:if test="${employeeObj?.employeeCode}">
                        <div class="label-header">Employee Code</div>

                        <div class="label-content">${employeeObj?.employeeCode}</div>
                    </g:if>

                    <div class="label-header">Department</div>

                    <div class="label-content">${employeeObj?.department?.name}</div>


                    <g:if test="${employeeObj?.panNumber}">
                        <div class="label-header">PAN Number</div>

                        <div class="label-content">${employeeObj?.panNumber}</div>

                    </g:if>
                    <g:if test="${employeeObj?.bankName}">
                        <div class="label-header">Bank Name</div>

                        <div class="label-content">${employeeObj?.bankName}</div>

                    </g:if>
                    <g:if test="${employeeObj?.accountNo}">
                        <div class="label-header">Bank Account No</div>

                        <div class="label-content">${employeeObj?.accountNo}</div>

                    </g:if>

                </div>
            </td>

        </tr>
    </table>
</p>
</div>

</div>
</fieldset>

</div>

</body>
</html>