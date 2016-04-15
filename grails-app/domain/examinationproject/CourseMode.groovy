package examinationproject

class CourseMode {
    /*Saves course mode type like semester or annual*/

    String modeName

    static constraints = {

    }

    static mapping = {
        id column: "ModeId"
        modeName column: "ModeName"
      }
}
