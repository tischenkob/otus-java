package ru.otus.crm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
public class AddressDataSet implements Serializable {
    @Id
    private String street;

    @OneToOne(mappedBy = "addressDataSet", orphanRemoval = true)
    private Client client;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        setStreet(street);
    }
}
