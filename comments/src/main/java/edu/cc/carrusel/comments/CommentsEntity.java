package edu.cc.carrusel.comments;

import java.io.Serializable;
import javax.persistence.*;

import edu.cc.carrusel.images.ImagesEntity;

@Entity
@Table(name="comments")
public class CommentsEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    private String texto;

    @ManyToOne()
    @JoinColumn(name = "image_id")
    private ImagesEntity image;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ImagesEntity getImage() {
        return this.image;
    }

    public void setImage_id(ImagesEntity image) {
        this.image = image;
    }





}