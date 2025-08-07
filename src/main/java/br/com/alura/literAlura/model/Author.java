package br.com.alura.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long birthYear;
    private Long deathYear;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Author(DataAuthorRecord authorsData, Book book) {
        this.name = authorsData.name();
        this.birthYear = authorsData.birthYear();
        this.deathYear = authorsData.deathYear();
        this.book = book;
    }

    public Author() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Long birthYear) {
        this.birthYear = birthYear;
    }

    public Long getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Long deathYear) {
        this.deathYear = deathYear;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Autor: " +
                "Nome: '" + this.name +
                ", Data de Nascimento: " + (birthYear != null ? birthYear : "N/A") +
                ", Data da morte: " + (deathYear != null ? deathYear : "N/A") +
                ", Obras: " + (book != null ? book.getTitle() : "N/A");
    }
}
