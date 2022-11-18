package com.example.demo.repositories;

import java.math.BigInteger;

import com.example.demo.models.Spotify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SpotifyRepository extends JpaRepository<Spotify, BigInteger>,JpaSpecificationExecutor<Spotify> {

}