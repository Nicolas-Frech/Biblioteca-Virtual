package br.com.nicolasfrech.biblioteca_online.application;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookDataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookDataSeeder(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author author = new Author(new AuthorDTO("J.K. Rowling", LocalDate.parse("1970-11-11")));
        authorRepository.save(author);
        Author authorPersisted = authorRepository.findByName("J.K. Rowling");

        List<Book> livros = List.of(
                new Book(new BookDTO("Harry Potter e a Pedra Filosofal", Genre.FANTASY, author.getName(), LocalDate.of(1997, 6, 26),
                        "https://m.media-amazon.com/images/I/81ibfYk4qmL.jpg",
                        "Harry descobre, no seu décimo primeiro aniversário, que é um bruxo e é convidado a estudar na Escola de Magia e Bruxaria de Hogwarts. Lá, ele inicia sua jornada mágica e desvenda o mistério da Pedra Filosofal.")),

                new Book(new BookDTO("Harry Potter e a Câmara Secreta", Genre.FANTASY, author.getName(), LocalDate.of(1998, 7, 2),
                        "https://m.media-amazon.com/images/I/81jbivNEVML.jpg",
                        "Harry retorna a Hogwarts e enfrenta uma série de ataques a alunos. Com a ajuda de seus amigos, ele descobre os segredos da Câmara Secreta e confronta um inimigo oculto.")),

                new Book(new BookDTO("Harry Potter e o Prisioneiro de Azkaban", Genre.FANTASY, "J.K. Rowling", LocalDate.of(1999, 7, 8),
                        "https://m.media-amazon.com/images/I/81u+ljPVifL.jpg",
                        "Um perigoso prisioneiro chamado Sirius Black escapa de Azkaban, e Harry aprende mais sobre seu passado, a morte de seus pais e a verdade por trás da fuga de Black.")),

                new Book(new BookDTO("Harry Potter e o Cálice de Fogo", Genre.FANTASY, author.getName(), LocalDate.of(2000, 7, 8),
                        "https://m.media-amazon.com/images/I/81nTLN-kz7L.jpg",
                        "Harry é misteriosamente inscrito no perigoso Torneio Tribruxo. Enquanto enfrenta tarefas mortais, forças das trevas começam a ressurgir.")),

                new Book(new BookDTO("Harry Potter e a Ordem da Fênix", Genre.FANTASY, author.getName(), LocalDate.of(2003, 6, 21),
                        "https://m.media-amazon.com/images/I/81RI+iGwPGL.jpg",
                        "Com o retorno de Voldemort sendo negado pelo Ministério, Harry forma a Armada de Dumbledore para se preparar para a guerra que se aproxima, enquanto enfrenta a tirânica professora Dolores Umbridge.")),

                new Book(new BookDTO("Harry Potter e o Enigma do Príncipe", Genre.FANTASY, author.getName(), LocalDate.of(2005, 7, 16),
                        "https://m.media-amazon.com/images/I/81-jvnt+hgL._AC_UF1000,1000_QL80_.jpg",
                        "Dumbledore mostra a Harry memórias do passado de Voldemort, e juntos procuram o segredo para derrotá-lo: as Horcruxes.")),

                new Book(new BookDTO("Harry Potter e as Relíquias da Morte", Genre.FANTASY, author.getName(), LocalDate.of(2007, 7, 21),
                        "https://m.media-amazon.com/images/I/81rvO7xcJOL.jpg",
                        "Harry, Rony e Hermione partem em uma missão para encontrar e destruir as Horcruxes restantes, levando à batalha final contra Voldemort."))
            );

        for (Book livro : livros) {
            livro.addAuthor(authorPersisted);
        }


        bookRepository.saveAll(livros);
    }
}
