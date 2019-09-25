package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.MoviePO;

import java.util.List;

public interface MovieService {
    public Movie createMovie(MoviePO po);

    public List<MoviePO> getMovies();

    public MoviePO findByMovieName(String name);
}
