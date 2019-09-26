package nz.co.indepth.infinity.serviceimpl;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.MovieRepository;
import nz.co.indepth.infinity.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(MoviePO po) {
        Movie movie = movieMapper.moviePOToEntity (po);
        return movieRepository.save (movie);
    }

    @Override
    public MoviePO findByMovieName(String name) {
        Movie movie1 = movieRepository.findByMovieName (name);
        Movie movie2 = movieRepository.findMovieByMovieName (name);

        boolean isEqual = movie1.equals (movie2) ;

        Assert.isTrue (isEqual, "The above two method should return the same result. ");

        return movieMapper.movieToPo (movie1);
    }

    @Override
    public List<MoviePO> findMovieByName(String name) {

        if(StringUtils.isEmpty (name)) {
            return getMovies();
        }

        // 1. using @Query annotation
        Pageable sortedByPriceDescNameAsc =
                PageRequest.of(0, 3, Sort.by("price").descending().and(Sort.by("movieName")));
        List<Movie> movies = movieRepository.findMovieByName (name, sortedByPriceDescNameAsc);

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

        return movieMapper.movieListToPo (moviesUsedContains);
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
