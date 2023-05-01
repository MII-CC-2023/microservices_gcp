package edu.cc.carrusel.images;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cc.carrusel.images.ImageDAO;
import edu.cc.carrusel.images.ImagesEntity;
import edu.cc.carrusel.images.ImagesService;

@Service
public class ImagesServicesImpl implements ImagesService {

    @Autowired
    private ImageDAO imageDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<ImagesEntity> listImages() {
        List<ImagesEntity> listImages = (List<ImagesEntity>) imageDao.findAll();
        return listImages;
    }

    @Override
    @Transactional
    public void guardarImage(ImagesEntity img) {
        imageDao.save(img);
    }

    @Override
    @Transactional
    public void eliminarImage(ImagesEntity img) {
        imageDao.delete(img);
    }

    @Override
    @Transactional(readOnly = true)
    public ImagesEntity findImage(ImagesEntity img) {
        return imageDao.findById(img.getId()).orElse(null);
    }
}
