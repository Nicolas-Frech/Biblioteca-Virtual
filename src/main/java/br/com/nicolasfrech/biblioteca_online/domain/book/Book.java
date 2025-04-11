package br.com.nicolasfrech.biblioteca_online.domain.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.Review;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private Author author;
    private Set<User> users = new HashSet<>();
    private String cover;
    private LocalDate releaseDate;
    private String synopsis;
    private Boolean active;
    private List<Review> reviews = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();

    public Book() {
    }

    public Book(Long id, String title, Genre genre, Author author, Set<User> users, String cover, LocalDate releaseDate, String synopsis, Boolean active, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.users = users;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.active = active;
        this.reviews = reviews;
    }

    public Book(BookDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.cover = dto.cover();
        this.releaseDate = dto.releaseDate();
        this.synopsis = dto.synopsis();
        this.active = true;
    }

    public void addRating(Integer rating) {
        this.ratings.add(rating);
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
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

    public Set<User> getUsers() {
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

}
