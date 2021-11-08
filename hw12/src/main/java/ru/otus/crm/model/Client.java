package ru.otus.crm.model;


import static java.util.stream.Collectors.*;
import static javax.persistence.FetchType.EAGER;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_data_set_id")
    private AddressDataSet addressDataSet;

    @OneToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Set<PhoneDataSet> phoneDataSets = new HashSet<>();

    public Client() {
        this(null, null);
    }

    public Client(String name) {
        this(null, name);
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Client from(Client.DTO dto) {
        final Client client = new Client();
        client.setName(dto.name);
        client.setAddressDataSet(new AddressDataSet(dto.street));
        Set<PhoneDataSet> numbers = Arrays.stream(dto.numbers.split(","))
                                          .map(String::trim)
                                          .map(PhoneDataSet::new)
                                          .collect(toSet());
        client.setPhoneDataSets(numbers);
        return client;
    }

    public Client.DTO toDTO() {
        String street = addressDataSet == null ? null : addressDataSet.getStreet();
        String numbers = phoneDataSets == null ? null
                : phoneDataSets.stream()
                               .map(PhoneDataSet::getNumber)
                               .collect(joining(", "));
        return new DTO(this.id, this.name, street, numbers);
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, addressDataSet, phoneDataSets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneDataSets, addressDataSet);
    }

    @Getter
    @RequiredArgsConstructor
    public static class DTO {

        private final long id;
        private final String name;
        private final String street;
        private final String numbers;
    }
}
