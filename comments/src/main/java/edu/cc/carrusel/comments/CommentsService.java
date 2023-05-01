package edu.cc.carrusel.comments;

import java.util.List;

import edu.cc.carrusel.comments.CommentsEntity;
import edu.cc.carrusel.images.ImagesEntity;;

public interface CommentsService {

	public List<CommentsEntity> listComments();
	
	public void guardarComment(CommentsEntity comment);
	
	public void eliminarComment(CommentsEntity comment);
	
	public CommentsEntity findComment(CommentsEntity comment);

	public List<CommentsEntity> findCommentsImagen(ImagesEntity img);
	
}