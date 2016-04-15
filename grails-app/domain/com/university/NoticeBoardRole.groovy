package com.university

class NoticeBoardRole {
    /*Save details of role which can see the particular notice(Identifies whether a particular user can see specified notice or not)*/

    Role role

    static belongsTo = [noticeboard:NoticeBoard]
    static mapping = {
        noticeboard cascade: "none"
        role column: 'role'
        noticeboard column: 'noticeboard'
    }
    static constraints = {
        role(nullable: false)
    }
}
