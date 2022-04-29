package com.example.demo.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.HttpReponse;

import com.example.demo.models.Spotify;
import com.example.demo.services.SpotifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/spotify")
public class SpotifyController {

        @Autowired
        SpotifyService spotifyService;

        // Get All
        @GetMapping(value={""})
        public ResponseEntity<HttpReponse<List<Spotify>>> readSpotify() {

            List<Spotify> body = spotifyService.getSpotify();
            HttpReponse<List<Spotify>> apiResponse = new HttpReponse<>(
                HttpStatus.OK, body
            );
            
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        }

        @PutMapping(value="/{spotifyId}")
        public ResponseEntity<HttpReponse<Spotify>> putSpotify(@PathVariable(value = "spotifyId") BigInteger id, @RequestBody Spotify spotifyBody) {
            HttpStatus httpStatus;
            HttpReponse<Spotify> apiResponse;

            Optional<Spotify> body = spotifyService.getSpotifyId(id);

            if(body.isPresent()){
                httpStatus = HttpStatus.OK;
                Spotify dataBody = spotifyService.updateSpotify(id,spotifyBody);
                apiResponse = new HttpReponse<>(httpStatus, dataBody,"Success Update");
            } else{
                httpStatus = HttpStatus.NOT_FOUND;
                apiResponse = new HttpReponse<>(httpStatus);

            }
               
            return new ResponseEntity<>(apiResponse, httpStatus);
        }

        @PostMapping(value={""})
        public ResponseEntity<HttpReponse<Spotify>> postSpotify(@RequestBody Spotify spotifyBody) {

            Spotify body = spotifyService.createSpotify(spotifyBody);
            HttpReponse<Spotify> apiResponse = new HttpReponse<>(
                HttpStatus.OK, body, "Success Created"
            );
            
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        }

        @GetMapping(value="/{spotifyId}")
        public ResponseEntity<HttpReponse<Optional<Spotify>>> readSpotifyId(@PathVariable(value = "spotifyId") BigInteger id) {

            HttpStatus httpStatus;
            HttpReponse<Optional<Spotify>> apiResponse;

            Optional<Spotify> body = spotifyService.getSpotifyId(id);

            if(body.isPresent()){
                httpStatus = HttpStatus.OK;
                apiResponse = new HttpReponse<>(httpStatus, body);
            } else{
                httpStatus = HttpStatus.NOT_FOUND;
                apiResponse = new HttpReponse<>(httpStatus);

            }
            return new ResponseEntity<>(apiResponse, httpStatus);
        }
      
        //Get By Query WITH Pagination
        @GetMapping(value={"/search"})
        public ResponseEntity<HttpReponse<Page<Spotify>>> getQuerySpotify(
            @RequestParam(required = false) String trackName,
            @RequestParam(required = false) String artistName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer popularity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
        ) {

            Page<Spotify> body = spotifyService.getByQuerySpotify(trackName,artistName,genre,popularity,page,pageSize);
            HttpReponse<Page<Spotify>> apiResponse = new HttpReponse<>(
                HttpStatus.OK, body
            );         
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        @DeleteMapping(value = "/{spotifyId}")
        public ResponseEntity<HttpReponse<Boolean>> removeSpotify(
            @PathVariable(value = "spotifyId") BigInteger id
        ) {

            Boolean isDeleted = spotifyService.deleteSpotify(id);
            HttpStatus httpStatus;

            httpStatus = isDeleted ? HttpStatus.OK :HttpStatus.NOT_FOUND;

            HttpReponse<Boolean> apiResponse = new HttpReponse<>(
                httpStatus
            );
            return new ResponseEntity<>(apiResponse, httpStatus);
        }


}
