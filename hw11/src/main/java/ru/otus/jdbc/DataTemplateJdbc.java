package ru.otus.jdbc;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static ru.otus.core.sessionmanager.TransactionRunnerJdbc.wrapException;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> classMetaData;
    private final Function<ResultSet, T> resultSetToEntity;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> classMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.classMetaData = classMetaData;

        this.classMetaData.getAllFields().forEach(field -> field.setAccessible(true));

        this.resultSetToEntity = resultSet -> wrapException(() -> {
            Function<Field, Object> fieldToEntity = field -> wrapException(() ->
                    resultSet.getObject(field.getName(), field.getType()));
            Object[] fieldValues = this.classMetaData.getAllFields().stream().map(fieldToEntity).toArray();
            return classMetaData.getConstructor().newInstance(fieldValues);
        });
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String sql = entitySQLMetaData.getSelectByIdSql();
        return dbExecutor.executeSelect(connection, sql, List.of(id),
                resultSet -> wrapException(() -> {
                    if (!resultSet.next()) return null;
                    return resultSetToEntity.apply(resultSet);
                }));
    }

    @Override
    public List<T> findAll(Connection connection) {
        String sql = entitySQLMetaData.getSelectAllSql();
        return dbExecutor.executeSelect(connection, sql, emptyList(),
                resultSet -> wrapException(() -> {
                    List<T> entities = new ArrayList<>();
                    while (resultSet.next()) entities.add(resultSetToEntity.apply(resultSet));
                    return unmodifiableList(entities);
                })).orElse(emptyList());
    }

    @Override
    public long insert(Connection connection, T entity) {
        String sql = entitySQLMetaData.getInsertSql();
        List<Object> values = getFieldsWithoutIdValues(entity);
        return dbExecutor.executeStatement(connection, sql, values);
    }

    @Override
    public void update(Connection connection, T entity) {
        String sql = entitySQLMetaData.getUpdateSql();
        List<Object> values = getFieldsWithoutIdValues(entity);
        values.add(wrapException(() -> this.classMetaData.getIdField().get(entity)));
        dbExecutor.executeStatement(connection, sql, values);
    }

    private List<Object> getFieldsWithoutIdValues(T entity) {
        Function<Field, Object> fieldToValue = field -> wrapException(() -> field.get(entity));
        return this.classMetaData.getFieldsWithoutId().stream().map(fieldToValue).collect(toList());
    }
}
