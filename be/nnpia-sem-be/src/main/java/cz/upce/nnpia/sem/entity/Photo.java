package cz.upce.nnpia.sem.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private Blob photo;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @Column
    private Date deletedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "restaurantId")
    private Restaurant restaurant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
