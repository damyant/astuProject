<%--
  Created by Damyant Software Pvt Ltd.
  User: IDOL_2
  Date: 8/28/14
  Time: 6:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <fieldset class="form">
        <table class="inner university-size-full-1-1" style="min-height: 410px; margin: auto;">
            %{--<tr>--}%
                %{--<td class="university-size-2-3" style="vertical-align: middle;line-height: 20px;padding: 10px 20px;text-align: justify;box-shadow: 5px 0 5px -5px #aaaaaa;border-radius: 5px;">--}%
                        %{--<p>The Gauhati University Institute of Science and Technology (GU IST) was set up at the university campus in 2009 to enhance quality and innovation in Technology and Applied science education. The Programmes currently being offered by the Institute are the  four-year Bachelor of Science (B.S.) and Bachelor of Technology (B.Tech.) Programmes, the two-year M.S. and M.Tech. Programmes, and Ph.D. programs in different disciplines.</p>--}%

                        %{--<p>The BS programme has been designed in parallel to the 4-year B.Tech. programmes. The BS and BTech programme curriculum's have been developed with a holistic approach of integrating Science, Technology, Humanities, and Management courses together to create human resources fit for the current society. There is also a provision to switch from a B.Tech. Programme to a B.S. Programme under certain conditions. First two semesters of BS and B.Tech. programmes are with all common courses, and in next 6 semesters also they have to undergo selected common Humanities, Management and Technological courses. Important common courses that all BS and B.Tech. students have to undertake includes Engineering Workshop, Engineering Drawing, Environmental Studies, Basic Electronics, Computer Programming, Management, Humanities electives etc.</p>--}%

                        %{--<p>The Council of Scientific and Industrial Research (CSIR), Govt. of India has revised the eligibility criteria for the National Eligibility Test, conducted for Junior Research Fellowship (JRF), through a notification on 19th March, 2012. As per this notification, from the year 2012 onwards, the 4-years BS Degree has been included as an Educational Eligibility criteria for appearing NET for JRF conducted by CSIR. The first batch of BS students who are in their final year has already appeared the CSIR JRF examination last December.</p>--}%

                        %{--<p>The progression to higher studies and research in Science and Technology are being facilitated by the two in house M.Tech. Programmes. M.Tech. in Electronics and Communication Technology (ECT) was introduced in 2009 and M.Tech. in Information Technology (IT) was introduced in the year 2010. MS programmes in Physical Science, Chemical Science, Mathematical Science are being offered from the academic session 2013-14 to facilitate continuation of higher studies to the first batch of BS students who are graduating in June, 2013.</p>--}%
                        %{--<div style="width: 70%;float: right; text-align: left;padding: 10px 20px;">--}%
                            %{--<div>Director In charge :     Professor Shikhar Kr. Sarma</div>--}%
                            %{--<div>Address and contact :    Gauhati University Institute of Science and Technology--}%
                                %{--(Old BT Hostel Premises)</div>--}%
                                %{--<div>Guwahati 781014, Assam, India</div>--}%
                                %{--<div>Phone +91-3612672233</div></div>--}%
                %{--</td>--}%
                %{--<td class="university-size-1-3" style="vertical-align: top;">--}%
                    %{--<table class="university-size-full-1-1 inner" cellspacing="10" style="height: 408px; margin: auto;">--}%
                        %{--<tr>--}%
                            %{--<td style="text-align: center;border: 1px solid #0000ff;">--}%
                                %{--<img src="${resource(dir: 'images', file: 'symphonie_Header.png')}" style="width: 95%;margin: auto;"/>--}%
                            %{--</td>--}%
                        %{--</tr>--}%
                        %{--<tr>--}%
                            %{--<td id="noticeBoardView">--}%

                                  %{--<h4 style="line-height: 24px;">GUIST Academic Automation System is a collaborative educational resource management portal, which allows activities such as-</h4>--}%
                                %{--<ul style="text-align: left; line-height: 20px;"><li>Student enrollment</li>--}%

                                %{--<li>Semester registration</li>--}%

                                    %{--<li>Fee management</li>--}%

                                    %{--<li>Marks and transcript generation</li>--}%

                                    %{--<li>Employee record administration</li>--}%

                                    %{--<li>Lab equipment administration</li>--}%

                                    %{--<li>Library  catalog management</li>--}%

                                    %{--<li>Notice board access</li>--}%

                                    %{--<li>Report management</li>--}%
                                    %{--</ul>--}%
                            %{--</td>--}%
                        %{--</tr>--}%
                    %{--</table>--}%

                %{--</td>--}%
            %{--</tr>--}%
        </table>
        </fieldset>
    </div>
</body>
</html>