package ru.otus.crm.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class PhoneDataSet implements Serializable {
    @Id
    private String number;

    @ManyToOne
    private Client client;

    public PhoneDataSet(String number) {
        setNumber(number);
    }
}
