package edu.cc.carrusel.images;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import edu.cc.carrusel.comments.CommentsEntity;

@Entity
@Table(name="images")
public class ImagesEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    private String title;
    private String descrip; 
	private String bucket;
	private String name;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentsEntity> comments;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return this.descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    /*
    public List<CommentsEntity> getComments() {
        return this.comments;
    }

    public void setComments(List<CommentsEntity> comments) {
        this.comments = comments;
    }
     */

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
}