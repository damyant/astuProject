package universityproject



class SendMailJob {
    def sendMailService
    static triggers = {
//      simple repeatInterval: 5000l // execute job once in 5 seconds
//        cron cronExpression: "0 * 12 * * ?"
        cron cronExpression: "0 0 17 * * ?"
        def group = "MyGroup"
    }

    def execute() {
        // execute job

         sendMailService.mailSend()






    }
}
