package ru.otus.crm.dto;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;

@Getter
@RequiredArgsConstructor
public class ClientViewDto {

    private final long id;
    private final String name;
    private final String street;
    private final String numbers;

    public Client toClient() {
        final Client client = new Client();
        client.setName(this.getName());
        client.setAddressDataSet(new AddressDataSet(this.getStreet()));
        Set<PhoneDataSet> numbers = Arrays.stream(this.getNumbers().split(","))
                                          .map(String::trim)
                                          .map(PhoneDataSet::new)
                                          .collect(toSet());
        client.setPhoneDataSets(numbers);
        return client;
    }

    public ClientViewDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.street = client.getAddressDataSet() == null ? null
                : client.getAddressDataSet().getStreet();
        this.numbers = client.getPhoneDataSets() == null ? null
                : client.getPhoneDataSets().stream()
                        .map(PhoneDataSet::getNumber)
                        .collect(joining(", "));
    }
}
