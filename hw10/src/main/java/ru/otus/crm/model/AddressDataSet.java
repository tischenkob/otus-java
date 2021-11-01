package ru.otus.crm.model;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
public class AddressDataSet {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String street;

    @OneToOne(mappedBy = "addressDataSet", orphanRemoval = true)
    private Client client;
}
