package br.com.devsdofuturobr.vendor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vendors")
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vend_id")
    private Long id;

    @Column(name = "vend_name", nullable = false, length = 100)
    private String name;

    @Column(name = "vend_address", length = 255)
    private String address;

    @Column(name = "vend_city", length = 100)
    private String city;

    @Column(name = "vend_state", length = 50)
    private String state;

    @Column(name = "vend_zip", length = 20)
    private String zip;

    @Column(name = "vend_country", length = 50)
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}
