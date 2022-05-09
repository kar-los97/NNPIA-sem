package cz.upce.nnpia.sem.entity;

import javax.persistence.*;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer stars;

    @Column
    private String comment;

    @ManyToOne
    @Column(nullable = false,name = "userId")
    private User user;

    @ManyToOne
    @Column(name="restaurantId")
    private Restaurant restaurant;

}
