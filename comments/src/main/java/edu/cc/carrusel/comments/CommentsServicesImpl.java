package edu.cc.carrusel.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cc.carrusel.comments.CommentsDAO;
import edu.cc.carrusel.comments.CommentsEntity;
import edu.cc.carrusel.comments.CommentsService;
import edu.cc.carrusel.images.ImagesEntity;
@Service
public class CommentsServicesImpl implements CommentsService {

    @Autowired
    private CommentsDAO commentDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<CommentsEntity> listComments() {
        List<CommentsEntity> listComments = (List<CommentsEntity>) commentDao.findAll();
        return listComments;
    }

    @Override
    @Transactional
    public void guardarComment(CommentsEntity comment) {
        commentDao.save(comment);
    }

    @Override
    @Transactional
    public void eliminarComment(CommentsEntity comment) {
        commentDao.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsEntity findComment(CommentsEntity comment) {
        return commentDao.findById(comment.getId()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentsEntity> findCommentsImagen(ImagesEntity img) {
        return commentDao.findByImage(img);
        
    }
}