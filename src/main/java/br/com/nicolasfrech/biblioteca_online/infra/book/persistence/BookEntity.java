package br.com.nicolasfrech.biblioteca_online.infra.book.persistence;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.Review;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "myLibrary")
    private Set<UserEntity> users = new HashSet<>();

    private String cover;
    private LocalDate releaseDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String synopsis;
    private Boolean active;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String reviewsJson;

    @Transient
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "book_ratings", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "rating")
    private List<Integer> ratings = new ArrayList<>();

    public BookEntity() {
    }

    public BookEntity(Long id, String title, Genre genre, AuthorEntity author, Set<UserEntity> users, String cover, LocalDate releaseDate, String synopsis, Boolean active, String reviews) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.users = users;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.active = active;
        this.reviewsJson = reviews;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        if (reviewsJson == null || reviewsJson.isEmpty()) return new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reviewsJson, new TypeReference<List<Review>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.reviewsJson = mapper.writeValueAsString(reviews);
        } catch (Exception e) {
            this.reviewsJson = "[]";
        }
    }

    public List<Integer> getRatings() {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }

        ratings.removeIf(r -> r == null);
        return ratings;
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

    public Set<UserEntity> getUsers() {
        return users;
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
