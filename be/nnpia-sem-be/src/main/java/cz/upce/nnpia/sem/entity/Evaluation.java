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
    @JoinColumn(nullable = false,name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="restaurantId")
    private Restaurant restaurant;

}
