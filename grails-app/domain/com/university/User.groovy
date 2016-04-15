package com.university

import IST.Department

class User {
	/*Saves user details*/

	transient springSecurityService

	String username
	String password
    String email
    int studyCentreId
    boolean upDifferent
    Department department
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    String employeeCode

	static constraints = {
		username blank: false, unique: true
		password blank: false
        email(nullable:true)
        studyCentreId(nullable:true)
        department(nullable:true)
        employeeCode(nullable:true)

	}

	static mapping = {
		password column: '`password`'
        studyCentreId column: "studyCentreId"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
