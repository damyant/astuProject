<%--
  Created by Damyant Software Pvt Ltd.
  User: IDOL_2
  Date: 7/4/14
  Time: 5:49 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='postExamination.js'/>
    <title></title>
</head>
<body>
<div id="main">
    <fieldset class="form">
        <sec:ifNotLoggedIn>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <script type="text/javascript">

                $(document).ready(function () {
                    loadHomePageMessage()
                })
            </script>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <g:if test="${flash.warning}">
                <div class="warning"><div class="university-status-message">${flash.warning}</div></div>
            </g:if>
            <table class="inner university-size-full-1-1" style="min-height: 410px; margin: auto;">
                <tr>
                    <td class="university-size-2-3" style="vertical-align: middle;box-shadow: 5px 0 5px -5px #aaaaaa;border: 0px;">
                        <table class="university-size-full-1-1 inner" cellspacing="10"
                               style="height: 408px; margin: auto;">
                            <tr>
                                <td class="university-size-1-3" style="vertical-align: top;box-shadow: 0 5px 5px -5px ;">
                                    <h4 style="line-height: 24px;font-size: 18px;margin: 4px auto;">GUIST Academic Automation System is a collaborative educational resource management portal, which allows activities such as-</h4>
                                    <table class="university-size-full-1-1 inner" cellspacing="1" style="margin: auto;">
                                        <tr>
                                            <td style="width: 40%">

                                                <ul style="text-align: left; line-height: 20px;"><li>Student enrollment</li>

                                                    <li>Semester registration</li>

                                                    <li>Fee management</li>

                                                    <li>Marks and transcript generation</li>

                                                    <li>Employee record administration</li>

                                                    <li>Lab equipment administration</li>

                                                    <li>Library  catalog management</li>

                                                    <li>Notice board access</li>

                                                    <li>Report management</li>
                                                </ul>
                                            </td>
                                            <td style="width: 50%; text-align: center;vertical-align: top;">
                                                <img src="${resource(dir: 'images', file: 'symphonie_Header.png')}"
                                                     style="width: 100%;margin: 20px 0px;"/>
                                            </td>
                                        </tr>
                                    </table>

                                </td>
                            </tr>
                            <tr>
                                <td class="university-size-2-3"
                                    style="vertical-align: middle;line-height: 20px;padding: 10px 20px;text-align: justify;border-radius: 5px;">
                                    <p>The Gauhati University Institute of Science and Technology (GU IST) was set up at the university campus in 2009 to enhance quality and innovation in Technology and Applied science education. The Programmes currently being offered by the Institute are the  four-year Bachelor of Science (B.S.) and Bachelor of Technology (B.Tech.) Programmes, the two-year M.S. and M.Tech. Programmes, and Ph.D. programs in different disciplines.</p>

                                    <p>The BS programme has been designed in parallel to the 4-year B.Tech. programmes. The BS and BTech programme curriculum's have been developed with a holistic approach of integrating Science, Technology, Humanities, and Management courses together to create human resources fit for the current society. There is also a provision to switch from a B.Tech. Programme to a B.S. Programme under certain conditions. First two semesters of BS and B.Tech. programmes are with all common courses, and in next 6 semesters also they have to undergo selected common Humanities, Management and Technological courses. Important common courses that all BS and B.Tech. students have to undertake includes Engineering Workshop, Engineering Drawing, Environmental Studies, Basic Electronics, Computer Programming, Management, Humanities electives etc.</p>

                                    <p>The Council of Scientific and Industrial Research (CSIR), Govt. of India has revised the eligibility criteria for the National Eligibility Test, conducted for Junior Research Fellowship (JRF), through a notification on 19th March, 2012. As per this notification, from the year 2012 onwards, the 4-years BS Degree has been included as an Educational Eligibility criteria for appearing NET for JRF conducted by CSIR. The first batch of BS students who are in their final year has already appeared the CSIR JRF examination last December.</p>

                                    <p>The progression to higher studies and research in Science and Technology are being facilitated by the two in house M.Tech. Programmes. M.Tech. in Electronics and Communication Technology (ECT) was introduced in 2009 and M.Tech. in Information Technology (IT) was introduced in the year 2010. MS programmes in Physical Science, Chemical Science, Mathematical Science are being offered from the academic session 2013-14 to facilitate continuation of higher studies to the first batch of BS students who are graduating in June, 2013.</p>

                                    <div style="width: 70%;float: right; text-align: left;padding: 10px 20px;">
                                        <div>Director In charge :     Professor Shikhar Kr. Sarma</div>

                                        <div>Address and contact :    Gauhati University Institute of Science and Technology
                                        (Old BT Hostel Premises)</div>

                                        <div>Guwahati 781014, Assam, India</div>

                                        <div>Phone +91-3612672233</div></div>
                                </td>
                            </tr>

                        </table>
                    </td>

                    <td class="university-size-1-3" style="vertical-align: top;">
                        <table class="university-size-full-1-1" cellspacing="10" style="height:408px;margin: auto;">
                            <tr>
                                <th id="noticeHeader" style="">
                                    Notice Board
                                </th>
                            </tr>
                            <tr>
                                <td id="noticeBoardView">
                                    <g:if test="${noticeList}">
                                        <marquee behavior="scroll" vspace="5" onmouseover="this.stop();"
                                                 onmouseout="this.start();" scrolldelay="0" scrollamount="3"
                                                 direction="up"
                                                 style="height: 100%; margin: auto;">
                                            <div id="noticeBoard" class="university-size-full-1-1"
                                                 style="height: 100%; margin: auto;">
                                                <ul>
                                                    <g:each in="${noticeList}" var="notice" status="index">

                                                        <div style="text-align: left; text-transform: capitalize;margin: 10px auto;">
                                                            <li class="noticeBoardDesc">
                                                                <g:link controller="admin" action="download"
                                                                        params="[fileName: notice.fileName]"
                                                                        target="_blank"><label>${notice.noticeHeader}</label></g:link>
                                                            </li>
                                                        </div>

                                                    </g:each>
                                                </ul>
                                            </div>
                                        </marquee>
                                    </g:if>
                                    <g:else>
                                        <h5 style="text-align: center;">No Notice Available.</h5>
                                    </g:else>
                                </td>
                            </tr>

                        </table>

                    </td>
                </tr>
            </table>
        </sec:ifLoggedIn>
    </fieldset>
    <div id="dialog" title="Message" style="display: none;"></div>
</div>
</body>

</html>