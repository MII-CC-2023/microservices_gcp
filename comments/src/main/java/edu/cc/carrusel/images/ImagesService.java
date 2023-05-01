package edu.cc.carrusel.images;

import java.util.List;

import edu.cc.carrusel.images.ImagesEntity;

public interface ImagesService {

	public List<ImagesEntity> listImages();
	
	public void guardarImage(ImagesEntity img);
	
	public void eliminarImage(ImagesEntity img);
	
	public ImagesEntity findImage(ImagesEntity img);
	
}