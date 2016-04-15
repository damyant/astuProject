<%--
  Created by Damyant Software Pvt Ltd.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
%{--Page for  Student Registration (entering student details )--}%
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='registerPage.js'/>
    <g:javascript src='jquery.select-to-autocomplete.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            /*function for displaying text box for country name(if nationality is Non-Indian)*/
            $("input:radio[name='nationality']").click(function() {
                if($("input:radio[name='nationality']:checked").val()=='Non-Indian'){
                    $("#forForeignStudent").css("display", "");}
                else
                    $("#forForeignStudent").css("display", "none");
            })
            $(".dialog").dialog({
                autoOpen: false,
                draggable: false,
                position: ['center', 0],
                width: 850,
                resizable: false,
                height: 750,
                modal: true,
                title: 'Subject Selection',
                close: function (ev, ui) {
                    $.unblockUI();
                }
            });
            var programType=$('#programType').val()
            var board='', sub='', year='',div='',percentage=''
            if(programType!='4'){
                board =$("#School12th").val()
                sub =$("#Subject12th").val()
                year=$("#Year12th").val()
                div=$("#Div12th").val()
                percentage=$("#Percntge12th").val()
                if(board!='' && sub!=''&& year!=''&& div!=''&& percentage!=''){
                    $('#submitButton').prop('disabled', false)
                }
                else{
                    $('#submitButton').prop('disabled', true)
                }
            }
            else{
                board =$("#diplomaSchool").val()
                sub =$("#diplomaSubject").val()
                year=$("#diplomaYear").val()
                div=$("#diplomaDiv").val()
                percentage=$("#diplomaPercntge").val()
                if(board!='' && sub!=''&& year!=''&& div!=''&& percentage!=''){
                    $('#submitButton').prop('disabled', false)
                }
                else{
                    $('#submitButton').prop('disabled', true)
                }
            }
        });
        function copyAllAdress(){
            /*for copying permanent address to mailing address*/
            if($('#sameAsPermanent').prop('checked')){
                $('#mailing_Address').val($('#permanent_Address').val())
                $('#mailingAddressDistrict').val($('#permanentAddressDistrict').val())
                $('#mailingStateAddress').val($('#permanentStateAddress').val())
                $('#mailingPincode').val($('#permanentPincode').val())
                $('#mailingMobileNo').val($('#permanentMobileNo').val())
            }
            else{
                $('#mailing_Address').val('')
                $('#mailingAddressDistrict').val('')
                $('#mailingStateAddress').val('')
                $('#mailingPincode').val('')
                $('#mailingMobileNo').val('')
            }

        }
        var gender = "${studInstance?.gender}"
        var category = "${studInstance?.category}"
        var nationality = "${studInstance?.nationality}"
        var state = "${studInstance?.state}"
        var categoryCA = "${studInstance?.isExtraCurricularActivity}"
        var categoryGU = "${studInstance?.isWardOfGuEmployee}"
        var categoryFV = "${studInstance?.isFringeVillage}"
        var categoryDA = "${studInstance?.isPH}"
        var categoryNe = "${studInstance?.isNEStudent}"
        var country = "${studInstance?.nationalityName}"



        $('#studentRegister').ready(function () {
            $("input[name='nationality'][value=" + nationality + "]").attr('checked', 'checked');
            $("input.radioInput[name='category'][value='" + category + "']").attr('checked', 'checked');
            $(".radioInput[name='gender'][value=" + gender + "]").attr('checked', 'checked');
            $(".radioInput[name='state'][value=" + state + "]").attr('checked', 'checked');
            $(".checkInput[name= 'categoryCA'][value=" + categoryCA + "]").attr('checked', 'checked');
            $(".checkInput[name= 'categoryGU'][value=" + categoryGU + "]").attr('checked', 'checked');
            $(".checkInput[name= 'categoryFV'][value=" + categoryFV + "]").attr('checked', 'checked');
            $(".checkInput[name= 'categoryDA'][value=" + categoryDA + "]").attr('checked', 'checked');
            $(".checkInput[name= 'categoryNe'][value=" + categoryNe + "]").attr('checked', 'checked');
            if(nationality=="Non-Indian"){
                $("#forForeignStudent").css("display", "");
                $("#nonIndian").val(country);
            }
            else
                $("#forForeignStudent").css("display", "none");
        });
    </script>

</head>

<body>
<div id="main">
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>

<g:hasErrors bean="${studInstance}">
    <div class="errors">
        <g:renderErrors bean="${studInstance}" as="list"/>
    </div>
</g:hasErrors>
<fieldset class="form">
<g:if test="${params.registered}">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'correct.png')}" alt="Success"
                        style="margin: auto; width: 200px;"/></p>
                    <p><g:message code="register.created.message"/></p>
            </div></div>
        </div>
</g:if>
<g:elseif test="${status}">
    <div class='body'>
        <div class='errors'><div class="university-not-authorized">
            <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                    style="margin: auto;"/></p>

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
        </div></div>
    </div>
</g:elseif>
<g:else>
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data"
              id="studentRegister" name="studentRegister">
<h3>STUDENT INFORMATION SHEET</h3>
%{--page for new student registration and edit registration--}%
<div style="margin-left: 10px;"><label><h6>All [<span
        class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
<table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;">
<g:if test="${studInstance}">
    <g:hiddenField name="studentId" value="${studInstance?.id}"/>
</g:if>
<g:if test="${studInstance?.reasonForRejection}">
    <tr>
        <td colspan="2" style="text-align: center;font-weight: bold"><label class="error"> Reason for rejection::: ${studInstance?.reasonForRejection}</label>
        </td>
    </tr>
</g:if>
<!----- First Name ---------------------------------------------------------->
<tr>
    <td>Name of the applicant <span class="university-obligatory">*</span></td>
    <td>
        <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
            <tr>
                <td>
                    <input type="text" placeholder="First Name" name="firstName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.firstName}"/>
                </td>
                <td style="vertical-align: top;">
                    <input type="text" placeholder="Middle Name" name="middleName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.middleName}"/>
                </td>
                <td>
                    <input type="text" placeholder="Last Name" name="lastName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.lastName}"/>
                </td>
            </tr>
        </table>

    </td>
</tr>
<g:if test="${studInstance}">
    <tr>
        <td>Roll Number <span class="university-obligatory">*</span></td>
        <td>
            <input type="text" name="rollNo" readonly
                   class="university-size-1-1" value="${studInstance.rollNo}"/>

        </td>
    </tr>
</g:if>
<g:else>
    <tr>
        <td>Roll Number <span class="university-obligatory">*</span></td>
        <td>
            <input type="text" name="rollNo" readonly
                   class="university-size-1-1" value="${currentUser}"/>

        </td>
    </tr>
</g:else>

<!----- Program Name ---------------------------------------------------------->
<g:if test="${studInstance}">
    <tr>
        <td>Program Type<span class="university-obligatory">*</span></td>
        <td>
            <select name="programType" id="programType"  class="university-size-1-1" >
                <option value="${studInstance?.program.programType.id}">${studInstance?.program.programType.type}</option>
            </select>
        </td>
    </tr>
</g:if>
<g:else>
    <tr>
        <td>Program Type<span class="university-obligatory">*</span></td>
        <td>
            <select name="programType" id="programType"   class="university-size-1-1" >
                <option value="${currentProgram?.id}">${currentProgram?.type}</option>
            </select>
        </td>
    </tr>
</g:else>

<g:if test="${studInstance}">

    <tr>
        <td>Program Branch<span class="university-obligatory">*</span></td>
        <td>
            <sec:ifAnyGranted roles="ROLE_HOD, ROLE_ADMIN">
                <g:select name="programBranch" class="university-size-1-1" id="programBranch" onchange="loadProgramDetail(this)"
                          value="${studInstance?.program?.programBranch?.id}" optionKey="id" optionValue="name"
                          from="${programBranchList}" noSelection="['': ' Select Program Branch']"/>

            </sec:ifAnyGranted>
            <sec:ifNotGranted roles="ROLE_HOD, ROLE_ADMIN">
            <select name="programBranch"  class="university-size-1-1" >
                <option value="${studInstance?.programBranch.id}">${studInstance?.programBranch.name}</option>
            </select>
            </sec:ifNotGranted>
        </td>
    </tr>
</g:if>
<g:else>
    <tr>
        <td>Program Branch<span class="university-obligatory">*</span></td>
        <td>
            <select name="programBranch"  class="university-size-1-1" >
                <option value="${currentBranch?.id}">${currentBranch?.name}</option>
            </select>
        </td>
    </tr>
</g:else>
<g:if test="${studInstance}">
    <tr>
        <td>Program <span class="university-obligatory">*</span></td>
        <td>
            <g:select name="programId" class="university-size-1-1" id="programId"
                      value="${studInstance?.program.id}" optionKey="id" optionValue="courseName"
                      from="${programList}" noSelection="['': ' Select Programme']"/>

        </td>
    </tr>
</g:if>
<g:else>
    <tr>
        <td>Program <span class="university-obligatory">*</span></td>
        <td>
            <select name="programId" class="university-size-1-1" >
                <g:each in="${currentProgDetail}" var="cProg">
                    <option value="${cProg.id}">${cProg.courseName}</option>
                </g:each>
            </select>
        </td>
    </tr>
</g:else>





<tr>
    <td>Date of Birth <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" name="dob" maxlength="10" PLACEHOLDER="DD/MM/YYYY" class="university-size-1-2"
         id="datepicker" value="<g:formatDate format="dd/MM/yyyy" date="${studInstance?.dob}"/>">
    </td>
</tr>



<!----- category ----------------------------------------------------------->
<tr>
    <td>Category <span class="university-obligatory">*</span></td>
    <td style="border: 1px solid beige">
        <div class="radio_options" style="border: 1px;">
            <label><span>General</span><input type="radio" name="category" value="General" class="radioInput"/></label>

            <label><span>MOBC</span><input type="radio" name="category" value="MOBC" class="radioInput"/></label>

            <label><span>OBC</span><input type="radio" name="category" value="OBC" class="radioInput"/></label>

            <label><span>SC</span><input type="radio" name="category" value="SC" class="radioInput" style=""/></label>

            <label><span>ST</span><input type="radio" name="category" value="S.T" class="radioInput"/></label>

            <label><span>Minority</span><input type="radio" name="category" value="Minority" class="radioInput"/></label>


        </div>
        <div style="margin-top: 10px">
            <label><span>Extra Curricular Activity</span><input type="Checkbox" name="categoryCA"  value="true" class="checkInput" /></label>

            <label><span>Ward of GU Employee</span><input type="Checkbox" name="categoryGU" value="true"  class="checkInput" /></label>

            <label><span>Fringe Village</span><input type="Checkbox" name="categoryFV" value="true" class="checkInput" /></label>

            <label><span>Differently Abled</span><input type="Checkbox" name="categoryDA" value="true" class="checkInput" /></label>
            <label><span>NE Quota</span><input type="Checkbox" name="categoryNe" value="true" class="checkInput" /></label>
        </div>
    </td>
</tr>


<!----- Nationality ----------------------------------------------------------->
<tr>
    <td>Nationality <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Indian</span><input type="radio" name="nationality" value="Indian" class="radioInput"/></label>
            <label><span>Foreign Student</span><input type="radio" name="nationality" value="Non-Indian"   class="radioInput"/>
            </label>
            <label id="forForeignStudent" style="display: none;">
                <select name="Country" value="${studInstance?.nationalityName}" class="university-size-1-2" id="nonIndian">
                    <option value="">Select Country</option>
                    <option value="Afghanistan">Afghanistan</option>
                    <option value="Åland Islands" >Åland Islands</option>
                    <option value="Albania">Albania</option>
                    <option value="Algeria" >Algeria</option>
                    <option value="American Samoa" >American Samoa</option>
                    <option value="Andorra">Andorra</option>
                    <option value="Angola">Angola</option>
                    <option value="Anguilla">Anguilla</option>
                    <option value="Antarctica">Antarctica</option>
                    <option value="Antigua And Barbuda">Antigua And Barbuda</option>
                    <option value="Argentina">Argentina</option>
                    <option value="Armenia">Armenia</option>
                    <option value="Aruba">Aruba</option>
                    <option value="Australia">Australia</option>
                    <option value="Austria">Austria</option>
                    <option value="Azerbaijan">Azerbaijan</option>
                    <option value="Bahamas">Bahamas</option>
                    <option value="Bahrain">Bahrain</option>
                    <option value="Bangladesh">Bangladesh</option>
                    <option value="Barbados">Barbados</option>
                    <option value="Belarus">Belarus</option>
                    <option value="Belgium">Belgium</option>
                    <option value="Belize">Belize</option>
                    <option value="Benin">Benin</option>
                    <option value="Bermuda">Bermuda</option>
                    <option value="Bhutan">Bhutan</option>
                    <option value="Bolivia">Bolivia</option>
                    <option value="Bonaire, Sint Eustatius and Saba">Bonaire, Sint Eustatius and Saba</option>
                    <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
                    <option value="Botswana">Botswana</option>
                    <option value="Bouvet Island">Bouvet Island</option>
                    <option value="Brazil">Brazil</option>
                    <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
                    <option value="Brunei Darussalam">Brunei Darussalam</option>
                    <option value="Bulgaria">Bulgaria</option>
                    <option value="Burkina Faso">Burkina Faso</option>
                    <option value="Burundi">Burundi</option>
                    <option value="Cambodia">Cambodia</option>
                    <option value="Cameroon">Cameroon</option>
                    <option value="Canada">Canada</option>
                    <option value="Cape Verde">Cape Verde</option>
                    <option value="Cayman Islands">Cayman Islands</option>
                    <option value="Central African Republic">Central African Republic</option>
                    <option value="Chad">Chad</option>
                    <option value="Chile">Chile</option>
                    <option value="China" >China</option>
                    <option value="Christmas Island">Christmas Island</option>
                    <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
                    <option value="Colombia">Colombia</option>
                    <option value="Comoros">Comoros</option>
                    <option value="Congo" data-alternative-spellings="CG">Congo</option>
                    <option value="Congo, the Democratic Republic of the">Congo, the Democratic Republic of the</option>
                    <option value="Cook Islands">Cook Islands</option>
                    <option value="Costa Rica">Costa Rica</option>
                    <option value="Côte d'Ivoire">Côte d'Ivoire</option>
                    <option value="Croatia">Croatia</option>
                    <option value="Cuba">Cuba</option>
                    <option value="Curaçao">Curaçao</option>
                    <option value="Cyprus">Cyprus</option>
                    <option value="Czech Republic" data-alternative-spellings="CZ Česká Ceska">Czech Republic</option>
                    <option value="Denmark" data-alternative-spellings="DK Danmark" data-relevancy-booster="1.5">Denmark</option>
                    <option value="Djibouti" data-alternative-spellings="DJ جيبوتي‎ Jabuuti Gabuuti">Djibouti</option>
                    <option value="Dominica" data-alternative-spellings="DM Dominique" data-relevancy-booster="0.5">Dominica</option>
                    <option value="Dominican Republic" data-alternative-spellings="DO">Dominican Republic</option>
                    <option value="Ecuador" data-alternative-spellings="EC">Ecuador</option>
                    <option value="Egypt" data-alternative-spellings="EG" data-relevancy-booster="1.5">Egypt</option>
                    <option value="El Salvador" data-alternative-spellings="SV">El Salvador</option>
                    <option value="Equatorial Guinea" data-alternative-spellings="GQ">Equatorial Guinea</option>
                    <option value="Eritrea" data-alternative-spellings="ER إرتريا ኤርትራ">Eritrea</option>
                    <option value="Estonia" data-alternative-spellings="EE Eesti">Estonia</option>
                    <option value="Ethiopia" data-alternative-spellings="ET ኢትዮጵያ">Ethiopia</option>
                    <option value="Falkland Islands (Malvinas)" data-alternative-spellings="FK" data-relevancy-booster="0.5">Falkland Islands (Malvinas)</option>
                    <option value="Faroe Islands" data-alternative-spellings="FO Føroyar Færøerne" data-relevancy-booster="0.5">Faroe Islands</option>
                    <option value="Fiji" data-alternative-spellings="FJ Viti फ़िजी">Fiji</option>
                    <option value="Finland" data-alternative-spellings="FI Suomi">Finland</option>
                    <option value="France" data-alternative-spellings="FR République française" data-relevancy-booster="2.5">France</option>
                    <option value="French Guiana" data-alternative-spellings="GF">French Guiana</option>
                    <option value="French Polynesia" data-alternative-spellings="PF Polynésie française">French Polynesia</option>
                    <option value="French Southern Territories" data-alternative-spellings="TF">French Southern Territories</option>
                    <option value="Gabon" data-alternative-spellings="GA République Gabonaise">Gabon</option>
                    <option value="Gambia" data-alternative-spellings="GM">Gambia</option>
                    <option value="Georgia" data-alternative-spellings="GE საქართველო">Georgia</option>
                    <option value="Germany" data-alternative-spellings="DE Bundesrepublik Deutschland" data-relevancy-booster="3">Germany</option>
                    <option value="Ghana" data-alternative-spellings="GH">Ghana</option>
                    <option value="Gibraltar" data-alternative-spellings="GI" data-relevancy-booster="0.5">Gibraltar</option>
                    <option value="Greece" data-alternative-spellings="GR Ελλάδα" data-relevancy-booster="1.5">Greece</option>
                    <option value="Greenland" data-alternative-spellings="GL grønland" data-relevancy-booster="0.5">Greenland</option>
                    <option value="Grenada" data-alternative-spellings="GD">Grenada</option>
                    <option value="Guadeloupe" data-alternative-spellings="GP">Guadeloupe</option>
                    <option value="Guam" data-alternative-spellings="GU">Guam</option>
                    <option value="Guatemala" data-alternative-spellings="GT">Guatemala</option>
                    <option value="Guernsey" data-alternative-spellings="GG" data-relevancy-booster="0.5">Guernsey</option>
                    <option value="Guinea" data-alternative-spellings="GN">Guinea</option>
                    <option value="Guinea-Bissau" data-alternative-spellings="GW">Guinea-Bissau</option>
                    <option value="Guyana" data-alternative-spellings="GY">Guyana</option>
                    <option value="Haiti" data-alternative-spellings="HT">Haiti</option>
                    <option value="Heard Island and McDonald Islands" data-alternative-spellings="HM">Heard Island and McDonald Islands</option>
                    <option value="Holy See (Vatican City State)" data-alternative-spellings="VA" data-relevancy-booster="0.5">Holy See (Vatican City State)</option>
                    <option value="Honduras" data-alternative-spellings="HN">Honduras</option>
                    <option value="Hong Kong" data-alternative-spellings="HK 香港">Hong Kong</option>
                    <option value="Hungary" data-alternative-spellings="HU Magyarország">Hungary</option>
                    <option value="Iceland" data-alternative-spellings="IS Island">Iceland</option>
                    <option value="India" data-alternative-spellings="IN भारत गणराज्य Hindustan" data-relevancy-booster="3">India</option>
                    <option value="Indonesia" data-alternative-spellings="ID" data-relevancy-booster="2">Indonesia</option>
                    <option value="Iran, Islamic Republic of" data-alternative-spellings="IR ایران">Iran, Islamic Republic of</option>
                    <option value="Iraq" data-alternative-spellings="IQ العراق‎">Iraq</option>
                    <option value="Ireland" data-alternative-spellings="IE Éire" data-relevancy-booster="1.2">Ireland</option>
                    <option value="Isle of Man" data-alternative-spellings="IM" data-relevancy-booster="0.5">Isle of Man</option>
                    <option value="Israel" data-alternative-spellings="IL إسرائيل ישראל">Israel</option>
                    <option value="Italy" data-alternative-spellings="IT Italia" data-relevancy-booster="2">Italy</option>
                    <option value="Jamaica" data-alternative-spellings="JM">Jamaica</option>
                    <option value="Japan" data-alternative-spellings="JP Nippon Nihon 日本" data-relevancy-booster="2.5">Japan</option>
                    <option value="Jersey" data-alternative-spellings="JE" data-relevancy-booster="0.5">Jersey</option>
                    <option value="Jordan" data-alternative-spellings="JO الأردن">Jordan</option>
                    <option value="Kazakhstan" data-alternative-spellings="KZ Қазақстан Казахстан">Kazakhstan</option>
                    <option value="Kenya" data-alternative-spellings="KE">Kenya</option>
                    <option value="Kiribati" data-alternative-spellings="KI">Kiribati</option>
                    <option value="Korea, Democratic People's Republic of" data-alternative-spellings="KP North Korea">Korea, Democratic People's Republic of</option>
                    <option value="Korea, Republic of" data-alternative-spellings="KR South Korea" data-relevancy-booster="1.5">Korea, Republic of</option>
                    <option value="Kuwait" data-alternative-spellings="KW الكويت">Kuwait</option>
                    <option value="Kyrgyzstan" data-alternative-spellings="KG Кыргызстан">Kyrgyzstan</option>
                    <option value="Lao People's Democratic Republic" data-alternative-spellings="LA">Lao People's Democratic Republic</option>
                    <option value="Latvia" data-alternative-spellings="LV Latvija">Latvia</option>
                    <option value="Lebanon" data-alternative-spellings="LB لبنان">Lebanon</option>
                    <option value="Lesotho" data-alternative-spellings="LS">Lesotho</option>
                    <option value="Liberia" data-alternative-spellings="LR">Liberia</option>
                    <option value="Libyan Arab Jamahiriya" data-alternative-spellings="LY ليبيا">Libyan Arab Jamahiriya</option>
                    <option value="Liechtenstein" data-alternative-spellings="LI">Liechtenstein</option>
                    <option value="Lithuania" data-alternative-spellings="LT Lietuva">Lithuania</option>
                    <option value="Luxembourg" data-alternative-spellings="LU">Luxembourg</option>
                    <option value="Macao" data-alternative-spellings="MO">Macao</option>
                    <option value="Macedonia, The Former Yugoslav Republic Of" data-alternative-spellings="MK Македонија">Macedonia, The Former Yugoslav Republic Of</option>
                    <option value="Madagascar" data-alternative-spellings="MG Madagasikara">Madagascar</option>
                    <option value="Malawi" data-alternative-spellings="MW">Malawi</option>
                    <option value="Malaysia" data-alternative-spellings="MY">Malaysia</option>
                    <option value="Maldives" data-alternative-spellings="MV">Maldives</option>
                    <option value="Mali" data-alternative-spellings="ML">Mali</option>
                    <option value="Malta" data-alternative-spellings="MT">Malta</option>
                    <option value="Marshall Islands" data-alternative-spellings="MH" data-relevancy-booster="0.5">Marshall Islands</option>
                    <option value="Martinique" data-alternative-spellings="MQ">Martinique</option>
                    <option value="Mauritania" data-alternative-spellings="MR الموريتانية">Mauritania</option>
                    <option value="Mauritius" data-alternative-spellings="MU">Mauritius</option>
                    <option value="Mayotte" data-alternative-spellings="YT">Mayotte</option>
                    <option value="Mexico" data-alternative-spellings="MX Mexicanos" data-relevancy-booster="1.5">Mexico</option>
                    <option value="Micronesia, Federated States of" data-alternative-spellings="FM">Micronesia, Federated States of</option>
                    <option value="Moldova, Republic of" data-alternative-spellings="MD">Moldova, Republic of</option>
                    <option value="Monaco" data-alternative-spellings="MC">Monaco</option>
                    <option value="Mongolia" data-alternative-spellings="MN Mongγol ulus Монгол улс">Mongolia</option>
                    <option value="Montenegro" data-alternative-spellings="ME">Montenegro</option>
                    <option value="Montserrat" data-alternative-spellings="MS" data-relevancy-booster="0.5">Montserrat</option>
                    <option value="Morocco" data-alternative-spellings="MA المغرب">Morocco</option>
                    <option value="Mozambique" data-alternative-spellings="MZ Moçambique">Mozambique</option>
                    <option value="Myanmar" data-alternative-spellings="MM">Myanmar</option>
                    <option value="Namibia" data-alternative-spellings="NA Namibië">Namibia</option>
                    <option value="Nauru" data-alternative-spellings="NR Naoero" data-relevancy-booster="0.5">Nauru</option>
                    <option value="Nepal" data-alternative-spellings="NP नेपाल">Nepal</option>
                    <option value="Netherlands" data-alternative-spellings="NL Holland Nederland" data-relevancy-booster="1.5">Netherlands</option>
                    <option value="New Caledonia" data-alternative-spellings="NC" data-relevancy-booster="0.5">New Caledonia</option>
                    <option value="New Zealand" data-alternative-spellings="NZ Aotearoa">New Zealand</option>
                    <option value="Nicaragua" data-alternative-spellings="NI">Nicaragua</option>
                    <option value="Niger" data-alternative-spellings="NE Nijar">Niger</option>
                    <option value="Nigeria" data-alternative-spellings="NG Nijeriya Naíjíríà" data-relevancy-booster="1.5">Nigeria</option>
                    <option value="Niue" data-alternative-spellings="NU" data-relevancy-booster="0.5">Niue</option>
                    <option value="Norfolk Island" data-alternative-spellings="NF" data-relevancy-booster="0.5">Norfolk Island</option>
                    <option value="Northern Mariana Islands" data-alternative-spellings="MP" data-relevancy-booster="0.5">Northern Mariana Islands</option>
                    <option value="Norway" data-alternative-spellings="NO Norge Noreg" data-relevancy-booster="1.5">Norway</option>
                    <option value="Oman" data-alternative-spellings="OM عمان">Oman</option>
                    <option value="Pakistan" data-alternative-spellings="PK پاکستان" data-relevancy-booster="2">Pakistan</option>
                    <option value="Palau" data-alternative-spellings="PW" data-relevancy-booster="0.5">Palau</option>
                    <option value="Palestinian Territory, Occupied" data-alternative-spellings="PS فلسطين">Palestinian Territory, Occupied</option>
                    <option value="Panama" data-alternative-spellings="PA">Panama</option>
                    <option value="Papua New Guinea" data-alternative-spellings="PG">Papua New Guinea</option>
                    <option value="Paraguay" data-alternative-spellings="PY">Paraguay</option>
                    <option value="Peru" data-alternative-spellings="PE">Peru</option>
                    <option value="Philippines" data-alternative-spellings="PH Pilipinas" data-relevancy-booster="1.5">Philippines</option>
                    <option value="Pitcairn" data-alternative-spellings="PN" data-relevancy-booster="0.5">Pitcairn</option>
                    <option value="Poland" data-alternative-spellings="PL Polska" data-relevancy-booster="1.25">Poland</option>
                    <option value="Portugal" data-alternative-spellings="PT Portuguesa" data-relevancy-booster="1.5">Portugal</option>
                    <option value="Puerto Rico" data-alternative-spellings="PR">Puerto Rico</option>
                    <option value="Qatar" data-alternative-spellings="QA قطر">Qatar</option>
                    <option value="Réunion" data-alternative-spellings="RE Reunion">Réunion</option>
                    <option value="Romania" data-alternative-spellings="RO Rumania Roumania România">Romania</option>
                    <option value="Russian Federation" data-alternative-spellings="RU Rossiya Российская Россия" data-relevancy-booster="2.5">Russian Federation</option>
                    <option value="Rwanda" data-alternative-spellings="RW">Rwanda</option>
                    <option value="Saint Barthélemy" data-alternative-spellings="BL St. Barthelemy">Saint Barthélemy</option>
                    <option value="Saint Helena" data-alternative-spellings="SH St.">Saint Helena</option>
                    <option value="Saint Kitts and Nevis" data-alternative-spellings="KN St.">Saint Kitts and Nevis</option>
                    <option value="Saint Lucia" data-alternative-spellings="LC St.">Saint Lucia</option>
                    <option value="Saint Martin (French Part)" data-alternative-spellings="MF St.">Saint Martin (French Part)</option>
                    <option value="Saint Pierre and Miquelon" data-alternative-spellings="PM St.">Saint Pierre and Miquelon</option>
                    <option value="Saint Vincent and the Grenadines" data-alternative-spellings="VC St.">Saint Vincent and the Grenadines</option>
                    <option value="Samoa" data-alternative-spellings="WS">Samoa</option>
                    <option value="San Marino" data-alternative-spellings="SM RSM Repubblica">San Marino</option>
                    <option value="Sao Tome and Principe" data-alternative-spellings="ST">Sao Tome and Principe</option>
                    <option value="Saudi Arabia" data-alternative-spellings="SA السعودية">Saudi Arabia</option>
                    <option value="Senegal" data-alternative-spellings="SN Sénégal">Senegal</option>
                    <option value="Serbia" data-alternative-spellings="RS Србија Srbija">Serbia</option>
                    <option value="Seychelles" data-alternative-spellings="SC" data-relevancy-booster="0.5">Seychelles</option>
                    <option value="Sierra Leone" data-alternative-spellings="SL">Sierra Leone</option>
                    <option value="Singapore" data-alternative-spellings="SG Singapura  சிங்கப்பூர் குடியரசு 新加坡共和国">Singapore</option>
                    <option value="Sint Maarten (Dutch Part)" data-alternative-spellings="SX">Sint Maarten (Dutch Part)</option>
                    <option value="Slovakia" data-alternative-spellings="SK Slovenská Slovensko">Slovakia</option>
                    <option value="Slovenia" data-alternative-spellings="SI Slovenija">Slovenia</option>
                    <option value="Solomon Islands" data-alternative-spellings="SB">Solomon Islands</option>
                    <option value="Somalia" data-alternative-spellings="SO الصومال">Somalia</option>
                    <option value="South Africa" data-alternative-spellings="ZA RSA Suid-Afrika">South Africa</option>
                    <option value="South Georgia and the South Sandwich Islands" data-alternative-spellings="GS">South Georgia and the South Sandwich Islands</option>
                    <option value="South Sudan" data-alternative-spellings="SS">South Sudan</option>
                    <option value="Spain" data-alternative-spellings="ES España" data-relevancy-booster="2">Spain</option>
                    <option value="Sri Lanka" data-alternative-spellings="LK ශ්‍රී ලංකා இலங்கை Ceylon">Sri Lanka</option>
                    <option value="Sudan" data-alternative-spellings="SD السودان">Sudan</option>
                    <option value="Suriname" data-alternative-spellings="SR शर्नम् Sarnam Sranangron">Suriname</option>
                    <option value="Svalbard and Jan Mayen" data-alternative-spellings="SJ" data-relevancy-booster="0.5">Svalbard and Jan Mayen</option>
                    <option value="Swaziland" data-alternative-spellings="SZ weSwatini Swatini Ngwane">Swaziland</option>
                    <option value="Sweden" data-alternative-spellings="SE Sverige" data-relevancy-booster="1.5">Sweden</option>
                    <option value="Switzerland" data-alternative-spellings="CH Swiss Confederation Schweiz Suisse Svizzera Svizra" data-relevancy-booster="1.5">Switzerland</option>
                    <option value="Syrian Arab Republic" data-alternative-spellings="SY Syria سورية">Syrian Arab Republic</option>
                    <option value="Taiwan, Province of China" data-alternative-spellings="TW 台灣 臺灣">Taiwan, Province of China</option>
                    <option value="Tajikistan" data-alternative-spellings="TJ Тоҷикистон Toçikiston">Tajikistan</option>
                    <option value="Tanzania, United Republic of" data-alternative-spellings="TZ">Tanzania, United Republic of</option>
                    <option value="Thailand" data-alternative-spellings="TH ประเทศไทย Prathet Thai">Thailand</option>
                    <option value="Timor-Leste" data-alternative-spellings="TL">Timor-Leste</option>
                    <option value="Togo" data-alternative-spellings="TG Togolese">Togo</option>
                    <option value="Tokelau" data-alternative-spellings="TK" data-relevancy-booster="0.5">Tokelau</option>
                    <option value="Tonga" data-alternative-spellings="TO">Tonga</option>
                    <option value="Trinidad and Tobago" data-alternative-spellings="TT">Trinidad and Tobago</option>
                    <option value="Tunisia" data-alternative-spellings="TN تونس">Tunisia</option>
                    <option value="Turkey" data-alternative-spellings="TR Türkiye Turkiye">Turkey</option>
                    <option value="Turkmenistan" data-alternative-spellings="TM Türkmenistan">Turkmenistan</option>
                    <option value="Turks and Caicos Islands" data-alternative-spellings="TC" data-relevancy-booster="0.5">Turks and Caicos Islands</option>
                    <option value="Tuvalu" data-alternative-spellings="TV" data-relevancy-booster="0.5">Tuvalu</option>
                    <option value="Uganda" data-alternative-spellings="UG">Uganda</option>
                    <option value="Ukraine" data-alternative-spellings="UA Ukrayina Україна">Ukraine</option>
                    <option value="United Arab Emirates" data-alternative-spellings="AE UAE الإمارات">United Arab Emirates</option>
                    <option value="United Kingdom" data-alternative-spellings="GB Great Britain England UK Wales Scotland Northern Ireland" data-relevancy-booster="2.5">United Kingdom</option>
                    <option value="United States" data-relevancy-booster="3.5" data-alternative-spellings="US USA United States of America">United States</option>
                    <option value="United States Minor Outlying Islands" data-alternative-spellings="UM">United States Minor Outlying Islands</option>
                    <option value="Uruguay" data-alternative-spellings="UY">Uruguay</option>
                    <option value="Uzbekistan" data-alternative-spellings="UZ Ўзбекистон O'zbekstan O‘zbekiston">Uzbekistan</option>
                    <option value="Vanuatu" data-alternative-spellings="VU">Vanuatu</option>
                    <option value="Venezuela" data-alternative-spellings="VE">Venezuela</option>
                    <option value="Vietnam" data-alternative-spellings="VN Việt Nam" data-relevancy-booster="1.5">Vietnam</option>
                    <option value="Virgin Islands, British" data-alternative-spellings="VG" data-relevancy-booster="0.5">Virgin Islands, British</option>
                    <option value="Virgin Islands, U.S." data-alternative-spellings="VI" data-relevancy-booster="0.5">Virgin Islands, U.S.</option>
                    <option value="Wallis and Futuna" data-alternative-spellings="WF" data-relevancy-booster="0.5">Wallis and Futuna</option>
                    <option value="Western Sahara" data-alternative-spellings="EH لصحراء الغربية">Western Sahara</option>
                    <option value="Yemen" data-alternative-spellings="YE اليمن">Yemen</option>
                    <option value="Zambia" data-alternative-spellings="ZM">Zambia</option>
                    <option value="Zimbabwe" data-alternative-spellings="ZW">Zimbabwe</option>
                </select>
            </label>
        </div>
    </td>
</tr>

<!----- Gender ----------------------------------------------------------->
<tr>
    <td>Gender <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Male</span><input type="radio" name="gender" value="Male" class="radioInput"/></label>
            <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/></label>
            %{--<label><span>Other</span><input type="radio" name="gender" value="Other" class="radioInput"/></label>--}%

        </div>
    </td>
</tr>
<!----- State of Domicile ----------------------------------------------------------->
<tr>
    <td>State of Domicile <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Assam</span><input type="radio" name="state" value="Assam" class="radioInput"/></label>
            <label><span>Others</span><input type="radio" name="state" value="Others" class="radioInput"/></label>
        </div>
    </td>
</tr>

<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Mobile Number <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" id="mobileNoCntryCode" name="mobileNoCntryCode" maxlength="3" value="+91" readonly> - <input
            type="text" id="mobileNo" name="mobileNo" maxlength="10" value="${studInstance?.mobileNo}"
            onkeypress="return isNumber(event)"/>
    </td>
</tr>
<!----- Blood Group ----------------------------------------------------------->
<tr>
    <td>Email<span class="university-obligatory">*</span></td>
    <td>
        <input type="text" id="email" name="email" class="university-size-1-2" maxlength="50" value="${studInstance?.email}"/>
    </td>
</tr>
<tr>
    <td>Blood Group <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" id="bloodGroup" name="bloodGroup" class="university-size-1-2" maxlength="3" value="${studInstance?.bloodGroup}"/>
    </td>
</tr>
<!----- Address ---------------------------------------------------------->
<tr>
    <!-----Father's Name---------------------------------------------------------->
    <td>Father's Name<span class="university-obligatory">*</span></td>
    <td><input type="text" id="fatherName" class="university-size-1-2" name="fatherName" maxlength="100"
               value="${studInstance?.fatherName}"
               onkeypress="return onlyAlphabets(event);"/>
    </td>
</tr>
<tr>
    <!----- Mother's Name ---------------------------------------------------------->
    <td>Mother's Name<span class="university-obligatory">*</span></td>
    <td><input type="text" id="motherName" class="university-size-1-2" name="motherName" maxlength="100"
               value="${studInstance?.motherName}"
               onkeypress="return onlyAlphabets(event);"/>
    </td>
</tr>
<tr>
    <!----- Guardian's Name ---------------------------------------------------------->
    <td>Guardian's Name<span class="university-obligatory">*</span></td>
    <td><input type="text" id="guardianName" class="university-size-1-2" name="guardianName" maxlength="100"
               value="${studInstance?.guardianName}"
               onkeypress="return onlyAlphabets(event);"/>
    </td>
</tr>
<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Guardian's Phone Number (Both Number Mandatory)<span class="university-obligatory">*</span></td>
    <td>
        <label> (1)  </label><input type="text" id="guardianCntryCode1" name="guardianCntryCode1" style="width: 30px;" maxlength="3" value="+91" readonly> - <input
            type="text" id="guardianMobileNo1" name="guardianMobileNo1" maxlength="10" value="${studInstance?.guardianMobileNo1}"
            onkeypress="return isNumber(event)"/>

        <label> (2)  </label><input type="text" id="guardianCntryCode2" name="guardianCntryCode2" style="width: 30px;" maxlength="3" value="+91" readonly> - <input
            type="text" id="guardianMobileNo2" name="guardianMobileNo2" maxlength="10" value="${studInstance?.guardianMobileNo2}"
            onkeypress="return isNumber(event)"/>
    </td>
</tr>
<tr>
    <td>Address<span class="university-obligatory">*</span><br/><br/><br/></td>
    <td>
        <table style="width: 100%" id="examCenterAddress">
            <tr>
                <td class="university-size-1-2"> Permanent Address</td>
                <td class="university-size-1-2"> <div>Mailing Address</div>
                <div style="margin-top: 10px;">
                    <label>
                        <input type="checkbox" id="sameAsPermanent" onchange="copyAllAdress()">
                        Same As Permanent Adress
                    </label>
                </div>
                </td>
            </tr>
            <tr>
                <td><g:textArea class="university-size-1-1" value="${studInstance?.permanent_Address}" rows="5" id="permanent_Address" name="permanent_Address"  placeholder="Address"></g:textArea> </td>
                <td><g:textArea class="university-size-1-1" value="${studInstance?.mailing_Address}" rows="5" name="mailing_Address" id="mailing_Address" placeholder="Address"></g:textArea> </td>
            </tr>

            <tr>
                <td>
                    <input type="text" name="permanentAddressDistrict" placeholder="District" onkeypress="return onlyAlphabets(event);" value="${studInstance?.permanentAddressDistrict}" id="permanentAddressDistrict" style="width: 44%"/>
                    <input type="text" name="permanentStateAddress" placeholder="State" onkeypress="return onlyAlphabets(event);"  value="${studInstance?.permanentStateAddress}" maxlength="30" id="permanentStateAddress" style="width: 44%"/>
                </td>

                <td>
                    <input type="text" name="mailingAddressDistrict" placeholder="District" onkeypress="return onlyAlphabets(event);" value="${studInstance?.mailingAddressDistrict}" id="mailingAddressDistrict" style="width: 44%"/>
                    <input type="text" name="mailingStateAddress" placeholder="State" onkeypress="return onlyAlphabets(event);" value="${studInstance?.mailingStateAddress}" maxlength="30" id="mailingStateAddress" style="width: 44%"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="permanentPincode" id="permanentPincode"  value="${studInstance?.permanentPincode}" placeholder="Pincode" maxlength="6" style="width: 36%" onkeypress="return isNumber(event)"/>
                    <input type="text" id="permanentCntryCode" name="permanentCntryCode" style="width: 25px" maxlength="3" value="+91" readonly> - <input
                        type="text" id="permanentMobileNo" name="permanentMobileNo" value="${studInstance?.permanentMobileNo}" placeholder="Phone No" maxlength="10" style="width: 40%"  onkeypress="return isNumber(event)"/>
                </td>
                <td>
                    <input type="text" name="mailingPincode" id="mailingPincode" value="${studInstance?.mailingPincode}"placeholder="Pincode" maxlength="6" style="width: 36%" onkeypress="return isNumber(event)"/>
                    <input type="text" id="mailingCntryCode" name="mailingCntryCode" style="width: 25px" maxlength="3" value="+91" readonly> - <input
                        type="text" id="mailingMobileNo" name="mailingMobileNo" value="${studInstance?.mailingMobileNo}" placeholder="Phone No" maxlength="10" style="width: 40%"  onkeypress="return isNumber(event)"/>
                </td>

            </tr>
        </table>
    </td>
</tr>
<tr>
    <td colspan="2">
        <table>
            <tr>
                <th style="width: 15%;">Examination</th>
                <th style="width: 35%;"> Board/University</th>
                <th style="width: 25%;">Subjects</th>
                <th style="width: 9%;">Year</th>
                <th style="width: 8%;">Division</th>
                <th style="width: 8%;">Percentage/CGPI</th>
            </tr>
            <tr>
                <td style="text-align: left;">10th</td>
                <td><input type="text" id="School10th" name="School10th" value="${studInstance?.school10th}"  maxlength="200"></td>
                <td><input type="text" id="Subject10th" name="Subject10th" value="${studInstance?.subject10th}" maxlength="200"></td>
                <td><input type="text" id="Year10th" name="Year10th" value="${studInstance?.year10th}" maxlength="4"></td>
                <td><input type="text" id="Div10th" name="Div10th" value="${studInstance?.div10th}" maxlength="200"></td>
                <td><input type="text" id="Percntge10th" name="Percntge10th"  value="${studInstance?.percntge10th}" maxlength="5"></td>
            </tr>
            <tr>
                <td style="text-align: left;">12th</td>
                <td><input type="text" id="School12th" onchange="checkQualification()" name="School12th" value="${studInstance?.school12th}" maxlength="200"></td>
                <td><input type="text" id="Subject12th" onchange="checkQualification()"  name="Subject12th" value="${studInstance?.subject12th}" maxlength="200"></td>
                <td><input type="text" id="Year12th" onchange="checkQualification()"  name="Year12th" value="${studInstance?.year12th}" maxlength="4"></td>
                <td><input type="text" id="Div12th" onchange="checkQualification()"  name="Div12th" value="${studInstance?.div12th}" maxlength="200"></td>
                <td><input type="text" id="Percntge12th"  onchange="checkQualification()"  name="Percntge12th" value="${studInstance?.percntge12th}" maxlength="5"></td>
            </tr><tr>
                <td style="text-align: left;">Diploma <label style="font-size: 9px;">(for Lateral Entry)</label></td>
                <td><input type="text" id="diplomaSchool" onchange="checkQualification()"  name="diplomaSchool" value="${studInstance?.diplomaSchool}" maxlength="200"></td>
                <td><input type="text" id="diplomaSubject" onchange="checkQualification()"  name="diplomaSubject" value="${studInstance?.diplomaSubject}"  maxlength="200"></td>
                <td><input type="text" id="diplomaYear" onchange="checkQualification()"  name="diplomaYear" value="${studInstance?.diplomaYear}" maxlength="4"></td>
                <td><input type="text" id="diplomaDiv" onchange="checkQualification()"  name="diplomaDiv" value="${studInstance?.diplomaDiv}" maxlength="200"></td>
                <td><input type="text" id="diplomaPercntge" onchange="checkQualification()"  name="diplomaPercntge" value="${studInstance?.diplomaPercntge}" maxlength="5"></td>
            </tr>
            <tr>
                <td style="text-align: left;">Degree</td>
                <td><input type="text" id="degreeSchool" name="degreeSchool" value="${studInstance?.degreeSchool}" maxlength="200"></td>
                <td><input type="text" id="degreeSubject" name="degreeSubject" value="${studInstance?.degreeSubject}"  maxlength="200"></td>
                <td><input type="text" id="degreeYear" name="degreeYear" value="${studInstance?.degreeYear}" maxlength="4"></td>
                <td><input type="text" id="degreeDiv" name="degreeDiv" value="${studInstance?.degreeDiv}"  maxlength="200"></td>
                <td><input type="text" id="degreePercntge" name="degreePercntge" value="${studInstance?.degreePercntge}" maxlength="5"></td>
            </tr>
            <tr>
                <td style="text-align: left;">Other</td>
                <td><input type="text" id="otherSchool" name="otherSchool" value="${studInstance?.otherSchool}" maxlength="200"></td>
                <td><input type="text" id="otherSubject" name="otherSubject" value="${studInstance?.otherSubject}" maxlength="200"></td>
                <td><input type="text" id="otherYear" name="otherYear"value="${studInstance?.otherYear}" maxlength="4"></td>
                <td><input type="text" id="otherDiv" name="otherDiv" value="${studInstance?.otherDiv}" maxlength="200"></td>
                <td><input type="text" id="otherPercntge" name="otherPercntge" value="${studInstance?.otherPercntge}" maxlength="5"></td>
            </tr>
            <tr>
                <td style="text-align: left;" colspan="2">GATE/JRF Details ( For MS/MTech only)</td>
                <td><input type="text" id="gateSubject" name="gateSubject" value="${studInstance?.gateSubject}" maxlength="200"></td>
                <td><input type="text" id="gateYear" name="gateYear" value="${studInstance?.gateYear}" maxlength="4"></td>
                <td><input type="text" id="gateDiv" name="gateDiv" value="${studInstance?.gateDiv}" maxlength="200"></td>
                <td><input type="text" id="gatePercntge" name="gatePercntge" value="${studInstance?.gatePercntge}" maxlength="5"></td>
            </tr>
        </table>

    </td>
</tr>
<tr>
    <td style="text-align: left;">Professional Experiance Details ( For MTech PartTime only)</td>
    <td ><g:textArea name="expDetails" id="expDetails"  value ="${studInstance?.expDetails}" rows="4" class="university-size-1-2"></g:textArea></td>
</tr>
<tr>
    <td>CEE Score</td>
    <td><input type="text" name="ceeScore" value ="${studInstance?.ceeScore}" maxlength="3" class="university-size-1-2" onkeypress="return isNumber(event)" id="ceeScore"></td>
</tr>

<sec:ifLoggedIn>
    <tr>
        <td>
            Upload recent Passport size Photograph (Size: Less then 50KB )
        </td>
        <td>
            <g:if test="${studInstance}">
                <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id
                        , mime: 'image/jpeg')}" class="university-registration-photo" name ="picture1" id="picture1"/>
                <input type='file' id="profileImage" onchange="readURL(this, 'picture1');" class="university-button"
                       name="photograph"/>
            </g:if>
            <g:else>
                <div id="profile-image" class='registration-image-div'><img src="" alt="Space for Photograph "
                class="university-registration-photo"  name ="picture" id="picture"/></div>
                <input type='file' id="profileImage" onchange="readURL(this, 'picture');" class="university-button"
                       name="photograph"/>
                <input type="text" id="imageValidate" name="imageValidate" value="" style="width: 1px;height: 1px;border: 0px;"/>            </g:else>

        </td>
    </tr>
</sec:ifLoggedIn>

<sec:ifAnyGranted roles="ROLE_STUDENT">
<g:if test="${!studInstance}">
<tr>
    <td colspan="2">
        <label id="declaration-label"><input type="checkbox" id="declaration" name="declaration"/>
            I hereby declare that the information as indicated above is true to the best of my knowledge. <span
                class="university-obligatory">*</span>
        </label>
    </td>
</tr>
</g:if>
<tr>
    <td colspan="2" align="center">
        <input type="Submit" value="Submit" disabled id="submitButton" onclick="validate()" class="university-button">
        <input type="reset" value="Reset" onclick="resetImage()" class="university-button">
    </td>
</tr>
</sec:ifAnyGranted>
<g:if test="${params.update}">
<tr>
    <td colspan="2" align="center">
        <input type="Submit" value="Submit" onclick="validate()" class="university-button">
        %{--<input type="reset" value="Reset" onclick="resetImage()" class="university-button">--}%
    </td>
</tr>
</g:if>
</table>

</g:uploadForm>
<sec:ifAnyGranted roles="ROLE_HOD,ROLE_FACULTYADVISOR,ROLE_ADMIN">
%{--for Approval By HOD,FA and admin--}%
    <g:if test="${params.view==null}" >
        <g:if test="${!params.update}">
   <table> <tr>
        <td colspan="2" align="center">
            <g:link controller="hod" action="approveStudent" params="[studentID:studInstance.id]"><button class="university-button">Approve</button></g:link>
            <button class="university-button" onclick="visText()">Reject</button>
            %{--<g:link controller="hod" action="rejectStudent" params="[studentID:studInstance.id]"><button class="university-button">Reject</button></g:link>--}%

        </td>
    </tr></table>
        </g:if>
    </g:if>
</sec:ifAnyGranted>
</fieldset>
<table  name="rejectionFormTable" style="display:none;" id="rejectionFormTable">
    <g:form controller="hod" action="rejectStudent">
    %{--for rejecting student registration(with reason for rejection)--}%
        <tr>
            <td>
                <g:hiddenField name="studentId" value="${studInstance?.id}"/>
                %{--<label for="reasonForRejection">Enter Reason</label>--}%
                <textArea id="reasonForRejection" rows="4" cols="32" name="reasonForRejection"  placeholder="Reason for rejection"></textArea>


            </td>
            <td>
                <g:submitButton name="Reject">Reject</g:submitButton>
            </td>
        </tr>
    </g:form>
</table>
</g:else>
%{--</g:else>--}%
</div>
<script>
    $('#signatureFile').bind('change', function () {
//    alert('This file size is: ' + this.files[0].size/1024/1024 + "MB");
    })
    function resetImage() {
        $("#signature").attr('src', '#')
        $("#picture").attr('src', '#')
    }
    function visText(){
        $("#rejectionFormTable").show();
    }
    $(function () {
        $(function () {
            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $("#paymentDate").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
    });
    $('#submitButton').on('click', function () {
        if ($('#studentRegister').valid()) {
            setTimeout(function () {
                $('#studentRegister')[0].reset();
                var abc = $('#picture2').remove();
                $('#profile-image').append('<img src="" alt="Space for Photograph " class="university-registration-photo" id="picture2"/>')
//                  location.reload()
            }, 1000)

        }
    })
</script>
</body>
</html>