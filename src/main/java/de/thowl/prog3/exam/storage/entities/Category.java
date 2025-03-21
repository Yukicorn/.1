package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    //@Size(min = 5, max = 30)
    private String name;

}
