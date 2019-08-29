package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.serviceimpl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieServiceImpl movieService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @PostMapping
    public MoviePO createMovie(@RequestBody MoviePO moviePO) {
        // TODO: search springboot has the validator or not
        Movie movie = movieService.createMovie (moviePO);
        LOGGER.debug ("---------- createMovie");
        return movieMapper.movieToPo (movie);
    }

}
