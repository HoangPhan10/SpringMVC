package com.example.springbootweb.Respository;

import com.example.springbootweb.Models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieModel,Long> {
}
