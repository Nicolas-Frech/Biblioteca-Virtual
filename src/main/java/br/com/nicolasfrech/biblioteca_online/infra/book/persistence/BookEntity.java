package br.com.nicolasfrech.biblioteca_online.infra.book.persistence;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = ("author_id"))
    private AuthorEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ("user_id"), nullable = true)
    private UserEntity userReserved = new UserEntity();

    private String cover;
    private LocalDate releaseDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String synopsis;
    private Boolean reserved;
    private Boolean active;

    public BookEntity() {
    }

    public BookEntity(Long id, String title, Genre genre, AuthorEntity author, UserEntity user, String cover, LocalDate releaseDate, String synopsis, Boolean reserved, Boolean active) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.userReserved = user;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.reserved = reserved;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public UserEntity getUserReserved() {
        return userReserved;
    }

    public String getCover() {
        return cover;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
