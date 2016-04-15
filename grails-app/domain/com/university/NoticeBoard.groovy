package com.university

class NoticeBoard {
	/*saves details of notice*/
	transient springSecurityService

	String noticeHeader
	String fileName
	Boolean isArchive
    Date noticeDate
    static hasMany = [
            noticeBoardRole : NoticeBoardRole
    ]

	static constraints = {

	}

	static mapping = {
        noticeHeader column: 'noticeHeader'
        fileName column: "fileName"
        noticeDate column: "noticeDate"
        isArchive column: "isArchive"
	}

	}
