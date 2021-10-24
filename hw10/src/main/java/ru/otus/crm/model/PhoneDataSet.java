package ru.otus.crm.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class PhoneDataSet {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String number;

    @ManyToOne
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
