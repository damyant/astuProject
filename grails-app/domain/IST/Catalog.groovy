package IST

class Catalog {
    /*save details of catalog(ISBN, Title, Author, quantity etc)*/

    transient springSecurityService

    CatalogType type
//    CatalogCatagory catagory
    String isbn
    String title
    String author
    String publisher
    Integer year
    Integer quantity
    Integer availableCatalog
    Department department

    static constraints = {
        availableCatalog (nullable: true)
        isbn (nullable: true)
//        catagory (nullable:true)
    }

    static mapping = {
        type column: "type"
//        catagory column: "catagory"
        isbn column: 'isbn'
        title column: "title"
        author column: "author"
        publisher column: "publisher"
        year column:"year"
        quantity column:"quantity"
        availableCatalog column: "availableCatalog"
    }

}
