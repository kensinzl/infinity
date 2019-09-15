package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.mapper.MovieMapper;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.serviceimpl.MovieServiceImpl;
import nz.co.indepth.infinity.validator.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private BeanValidators beanValidators;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @PostMapping
    public MoviePO createMovie(@RequestBody MoviePO moviePO) {

        Map<String, String> validations = beanValidators.validateBeanMayWithException (moviePO);
        if(validations.isEmpty ()) {
            Movie movie = movieService.createMovie (moviePO);
            LOGGER.debug ("-------- created movie: " + movie);
            return movieMapper.movieToPo (movie);
        } else {
            // TODO: how to make a springboot response
            validations.forEach ((k, v) -> System.out.println ("--------" + k + ": " + v));
            return new MoviePO ();
        }
    }

}
