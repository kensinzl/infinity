package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.service.MovieService;
import nz.co.indepth.infinity.validator.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private BeanValidators beanValidators;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @PostMapping
    public ResponseEntity<MoviePO> createMovie(@RequestBody MoviePO moviePO) {
        Map<String, String> validations = beanValidators.validateBeanMayWithException (moviePO);
        if(validations.isEmpty ()) {
            MoviePO createdMoviePO = movieService.createMovie (moviePO);
            LOGGER.debug ("-------- created movie: " + createdMoviePO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMoviePO);
        } else {
            // TODO: how to make a springboot response
            validations.forEach ((k, v) -> System.out.println ("--------" + k + ": " + v));
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).build ();
        }
    }

    @PutMapping
    public MoviePO updateMovie(@RequestBody MoviePO moviePO) {
        Map<String, String> validations = beanValidators.validateBeanMayWithException (moviePO);
        if(validations.isEmpty ()) {
            MoviePO updatedMoviePO = movieService.updateMovie (moviePO);
            LOGGER.debug ("-------- updated movie: " + updatedMoviePO);
            return updatedMoviePO;
        } else {
            // TODO: how to make a springboot response
            validations.forEach ((k, v) -> System.out.println ("--------" + k + ": " + v));
            return new MoviePO ();
        }
    }

    /**
     * @RequestParam and @PathVariable
     * https://www.baeldung.com/spring-request-param
     */
    @GetMapping
    public List<MoviePO> findMovieByName(@RequestParam(name="name", required=false) String name) {
        return movieService.findMovieByName (name);
    }

    @GetMapping("/{name}")
    public MoviePO fetchByMovieName(@PathVariable("name") String name) {
        return movieService.findByMovieName (name);
    }

    @DeleteMapping
    public String deleteMovie(@RequestBody MoviePO moviePO) {
        return movieService.deleteMovie (moviePO);
    }

}
