package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "OBSEQUIOS")
public class Obsequio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOBSEQUIO")
    private Long id;

}
