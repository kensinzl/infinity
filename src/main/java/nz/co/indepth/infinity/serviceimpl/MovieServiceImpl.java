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
    public List<MoviePO> getMovies() {
        /**
         * sort for all records, then only pick first page and first three rows
         */
        Pageable sortedByPriceDescNameAsc =
                PageRequest.of(0, 3, Sort.by("price").descending().and(Sort.by("movieName")));
        List<Movie> movies = movieRepository.findAll (sortedByPriceDescNameAsc).getContent ();
        return movieMapper.movieListToPo (movies);
    }

    @Override
    public MoviePO findByMovieName(String name) {
        Movie movie = movieRepository.findByMovieName (name);
        return movieMapper.movieToPo (movie);
    }

}
