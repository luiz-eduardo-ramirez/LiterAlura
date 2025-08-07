package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Author> authors = new ArrayList<>();
    private String language;
    private Integer downloadCount;
    private String image;

    public Book(DataBookRecord dataBookRecord) {
        this.title = dataBookRecord.title();
        this.language = dataBookRecord.getLanguage();
        this.downloadCount = dataBookRecord.download_count();
        this.image = dataBookRecord.formats().get("image/jpeg");

        List<DataAuthorRecord> authorsData = dataBookRecord.authors().stream().toList();
        authorsData.forEach(a -> authors.add(new Author(a,this)));
    }

    public Book() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Livro: " +
                "Título: '" + this.title +
                ", Língua: '" + this.language +
                ", Quantidade de download:" + this.downloadCount +
                ", Autores: " + this.authors.stream().map(Author::toString)
                .collect(Collectors.joining(", "));
    }
}
