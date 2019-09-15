package nz.co.indepth.infinity.entity;

import javax.persistence.*;
import java.math.BigDecimal;

// TODO: search does need implements Serializable
@Entity
@Table(name = "MOVIE")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "movie_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "movie_price")
    private BigDecimal price;

    @Column(name = "author")
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Movie Entity {" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
