package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@EqualsAndHashCode(of = "idInventory")
@NoArgsConstructor
public class Inventory {
    @Id
    @Column(name = "id_inventory")
    @SequenceGenerator(name="inventory_id_inventory_seq2", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="inventory_id_inventory_seq2")
    private long idInventory;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "difference")
    private int difference;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id_provider_product")
    private ProviderProduct product;
}
