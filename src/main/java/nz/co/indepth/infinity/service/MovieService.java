package nz.co.indepth.infinity.service;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.MoviePO;

public interface MovieService {
    public Movie createMovie(MoviePO po);
}
