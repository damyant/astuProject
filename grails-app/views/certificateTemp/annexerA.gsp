<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 4:08 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{-- creating certificate template for Parent's income tax purpose--}%
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

        <div style=" padding-top: 60px; padding-bottom: 50px;font-size: 18px; width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;">
            <span style="text-align: center;width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                <label class="view" id="certificateMsgHeaderView"
                       style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">${header}</label>
                <input type="text" class=" form-inline input-sm editCertificate col-sm-7" style="display:none;"
                       name="certificateMsgHeaderEdit" id="certificateMsgHeaderEdit"/>
            </span>

            <p class="view" id="paragraphView1"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">
                <g:if test="${paragraph1 != null}">${paragraph1}</g:if>
            </p>
            <g:textArea name="paragraphEdit1" id="paragraphEdit1"
                        class="editCertificate col-sm-10  form-inline input-sm"
                        style="margin: 4px;display:none;"/>

            <p id="paragraphView2" class="view"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;"><g:if
                    test="${paragraph2 != null}">${paragraph2}</g:if></p>
            <g:textArea name="paragraphEdit2" id="paragraphEdit2"
                        class="editCertificate col-sm-10 form-inline input-sm"
                        style="margin: 4px;display:none;"/>
            <p id="paragraphView3" class="view"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;"><g:if
                    test="${paragraph3 != null}">${paragraph3}</g:if></p>
            <g:textArea name="paragraphEdit3" id="paragraphEdit3"
                        class="editCertificate col-sm-10 form-inline input-sm"
                        style="margin: 4px;display:none;"/>
            <p>
            <table style="width: 70%;margin: auto;border: 0px;" class="table-condensed">
                <tr>
                    <td style="text-align: center;width: 33%;">Year</td>
                    <td style="text-align: center;width: 33%;">Amount</td>
                    <td style="text-align: center;width: 33%;">Total</td>
                </tr>
                <tr><td colspan="2">${year} Year</td> <td rowspan="11" style="vertical-align: middle;text-align: center;"><label>Rs. </label><label id="totalAmount">${totalFee}</label></td></tr>

                <g:if test="${semesterNo > 1}">
                    <tr><td colspan="2">${prevSemester} Semester</td></tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Tuition Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearTutionFeeView">${prevYearTuitionFee}</label>
                            <input type="text" onchange="calculateTotalFee()" class="col-sm-9 editCertificate feeAmount" id="prevYearTutionFeeEdit" name="prevYearTutionFeeEdit"  style="display: none;"/>

                        </td>

                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Admission Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearAdmissionFeeView">${prevYearAdmissionFee}</label>
                            <input type="text" style="display: none;"  class="col-sm-9 editCertificate feeAmount" id="prevYearAdmissionFeeEdit" onchange="calculateTotalFee()" name="prevYearAdmissionFeeEdit"/>
                        </td>

                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Examination Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearExamFeeView">${prevYearExamFee}</label><input type="text" style="display: none;"
                                                                                                                                             class="col-sm-9 editCertificate feeAmount"
                                                                                                                                             id="prevYearExamFeeEdit" onchange="calculateTotalFee()"
                                                                                                                                             name="prevYearExamFeeEdit"/></td>
                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Other Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearOtherFeeView">${prevYearOtherFee}</label><input type="text" style="display: none;"
                                                                                                                                               class="col-sm-9 editCertificate feeAmount"
                                                                                                                                               id="prevYearOtherFeeEdit" onchange="calculateTotalFee()"
                                                                                                                                               name="prevYearOtherFeeEdit"/></td>
                    </tr>
                </g:if>
                <tr><td colspan="2">${semester} Semester</td></tr>
                <tr>
                    <td><label class="col-sm-1"></label>Tution Fee</td>
                    <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearTutionFeeView">${thisYearTuitionFee}</label>
                        <input type="text" class="col-sm-9 editCertificate feeAmount" onchange="calculateTotalFee()" id="thisYearTutionFeeEdit" style="display: none;" name="thisYearTutionFeeEdit"/>
                     </td>
                </tr>
                <tr>
                    <td><label class="col-sm-1"></label>Examination Fee</td>
                    <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearExamFeeView">${thisYearExamFee}</label><input type="text"  style="display: none;" onchange="calculateTotalFee()"
                                                                                                                                         class="col-sm-9 feeAmount editCertificate"
                                                                                                                                         id="thisYearExamFeeEdit"
                                                                                                                                         name="thisYearExamFeeEdit"/>
                    </td>
                </tr>
                <tr>
                    <td><label class="col-sm-1"></label>Laboratory fee</td>
                    <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearLabFeeView">${thisYearLabFee}</label><input type="text" style="display: none;" onchange="calculateTotalFee()"
                                                                                                                                       class="feeAmount col-sm-9 editCertificate"
                                                                                                                                       id="thisYearLabFeeEdit"
                                                                                                                                       name="thisYearLabFeeEdit"/>
                    </td>
                </tr>

            </table>
        </p>

        </div>

        <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

        <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
            <label class="view" id="signatureNameView"
                   style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">${signatureName}</label>
            <input type="text" class="editCertificate  form-inline input-sm col-sm-7" name="signatureNameEdit"
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

        <div style=" padding-top: 60px; padding-bottom: 50px;font-size: 18px; width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;">
            <span style="text-align: center;width: 100%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
                <label class="view" id="certificateMsgHeaderView"
                       style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">To whom it may concern</label>
                <input type="text" class=" form-inline input-sm editCertificate col-sm-7" style="display:none;"
                       name="certificateMsgHeaderEdit" id="certificateMsgHeaderEdit"/>
            </span>

            <p class="view" id="paragraphView1"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">This is to certify that ${applicantName}, Roll No. ${rollNo}, <g:if
                    test="${male}">son</g:if><g:else>daughter</g:else> of ${gurdianName} is a ${semester} semester student of ${program} (${department}) programme of Gauhati University Institute of Science and Technology.</p>
            <g:textArea name="paragraphEdit1" id="paragraphEdit1"
                        class="editCertificate col-sm-10  form-inline input-sm"
                        style="margin: 4px;display:none;"/>

            <p id="paragraphView2" class="view"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;"></p>
            <g:textArea name="paragraphEdit2" id="paragraphEdit2"
                        class="editCertificate col-sm-10 form-inline input-sm"
                        style="margin: 4px;display:none;"/>

            <p id="paragraphView3" class="view"
               style="margin-bottom: 10px; padding-right: 15px; padding-left: 15px; margin-right: auto; margin-left: auto;clear: both;text-align: justify;">The fee for the ${year} year  paid by the student is given below.</p>
            <g:textArea name="paragraphEdit3" id="paragraphEdit3"
                        class="editCertificate col-sm-10 form-inline input-sm"
                        style="margin: 4px;display:none;"/>
            <p>
            <table style="width: 70%;margin: auto;border: 0px;" class="table-condensed">
                <tr>
                    <td style="text-align: center;width: 33%;">Year</td>
                    <td style="text-align: center;width: 33%;">Amount</td>
                    <td style="text-align: center;width: 33%;">Total</td>
                </tr>
                <tr><td colspan="2">${year} Year</td> <td rowspan="11" style="vertical-align: middle;text-align: center;"><label>Rs. </label><label id="totalAmount">${totalAmount}</label></td></tr>

                <g:if test="${semesterNo > 1}">
                    <tr><td colspan="2">${prevSemester} Semester</td></tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Tuition Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearTutionFeeView"></label>
                            <input type="text" onchange="calculateTotalFee()" class="col-sm-9 editCertificate feeAmount" id="prevYearTutionFeeEdit" name="prevYearTutionFeeEdit"  style="display: none;"/>
                        </td>

                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Admission Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearAdmissionFeeView">${prevSemFeeAmount}</label>
                            <input type="text" style="display: none;"  class="col-sm-9 editCertificate feeAmount" id="prevYearAdmissionFeeEdit" onchange="calculateTotalFee()" name="prevYearAdmissionFeeEdit"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Examination Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearExamFeeView"></label><input type="text" style="display: none;"
                                                                                           class="col-sm-9 editCertificate feeAmount"
                                                                                           id="prevYearExamFeeEdit" onchange="calculateTotalFee()"
                                                                                           name="prevYearExamFeeEdit"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label class="col-sm-1"></label>Other Fee</td>
                        <td><label class="col-sm-2">Rs</label> <label class="view" id="prevYearOtherFeeView"></label><input type="text" style="display: none;"
                                                                                           class="col-sm-9 editCertificate feeAmount"
                                                                                           id="prevYearOtherFeeEdit" onchange="calculateTotalFee()"
                                                                                           name="prevYearOtherFeeEdit"/>
                        </td>
                    </tr>
                </g:if>
            <tr><td colspan="2">${semester} Semester</td></tr>
            <tr>
                <td><label class="col-sm-1"></label>Tution Fee</td>
                <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearTutionFeeView">${thisSemFeeAmount}</label>
                    <input type="text" class="col-sm-9 editCertificate feeAmount" onchange="calculateTotalFee()" id="thisYearTutionFeeEdit" style="display: none;" name="thisYearTutionFeeEdit"/>
                </td>
            </tr>
            <tr>
                <td><label class="col-sm-1"></label>Examination Fee</td>
                <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearExamFeeView"></label><input type="text"  style="display: none;" onchange="calculateTotalFee()"
                                                                                                                   class="col-sm-9 feeAmount editCertificate"
                                                                                                                   id="thisYearExamFeeEdit"
                                                                                                                   name="thisYearExamFeeEdit"/>
                </td>
            </tr>
            <tr>
                <td><label class="col-sm-1"></label>Laboratory fee</td>
                <td><label class="col-sm-2">Rs</label> <label class="view" id="thisYearLabFeeView"></label><input type="text" style="display: none;" onchange="calculateTotalFee()"
                                                                                                                   class="feeAmount col-sm-9 editCertificate"
                                                                                                                   id="thisYearLabFeeEdit"
                                                                                                                   name="thisYearLabFeeEdit"/>
                </td>
            </tr>

            </table>
        </p>

        </div>

        <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;"></div>

        <div style="text-align: center;width: 50%;float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;">
            <label class="view" id="signatureNameView"
                   style=" margin-bottom: 30px;font-size: 21px;font-weight: bold;">(S. K. Sarma)</label>
            <input type="text" class="editCertificate  form-inline input-sm col-sm-7" name="signatureNameEdit"
                   id="signatureNameEdit" style="display: none;margin-bottom: 30px;" value="(S. K. Sarma)">
        </div>
    </div>
</g:else>

</div>
<input type="hidden" id="showEdit" name="showEdit" value="${showEdit}"/>
<input type="hidden" id="certificateType" name="certificateType" value="a"/>
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
        if ($('#prevYearTutionFeeEdit').val() == '') {
            $('#prevYearTutionFeeEdit').val($('#prevYearTutionFeeView').text())
        }
        if ($('#prevYearAdmissionFeeEdit').val() == '') {
            $('#prevYearAdmissionFeeEdit').val($('#prevYearAdmissionFeeView').text())
        }
        if ($('#prevYearExamFeeEdit').val() == '') {
            $('#prevYearExamFeeEdit').val($('#prevYearExamFeeView').text())
        }
        if ($('#prevYearOtherFeeEdit').val() == '') {
            $('#prevYearOtherFeeEdit').val($('#prevYearOtherFeeView').text())
        }
        if ($('#thisYearTutionFeeEdit').val() == '') {
            $('#thisYearTutionFeeEdit').val($('#thisYearTutionFeeView').text())
        }
        if ($('#thisYearExamFeeEdit').val() == '') {
            $('#thisYearExamFeeEdit').val($('#thisYearExamFeeView').text())
        }
        if ($('#thisYearLabFeeEdit').val() == '') {
            $('#thisYearLabFeeEdit').val($('#thisYearLabFeeView').text())
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
            $('#paragraphEdit1').val($('#paragraphView1').text())
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

    });

</script>

</body>
</html>