package br.com.alura.literAlura.repositoy;

import br.com.alura.literAlura.model.Author;
import br.com.alura.literAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select a from Author a")
    List<Author> listRegisteredAuthors();

    @Query("select a from Author a where a.deathYear > :year")
    List<Author> listLivingAuthorsInYear(int year);

    @Query("select b from Book b where b.language = :language")
    List<Book> listBooksInLanguage(String language);

    @Query("select b from Book b order by b.downloadCount desc")
    List<Book> findTop10ByOrderByDownloadCountDesc();

    @Query("select a from Author a where lower(a.name) like lower(concat('%', :name, '%'))")
    List<Author> findAuthorsByNameContaining(String name);
}
