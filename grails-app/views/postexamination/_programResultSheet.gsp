<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 8/8/2014
  Time: 7:10 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<div style="width: 100%;text-align: center;font-size: 18px;">GAUHATI UNIVERSITY</div>

<div style="width: 100%;text-align: center;font-size: 15px;text-decoration: underline">Result of ${courseName} semester ${semester} Under Gauhati University Institute of Distance and Open Learning</div>

<div style="width: 100%;text-align: left; font-size: 14px;">List of succesful candidates</div>
<div style="width: 100%;text-align: left; font-size: 14px;">Total :  ${studPassList.size()}</div>

<p>
    <g:if test="${studPassList}">
        <g:each in="${studPassList.reverse()}" var="stud" status="i">
            <label>${stud.rollNo},       </label>

        </g:each>
    </g:if>
    <g:else>
        NO STUDENT CLEARED IN ALL SUBJECTS
    </g:else>
</p>

<div style="width: 100%;text-align: left; font-size: 14px;">List of candidates who have partially cleared the semester</div>
<g:if test="${studPartialList}">
    <div style="width: 100%;text-align: left; font-size: 14px;">Total :  ${studPartialList.size()}</div>
</g:if>
<p>
    <g:if test="${studPartialList}">
        <g:each in="${studPartialList}" var="stud" status="i">
            <label>${stud.rollNo},       </label>
        </g:each>
    </g:if>
    <g:else>
        NO STUDENT CLEARED IN WITH PARTIAL SUBJECTS
    </g:else>
</p>
</body>
</html>