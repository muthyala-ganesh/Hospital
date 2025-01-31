package com.hospital.main.modelentity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="hospitals")
public class HospitalEntity {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String name;
private String location;
private double rating;
private String email;
}
