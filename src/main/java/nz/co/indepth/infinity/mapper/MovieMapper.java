package nz.co.indepth.infinity.mapper;

import nz.co.indepth.infinity.entity.Movie;
import nz.co.indepth.infinity.po.MoviePO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MovieMapper {

    @Named("movieToPo")
    public MoviePO movieToPo(Movie movie);

    @IterableMapping(qualifiedByName = "movieToPo")
    public List<MoviePO> movieListToPo(List<Movie> movies);

    @Named("moviePOToEntity")
    public Movie moviePOToEntity(MoviePO moviePO);

    @IterableMapping(qualifiedByName = "moviePOToEntity")
    public List<Movie> moviePOListToEntity(List<MoviePO> moviePOs);

}
