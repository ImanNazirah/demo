package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import com.example.demo.models.Spotify;
import com.example.demo.repositories.SpotifyRepository;

import java.util.List;
import java.util.Optional;


@Service
public class SpotifyService {

    //Dependency Injection
    @Autowired
    SpotifyRepository spotifyRepository; 
    
    // CREATE 
    public Spotify createSpotify(Spotify spotify){
        return spotifyRepository.save(spotify);
    }

    // READ
    public List<Spotify> getSpotify() {
        return spotifyRepository.findAll();
    }

    // READ by id
    public Optional<Spotify> getSpotifyId(BigInteger spotifyId){
        return spotifyRepository.findById(spotifyId);
    }

    // Delete
    public Boolean deleteSpotify(BigInteger spotifyId){
        
        Optional<Spotify> spotify = spotifyRepository.findById(spotifyId);

        if (spotify.isPresent()){
            spotifyRepository.deleteById(spotifyId);
            return true;

        } else{
            return false;
        }
    }

    // UPDATE
    public Spotify updateSpotify(BigInteger spotifyId, Spotify spotify){

        Spotify data = spotifyRepository.findById(spotifyId).get();
        data.setTrackName(spotify.getTrackName());
        data.setArtistName(spotify.getArtistName());
        data.setGenre(spotify.getGenre());
        data.setPopularity(spotify.getPopularity());

        
        return spotifyRepository.save(data);                                
    }

    // Get By Query WITH Pagination
    public Page<Spotify> getByQuerySpotify(String trackName, String artistName, String genre, Integer popularity, int page, int pageSize){
        
        Spotify SpotifyMatch = new Spotify();
        SpotifyMatch.setTrackName(trackName);
        SpotifyMatch.setArtistName(artistName);
        SpotifyMatch.setGenre(genre);
        SpotifyMatch.setPopularity(popularity);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
        Example<Spotify> example = Example.of(SpotifyMatch, matcher);

        Pageable pageable = PageRequest.of(page, pageSize);


        return spotifyRepository.findAll(example,pageable);
    }

    
    // public Optional<Spotify> deleteSpotify(BigInteger spotifyId){
        
    //     return spotifyRepository.deleteById(spotifyId);
    // }
    
    // Get By Query WITHOUT Pagination
    // public List<Spotify> getByQuerySpotify(
    //     String trackName,
    //     String artistName,
    //     String genre,
    //     Integer popularity,
    // ){
        
    //     //search by Query Parameter
    //     Spotify SpotifyMatch = new Spotify();
    //     SpotifyMatch.setTrackName(trackName);
    //     SpotifyMatch.setArtistName(artistName);
    //     SpotifyMatch.setGenre(genre);
    //     SpotifyMatch.setPopularity(popularity);

    //     ExampleMatcher matcher = ExampleMatcher
    //             .matchingAll()
    //             .withIgnoreCase()
    //             .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
    //     Example<Spotify> example = Example.of(SpotifyMatch, matcher);

    //     return spotifyRepository.findAll(example);
    // }


    // DELETE
    // public void deleteSpotify(BigInteger spotifyId){
    //     spotifyRepository.deleteById(spotifyId);
    // }

    
}
