package com.example.springbootweb.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMovie {
    private boolean adult;
    private String backdrop_path;
    private List<String> genre_ids;
    private Integer id;
    private String original_language;
    private String original_title;
    private String overview;
    private String popularity;
    private String poster_path;
    private  String release_date;
    private String title;
    private Float vote_average;
    private Integer vote_count;
    private String video;

}
