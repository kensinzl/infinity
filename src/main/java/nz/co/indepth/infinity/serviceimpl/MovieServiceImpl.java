package nz.co.indepth.infinity.serviceimpl;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.MovieRepository;
import nz.co.indepth.infinity.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
