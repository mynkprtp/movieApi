package com.moviesclub.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @NotBlank(message = "Please provide movie's  title !!")
    private String title;

    @NotBlank(message = "Please provide movie's  Director !!")
    private String director;

    @NotBlank(message = "Please provide movie's  Studio !!")
    private String studio;

    private Set<String> movieCast;

    @NotNull(message = "Please provide movie's  Release year !!")
    private Integer releaseYear;

    @NotBlank(message = "Please provide movie's  Poster !!")
    private String poster;

    @NotBlank(message = "Please provide movie's  Poster's URL !!")
    private String posterUrl;
}
