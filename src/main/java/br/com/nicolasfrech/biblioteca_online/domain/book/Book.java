package br.com.nicolasfrech.biblioteca_online.domain.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.Review;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private Author author;
    private User userReserved = new User();
    private String cover;
    private LocalDate releaseDate;
    private String synopsis;
    private Boolean reserved;
    private Boolean active;
    private List<Review> reviews = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();

    public Book() {
    }

    public Book(Long id, String title, Genre genre, Author author, User userReserved, String cover, LocalDate releaseDate, String synopsis, Boolean reserved, Boolean active, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.userReserved = userReserved;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.reserved = reserved;
        this.active = active;
        this.reviews = reviews;
    }

    public Book(BookDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.cover = dto.cover();
        this.releaseDate = dto.releaseDate();
        this.synopsis = dto.synopsis();
        this.reserved = false;
        this.active = true;
    }

    public void addRating(Integer rating) {
        this.ratings.add(rating);
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public double getAvgRatings() {
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (int rating : ratings) {
            sum += rating;
        }

        return sum / ratings.size();
    }

    public void addReview(String username, String content) {
        this.reviews.add(new Review(username, content));
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addAuthor(Author author) {
        this.author = author;
    }

    public void deleteBook() {
        this.active = false;
    }

    public void reserveBook() {
        this.reserved = true;
    }

    public void returnBook() {
        this.userReserved = null;
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

    public Author getAuthor() {
        return author;
    }

    public User getUserReserved() {
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

}
