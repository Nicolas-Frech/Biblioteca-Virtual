package br.com.nicolasfrech.biblioteca_online.infra.author.persistence;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ElementCollection(targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "author_genres", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "genre")
    private List<Genre> genres;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookEntity> books;

    private LocalDate birthdate;
    private Boolean active;

    public AuthorEntity() {
    }

    public AuthorEntity(Long id, String name, List<Genre> genres, List<BookEntity> books, LocalDate birthdate, Boolean active) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.books = books;
        this.birthdate = birthdate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
