<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 31/3/14
  Time: 11:58 AM
--%>

<%--
  Created by Damyant Software Pvt Ltd.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script>

          function funload(){
            var status= window.confirm('Do you want to print this Application ?')
            if(status){
               var printing = window.print();
                if(printing){
                    window.close();
                }
            }
          }



    </script>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>
    <style type="text/css">

    </style>
</head>

<body onload="funload()">
<div id="main" style="text-transform: capitalize">
    <div>
        <table align="center" cellpadding="13" style="width: 100%;height:150px">
            <tr>
                <td style="width: 80%; vertical-align: top;">
                    <div class="preview-header" style="text-align: center; top:2px;">
                        <div><strong>Institute Of Science & Technology (IST)</strong></div>

                        <div>Guwahati University,Gopinath Bardoloi Nagar</div>

                        <div>Jalukbari, Guwahati - 781014</div>

                        <div>Toll Free No. </div>

                        <div>Phone : </div>

                        <div>Fax: </div>

                        <div>Email: </div>

                    </div>
                </td>
                <td style="width: 80%;">
                    <div style="margin:auto; height: 150px;width:117px;text-align: center ; vertical-align:middle;border: 1px solid;">
                        <g:if test="${studentInstance?.getStudentImage()}">
                            <rendering:inlineJpeg bytes="${studentInstance?.getStudentImage()}" style="margin:auto; height: 150px;width: 117px;border: 1px solid black;    display: block;
                                                  background-position: bottom; background-size: 100%; text-align: center;"/>
                        </g:if>
                        <g:else>
                            <div style="margin: 68px 0px;">
                                Affix Passport Size Photo
                            </div>
                        </g:else>
                    </div>
                    <div style="margin: 3px auto;padding: 2px;width: 117px; font-size: 12px;border: 1px solid;"><label>Ref No : </label><label>${studentInstance?.referenceNumber}</label>
                    </div>
                </td>
            </tr>
        </table>
        <table class="university-size-1-1" style="border: 1px solid brown;margin:auto;font-size: 9px;">
            <tr>
                <td>
                    <i>* Take print of this PDF and send to</i>
                    <b>IST, Gauhati University</b><i> along with the </i><b>Bank Draft</b>.
                </td>
            </tr>
        </table>
        <table align="center" cellpadding="2" id="preview-pdf" class="university-table-1-2"
               style="width: 100%;margin: auto; border: 1px solid; ">
            <tr>
                <td style="width: 60%;">
                    <label>Name</label>
                </td>
                <td style="width: 40%;">
                    <label>${studentInstance?.firstName} ${studentInstance?.middleName} ${studentInstance?.lastName}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Date of Birth</label>
                </td>
                <td>
                    <label><g:formatDate date="${studentInstance?.dob}" format="dd MMM yyyy"/></label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Programme</label>
                </td>
                <td>
                    <label>${studentInstance?.courseType.courseTypeName}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Category</label>
                </td>
                <td>
                    <label>${studentInstance?.category}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Nationality</label>
                </td>
                <td>
                    <label>${studentInstance?.nationality}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Gender</label>
                </td>
                <td>
                    <label>${studentInstance?.gender}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>State of Domicile</label>
                </td>
                <td>
                    <label>${studentInstance?.state}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Mobile Number</label>
                </td>
                <td>
                    <label>${studentInstance?.mobileNo}</label>
                </td>
            </tr>

            <g:if test="${studentInstance?.mailing_Address}">
                <tr>
                    <td style="vertical-align: top;">
                        <label>Complete Mailing Address</label>
                    </td>
                    <td style="vertical-align: top;">
                        <div>${studentInstance?.mailing_Address}</div>

                        <div> ${studentInstance?.mailingAddressDistrict}</div>

                        <div>${studentInstance?.mailingStateAddress},  ${studentInstance?.mailingPincode}</div>
                    </td>
                </tr>
            </g:if>
        </table>
        <hr style="border-top: dashed 2px;" />

        <table cellpadding="2"  class="university-table-1-2"
               style="width: 100%;margin: auto; border: 1px solid; ">
            <tr><td style="width: 60%;">Challan Number</td><td style="width: 40%;">${feeDetails.challanNo}</td></tr>
            <tr><td>Fee</td><td>${feeDetails.admissionFeeAmount}</td></tr>

            <tr><td>Payment Mode</td><td>${feeDetails.paymentMode}</td></tr>
            <tr><td>Payment Date</td><td>${feeDetails.paymentDate}</td></tr>
            <tr><td>Payment Ref Number</td><td>${feeDetails.feeReferenceNumber}</td></tr>
            <tr><td>Bank</td><td>${feeDetails.bankName}</td></tr>
            <tr><td>Branch</td><td>${feeDetails.branchName}</td></tr>
        </table><br/><br/>
        <table style="width:100%;margin: auto;">
            <tr>
                <td style="width:50%;height: 70px;"><label style="float: left; margin-left:10px;">Date:</label></td>
                <td style="width:50%;"><label style="float: right; margin-right:10px;">Signature</label></td>
            </tr>
        </table>
        <table style="width:100%;margin: auto;font-size: 10px; border:1px double #3b3b3b;">
            <td>
                <b>Enclosures:</b>
                <ol>
                    <li>Two copies of Passport size photographs, signed by the candidate.</li>
                    <li>Self attested copies of degree marksheets.</li>
                    <li>Self attested copy of G.U. Registration certificate (for a student of GU).</li>
                </ol>
            </td>
        </table>

    </div>
</div>
</body>
</html>