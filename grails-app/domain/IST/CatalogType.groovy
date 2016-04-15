package IST

class CatalogType {
    /*Save catalog type name(Book/CD/Research papaers etc)*/
    String catalogTypeName

    static constraints = {
    }

    static mapping = {
        catalogTypeName column: "catalogTypeName"
    }
}