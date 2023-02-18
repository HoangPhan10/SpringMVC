package com.example.springbootweb.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name = "Movies_Trending")
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name = "description",length = 200000000)
    private String description;
    private String dateTime;
    private String path;
    private String type;
    private Float popularity;

}
