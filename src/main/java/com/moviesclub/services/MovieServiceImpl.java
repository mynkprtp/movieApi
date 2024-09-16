package com.moviesclub.services;

import com.moviesclub.dto.MovieDto;
import com.moviesclub.dto.MoviePageResponse;
import com.moviesclub.entities.Movie;
import com.moviesclub.exceptions.FileExistsException;
import com.moviesclub.exceptions.MovieNotFoundException;
import com.moviesclub.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // check if file already present with same name
        if(Files.exists(Paths.get(path+ File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("File already exists!! Please enter unique filename");
        }
        // Upload the file
        String uploadedFileName = fileService.uploadFile(path,file);
        // set the value of field 'poster' as fileName
        movieDto.setPoster(uploadedFileName);
        // map dto to Movie Object
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // Save the movie Object -> return saved movie Object
        Movie savedMovie = movieRepository.save(movie);

        // generating poster URL
        String movieUrl = baseUrl + "/file/" + uploadedFileName;

        // map Movie object to DTO object and return it
        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                movieUrl
        );
        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check the data in DB and if exits, fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id "+movieId));
        // generate pasteURL
        String posterUrl = baseUrl + "/file/" + movie.getPoster();
        // map the movieDTO object
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        // fetch all data from DB
        List<Movie> movies = movieRepository.findAll();

        List <MovieDto> movieDtoList = new ArrayList<>();
        // iterate through the list and generate the URL and map to movieDTO object
        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtoList.add(movieDto);
        }

        return movieDtoList;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // check if movie exists or not
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id "+movieId));

        // if file is null, do nothing
        // if file is not null then delete existing file associated with the record and upload new file
        String fileName = movie.getPoster();
        if(file!=null){
            Files.delete(Paths.get(path+ File.separator + fileName));
            fileName = fileService.uploadFile(path,file);
        }
        // set movieDto poster value after file upload
        movieDto.setPoster(fileName);
        // map it to Movie object
        Movie updatedMovie = new Movie(
                movie.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // save the movie object and return saved movie object
        movieRepository.save(updatedMovie);
        // generate posterUrl for it
        String posterUrl = baseUrl + "/file/" + fileName;
        // map to movieDto and return it
        MovieDto response = new MovieDto(
                updatedMovie.getMovieId(),
                updatedMovie.getTitle(),
                updatedMovie.getDirector(),
                updatedMovie.getStudio(),
                updatedMovie.getMovieCast(),
                updatedMovie.getReleaseYear(),
                updatedMovie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // check if movie exists in DB
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id "+movieId));

        // delete the file associated with object
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));

        // delete the movie object
        movieRepository.delete(movie);

        return "Movie with movieId "+ movieId + " deleted";
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        // information about the pages
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        // get the movies from the page information
        List<Movie> movies = moviePages.getContent();

        // convert to MovieDto
        List <MovieDto> movieDtoList = new ArrayList<>();
        // iterate through the list and generate the URL and map to movieDTO object
        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtoList.add(movieDto);
        }

        return new MoviePageResponse(movieDtoList,pageNumber,pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(dir).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        // information about the pages
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        // get the movies from the page information
        List<Movie> movies = moviePages.getContent();

        // convert to MovieDto
        List <MovieDto> movieDtoList = new ArrayList<>();
        // iterate through the list and generate the URL and map to movieDTO object
        for(Movie movie : movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtoList.add(movieDto);
        }

        return new MoviePageResponse(movieDtoList,pageNumber,pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast());
    }
}
