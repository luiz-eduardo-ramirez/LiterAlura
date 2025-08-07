package br.com.alura.literAlura.service;

import br.com.alura.literAlura.model.Book;
import br.com.alura.literAlura.repositoy.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }
    public DoubleSummaryStatistics getDownloadCountStatistics() {
        List<Book> books = repository.findAll();
        if(books.isEmpty()) {
            System.out.println("Não há livros registrados!");
        }
        DoubleSummaryStatistics stats = books.stream()
                .collect(Collectors.summarizingDouble(Book::getDownloadCount));
        return stats;
    }
}
