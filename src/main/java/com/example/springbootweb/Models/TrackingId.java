package com.example.springbootweb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TrackingIds")
@Getter
@Setter
public class TrackingId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsername;
    private String name;

}
