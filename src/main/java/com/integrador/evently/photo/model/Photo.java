package com.integrador.evently.photo.model;


import com.integrador.evently.activities.model.Activity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

}
