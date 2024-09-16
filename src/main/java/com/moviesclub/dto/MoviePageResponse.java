package com.moviesclub.dto;

import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtos,
                                Integer pageNumber,
                                Integer pagesize,
                                Long totalElements,
                                int totalPages,
                                boolean isLast) {

}
