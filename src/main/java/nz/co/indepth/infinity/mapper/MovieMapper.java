package nz.co.indepth.infinity.mapper;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.MoviePO;
import nz.co.indepth.infinity.repository.MovieRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class MovieMapper {

    @Autowired
    private MovieRepository movieRepository;


    public Movie moviePOToEntity(MoviePO moviePO) {
        if (Objects.isNull (moviePO)) {
            return null;
        } else if(Objects.isNull (moviePO.getId ()) || moviePO.getId () == 0) {
            return moviePOToEntity(new Movie (), moviePO);
        } else {
            return moviePOToEntity (movieRepository.findById (moviePO.getId ()).orElse (new Movie ()), moviePO);
        }
    }

    @Mapping (target = "employee", ignore = true)
    public abstract Movie moviePOToEntity(@MappingTarget Movie movie, MoviePO moviePO);

    public abstract List<Movie> moviePOListToEntity(List<MoviePO> moviePOs);



    /**
     *  avoid the recursive reference
     */
    @Mapping (target = "employeePO", source = "employee")
    @Mapping (target = "employeePO.moviePOs", ignore = true)
    public abstract MoviePO movieToPo(Movie movie);

    public abstract List<MoviePO> movieListToPo(List<Movie> movies);
}
