package com.university

class Role {
	/*Saves different type of roles for users*/
	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
