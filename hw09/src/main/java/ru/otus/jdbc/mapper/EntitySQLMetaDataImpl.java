package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static java.lang.String.format;
import static java.lang.String.join;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final String SELECT_ALL;
    private final String SELECT_ID;
    private final String INSERT;
    private final String UPDATE;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classData) {
        String tableName = classData.getName().toLowerCase();
        SELECT_ALL = format("select * from %s;", tableName);

        String idFieldName = classData.getIdField().getName();
        SELECT_ID = format("select * from %s where %s = ?;", tableName, idFieldName);

        String valuesTemplate = generateInsertValuesTemplate(classData.getFieldsWithoutId().size());
        String columnNames = generateInsertColumnNames(classData.getFieldsWithoutId());
        INSERT = format("insert into %s (%s) values (%s);", tableName, columnNames, valuesTemplate);

        String columnsTemplate = generateUpdateColumnsTemplate(classData.getFieldsWithoutId());
        UPDATE = format("update %s set (%s) where id = ?", tableName, columnsTemplate);
    }

    @Override
    public String getSelectAllSql() {
        return SELECT_ALL;
    }

    @Override
    public String getSelectByIdSql() {
        return SELECT_ID;
    }

    @Override
    public String getInsertSql() {
        return INSERT;
    }

    @Override
    public String getUpdateSql() {
        return UPDATE;
    }

    private String generateInsertValuesTemplate(int amount) {
        String[] values = new String[amount];
        Arrays.fill(values, "?");
        return join(", ", values);
    }

    private String generateUpdateColumnsTemplate(List<Field> fields) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Field field : fields) joiner.add(format("%s = ?", field.getName()));
        return joiner.toString();
    }

    private String generateInsertColumnNames(List<Field> fields) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Field field : fields) joiner.add(field.getName());
        return joiner.toString();
    }

}
