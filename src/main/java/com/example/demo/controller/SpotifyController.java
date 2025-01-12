package com.example.demo.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.demo.models.ResponseBody;
import com.example.demo.utility.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

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

@Slf4j
@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    @Autowired
    SpotifyService spotifyService;

    // Get All
    @GetMapping(value = {""})
    public ResponseEntity<ResponseBody<List<Spotify>>> readSpotify() {

        ResponseBody<List<Spotify>> apiResponse;

        try {
            List<Spotify> body = spotifyService.getSpotify();
            apiResponse = new ResponseBody<>(HttpStatus.OK, body);

        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

    }

    @PutMapping(value = "/{spotifyId}")
    public ResponseEntity<ResponseBody<Spotify>> putSpotify(@PathVariable(value = "spotifyId") BigInteger id, @RequestBody Spotify spotifyBody) {

        ResponseBody<Spotify> apiResponse;
        try{
            Optional<Spotify> body = spotifyService.getSpotifyId(id);

            if (body.isPresent()) {

                log.info("Data is present");
                Spotify dataBody = spotifyService.updateSpotify(id, spotifyBody);
                apiResponse = new ResponseBody<>(HttpStatus.OK, dataBody, "Success Update");

            } else {

                apiResponse = new ResponseBody<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping(value = {""})
    public ResponseEntity<ResponseBody<Spotify>> postSpotify(@RequestBody Spotify spotifyBody) {

        ResponseBody<Spotify> apiResponse;
        try{
            Spotify body = spotifyService.createSpotify(spotifyBody);
            apiResponse = new ResponseBody<>(HttpStatus.CREATED, body, "Success Created");
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

    }

    @GetMapping(value = "/{spotifyId}")
    public ResponseEntity<ResponseBody<Spotify>> readSpotifyId(@PathVariable(value = "spotifyId") BigInteger id) {

        ResponseBody<Spotify> apiResponse = null;
        try {
            Optional<Spotify> body = spotifyService.getSpotifyId(id);
            apiResponse = new ResponseBody<>(HttpStatus.OK, body.get());

        } catch (NoSuchElementException nse) {
            apiResponse = ErrorHandler.handleError(nse, HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    //Get By Query WITH Pagination
    @GetMapping(value = {"/search"})
    public ResponseEntity<ResponseBody<Page<Spotify>>> getQuerySpotify(
            @RequestParam(required = false) String trackName,
            @RequestParam(required = false) String artistName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer popularity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        ResponseBody<Page<Spotify>> apiResponse = null;
        try {
            Page<Spotify> body = spotifyService.getByQuerySpotify(trackName, artistName, genre, popularity, page, pageSize);
            apiResponse = new ResponseBody<>(HttpStatus.OK, body);

        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

    }

    @GetMapping(value = {"/global-search"})
    public ResponseEntity<ResponseBody<Page<Spotify>>> getGlobalSearchSpotify(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) List<String> column,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        ResponseBody<Page<Spotify>> apiResponse;
        try {
            Page<Spotify> body = spotifyService.getGlobalSearch(searchText, column, page, pageSize);
            apiResponse = new ResponseBody<>(HttpStatus.OK, body);
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);

        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @DeleteMapping(value = "/{spotifyId}")
    public ResponseEntity<ResponseBody<Boolean>> removeSpotify(
            @PathVariable(value = "spotifyId") BigInteger id
    ) {
        ResponseBody<Boolean> apiResponse = null;
        try {
            Boolean isDeleted = spotifyService.deleteSpotify(id);
            apiResponse = new ResponseBody<>(isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            apiResponse = ErrorHandler.handleError(e, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

    }


}
