package cz.upce.nnpia.sem.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RestaurantDto {
    private int id;
    private String address;
    private String name;
    private String note;
    private int adminId;
    private int photoId;
    private List<EvaluationDto> evaluation;
    private int stars;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public List<EvaluationDto> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(List<EvaluationDto> evaluation) {
        this.evaluation = evaluation;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
