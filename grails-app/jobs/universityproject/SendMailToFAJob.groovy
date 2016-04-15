package universityproject



class SendMailToFAJob {
    def sendMailService
    static triggers = {
//      simple repeatInterval: 5000l // execute job once in 5 seconds
//        cron cronExpression: "0 0 10 * * ? "
//        cron cronExpression: "0 * 11 * * ?"
        cron cronExpression: "0 0 17 * * ?"
        def group = "MyGroup"
    }

    def execute() {
        // execute job
        sendMailService.mailSendToFA()



    }
}
