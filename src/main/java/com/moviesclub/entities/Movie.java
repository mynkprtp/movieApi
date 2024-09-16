package com.moviesclub.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    @Id // DB level Validation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)// DB level Validation
    @NotBlank(message = "Please provide movie's  title !!")
    private String title;

    @Column(nullable = false)// DB level Validation
    @NotBlank(message = "Please provide movie's  Director !!")
    private String director;

    @Column(nullable = false)// DB level Validation
    @NotBlank(message = "Please provide movie's  Studio !!")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false) // DB level Validation
    @NotNull(message = "Please provide movie's  Release Year !!")
    private Integer releaseYear;

    @Column(nullable = false) // DB level Validation
    @NotBlank(message = "Please provide movie's  Poster !!")
    private String poster;
}
