package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    public MoviePO createMovie(MoviePO po) {
        Movie movie = movieMapper.moviePOToEntity (po);
        return movieMapper.movieToPo (movieRepository.save (movie));
    }

    public MoviePO updateMovie(MoviePO po) {
        Movie movie = movieMapper.moviePOToEntity (po);
        if(Objects.nonNull (movie.getId ()) && movie.getId () > 0) {
            boolean exist = movieRepository.findById (movie.getId ()).isPresent ();
            if(!exist) {
                // TODO: throw 400 to client
            }
        }
        return movieMapper.movieToPo (movieRepository.save (movie));
    }

    public MoviePO findByMovieName(String name) {
        Optional<Movie> movie1 = movieRepository.findByMovieName (name);
        Optional<Movie> movie2 = movieRepository.findMovieByMovieName (name);

        if(movie1.isPresent () && movie2.isPresent ()) {
            boolean isEqual = movie1.equals (movie2) ;
            Assert.isTrue (isEqual, "The above two method should return the same result. ");
            return movieMapper.movieToPo (movie1.get ());
        } else {
            // TODO: should return 404 not found
            return new MoviePO ();
        }

    }

    public List<MoviePO> findMovieByName(String name) {

        if(StringUtils.isEmpty (name)) {
            return getMovies();
        }

        // 1. using @Query annotation
        Pageable sortedByPriceDescNameAsc =
                PageRequest.of(0, 3, Sort.by("price").descending().and(Sort.by("movieName")));
        List<Movie> movies = movieRepository.fetchMovieByName (name, sortedByPriceDescNameAsc);

        // 2. using function method
        List<Movie> moviesUsedContains = movieRepository.findByMovieNameContains (name);
        // 3.
        List<Movie> moviesUsedContaining = movieRepository.findByMovieNameContaining (name);
        // 4.
        List<Movie> moviesUsedIsContaining = movieRepository.findByMovieNameIsContaining (name);

        boolean isEqual =
                moviesUsedContains.get (0).equals (moviesUsedContaining.get (0)) &&
                        moviesUsedContains.get (0).equals (moviesUsedIsContaining.get (0));
        Assert.isTrue (isEqual, "The above three method should return the same result. ");

        return movieMapper.movieListToPo (movies);
    }

    public String deleteMovie(MoviePO moviePO) {
        /**
         * No need to pass PO, because the source code of delete is still use ID.
         * In this instance, just use MovieId is enough
         *
         * FIXME: Here just for a negative exmaple, before deleting, should check the entity first.
         * Although, SpringBoot has done it for me. See the source code.
         */
        Movie movie = movieMapper.moviePOToEntity (moviePO);
        movieRepository.delete (movie);
        return "Successfully deleted. ";
    }


    private List<MoviePO> getMovies() {
        /**
         * sort for all records, then only pick first page and first three rows
         */
        Pageable sortedByPriceDescNameAsc =
                PageRequest.of(0, 3, Sort.by("price").descending().and(Sort.by("movieName")));
        List<Movie> movies = movieRepository.findAll (sortedByPriceDescNameAsc).getContent ();
        return movieMapper.movieListToPo (movies);
    }

}
