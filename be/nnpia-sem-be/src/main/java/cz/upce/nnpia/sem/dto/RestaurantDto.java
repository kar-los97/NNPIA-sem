package cz.upce.nnpia.sem.dto;

import org.springframework.web.multipart.MultipartFile;

public class RestaurantDto {
    private int id;
    private String address;
    private String name;
    private String note;
    private int adminId;

    private MultipartFile titlePhoto;

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

    public MultipartFile getTitlePhoto() {
        return titlePhoto;
    }

    public void setTitlePhoto(MultipartFile titlePhoto) {
        this.titlePhoto = titlePhoto;
    }
}
