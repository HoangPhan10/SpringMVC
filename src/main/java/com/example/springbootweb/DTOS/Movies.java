package com.example.springbootweb.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movies {
    private String page;
    private List<ResultMovie> results;
    private Integer total_pages;
    private Integer total_results;
}
