package br.com.devsdofuturobr.vendor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vend_id", nullable = false)
    private Vendor vendor;

    @Column(name = "prod_name", nullable = false, length = 100)
    private String name;

    @Column(name = "prod_price", nullable = false)
    private BigDecimal price;

    @Column(name = "prod_desc", columnDefinition = "TEXT")
    private String description;
}

