package ru.otus.crm.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "address_data_set_id")
    private AddressDataSet addressDataSet;

    @OneToMany(mappedBy = "client", fetch = EAGER)
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

    @Override
    public Client clone() {
        return new Client(this.id, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneDataSets, addressDataSet);
    }
}
