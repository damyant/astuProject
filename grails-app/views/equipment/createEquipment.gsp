<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/19/14
  Time: 1:18 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{--This page is for adding a new equipment --}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Equipment Page</title>
    <g:javascript src='validation.js'/>
    <g:javascript src='admin.js'/>
    <g:javascript src="postExamination.js"/>
    <script type="text/javascript">

    </script>

</head>

<body>

<div id="main">
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <fieldset class="form">
    %{--<h3>Empolyee </h3>--}%
        <g:if test="${equipmentObj?.equipmentName}">
            <g:hiddenField name="equId" value="${equipmentObj?.id}"/>
        </g:if>

        <g:uploadForm controller="equipment" action="saveEquipment" method='post' enctype="multipart/form-data"
                      id="equipmentCreation" name="equipmentCreation">
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="inner university-size-full-1-1">
                <tr>
                    <g:if test="${equipmentObj?.equipmentName}">
                        <g:hiddenField name="equId" value="${equipmentObj?.id}"/>
                    </g:if>

                    <td class="university-size-1-3">Name of Equipment <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">

                        <input type="text" tabindex="1" name="equipmentName"
                               onkeypress="return onlyAlphabets(event);"
                               maxlength="50" class="university-size-1-3" value="${equipmentObj?.equipmentName}"/>

                    </td>
                </tr>

                <tr>
                    <td class="university-size-1-3">Equipment Id <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <input type="text" name="equipmentId" id="equipmentId" tabindex="2"
                               value="${equipmentObj?.equipmentId}"
                               onkeypress="return isNumber(event)" onblur="validEquipCode()" maxlength="10" class="university-size-1-3" />
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Lab Inventory Id <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <input type="text" name="labInventoryId" id="labInventoryId" tabindex="3"
                               value="${equipmentObj?.labInventoryId}"
                               onkeypress="return isNumber(event)" maxlength="10" class="university-size-1-3" />
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Equipment Type <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <input type="text" name="equipmentType" id="equipmentType" tabindex="4"
                               value="${equipmentObj?.equipmentType}"
                               class="university-size-1-3" />
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Manufacturer <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <input type="text" name="manufacturer" id="manufacturer" tabindex="5"
                               value="${equipmentObj?.manufacturer}"
                               class="university-size-1-3" />
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>


                <tr>
                    <td>Date of Purchase <span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" name="dateOfPurchase" maxlength="10" tabindex="6" PLACEHOLDER="DD/MM/YYYY"
                               value="<g:formatDate format="MM/dd/yyyy" date="${equipmentObj?.dateOfPurchase}"/>"
                               class="university-size-1-3" id="datepicker3" tabindex="5">

                    </td>
                </tr>

                <tr>
                    <td class="university-size-1-3">Description <span class="university-obligatory">*</span></td>
                    <td class="university-size-2-3">
                        <g:textArea name="description" id="description" tabindex="7" rows="5"
                                    value="${equipmentObj?.description}"
                                    class="university-size-1-3"/>
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>

                <tr>
                    <td>Warranty<span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" id="warranty" name="warranty" tabindex="8" value="${equipmentObj?.warranty}"
                               class="university-size-1-3"/>
                    </td>
                </tr>
                <tr>
                    <td>Issued To<span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" id="issuedTo" name="issuedTo" tabindex="9" value="${equipmentObj?.issuedTo}"
                               class="university-size-1-3"/>
                    </td>
                </tr>
                <tr>
                    <td>Issued Date <span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" name="issuedDate" maxlength="10" tabindex="10" PLACEHOLDER="DD/MM/YYYY"
                               value="<g:formatDate format="MM/dd/yyyy" date="${equipmentObj?.issuedDate}"/>"
                               class="university-size-1-3" id="datepicker2">

                    </td>
                </tr>
                <tr>
                    <td>Vendor Name<span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" id="vendorName" name="vendorName" tabindex="11" value="${equipmentObj?.vendorName}"
                               class="university-size-1-3"/>
                    </td>
                </tr>
                <tr>
                    <td>Quantity<span class="university-obligatory">*</span></td>
                    <td>
                        <input type="text" id="quantity" name="quantity" tabindex="12" value="${equipmentObj?.quantity}"
                               class="university-size-1-3" onkeypress="return isNumber(event)"/>
                    </td>
                </tr>

                <tr>
                    <td>Attach Documents</td>
                    <td>

                        <g:if test="${equipmentObj?.documentImage}">
                            <img src="${createLink(controller: 'equipment', action: 'showImage', id: equipmentObj?.id, mime: 'image/jpeg')}"
                                 class="image-preview-div" id="picture1"/>
                            <input type='file' id="profileImage" onchange="readURL1(this, 'picture1');"
                                   class="university-button"
                                   name="documentImage" tabindex="13"/>
                        </g:if>
                        <g:else>

                            <div id="profile-image" class='image-preview-div'>
                                <img src="" class="image-preview-div" id="picture"/>
                                <input type='file' id="profileImage" onchange="readURL1(this, 'picture');"
                                       class="university-button"
                                       name="documentImage" tabindex="14"/>
                            </div>

                        </g:else>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td></td>
                    <td>

                        <input type="SUBMIT" value="Submit" onclick="validate()" id="" tabindex="15"
                               class="university-button">
                        <input type="reset" value="Reset" tabindex="16" onclick="resetImage()"
                               class="university-button">
                    </td>
                </tr>
            </table>
        </g:uploadForm>

    </fieldset>

</div>
<script type="text/javascript">
    $(function () {
        $(function () {
            $("#datepicker2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $("#datepicker3").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });

        });

    });

</script>
</body>
</html>