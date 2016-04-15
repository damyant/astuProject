package com.university

class LoginAlerts {
    /*saves notification(login alerts) for user*/
    String msg
    User user
    int viewStatus=0

    static constraints = {
    }
    static mapping = {
        viewStatus defaultValue: 0
        msg column: "msg"
        user column: "user"
        viewStatus column: "viewStatus"
    }
}
