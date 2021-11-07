package ru.otus.crm.service;

import java.util.List;
import ru.otus.crm.model.Client;

public interface DBServiceClient {

    void saveClient(Client client);

    List<Client> findAll();
}
