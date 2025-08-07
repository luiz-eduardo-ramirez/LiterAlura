package br.com.alura.literAlura.main;

import br.com.alura.literAlura.model.Author;
import br.com.alura.literAlura.model.Book;
import br.com.alura.literAlura.model.DataBookRecord;
import br.com.alura.literAlura.model.ResultsData;
import br.com.alura.literAlura.repositoy.BookRepository;
import br.com.alura.literAlura.service.APIconsumption;
import br.com.alura.literAlura.service.BookService;
import br.com.alura.literAlura.service.ConvertData;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private final APIconsumption apiConsumption = new APIconsumption();
    private ConvertData convertData = new ConvertData();
    private BookRepository repository;
    private final String baseUrlAdress = "https://gutendex.com/books";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    List<Book> book = new ArrayList<>();

    public Main(BookRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;

        while(option != 0) {
            System.out.println("Escolha o número de sua opção:");
            System.out.println("1- buscar livro pelo título");
            System.out.println("2- listar livros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos em um determinado ano");
            System.out.println("5- listar livros em um determinado idioma");
            System.out.println("6- Listar estatísticas de downloads de todos livros");
            System.out.println("7- Listar os Top 10 livros mais baixados");
            System.out.println("8- Buscar autor por nome");
            System.out.println("0 - sair");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    System.out.println("Comece com um ano:");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    listLivingAuthorsInYear(year);
                    break;
                case 5:
                    System.out.println("Introduzir a língua:");
                    System.out.println("Opções:");
                    System.out.println("pt - português");
                    System.out.println("en - inglês");
                    String language = scanner.nextLine();
                    listBooksInLanguage(language);
                    break;
                case 6:
                    printDownloadCountStatistics();
                    break;
                case 7:
                    listTop10MostDownloadedBooks();
                    break;
                case 8:
                    searchAuthorByName();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private ResultsData getBookByAPI(String title) {
        var url = baseUrlAdress + "?search=" + title.replace(" ", "%20");
        LOGGER.info("Fetching data from URL: " + url);
        var json = apiConsumption.getData(url);
        LOGGER.info("Received JSON: " + json);
        return convertData.getData(json, ResultsData.class);
    }

    private void searchBookByTitle() {
        System.out.println("Digite o nome do livro: ");
        String title = scanner.nextLine();
        ResultsData resultsData = getBookByAPI(title);
        List<DataBookRecord> bookRecords = resultsData.results().stream().toList();
        if (bookRecords.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título '" + title + "'.");
            return;
        }
        bookRecords.forEach(bookRecord -> book.add(new Book(bookRecord)));
        try {
            book.forEach(b -> repository.save(b));
        } catch (Exception e) {
        }
        System.out.println(book.toString());
    }

    private void listRegisteredBooks() {
        System.out.println("Livros Registrados: ");
        var books = repository.findAll();
        if(books.isEmpty()) {
            System.out.println("Não há livros registrados!");
            return;
        }
        books.forEach(b -> System.out.println(b.toString()));
    }

    private  void listRegisteredAuthors() {
        List<Author> authors = repository.listRegisteredAuthors();
        if(authors.isEmpty()) {
            System.out.println("Não há autores registrados!");
            return;
        }
        authors.forEach(author -> System.out.println("Autores: " + author.getName()));
    }

    private  void listLivingAuthorsInYear(int year) {
        System.out.println("Listagem dos autores vivos do ano: " + year);
        List<Author> authorsLiving = repository.listLivingAuthorsInYear(year);
        if(authorsLiving.isEmpty()) {
            System.out.println("Não há autores registrados vivo no ano " + year + "!");
            return;
        }
        authorsLiving.forEach(System.out::println);
    }

    private void listBooksInLanguage(String language) {
        System.out.println("Listagem de livros na língua: " + language);
        List<Book> books = repository.listBooksInLanguage(language);
        if(books.isEmpty()) {
            System.out.println("Não há livros nessa língua");
            return;
        }
        books.forEach(System.out::println);
    }

    public void printDownloadCountStatistics() {
        BookService bookService = new BookService(repository);
        DoubleSummaryStatistics stats = bookService.getDownloadCountStatistics();

        System.out.println("Estatísticas de downloadCount:");
        System.out.println("Contagem: " + stats.getCount());
        System.out.println("Soma: " + stats.getSum());
        System.out.println("Média: " + stats.getAverage());
        System.out.println("Mínimo: " + stats.getMin());
        System.out.println("Máximo: " + stats.getMax());
    }

    public void listTop10MostDownloadedBooks() {
        List<Book> topBooks = repository.findTop10ByOrderByDownloadCountDesc();
        System.out.println("Top 10 livros mais baixados:");
        topBooks.forEach(book -> System.out.println(book.toString()));
    }

    public void searchAuthorByName() {
        System.out.println("Digite o nome do autor: ");
        String name = scanner.nextLine();
        List<Author> authors = repository.findAuthorsByNameContaining(name);
        if (authors.isEmpty()) {
            System.out.println("Nenhum autor encontrado com o nome '" + name + "'.");
            return;
        }
        System.out.println("Autores encontrados:");
        authors.forEach(author -> System.out.println(author.toString()));
    }

}
