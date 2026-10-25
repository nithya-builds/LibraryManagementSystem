package model;

/** Author of a book. A Book holds an Author (aggregation). */
public class Author {

    private String name;
    private String country;

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}
