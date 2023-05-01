package edu.cc.carrusel.images;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.cc.carrusel.images.ImagesEntity;
import edu.cc.carrusel.images.ImagesService;
import edu.cc.carrusel.comments.CommentsEntity;
import edu.cc.carrusel.comments.CommentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;


@Controller
public class ImagesController {

	@Autowired
	private ImagesService imgService;

	@Autowired
	private CommentsService commentsService;

	@GetMapping("/")
	public String index(Model model) {
	    
	    List<ImagesEntity> imgs = imgService.listImages();
        model.addAttribute("images", imgs);
	    
		return "index";
	}

	@GetMapping("/see/{id}")
	public String see(ImagesEntity img, Model model) {
		ImagesEntity image = imgService.findImage(img);

		// Lista de comentarios
		List<CommentsEntity> comments = commentsService.findCommentsImagen(img);

		// Nuevo comentario
		CommentsEntity comment = new CommentsEntity();

		// Añadir datos al modelo para la vista
		model.addAttribute("img", image);
		model.addAttribute("comments", comments);
		model.addAttribute("comment", comment);
		return "see_image";
	}
	
	@PostMapping("/savecomment")
	public String savecomment(CommentsEntity comment) {

		commentsService.guardarComment(comment);
	
		return "redirect:/see/"+comment.getImage().getId();
	}
	
}