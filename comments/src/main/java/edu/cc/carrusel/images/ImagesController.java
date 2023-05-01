package edu.cc.carrusel.images;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cc.carrusel.comments.CommentsEntity;
import edu.cc.carrusel.images.ImagesEntity;
import edu.cc.carrusel.images.ImagesService;

import edu.cc.carrusel.comments.CommentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@Slf4j
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
	
	@GetMapping("/add")
	public String add(ImagesEntity img, Model model) {
		model.addAttribute("img", img);
		return "form_image";
	}
	
	@PostMapping("/save")
	public String save(ImagesEntity img) {
		imgService.guardarImage(img);
		
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ImagesEntity img, Model model) {
		ImagesEntity image = imgService.findImage(img);
		
		model.addAttribute("img", image);
		return "form_image";
	}
	
	@GetMapping("/delete")
	public String delete(ImagesEntity img){
		//log.info("Ejecutando método delete en controlador UserController");
		imgService.eliminarImage(img);
		return "redirect:/";
	}

	@GetMapping("/see/{id}")
	public String see(ImagesEntity img, Model model) {
		ImagesEntity image = imgService.findImage(img);

		// Lista de comentarios
		List<CommentsEntity> comments = commentsService.findCommentsImagen(img); //imgService.listImages();

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
		log.info("ID:  "+comment.getId());
	
		return "redirect:/see/"+comment.getImage().getId();
	}
	
}