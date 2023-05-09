package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.entity.Photo;
import cz.upce.nnpia.sem.repository.PhotoRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/photo")
@CrossOrigin
public class PhotoController {
    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable Integer id){
        try{
            Photo photo = photoRepository.getById(id);
            ByteArrayResource resource = new ByteArrayResource(photo.getPhoto());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, photo.getPhotoType())
                    .body(resource);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body("Photo not found");
        }

    }
}
