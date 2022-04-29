package com.example.demo.models;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "spotify_song")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString

public class Spotify {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id")
    private BigInteger id;
    
    @Column(name="track_name")
    private String trackName;
    
    @Column(name="artist_name")
    private String artistName;
    
    @Column(name="genre")
    private String genre;

    @Column(name="popularity")
    private Integer popularity;

    
}
