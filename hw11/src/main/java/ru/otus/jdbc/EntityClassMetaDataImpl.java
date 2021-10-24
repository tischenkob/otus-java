package ru.otus.jdbc;

import ru.otus.core.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNullElseGet;
import static java.util.stream.Collectors.toUnmodifiableList;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> entityClass;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> fields;

    public EntityClassMetaDataImpl(Class<T> instance) {
        entityClass = instance;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        constructor = requireNonNullElseGet(constructor, () -> {
            try {
                Class<?>[] types = getAllFields().stream()
                        .map(Field::getType)
                        .toArray(Class[]::new);
                return entityClass.getConstructor(types);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return constructor;
    }

    @Override
    public Field getIdField() {
        Predicate<Field> withIdAnnotation = f -> f.getAnnotation(Id.class) != null;
        idField = requireNonNullElseGet(idField, () -> getAllFields().stream()
                .filter(withIdAnnotation)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Class %s doesn't have @Id", getName()))));
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        fields = requireNonNullElseGet(fields, () -> Arrays.asList(entityClass.getDeclaredFields()));
        return Collections.unmodifiableList(fields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        Predicate<Field> withoutIdAnnotation = f -> !f.equals(getIdField());
        return getAllFields().stream().filter(withoutIdAnnotation).collect(toUnmodifiableList());
    }
}
