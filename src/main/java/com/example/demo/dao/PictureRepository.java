package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.dto.Picture;

public interface PictureRepository extends CrudRepository<Picture, String>{

}
