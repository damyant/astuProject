<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/15/2014
  Time: 5:27 PM
--%>

<%@ page import="examinationproject.ProgramType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    <style type="text/css">
    @page {
        size: 210mm 297mm;
    }

    div.break {
        page-break-after: always;
    }
    table {
        border-collapse: collapse;
    }

    </style>
</head>
<body>
<g:set var="counter" value='0' />
<g:each in="${studentInstance}" var="student" status="index">

    <div id="viewAdmit">
        <div>
            <div style="display: inline;width: 100%; ">
                <label style="float: left;font-size: 12px;">Assam Science and Technology University</label>
            </div><br/>
            <div class="university-clear-both"></div>
            <div style="width:100%;text-align: center;text-transform: uppercase;display: block;">
                <rendering:inlineJpeg bytes="${guLogo}" class="university-registration-photo" style="margin:auto; width: 70px;"/>
                <div>
                    <span style="font-family: Calibri;font-size: 18px;font-style: normal;font-weight: bolder;text-align: center;margin-top: 15px; text-transform: uppercase;">
                        Assam Science and Technology University
                    </span>
                </div>
                <span style="font-family: Arial Black;font-size: 15px; font-weight:bolder; color: #ffffff; background-color: #000;margin-top: 20px;
                text-align: center; padding: 2px 5px; text-transform: uppercase;">Admit</span>
                %{--<div style="font-family: Arial Black;font-size: 10px;margin-top: 5px;">Online Copy</div>--}%
            </div>
            <div style="width: 100%;text-transform: capitalize;font-size: 13px;line-height: 18px;">
                %{--<div style="width: 100%;height: 140px;"></div>--}%
                <table style="width: 100%;">
                    <tr>
                        <td style="width: 80%;">
                            <div style="width: 100%;display: inline;">
                                <div style="width: 25%;float: left;font-size: 11px;"><b>Name of the Candidate:</b></div>
                                <strong>
                                    <div style="width: 70%;float: right; text-transform: capitalize;" id="studentName">${student?.firstName} ${student?.middleName} ${student?.lastName}</div>
                                </strong>
                            </div>
                        </td>
                        <td rowspan="4" style="width: 20%;">
                            <div id="profile-image" style="float: right;">
                                <g:if test="${student?.getStudentImage()}">
                                    <rendering:inlineJpeg bytes="${student?.getStudentImage()}" style="margin:auto; height: 150px;width: 117px;border: 1px solid black;    display: block;
                                                  background-position: bottom; background-size: 100%; text-align: center;"/>
                                </g:if>
                            %{--<g:if test="${student?.imageFileName}">--}%
                            %{--<rendering:inlineJpeg bytes="${pictureList[index]}" class="university-registration-photo" style="margin:auto; width: 90px;"/>--}%
                            %{--</g:if>--}%
                                <g:else>
                                    <div style="margin:auto; height: 120px;width:110px;text-align: center ; vertical-align:middle;border: 1px solid;">
                                        <div style="margin: 20px 0px;">
                                            Affix Passport Size Photo
                                        </div>
                                    </div>
                                </g:else>
                            </div></td>
                    </tr>
                    <tr>
                        <td style="width: 80%;">
                            <div style="width: 100%;display: inline;">
                                <div style="width: 25%;float: left;font-size: 11px;"><b>Roll Number:</b></div>
                                <strong>
                                    <div style="width: 70%;float: right;" id="rollNo">${student.rollNo}</div>
                                </strong>
                            </div>
                        </td>
                        <td >
                        </td>
                    </tr>
                <tr>
                    <td style="width: 80%;">
                        <div style="width: 100%;display: inline;">
                            <div style="width: 25%;float: left;font-size: 11px;"><b>Program</b></div>
                            <strong>
                                <div style="width: 70%;float: right;" id="program">${student.program.courseName}</div>
                            </strong>
                        </div>
                    </td>
                    <td >
                    </td>
                </tr>
                <tr>
                    <td style="width: 80%;">
                        <div style="width: 100%;display: inline;">
                            <div style="width: 25%;float: left;font-size: 11px;"><b>Semester</b></div>
                            <strong>
                                <div style="width: 70%;float: right;" id="semester">${student.semester}</div>
                            </strong>
                        </div>
                    </td>
                    <td >
                    </td>
                </tr>
                    <tr>
                        <td colspan="2">
                            <table cellpadding="0px"
                                   style="border: 0px solid black;width: 80%;margin: auto;text-align: center;">
                                <tr>
                                    <g:each in="${0..dateCount[index] - 1}" var="i">
                                        <g:if test="${i > 0}">
                                            <td style="border: 1px solid black;">${examCourseList[index][i]}</td>
                                        </g:if>
                                        <g:else>
                                            <td style="border: 1px solid black;">${examCourseList[index][i]}</td>
                                        </g:else>
                                    </g:each>
                                </tr>
                                <tr>
                                    <g:each in="${0..dateCount[index] - 1}" var="i">
                                        <g:if test="${i > 0}">
                                            <td style="border: 1px solid black;"><g:formatDate format="dd-MMM-yyyy"
                                                                                               date="${examDate[index][i]}"/></td>
                                        </g:if>
                                        <g:else>
                                            <td style="border: 1px solid black;">${examDate[index][i]}</td>
                                        </g:else>
                                    </g:each>
                                </tr>
                                <tr>
                                    <g:each in="${0..dateCount[index] - 1}" var="i">
                                        <td style="border: 1px solid black;">${examTime[index][i]}</td>
                                    </g:each>
                                </tr>
                            </table>
                        </td>
                    </tr>

                <tr>
                    <td colspan="2" style="">
                        <table style="width: 100%">
                            <tr>
                                <td style="width: 50%;">
                            <g:if test="${admitInst?.signatureImg}">
                                <rendering:inlineJpeg bytes="${admitInst.getSignatureImg()}"
                                                      style="margin:auto; width: 150px; height: 70px"/>
                            </g:if>
                            <g:else>
                                <br/><br/>
                            </g:else>
                            <div style="width: 100%;display: inline-block;font-size:13px;">
                                Officer- in-charge
                            </div>
                        </td>
                            <td style="width: 50%; vertical-align: bottom">
                            <div style="width: 100%;display: inline-block;font-size:13px;text-align: right;vertical-align: bottom;">
                                %{--<div>Sd/- D. J. Choudhury</div>--}%
                                <div>Controller of Examinations</div>

                                <div>Assam Science and Technology University</div>
                            </div>
                        </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>
                %{--<div style="width: 100%;height: 550px; border: 1px;"></div>--}%
            </div>
        </div>
    </div>
    <hr/>
    <div style="border: 1px solid #000000; width: 90%;margin:auto;font-size: 9px;text-align: justify;padding: 10px;">
        <label style="font-size: 11px;">RULES FOR THE GUIDANCE OF CANDIDATES</label>
        <ol>
            <li> The examination will commence on the date according to the Programme previously notified.</li>
            <li> The doors of the Examination Hall will be opened on the morning of the first day; one hour, and in the afternoon and on other days, 12 minutes earlier than the time appointed
            for the commencement of the examination. The doors will be closed in each day 5 minutes before the commencement of the Examination, after which no candidate will
            be admitted without the special permission of the Officer-in-Charge. In no case will a candidate be admitted or given a question paper more than 15 minutes after the
            examination has commenced.</li>
            <li> Candidate must take their seats at least 5 minutes before the time appointed for the commencement of the examination.</li>
            <li> No candidate will be allowed to leave the Examination Hall until an hour has elapssed from the time when the papers are given out. Except as hereinafter provided, no candidate will
            be allowed to re-enter the Examination Hall during the hours of examination, after once leaving it, not to leave the Hall without giving up his answer-paper.
            A candidate may with the special permission of the Officer-in-Charge, leave the examination Hall temporarily for a necessary purpose, but or.iy under the survelliance of
            a trust-worthy person to be deputed by the Officer-in-Charge under proper safeguards to render recourse to unfair practices impossible.
            A candidate having completed his answer-paper must hand it over even if blank, to an invigilator before leaving the Examination Hall. The answer-paper must on no account
            be left on the desk. No candidate will be allowed to remain in the Examination Hall after the close of the examination, except to allow his answer-paper to be collected by the</li>
            <li> Candidates are required to provide themselves with their own pens and pencils. They are permitted to use fountain pens used with their own ink. They are also required to provide
            themselves with hard pencils, dividers, pencil compasses and a straight ruler showing centimeters and inches or other drawing implements when necessary - for examination in
            particular subjects like Geometry and Geography. They may also provide themselves with protractors and set squares for similar purposes Squares papers for answering questions
            on Graphs will be supplied by the University.The University will supply blotting paper ink (except for fountain pens) and stitched books
            in which to write the answers. On no account should any paper be torn from the answer-book.</li>
            <li> Each candidate shall write on the cover of the answer book, only his University Registration Number, Roll Number. But not his name or the name, of his College.</li>
            <li> Candidates are forbidden to carry into the Examination Hall or have in their possession while under examination, any book. note paper; writing; scribbling or other materials
            except their Admit Cards; University Registration Receipts and any other writing requisites or drawing implements. Any article carried into the Examination Hall or found in
            the possession of a candidate in contravention of this rule shall be liable to be seized by the Officer-in-Charge and the candidate shall be liable to expulsion</li>
            <li> A candidate while under examination shall not help or try to help any other candidate, nor obtain or try to obtain any help from any other candidate or other person. Communication
            of any sort or in any form is strictly forbidden between a candidate and any other person whether inside or outside the Examination Hall.
            A candidate requiring an additional answer book or the supply of ink or blotting papers or desiring permission to leave the room for a necessary purpose or desiring to give up his
            answer paper, may call the attention of the Invigilator by rising in his seat and without making any noise or disturbance. On no account is a candidate permitted to speak to an
            Invigilator on any matter with reference to any question or answer</li>
            <li> Candidates must not write any objectionable or Improper remark in their answer-papers or attempt in any way to under Identification of the answer-papers impossible by giving
            false names or numbers. or intentionally omitting to state the correct names or numbers. Candidates must not write anything on any question-paper or blotting paper 0' other
            paper, or carry away any writing or scribing from the Examination Hall.</li>
            <li>Candidates are required to produce their Admit Cards and Registration Receipts to sign their names as and when directed by the Officer-inCharge.</li>
            <li>Candidates are required to observe strictly the rules laid down by the University for conduct of examinations and the guidance of candidates.</li>
            <li>Candidates are warned that any attempt to use any unfair means at the examination, or any breach or attempted breach of these or other examination rules, will render them
            liable to expulsion by the Officer-in-Charge from the examination as any part thereof, and to such further penalties as Executive Council may determine.</li>
            <li>Not withstanding the issue of the Admit Card the Executive Council shall have the right, for any reason which may appear to them sufficient to cancel the admission of any candidate
            to any examination whether before, during or after the examination. The Executive Council may also debar a candidate from appearing at any subsequent
            University examination or examinations. The decision of the Executive Council in all such cases shall be final.</li>
        </ol>
    </div>


    <g:if test="${index != studentInstance.size()-1}">
<div class="break"></div>
</g:if>

</g:each>
</body>
</html>