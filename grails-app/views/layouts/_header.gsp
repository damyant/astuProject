<div>
<div class="logo">
    <div class="header-parts">
        <div id="logo_text" style="height: 120px;vertical-align: middle;">
            <g:link controller="home" action="index">
                <img src="${resource(dir: 'images', file: 'sysLogo.png')}"
                     style="width: 100% ;padding: 20px 0px;"/>
            </g:link>
        </div>
    </div>

    <div class="header-parts1" name="logout">
        <div style="text-align: right;">
            <h2>
                %{--<img src="${resource(dir: 'images', file: 'loIstGu.png')}"--}%
                     %{--style="height:80px ;vertical-align: bottom;"></h2>--}%
        </div>

        <div style="clear: both;"></div>
        <sec:ifLoggedIn>
            <div class="university-session-management" style="margin-top: 10px;text-align: center;">
                <sec:username/> || <g:link controller="logout" style="padding: 4px;">Logout</g:link>
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR">
                    ||<g:link controller="user" action="index" style="padding: 4px;">Manage User</g:link>
                </sec:ifAnyGranted>
                ||<g:link controller="user" action="resetUserPassword" style="padding: 4px;">Reset Password</g:link>
            </div>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <div class="university-session-management" style="margin-top: 10px;text-align: center;">
                |<g:link controller="login" action="auth" style="padding: 4px;">Login</g:link>|
            </div>
        </sec:ifNotLoggedIn>
    </div>
</div>

<div id="cssmenu">
<ul id="menu">
<li><g:link controller="home" action="index"><g:message
        code="default.mainMenu1"/></g:link>
    </li>
    <sec:ifNotLoggedIn>
<li><g:link controller="student" action="generateRollNo"><g:message
        code="default.mainMenu21"/></g:link>
</li></sec:ifNotLoggedIn>
<sec:ifAnyGranted roles="ROLE_EMPLOYEE_ENTER">
    <li><g:link controller="employe" action="createEmployee"><g:message
            code="default.mainMenu10.subMenu1"/></g:link></li>
</sec:ifAnyGranted>
%{--*************************************************************************************************--}%
%{------------------------------------------Library------------------------------------------------------}%
%{--*************************************************************************************************--}%
<sec:ifNotGranted roles="ROLE_LIBRARY, ROLE_DEPARTMENT_LIBRARY">
    <sec:ifAnyGranted roles="ROLE_STUDENT">
        <li><a href="#"><g:message code="default.mainMenu2"/></a>
            <ul>
                <sec:ifLoggedIn>
                    <sec:ifNotGranted roles="ROLE_HELP_DESK">
                        <li><g:link controller="student" action="registration"><g:message
                                code="default.mainMenu2.subMenuStudyCentre"/></g:link></li>
                    </sec:ifNotGranted>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li><g:link controller="student" action="registration"><g:message
                            code="default.mainMenu2.subMenu1"/></g:link></li>
                </sec:ifNotLoggedIn>
                <sec:ifAnyGranted roles="ROLE_STUDENT">
                    <li><g:link controller="student" action="studentSemesterRegistration"><g:message
                            code="default.mainMenu2.subMenu7"/></g:link></li>
                </sec:ifAnyGranted>
                <sec:ifLoggedIn>
                    <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_STUDENT">
                        <li><g:link controller="student" action="enrollmentAtIdol"><g:message
                                code="default.mainMenu2.enrollAtIdol"/></g:link></li>
                    </sec:ifNotGranted>
                </sec:ifLoggedIn>
                <li><g:link controller="student" action="semesterDetails"><g:message
                        code="default.mainMenu2.subMenu5"
                        default="Semester Detail"/></g:link></li>

                <li><g:link controller="student" action="requestCertificate"><g:message
                        code="default.mainMenu2.subMenu9"
                        default="Request Certificate"/></g:link></li>
                <li><g:link controller="admin" action="enrollProjectForm"><g:message
                        code="default.mainMenu2.subMenu2"
                        default="Enroll for Project"/></g:link></li>
                <li><g:link controller="student" action="viewStudentDetails"><g:message
                        code="default.mainMenu2.subMenu10"
                        default="My Details"/></g:link></li>

            </ul>
        </li>
    </sec:ifAnyGranted>
</sec:ifNotGranted>
%{--*************************************************************************************************--}%
%{------------------------------------------HOD------------------------------------------------------}%
%{--*************************************************************************************************--}%
<sec:ifAnyGranted roles="ROLE_HOD">
    <li><a href="#"><g:message code="default.mainMenu16"/></a>
        <ul>
            <li><a href="#"><g:message code="default.mainMenu6.subMenu7"/></a>
                <ul>
                    <li><g:link controller="hod" action="stuList"><g:message
                            code="default.mainMenu16.subMenu1"/></g:link></li>
                    <li><g:link controller="hod" action="studentListForSemesterRegistration"><g:message
                            code="default.mainMenu16.subMenu2"/></g:link></li>
                    <li><g:link controller="hod" action="studentListApprovedStudents"><g:message
                            code="default.mainMenu16.subMenu3"/></g:link></li>
                    <li><g:link controller="hod" action="studentListApprovedRegistrations"><g:message
                            code="default.mainMenu16.subMenu4"/></g:link></li>
                    <li><g:link controller="hod" action="studentListCertificateRequest"><g:message
                            code="default.mainMenu16.subMenu5"/></g:link></li>
                    <li><g:link controller="admin" action="searchStudentName"><g:message
                            code="default.mainMenu6.subMenu2"/></g:link></li>
                    <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                            code="default.mainMenu2.subMenu13"/></g:link></li>
                    <li><g:link controller="student" action="studentListView"><g:message
                            code="default.mainMenu2.subMenu6"/></g:link></li>

                </ul>
            </li>
            <li>
                <g:link controller="postExamination" action="marksEntering"><g:message
                        code="default.mainMenu4.subMenu2"/>
                </g:link>
            </li>
            <li>
                <a href="#"><g:message code="default.mainMenu11.subMenu2.submenu1"/></a>
                <ul>
                    <li>
                        <g:link controller="postExamination" action="showUnapprovedMarks"><g:message
                                code="default.mainMenu3.subMenu1"/>
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="postExamination" action="showApprovedMarks"><g:message
                                code="default.mainMenu3.subMenu2"/>
                        </g:link>
                    </li>
                </ul>
            </li>
            <li>
                <g:link controller="postExamination" action="gradeConversionToApprove"><g:message
                        code="default.mainMenu4.subMenu3"/>
                </g:link>
            </li>
            <li>
                <g:link controller="postExamination" action="viewEvaluatedMarks"><g:message
                        code="default.mainMenu4.subMenu4"/>
                </g:link>
            </li>
            <li><g:link controller="employe" action="listOfEmployee"><g:message
                    code="default.mainMenu10"/></g:link></li>

            <li><a href="#"><g:message code="default.mainMenu13"/></a>
                <ul>
                    <li><g:link controller="equipment" action="listOfEquipment"><g:message
                            code="default.mainMenu13.subMenu3"/></g:link></li>
                </ul>
            </li>
            <li><a href="#"><g:message code="default.mainMenu3.subMenu14" default="Guide"/></a>
                <ul>
                    <li><g:link controller="admin" action="addGuide"><g:message
                            code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                    <li><g:link controller="admin" action="listOfGuide"><g:message
                            code="default.mainMenu15.subMenu1"/></g:link></li>
                </ul>
            </li>
            <li><a href="#"><g:message code="default.mainMenu3.subMenu15" default="Project Domain"/></a>
                <ul>
                    <li><g:link controller="admin" action="addProjectDomain"><g:message
                            code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                    <li><g:link controller="admin" action="listOfProjectDomains"><g:message
                            code="default.mainMenu15.subMenu1"/></g:link></li>
                </ul>
            </li>
            <li><g:link controller="admin" action="assignFacultyAdvisor"><g:message
                    code="default.mainMenu6.subMenu9"/></g:link></li>
            <li><g:link controller="admin" action="approvedProjects"><g:message
                    code="default.mainMenu2.subMenu4"/></g:link></li>
        </ul>
    </li>
</sec:ifAnyGranted>
%{--*************************************************************************************************--}%
%{------------------------------------------FacultyAdvisor------------------------------------------------------}%
%{--*************************************************************************************************--}%
<sec:ifAnyGranted roles="ROLE_FACULTYADVISOR">
    <li><a href="#"><g:message code="default.mainMenu24"/></a>
        <ul>
            <li><g:link controller="hod" action="stuList"><g:message
                    code="default.mainMenu16.subMenu1"/></g:link></li>
            <li><g:link controller="hod" action="studentListForSemesterRegistration"><g:message
                    code="default.mainMenu16.subMenu2"/></g:link></li>
            <li><g:link controller="hod" action="studentListApprovedStudents"><g:message
                    code="default.mainMenu16.subMenu3"/></g:link></li>
            <li><g:link controller="hod" action="studentListApprovedRegistrations"><g:message
                    code="default.mainMenu16.subMenu4"/></g:link></li>
            <li><g:link controller="admin" action="searchStudentName"><g:message
                    code="default.mainMenu6.subMenu2"/></g:link></li>
            <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                    code="default.mainMenu2.subMenu13"/></g:link></li>
            <li><g:link controller="student" action="studentListView"><g:message
                    code="default.mainMenu2.subMenu6"/></g:link></li>
            <li><g:link controller="admin" action="approveEnrolledProject"><g:message
                    code="default.mainMenu2.subMenu3"/></g:link></li>
            <li><g:link controller="admin" action="approvedProjects"><g:message
                    code="default.mainMenu2.subMenu4"/></g:link></li>
        </ul>
    </li>
</sec:ifAnyGranted>
%{--*************************************************************************************************--}%
%{------------------------------------------Instructor------------------------------------------------------}%
%{--*************************************************************************************************--}%
<sec:ifLoggedIn>
    <sec:ifAnyGranted roles="ROLE_INSTRUCTOR">

        <li><a href="#"><g:message code="default.mainMenu5"/></a>
            <ul>
                <li><a href="#"><g:message code="default.mainMenu4.subMenu6"/></a>
                    <ul>
                        <li>
                            <g:link controller="postExamination" action="createCourseExam"><g:message
                                    code="default.mainMenu4.subMenu9.submenu3"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="postExamination" action="listOfExam"><g:message
                                    code="default.mainMenu4.subMenu9.submenu4"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu4.subMenu9"/></a>
                    <ul>
                        <li>
                            <g:link controller="admin" action="ruleListView"><g:message
                                    code="default.mainMenu4.subMenu7"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="admin" action="viewGradeConversionRule"><g:message
                                    code="default.mainMenu4.subMenu8"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
                <li>
                    <g:link controller="postExamination" action="marksEntering"><g:message
                            code="default.mainMenu4.subMenu2"/>
                    </g:link>
                </li>
                <li>
                    <a href="#"><g:message code="default.mainMenu11.subMenu2.submenu1"/></a>
                    <ul>
                        <li>
                            <g:link controller="postExamination" action="showUnapprovedMarks"><g:message
                                    code="default.mainMenu3.subMenu1"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="postExamination" action="showApprovedMarks"><g:message
                                    code="default.mainMenu3.subMenu2"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
                <li>
                    <g:link controller="postExamination" action="marksEvaluation"><g:message
                            code="default.mainMenu4.subMenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="gradeConversion"><g:message
                            code="default.mainMenu4.subMenu2.subMenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="gradeConversionToApprove"><g:message
                            code="default.mainMenu4.subMenu3"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="viewEvaluatedMarks"><g:message
                            code="default.mainMenu4.subMenu4"/>
                    </g:link>
                </li>

            </ul>
        </li>
    </sec:ifAnyGranted>
</sec:ifLoggedIn>
%{--*************************************************************************************************--}%
%{------------------------------------------ADMIN------------------------------------------------------}%
%{--*************************************************************************************************--}%
<sec:ifAnyGranted roles="ROLE_ADMIN">
<sec:ifLoggedIn>
<li><a href="#"><g:message code="default.mainMenu6"/></a>
<ul>
<li><a href="#"><g:message code="default.mainMenu3"/></a>
    <ul>
        <li>
            <a href="#"><g:message code="default.mainMenu3.subMenu5"/></a>
            <ul>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu12"/></a>
                    <ul>
                        <li><g:link controller="category" action="createNewCategory"><g:message
                                code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                        <li><g:link controller="category" action="categoryList"><g:message
                                code="default.mainMenu3.subMenu12.subMenu2"/></g:link></li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu11"/></a>
                    <ul>
                        <li><g:link controller="admin" action="addDepartment"><g:message
                                code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                        <li><g:link controller="admin" action="listOfDepartment"><g:message
                                code="default.mainMenu15.subMenu1"/></g:link></li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu13"/></a>
                    <ul>
                        <li><g:link controller="admin" action="addProgramBranch"><g:message
                                code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                        <li><g:link controller="admin" action="listOfProgramBranch"><g:message
                                code="default.mainMenu15.subMenu1"/></g:link></li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu14" default="Guide"/></a>
                    <ul>
                        <li><g:link controller="admin" action="addGuide"><g:message
                                code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                        <li><g:link controller="admin" action="listOfGuide"><g:message
                                code="default.mainMenu15.subMenu1"/></g:link></li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu15" default="Project Domain"/></a>
                    <ul>
                        <li><g:link controller="admin" action="addProjectDomain"><g:message
                                code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                        <li><g:link controller="admin" action="listOfProjectDomains"><g:message
                                code="default.mainMenu15.subMenu1"/></g:link></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu6"/></a>
            <ul>
                <li><g:link controller="course" action="createNewCourse"><g:message
                        code="default.mainMenu3.subMenu6.subMenu1"/></g:link></li>
                <li><g:link controller="course" action="listOfCourses"><g:message
                        code="default.mainMenu3.subMenu6.subMenu2"/></g:link></li>
                <li><g:link controller="course" action="updateCourses"><g:message
                        code="default.mainMenu3.subMenu6.subMenu3"/></g:link></li>
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu9"/></a>
            <ul>

                <li><g:link controller="admin" action="addCourses"><g:message
                        code="default.mainMenu3.subMenu11.subMenu1"/></g:link></li>

                <li><g:link controller="admin" action="listOfCourses"><g:message
                        code="default.mainMenu3.subMenu11.subMenu2"/></g:link></li>
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu4.subMenu6"/></a>
            <ul>
                <li><a href="#"><g:message code="default.mainMenu4.subMenu5"/></a>
                    <ul>
                        <li>
                            <g:link controller="admin" action="createNewExamType"><g:message
                                    code="default.button.create.label"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="admin" action="viewExamType"><g:message
                                    code="default.button.view.label"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu3"/></a>
                    <ul>
                        <li>
                            <g:link controller="admin" action="viewExamSubType"><g:message
                                    code="default.button.view.label"/>
                            </g:link>
                        </li>
                    </ul>
                </li>

                <li>
                    <g:link controller="postExamination" action="createCourseExam"><g:message
                            code="default.mainMenu4.subMenu9.submenu3"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="listOfExam"><g:message
                            code="default.mainMenu4.subMenu9.submenu4"/>
                    </g:link>
                </li>
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu4.subMenu9"/></a>
            <ul><li>
                <g:link controller="postExamination" action="definedSymbols"><g:message
                        code="default.mainMenu4.subMenu9.submenu2"/>
                </g:link>
            </li>
                <li>
                    <g:link controller="postExamination" action="totalCalculationRule"><g:message
                            code="default.mainMenu4.subMenu9.submenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="createMainRules"><g:message
                            code="default.mainMenu4.subMenu8.submenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="admin" action="ruleListView"><g:message
                            code="default.mainMenu4.subMenu7"/>
                    </g:link>
                </li>
                <li><a href="#"><g:message code="default.mainMenu4.subMenu8"/></a>
                    <ul>
                        <li>
                            <g:link controller="admin"
                                    action="createGradeConversionRule"><g:message
                                    code="default.button.create.label"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="admin"
                                    action="viewGradeConversionRule"><g:message
                                    code="default.button.view.label"/>
                            </g:link>
                        </li>
                    </ul>
                </li>

            </ul>
        </li>

    </ul>
</li>
    <li><g:link controller="exam" action="assignExamDate"><g:message
            code="default.mainMenu2.subMenu20"/></g:link></li>
    <li>
        <a href="#"><g:message code="default.mainMenu2.subMenu21"/></a>
            <ul>
                <li><g:link controller="exam" action="admitCard"><g:message
                        code="default.mainMenu2.subMenu22"/></g:link></li>
                <li><g:link controller="exam" action="bulkAdmit"><g:message
                        code="default.mainMenu2.subMenu23"/></g:link></li>
        </ul>
    </li>


<li><a href="#"><g:message code="default.mainMenu4"/></a>
    <ul>
        <li>
            <g:link controller="postExamination" action="marksEntering"><g:message
                    code="default.mainMenu4.subMenu2"/>
            </g:link>
        </li>
        <li>
            <a href="#"><g:message code="default.mainMenu11.subMenu2.submenu1"/></a>
            <ul>
                <li>
                    <g:link controller="postExamination" action="showUnapprovedMarks"><g:message
                            code="default.mainMenu3.subMenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="showApprovedMarks"><g:message
                            code="default.mainMenu3.subMenu2"/>
                    </g:link>
                </li>

            </ul>
        </li>
        <li>
            <g:link controller="postExamination" action="marksEvaluation"><g:message
                    code="default.mainMenu4.subMenu1"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination" action="gradeConversion"><g:message
                    code="default.mainMenu4.subMenu2.subMenu1"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination" action="gradeConversionToApprove"><g:message
                    code="default.mainMenu4.subMenu3"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination" action="viewEvaluatedMarks"><g:message
                    code="default.mainMenu4.subMenu4"/>
            </g:link>
        </li>
    </ul>
</li>

    <li>
        <a href="#"><g:message code="default.mainMenu2.subMenu24"/></a>
        <ul>
            <li><g:link controller="postExamination" action="singleMarksSheet"><g:message
                    code="default.mainMenu2.subMenu22"/></g:link></li>
            <li><g:link controller="postExamination" action="bulkMarksSheet"><g:message
                    code="default.mainMenu2.subMenu23"/></g:link></li>
        </ul>
    </li>

<li><a href="#"><g:message code="default.mainMenu12"/></a>
    <ul>
        <li>
            <g:link controller="admin" action="noticeBoard"><g:message
                    code="default.mainMenu3.subMenu7.subMenu3"/></g:link>
        </li>
        <li>
            <g:link controller="admin" action="noticeBoardDel"><g:message
                    code="default.mainMenu12.subMenu3"/></g:link>
        </li>
        <li>
            <g:link controller="admin" action="noticeBoardView"><g:message
                    code="default.mainMenu12.subMenu2"/></g:link>
        </li>
    </ul>
</li>
<li><a href="#"><g:message code="default.mainMenu6.subMenu7"/></a>
    <ul><sec:ifAnyGranted roles="ROLE_ADMIN">

        <li>
            <g:link controller="student" action="generateRollNo"><g:message
                    code="default.mainMenu20"/></g:link>
        </li>
        <li>
            <g:link controller="student" action="tempRollNo"><g:message
                    code="default.mainMenu14"/></g:link>
        </li>
        <li><g:link controller="admin" action="searchStudentName"><g:message
                code="default.mainMenu6.subMenu2"/></g:link></li>
        <li><g:link controller="student" action="studentListView"><g:message
                code="default.mainMenu2.subMenu6"/></g:link></li>
    </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_HOD,ROLE_FACULTYADVISOR">
            <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                    code="default.mainMenu2.subMenu13"/></g:link></li>
            <li><g:link controller="hod" action="stuList"><g:message
                    code="default.mainMenu16.subMenu1"/></g:link></li>
            <li><g:link controller="hod" action="studentListForSemesterRegistration"><g:message
                    code="default.mainMenu16.subMenu2"/></g:link></li>
            <li><g:link controller="hod" action="studentListApprovedStudents"><g:message
                    code="default.mainMenu16.subMenu3"/></g:link></li>
            <li><g:link controller="hod" action="studentListApprovedRegistrations"><g:message
                    code="default.mainMenu16.subMenu4"/></g:link></li>
            <li><g:link controller="admin" action="deleteCurrentSemRegistration"><g:message
                    code="default.mainMenu24.subMenu2"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
</li>
<sec:ifAnyGranted roles="ROLE_ADMIN">
    <li><a href="#"><g:message code="default.mainMenu10"/></a>
        <ul>
            <li><g:link controller="employe" action="createEmployee"><g:message
                    code="default.mainMenu10.subMenu1"/></g:link></li>
            <li><g:link controller="employe" action="listOfEmployee"><g:message
                    code="default.mainMenu10.subMenu2"/></g:link></li>
        </ul>
    </li>
    <li><a href="#"><g:message code="default.mainMenu13"/></a>
        <ul>
            <li><g:link controller="equipment" action="createEquipment"><g:message
                    code="default.mainMenu13.subMenu1"/></g:link></li>
            <li><g:link controller="equipment" action="listOfEquipment"><g:message
                    code="default.mainMenu13.subMenu2"/></g:link></li>
        </ul>
    </li>
    <li><a href="#"><g:message code="default.mainMenu3.subMenu7.subMenu7"/></a>
        <ul>
            <li><g:link controller="admin" action="assignAdmissionPeriod"><g:message
                    code="default.mainMenu3.subMenu7.subMenu7.subMenu1"/></g:link></li>
            <li><g:link controller="admin" action="viewListOfAssignDate"><g:message
                    code="default.mainMenu3.subMenu7.subMenu7.subMenu2"/></g:link></li>
        </ul>
    </li>
    <li><a href="#"><g:message code="default.mainMenu6.subMenu9"/></a>
        <ul>
            <li><g:link controller="admin" action="assignFacultyAdvisor"><g:message
                    code="default.mainMenu3.subMenu7.subMenu7.subMenu1"/></g:link></li>
            <li><g:link controller="admin" action="viewListOfAssignFA"><g:message
                    code="default.mainMenu3.subMenu7.subMenu7.subMenu2"/></g:link></li>
        </ul>
    </li>
    <li><a href="#"><g:message code="default.mainMenu4.subMenu8.submenu2"/></a>
        <ul>
            <li><g:link controller="admin" action="approveEnrolledProject"><g:message
                    code="default.mainMenu2.subMenu3"/></g:link></li>
            <li><g:link controller="admin" action="approvedProjects"><g:message
                    code="default.mainMenu2.subMenu4"/></g:link></li>
        </ul>
    </li>
</sec:ifAnyGranted>
</ul>
</li>
</sec:ifLoggedIn>
</sec:ifAnyGranted>
<sec:ifLoggedIn>
    <li><a href="#"><g:message code="default.mainMenu11"/></a>
        <ul>
            <sec:ifAnyGranted roles="ROLE_STUDENT">
                <li><g:link controller="admin" action="bookIssuedToStudent"><g:message
                        code="default.mainMenu11.subMenu6"/></g:link>
                </li>
            </sec:ifAnyGranted>
            <sec:ifAnyGranted roles="ROLE_EMPLOYEE">
                <li><g:link controller="admin" action="bookIssuedToEmployee"><g:message
                        code="default.mainMenu11.subMenu6"/></g:link>
                </li>
            </sec:ifAnyGranted>
            <sec:ifAnyGranted roles="ROLE_LIBRARY, ROLE_DEPARTMENT_LIBRARY">

                <li><g:link><g:message code="default.mainMenu11.subMenu2"/></g:link>
                    <ul>
                        <g:link controller="admin" action="catalogType"><g:message
                                code="default.mainMenu11.subMenu1.submenu1"/></g:link>
                        <g:link controller="admin" action="catalogType" params="[view: 'view']"><g:message
                                code="default.mainMenu11.subMenu1.submenu3"/></g:link>
                    </ul>
                </li>
                <li><a href="#"><g:message code="default.mainMenu11.subMenu1"/></a>
                    <ul>
                        <g:link controller="admin" action="addCatalog"><g:message
                                code="default.mainMenu11.subMenu1.submenu1"/></g:link>
                        <g:link controller="admin" action="editCatalog"><g:message
                                code="default.mainMenu11.subMenu1.submenu2"/></g:link>
                        <g:link controller="admin" action="viewCatalog"><g:message
                                code="default.mainMenu11.subMenu1.submenu3"/></g:link>
                    </ul>
                </li>

                <li><g:link controller="admin" action="bookIssue"><g:message
                        code="default.mainMenu11.subMenu5"/></g:link>
                </li>
                <li><g:link controller="libraryReports" action="booksIssuedByUser"><g:message
                        code="default.mainMenu11.subMenu4.submenu2.submenu1" default="Issued Book By User"/></g:link>
                </li>
            </sec:ifAnyGranted>
            <li><a href="#"><g:message
                    code="default.mainMenu11.subMenu4.submenu2"/></a>
                <ul>
                    <li><g:link controller="libraryReports" action="byIsbn"><g:message
                            code="default.mainMenu11.subMenu4.submenu2.submenu3"/></g:link></li>
                    <li><g:link controller="libraryReports" action="byTitle"><g:message
                            code="default.mainMenu11.subMenu4.submenu2.submenu4"/></g:link></li>
                    <li><g:link controller="libraryReports" action="byAuthor"><g:message
                            code="default.mainMenu11.subMenu4.submenu2.submenu5"/></g:link></li>
                </ul>
            </li>
        </ul>
    </li>
</sec:ifLoggedIn>
<sec:ifLoggedIn>
    <sec:ifNotGranted roles="ROLE_STUDENT">
    <li><a href="#"><g:message
        code="default.mainMenu11.subMenu3" default="Certificate"/></a>
    <ul>
        <li><g:link controller="student" action="requestCertificate"><g:message
                code="default.mainMenu11.subMenu2.submenu2" default="Request"/></g:link></li>
        <sec:ifAnyGranted roles="ROLE_CERTIFICATE_ADMIN">
            <li><g:link controller="certificateTemp" action="createCertificate"><g:message code="default.mainMenu7"
                                                                                           default="Create Certificate"/></g:link></li>
            <li><g:link controller="certificateTemp" action="createCertificateTemplate"><g:message
                    code="default.mainMenu7.subMenu19" default="Create Certificate Template"/></g:link></li>
            <li><g:link controller="certificateTemp" action="listOfCertificate"><g:message
                    code="default.mainMenu7.subMenu20" default="Create Certificate Template"/></g:link></li>
            <li><g:link controller="admin" action="listOfUnApprovedCertificates"><g:message
                    code="default.mainMenu11.subMenu3.submenu1" default="Request"/></g:link></li>
            <li><g:link controller="admin" action="listOfApprovedCertificates"><g:message
                    code="default.mainMenu11.subMenu3.submenu2" default="Request"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
    <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_HOD">
        <li><g:link controller="report" action="reportIndex"><g:message code="default.mainMenu9"/></g:link></li>
    </sec:ifAnyGranted>
    </sec:ifNotGranted>
</sec:ifLoggedIn>
<sec:ifLoggedIn>
    <li><a href="https://172.16.0.8" target="_blank"><g:message code="default.mainMenu17"/></a></li>
</sec:ifLoggedIn>
</div>

<div class="scroller"><!-- this is for emulating position fixed of the nav -->
    <div class="scroller-inner">
        <g:render template="/layouts/statusPopup"/>
    </div>
</div>
</div>