package edu.cc.carrusel.comments;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cc.carrusel.comments.CommentsEntity;
import edu.cc.carrusel.images.ImagesEntity;;

public interface CommentsDAO extends JpaRepository<CommentsEntity, Integer>{

	// ya temdriamos los prinicpales métodos CRUD
	// y podemos crear nuestros propios métodos

    List<CommentsEntity> findByImage(ImagesEntity image);


}
