package kn18012.librarymanagement.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Title must not be empty!")
    @Column(name = "title", length = 100)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @OneToMany(targetEntity = Loan.class, cascade = CascadeType.ALL, mappedBy = "book")
    private Set<Loan> loans = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Lob
    @NotBlank(message = "Description must not be empty!")
    @Column(name = "description")
    private String description;

    @Range(min = 0, max = 10000, message = "Quantity  must be larger than 1!")
    @Column(name = "quanitity")
    private int quantity;

    public Book() {
    }

    public Book(Long id, @NotBlank(message = "Title must not be empty!") String title, Set<Author> authors, Set<Loan> loans, Genre genre, @NotBlank(message = "Description must not be empty!") String description, @Range(min = 0, max = 10000, message = "Quantity  must be larger than 0!") int quantity) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.loans = loans;
        this.genre = genre;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

}