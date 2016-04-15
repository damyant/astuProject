<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 4:08 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{--creating certificate template for Certificate stating details fee for applying Bank Loan--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <link rel='stylesheet' href="${resource(dir: 'bootstrap-3.3.4-dist/css', file: 'bootstrap.min.css')}"
          type='text/css'/>
    <g:javascript src="admin.js"></g:javascript>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div id="templateRegion">
            <g:if test="${fromDB}">
                <div style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                    <div style="border-bottom: 3px solid #000;width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <div style="font-weight: bold;font-size: 15px;text-align: right"></div>

                        <div style="width: 16.66666667%;float: left;min-height: 1px;padding-right: 15px;padding-left: 15px;">
                            <img src="${resource(dir: 'images', file: 'gu-logo.jpg')}"
                                 style="width: 100% ;padding: 10px 0px;"/>
                        </div>

                        <div style="width: 83.33333333%;float: left;min-height: 1px;padding-right: 15px;padding-left: 15px;margin-left: -25px;">

                            <div style="text-align: center;font-weight: bolder;font-size: 25px;">GAUHATI UNIVERSITY</div>

                            <div style="text-align: center;font-weight: bolder;font-size: 20px;">INSTITUTE OF SCIENCE AND TECHNOLOGY</div>

                            <div style="text-align: center;font-weight: bold;font-size: 18px;">Gopinath Bordoloi Nagar, Guwahati-781014</div>
                        </div>

                        <div style="clear: both;"></div>
                    </div>

                    <div style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <div style="width: 50%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <span style="width: 100%;float: left;min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label>Ref:</label><label id="viewRefNo"
                                                          class="view">${refNo}</label>
                                <input type="text" class="input-sm editCertificate" id="editRefNo"
                                       style="display: none;width: 40%;margin: 4px;" name="editRefNo"/>

                            </span>
                            <label class="col-sm-12"
                                   style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">From:</label>
                            <span style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label class="view"
                                       style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       id="viewFromName">${fromName}</label>
                                <input type="text" class="editCertificate  form-inline input-sm col-sm-5"
                                       style="display: none;float: left;margin: 4px; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       name="editFromName" id="editFromName"/>
                            </span>

                            <span style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label class="view"
                                       style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       id="viewFromPosition">${fromPosition}</label>
                                <input type="text" class="editCertificate  form-inline input-sm col-sm-5"
                                       style="display: none;;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;margin: 4px;"
                                       name="editFromPosition" id="editFromPosition"/>
                            </span>

                            <div style="clear: both;"></div>
                        </div>

                        <div style="width: 50%;float: left;min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <div style="width: 20%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

                            <div style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label style="width: 33.33333333%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">Date:</label>
                                <label id="viewDate" class="view"
                                       style=" width: 66.66666667%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">${dateShown}</label>
                                <input class="editCertificate form-inline input-sm col-sm-5"
                                       type="text"
                                       style="float: left;position: relative;margin: 4px; min-height: 1px; padding-right: 15px; padding-left: 15px;display: none;"
                                       id="editDate"
                                       name="editDate"/>
                            </div>

                            <div style="clear: both;"></div>
                        </div>
                    </div>

                    <div style="padding-top: 20px; padding-bottom: 30px;font-size: 15px; width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;">
                        <span style="text-align: center;width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <label class="view" id="certificateMsgHeaderView"
                                   style=" margin-bottom: 20px;font-size: 21px;font-weight: bold;">${header}</label>
                            <input type="text" class=" form-inline input-sm editCertificate col-sm-7"
                                   style="display:none;"
                                   name="certificateMsgHeaderEdit" id="certificateMsgHeaderEdit"/>
                        </span>

                        <p class="view" id="paragraphView1"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">
                            <g:if test="${paragraph1 != null}">${paragraph1}</g:if>
                        </p>
                        <g:textArea name="paragraphEdit1" id="paragraphEdit1"
                                    class="editCertificate col-sm-10  form-inline input-sm"
                                    style="margin: 4px;display:none;"/>

                        <p id="paragraphView2" class="view"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;"><g:if
                                test="${paragraph2 != null}">${paragraph2}</g:if></p>
                        <g:textArea name="paragraphEdit2" id="paragraphEdit2"
                                    class="editCertificate col-sm-10 form-inline input-sm"
                                    style="margin: 4px;display:none;"/>
                        <p id="paragraphView3" class="view"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;"><g:if
                                test="${paragraph3 != null}">${paragraph3}</g:if></p>
                        <g:textArea name="paragraphEdit3" id="paragraphEdit3"
                                    class="editCertificate col-sm-10 form-inline input-sm"
                                    style="margin: 4px;display:none;"/>
                        <p>
                            <style type="text/css">
                            table {
                                border-collapse: collapse;
                            }

                            table, th, td {
                                border: 1px solid black;
                            }
                            </style>
                        <table style="width: 80%;margin:15px auto 60px;line-height: 13px;font-size: 12px; padding: 1px;"
                               class="table-condensed">
                            <tr>
                                <th style="text-align: center;width: 35%;">Year</th>
                                <th style="text-align: center;width: 15%;">Amount</th>
                                <th style="text-align: center;width: 15%;">Total</th>
                                <th style="text-align: center;width: 35%;">Remarks</th>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">First Year</label>
                                    <label class="col-sm-12">First Semester</label>
                                    <label class="col-sm-12">Second Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="firstSemFeeView">${firstSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="firstSemFeeEdit"
                                        name="firstSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="secondSemFeeView">${secondSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="secondSemFeeEdit"
                                        name="secondSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 3px;">
                                    <label class="view" id="firstYearFeeView">${firstYearFeeEdit}</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="firstYearFeeEdit"
                                                                      name="firstYearFeeEdit"/>
                                </td>
                                <td rowspan="4" style="vertical-align: middle;line-height: 13px;">
                                    <label class="col-sm-12 view"
                                           id="courseFeeRemarksView">${courseFeeRemarksEdit}</label><g:textArea
                                        class="input-sm editCertificate" style="display: none;width: 100%;"
                                        id="courseFeeRemarksEdit"
                                        name="courseFeeRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Second Year</label>
                                    <label class="col-sm-12">Third Semester</label>
                                    <label class="col-sm-12">Fourth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="thirdSemFeeView">${thirdSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="thirdSemFeeEdit"
                                        name="thirdSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="fourthSemFeeView">${fourthSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="fourthSemFeeEdit"
                                        name="fourthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="secondYearFeeView">${secondYearFeeEdit}</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="secondYearFeeEdit"
                                                                      name="secondYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Third Year</label>
                                    <label class="col-sm-12">Fifth Semester</label>
                                    <label class="col-sm-12">Sixth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="fifthSemFeeView">${fifthSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="fifthSemFeeEdit"
                                        name="fifthSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="sixthSemFeeView">${sixthSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="sixthSemFeeEdit"
                                        name="sixthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="thirdYearFeeView">${thirdYearFeeEdit}</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="thirdYearFeeEdit"
                                                                      name="thirdYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Fourth Year</label>
                                    <label class="col-sm-12">Seventh Semester</label>
                                    <label class="col-sm-12">Eighth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="seventhSemFeeView">${seventhSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="seventhSemFeeEdit"
                                        name="seventhSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="eighthSemFeeView">${eighthSemFeeEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="eighthSemFeeEdit"
                                        name="eighthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="fourthYearFeeView">${fourthYearFeeEdit}</label><label
                                        class="view">/-</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="fourthYearFeeEdit" name="fourthYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Hostel Admission</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="hostelAdmissionFeeView">${hostelAdmissionFeeEdit}</label><label
                                        class="view">(Yearly)</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="hostelAdmissionFeeEdit" name="hostelAdmissionFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="hostelTotalFeeView">${hostelTotalFeeEdit}</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="hostelTotalFeeEdit"
                                                                      name="hostelTotalFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view"
                                           id="hostelRemarksView">${hostelRemarksEdit}</label><g:textArea
                                        class="input-sm editCertificate" style="display: none;width: 100%;"
                                        id="hostelRemarksEdit"
                                        name="hostelRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Approximate Amount of Yearly expenses (Boarding and Lodging, Book etc) @ 5000/- per month</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="otherExpensesView">${otherExpensesEdit}</label><label
                                        class="view">(Yearly)</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="otherExpensesEdit" name="otherExpensesEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                            id="otherExpensesTotalView">${otherExpensesTotalEdit}</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="otherExpensesTotalEdit"
                                        name="otherExpensesTotalEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;" rowspan="2">
                                    <label class="col-sm-12 view"
                                           id="otherExpensesRemarksView">${otherExpensesRemarksEdit}</label>
                                    <g:textArea class="input-sm editCertificate" style="display: none;width: 100%;"
                                                id="otherExpensesRemarksEdit"
                                                name="otherExpensesRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Laptop</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;" colspan="2"><label class="view"
                                                                         id="laptopExpensesView">${laptopExpensesEdit}</label><label
                                        class="view">(approx.)</label><input type="text"
                                                                             class="input-sm editCertificate"
                                                                             style="display: none;"
                                                                             id="laptopExpensesEdit"
                                                                             name="laptopExpensesEdit"/>
                                </td>
                            </tr>

                        </table>
                    </p>
                    </div>

                    <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

                    <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <label class="view" id="signatureNameView"
                               style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">${signatureName}</label>
                        <input type="text" class="editCertificate  form-inline input-sm col-sm-7"
                               name="signatureNameEdit"
                               id="signatureNameEdit" style="display: none;margin-bottom: 30px;" value="(S. K. Sarma)">
                    </div>
                </div>
            </g:if>
            <g:else>
                <div style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                    <div style="border-bottom: 3px solid #000;width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <div style="font-weight: bold;font-size: 15px;text-align: right"></div>

                        <div style="width: 16.66666667%;float: left;min-height: 1px;padding-right: 15px;padding-left: 15px;">
                            <img src="${resource(dir: 'images', file: 'gu-logo.jpg')}"
                                 style="width: 100% ;padding: 10px 0px;"/>
                        </div>

                        <div style="width: 83.33333333%;float: left;min-height: 1px;padding-right: 15px;padding-left: 15px;margin-left: -25px;">

                            <div style="text-align: center;font-weight: bolder;font-size: 25px;">GAUHATI UNIVERSITY</div>

                            <div style="text-align: center;font-weight: bolder;font-size: 20px;">INSTITUTE OF SCIENCE AND TECHNOLOGY</div>

                            <div style="text-align: center;font-weight: bold;font-size: 18px;">Gopinath Bordoloi Nagar, Guwahati-781014</div>
                        </div>

                        <div style="clear: both;"></div>
                    </div>

                    <div style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <div style="width: 50%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <span style="width: 100%;float: left;min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label>Ref:</label><label id="viewRefNo"
                                                          class="view">IST-GU/${year}/Certificates/${refNo}</label>
                                <input type="text" class="input-sm editCertificate" id="editRefNo"
                                       style="display: none;width: 40%;margin: 4px;" name="editRefNo"/>

                            </span>
                            <label class="col-sm-12"
                                   style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">From:</label>
                            <span style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label class="view"
                                       style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       id="viewFromName">Prof. S. K. Sarma</label>
                                <input type="text" class="editCertificate  form-inline input-sm col-sm-5"
                                       style="display: none;float: left;margin: 4px; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       name="editFromName" id="editFromName"/>
                            </span>

                            <span style="width: 100%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label class="view"
                                       style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"
                                       id="viewFromPosition">Director i/c</label>
                                <input type="text" class="editCertificate  form-inline input-sm col-sm-5"
                                       style="display: none;;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;margin: 4px;"
                                       name="editFromPosition" id="editFromPosition"/>
                            </span>

                            <div style="clear: both;"></div>
                        </div>

                        <div style="width: 50%;float: left;min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <div style="width: 20%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

                            <div style="width: 80%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                                <label style="width: 33.33333333%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">Date:</label>
                                <label id="viewDate" class="view"
                                       style=" width: 66.66666667%;float: left; min-height: 1px; padding-right: 15px; padding-left: 15px;">${new SimpleDateFormat("dd-MMM-yyy").format(new java.util.Date())}</label>
                                <input class="editCertificate form-inline input-sm col-sm-5"
                                       type="text"
                                       style="float: left;position: relative;margin: 4px; min-height: 1px; padding-right: 15px; padding-left: 15px;display: none;"
                                       id="editDate"
                                       name="editDate"/>
                            </div>

                            <div style="clear: both;"></div>
                        </div>
                    </div>

                    <div style="padding-top: 20px; padding-bottom: 30px;font-size: 15px; width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;">
                        <span style="text-align: center;width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                            <label class="view" id="certificateMsgHeaderView"
                                   style=" margin-bottom: 15px;font-size: 21px;font-weight: bold;">To whom it may concern</label>
                            <input type="text" class=" form-inline input-sm editCertificate col-sm-7"
                                   style="display:none;"
                                   name="certificateMsgHeaderEdit" id="certificateMsgHeaderEdit"/>
                        </span>

                        <p class="view" id="paragraphView1"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">This is to certify that ${applicantName}, Roll No. ${rollNo}, is a student of ${semester} semester of ${program}(${department})  (${programDuration}) of the GUIST.</p>
                        <g:textArea name="paragraphEdit1" id="paragraphEdit1"
                                    class="editCertificate col-sm-10  form-inline input-sm"
                                    style="margin: 4px;display:none;"/>

                        <p id="paragraphView2" class="view"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">The following table shows the  details of fee structure for the ${program}.  programme of Gauhati University Institute of Science and Technology. The fee structure is applicable for the student enrolled in the academic session ${academicSession}.</p>
                        <g:textArea name="paragraphEdit2" id="paragraphEdit2"
                                    class="editCertificate col-sm-10 form-inline input-sm"
                                    style="margin: 4px;display:none;"/>

                        <p id="paragraphView3" class="view"
                           style="margin-bottom: 5px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">The fee for the ${year} year  paid by the student is given below.</p>
                        <g:textArea name="paragraphEdit3" id="paragraphEdit3"
                                    class="editCertificate col-sm-10 form-inline input-sm"
                                    style="margin: 4px;display:none;"/>
                        <p>
                            <style type="text/css">
                            table {
                                border-collapse: collapse;
                            }

                            table, th, td {
                                border: 1px solid black;
                            }
                            </style>
                        <table style="width: 80%;margin:15px auto 60px;line-height: 13px;font-size: 12px; padding: 1px;"
                               class="table-condensed">
                            <tr>
                                <th style="text-align: center;width: 35%;">Year</th>
                                <th style="text-align: center;width: 15%;">Amount</th>
                                <th style="text-align: center;width: 15%;">Total</th>
                                <th style="text-align: center;width: 35%;">Remarks</th>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">First Year</label>
                                    <label class="col-sm-12">First Semester</label>
                                    <label class="col-sm-12">Second Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="firstSemFeeView">23100.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="firstSemFeeEdit"
                                        name="firstSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="secondSemFeeView">17700.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="secondSemFeeEdit"
                                        name="secondSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 3px;"><label class="view"
                                                                          id="firstYearFeeView">40800.00</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="firstYearFeeEdit"
                                                                      name="firstYearFeeEdit"/>
                                </td>
                                <td rowspan="4" style="vertical-align: middle;line-height: 13px;">
                                    <label class="col-sm-12 view"
                                           id="courseFeeRemarksView">Amount may be directly paid to Gauhati University.</label><g:textArea
                                        class="input-sm editCertificate" style="display: none;width: 100%;"
                                        id="courseFeeRemarksEdit"
                                        name="courseFeeRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Second Year</label>
                                    <label class="col-sm-12">Third Semester</label>
                                    <label class="col-sm-12">Fourth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="thirdSemFeeView">23100.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="thirdSemFeeEdit"
                                        name="thirdSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="fourthSemFeeView">17700.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="fourthSemFeeEdit"
                                        name="fourthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="secondYearFeeView">40800.00</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="secondYearFeeEdit"
                                                                      name="secondYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Third Year</label>
                                    <label class="col-sm-12">Fifth Semester</label>
                                    <label class="col-sm-12">Sixth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="fifthSemFeeView">23100.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="fifthSemFeeEdit"
                                        name="fifthSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="sixthSemFeeView">17700.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="sixthSemFeeEdit"
                                        name="sixthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="thirdYearFeeView">40800.00</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="thirdYearFeeEdit"
                                                                      name="thirdYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Fourth Year</label>
                                    <label class="col-sm-12">Seventh Semester</label>
                                    <label class="col-sm-12">Eighth Semester</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view" id="seventhSemFeeView">23100.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="seventhSemFeeEdit"
                                        name="seventhSemFeeEdit"/>
                                    <label class="col-sm-12 view" id="eighthSemFeeView">17700.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="eighthSemFeeEdit"
                                        name="eighthSemFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="fourthYearFeeView">40800.00</label><label
                                        class="view">/-</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="fourthYearFeeEdit" name="fourthYearFeeEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Hostel Admission</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="hostelAdmissionFeeView">2950.00</label><label
                                        class="view">(Yearly)</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="hostelAdmissionFeeEdit" name="hostelAdmissionFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="hostelTotalFeeView">11800.00</label><label
                                        class="view">/-</label><input type="text"
                                                                      class="input-sm editCertificate"
                                                                      style="display: none;"
                                                                      id="hostelTotalFeeEdit"
                                                                      name="hostelTotalFeeEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;">
                                    <label class="col-sm-12 view"
                                           id="hostelRemarksView">Amount may be paid only if the student stays in hostel.</label><g:textArea
                                        class="input-sm editCertificate" style="display: none;width: 100%;"
                                        id="hostelRemarksEdit"
                                        name="hostelRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Approximate Amount of Yearly expenses (Boarding and Lodging, Book etc) @ 5000/- per month</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                          id="otherExpensesView">6000.00</label><label
                                        class="view">(Yearly)</label>
                                    <input type="text" class="input-sm editCertificate" style="display: none;"
                                           id="otherExpensesEdit" name="otherExpensesEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;"><label class="view"
                                                                            id="otherExpensesTotalView">2,40,000.00</label><input
                                        type="text"
                                        class="input-sm editCertificate"
                                        style="display: none;"
                                        id="otherExpensesTotalEdit"
                                        name="otherExpensesTotalEdit"/>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;" rowspan="2">
                                    <label class="col-sm-12 view"
                                           id="otherExpensesRemarksView">Amount to be paid directly to the student.</label>
                                    <g:textArea class="input-sm editCertificate" style="display: none;width: 100%;"
                                                id="otherExpensesRemarksEdit"
                                                name="otherExpensesRemarksEdit"/>
                                </td>
                            </tr>
                            <tr>
                                <td style="line-height: 13px;vertical-align: bottom;">
                                    <label class="col-sm-12">Laptop</label>
                                </td>
                                <td style="vertical-align: bottom;line-height: 13px;" colspan="2"><label class="view"
                                                                         id="laptopExpensesView">40000.00</label><label
                                        class="view">(approx.)</label><input type="text"
                                                                             class="input-sm editCertificate"
                                                                             style="display: none;"
                                                                             id="laptopExpensesEdit"
                                                                             name="laptopExpensesEdit"/>
                                </td>
                            </tr>

                        </table>
                    </p>

                    </div>

                    <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

                    <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                        <label class="view" id="signatureNameView"
                               style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">(S. K. Sarma)</label>
                        <input type="text" class="editCertificate  form-inline input-sm col-sm-7"
                               name="signatureNameEdit"
                               id="signatureNameEdit" style="display: none;margin-bottom: 30px;" value="(S. K. Sarma)">
                    </div>
                </div>
            </g:else>

        </div>
        <input type="hidden" id="showEdit" name="showEdit" value="${showEdit}"/>
        <input type="hidden" id="certificateType" name="certificateType" value="f"/>
        <input type="hidden" id="certificateReqId" name="certificateReqId" value="${certificateReqId}"/>
        <input type="hidden" id="year" name="year" value="${year}"/>
        <input type="hidden" id="certificateNo" name="certificateNo" value="${refNo}"/>

        <div class="col-sm-12 text-center">
            <input type="button" class="btn btn-default" id="btnPrint" value="Print" onclick="printCertificate()"/>
            <g:if test="${showEdit}">
                <input type="button" class="btn btn-info" id="btnEdit" value="Edit" onclick="editCertificate()"/>
                <input type="button" class="btn btn-success" id="btnEditDone" disabled value="Edit Complete"
                       onclick="editDone()"/>
                <input type="button" class="btn btn-success" id="sendForApproval" value="Send For Approval"
                       onclick="sendForApproval()"/>
            </g:if>
        </div>
    </fieldset>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $("#editDate").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd-M-yy",
                maxDate: 0
            });
        });

        if ($('#editRefNo').val() == '') {
            $('#editRefNo').val($('#viewRefNo').text())
        }
        if ($('#editFromName').val() == '') {
            $('#editFromName').val($('#viewFromName').text())
        }
        if ($('#editFromPosition').val() == '') {
            $('#editFromPosition').val($('#viewFromPosition').text())
        }
        if ($('#editDate').val() == '') {
            $('#editDate').val($('#viewDate').text())
        }
        if ($('#certificateMsgHeaderEdit').val() == '') {
            $('#certificateMsgHeaderEdit').val($('#certificateMsgHeaderView').text())
        }
        if ($('#paragraphEdit1').val() == '') {
            $('#paragraphEdit1').val($('#paragraphView1').text().trim())
        }
        if ($('#paragraphEdit2').val() == '') {
            $('#paragraphEdit2').val($('#paragraphView2').text())
        }
        if ($('#paragraphEdit3').val() == '') {
            $('#paragraphEdit3').val($('#paragraphView3').text())
        }
        if ($('#signatureNameEdit').val() == '') {
            $('#signatureNameEdit').val($('#signatureNameView').text())
        }
        if ($('#firstSemFeeEdit').val() == '') {
            $('#firstSemFeeEdit').val($('#firstSemFeeView').text())
        }
        if ($('#secondSemFeeEdit').val() == '') {
            $('#secondSemFeeEdit').val($('#secondSemFeeView').text())
        }
        if ($('#thirdSemFeeEdit').val() == '') {
            $('#thirdSemFeeEdit').val($('#thirdSemFeeView').text())
        }
        if ($('#fourthSemFeeEdit').val() == '') {
            $('#fourthSemFeeEdit').val($('#fourthSemFeeView').text())
        }
        if ($('#fifthSemFeeEdit').val() == '') {
            $('#fifthSemFeeEdit').val($('#fifthSemFeeView').text())
        }
        if ($('#sixthSemFeeEdit').val() == '') {
            $('#sixthSemFeeEdit').val($('#sixthSemFeeView').text())
        }
        if ($('#seventhSemFeeEdit').val() == '') {
            $('#seventhSemFeeEdit').val($('#seventhSemFeeView').text())
        }
        if ($('#eighthSemFeeEdit').val() == '') {
            $('#eighthSemFeeEdit').val($('#eighthSemFeeView').text())
        }
        if ($('#hostelAdmissionFeeEdit').val() == '') {
            $('#hostelAdmissionFeeEdit').val($('#hostelAdmissionFeeView').text())
        }
        if ($('#otherExpensesEdit').val() == '') {
            $('#otherExpensesEdit').val($('#otherExpensesView').text())
        }
        if ($('#firstYearFeeEdit').val() == '') {
            $('#firstYearFeeEdit').val($('#firstYearFeeView').text())
        }
        if ($('#secondYearFeeEdit').val() == '') {
            $('#secondYearFeeEdit').val($('#secondYearFeeView').text())
        }
        if ($('#fourthYearFeeEdit').val() == '') {
            $('#fourthYearFeeEdit').val($('#fourthYearFeeView').text())
        }
        if ($('#thirdYearFeeEdit').val() == '') {
            $('#thirdYearFeeEdit').val($('#thirdYearFeeView').text())
        }
        if ($('#hostelTotalFeeEdit').val() == '') {
            $('#hostelTotalFeeEdit').val($('#hostelTotalFeeView').text())
        }
        if ($('#otherExpensesTotalEdit').val() == '') {
            $('#otherExpensesTotalEdit').val($('#otherExpensesTotalView').text())
        }
        if ($('#laptopExpensesEdit').val() == '') {
            $('#laptopExpensesEdit').val($('#laptopExpensesView').text())
        }
        if ($('#courseFeeRemarksEdit').val() == '') {
            $('#courseFeeRemarksEdit').val($('#courseFeeRemarksView').text())
        }
        if ($('#hostelRemarksEdit').val() == '') {
            $('#hostelRemarksEdit').val($('#hostelRemarksView').text())
        }
        if ($('#otherExpensesRemarksEdit').val() == '') {
            $('#otherExpensesRemarksEdit').val($('#otherExpensesRemarksView').text())
        }

    });

</script>

</body>
</html>