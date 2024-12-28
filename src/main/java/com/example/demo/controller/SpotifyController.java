package com.example.demo.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.ResponseBody;
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


@RestController
@RequestMapping("/spotify")
public class SpotifyController {

        @Autowired
        SpotifyService spotifyService;

        // Get All
        @GetMapping(value={""})
        public ResponseEntity<ResponseBody<List<Spotify>>> readSpotify() {

            List<Spotify> body = spotifyService.getSpotify();
            ResponseBody<List<Spotify>> apiResponse = new ResponseBody<>(HttpStatus.OK, body);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        }

        @PutMapping(value="/{spotifyId}")
        public ResponseEntity<ResponseBody<Spotify>> putSpotify(@PathVariable(value = "spotifyId") BigInteger id, @RequestBody Spotify spotifyBody) {

            ResponseBody<Spotify> apiResponse;
            Optional<Spotify> body = spotifyService.getSpotifyId(id);

            if (body.isPresent()) {

                Spotify dataBody = spotifyService.updateSpotify(id, spotifyBody);
                apiResponse = new ResponseBody<>(HttpStatus.OK, dataBody, "Success Update");

            } else {

                apiResponse = new ResponseBody<>(HttpStatus.NOT_FOUND);
            }
               
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }

        @PostMapping(value={""})
        public ResponseEntity<ResponseBody<Spotify>> postSpotify(@RequestBody Spotify spotifyBody) {

            Spotify body = spotifyService.createSpotify(spotifyBody);
            ResponseBody<Spotify> apiResponse = new ResponseBody<>(HttpStatus.OK, body, "Success Created");
            
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        }

        @GetMapping(value="/{spotifyId}")
        public ResponseEntity<ResponseBody<Optional<Spotify>>> readSpotifyId(@PathVariable(value = "spotifyId") BigInteger id) {

            ResponseBody<Optional<Spotify>> apiResponse;
            Optional<Spotify> body = spotifyService.getSpotifyId(id);

            if(body.isPresent()){

                apiResponse = new ResponseBody<>(HttpStatus.OK, body);

            } else{

                apiResponse = new ResponseBody<>(HttpStatus.NOT_FOUND);

            }

            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }
      
        //Get By Query WITH Pagination
        @GetMapping(value={"/search"})
        public ResponseEntity<ResponseBody<Page<Spotify>>> getQuerySpotify(
            @RequestParam(required = false) String trackName,
            @RequestParam(required = false) String artistName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer popularity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
        ) {

            Page<Spotify> body = spotifyService.getByQuerySpotify(trackName,artistName,genre,popularity,page,pageSize);
            ResponseBody<Page<Spotify>> apiResponse = new ResponseBody<>(HttpStatus.OK, body);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        }

        @GetMapping(value={"/global-search"})
        public ResponseEntity<ResponseBody<Page<Spotify>>> getGlobalSearchSpotify(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) List<String> column,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
        ) {

            Page<Spotify> body = spotifyService.getGlobalSearch(searchText,column,page,pageSize);
            ResponseBody<Page<Spotify>> apiResponse = new ResponseBody<>(HttpStatus.OK, body);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }

        @DeleteMapping(value = "/{spotifyId}")
        public ResponseEntity<ResponseBody<Boolean>> removeSpotify(
            @PathVariable(value = "spotifyId") BigInteger id
        ) {

            Boolean isDeleted = spotifyService.deleteSpotify(id);
            ResponseBody<Boolean> apiResponse = new ResponseBody<>(isDeleted ? HttpStatus.OK :HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        }


}
