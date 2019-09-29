package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.MoviePO;

import java.util.List;

public interface MovieService {
    public MoviePO createMovie(MoviePO po);

    public MoviePO updateMovie(MoviePO po);

    public MoviePO findByMovieName(String name);

    public List<MoviePO>  findMovieByName(String name);

    public String deleteMovie(MoviePO moviePO);
}
