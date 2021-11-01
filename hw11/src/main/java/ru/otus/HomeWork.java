package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceManagerImpl;
import ru.otus.jdbc.*;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        // Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);

        // Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        var client = dbServiceClient.saveClient(new Client("OTUS"));

        var timeBeforeDB = System.currentTimeMillis();
        var clientDB = dbServiceClient.getClient(client.getId()).orElseThrow(
                () -> new RuntimeException("Client not found, id:" + client.getId())
        );
        var timeAfterDB = System.currentTimeMillis();

        var clientCache = dbServiceClient.getClient(client.getId()).orElseThrow(
                () -> new RuntimeException("Client not found, id:" + client.getId())
        );
        var timeAfterCache = System.currentTimeMillis();

        if (clientDB != clientCache) throw new RuntimeException("Wrong client");
        var deltaDB =  timeAfterDB - timeBeforeDB;
        var deltaCache = timeAfterDB - timeAfterCache;

        if (deltaCache > deltaDB) throw new RuntimeException("Cache is not working!");
        log.info("Database: {}\t Cache: {}", deltaDB, deltaCache);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
