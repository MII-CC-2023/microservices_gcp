package edu.cc.carrusel.images;


import org.springframework.data.jpa.repository.JpaRepository;

import edu.cc.carrusel.images.ImagesEntity;

public interface ImageDAO extends JpaRepository<ImagesEntity, Integer>{

	// ya temdriamos los prinicpales métodos CRUD
	// y podemos crear nuestros propios métodos

}

