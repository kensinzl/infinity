package nz.co.indepth.infinity.po;

import nz.co.indepth.infinity.validator.Author;

import java.math.BigDecimal;

public class MoviePO {
    private Long id;
    private String movieName;
    private BigDecimal price;

    @Author
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
        return "MoviePO{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                '}';
    }
}
