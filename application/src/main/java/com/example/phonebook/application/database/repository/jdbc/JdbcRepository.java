package com.example.phonebook.application.database.repository.jdbc;

import com.example.phonebook.application.database.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class JdbcRepository<T, ID> implements Repository<T, ID> {

    private static final String SELECT_ALL_SQL = "SELECT * FROM  %s.%s";

    protected static final String SELECT_BY_ID_SQL = "SELECT * FROM  %s.%s WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO %s.%s(%s) VALUES (%S)";
    private static final String UPDATE_SQL = "UPDATE %s.%s SET %s WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM %s.%s WHERE id = ?";

    private final RowMapper<T> rowMapper;
    private final String tableName;
    private final String databaseSchema;
    private final List<String> entityProperties;

    protected JdbcTemplate jdbcTemplate;

    protected JdbcRepository(RowMapper<T> rowMapper, String tableName, String databaseSchema, List<String> entityProperties) {
        this.rowMapper = rowMapper;
        this.tableName = tableName;
        this.databaseSchema = databaseSchema;
        this.entityProperties = entityProperties;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(
                String.format(SELECT_ALL_SQL, databaseSchema, tableName),
                rowMapper);
    }

    @Override
    public List<T> findAll(Specification<T> var1) {
        return null;
    }

    @Override
    public Optional<T> findById(ID var1) {
        T result = jdbcTemplate.queryForObject(
                String.format(SELECT_BY_ID_SQL, databaseSchema, tableName),
                rowMapper,
                var1);
        return Objects.nonNull(result) ? Optional.of(result) : Optional.empty();
    }

    @Override
    public <S extends T> S save(S var1) {
        List<Object> values = entityProperties.stream()
                .map(fn -> getValue(var1, fn))
                .collect(Collectors.toList());
        Integer id = (Integer) getValue(var1, "id");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        boolean isInsert = Objects.isNull(id) || id == 0;
        String sql = isInsert
                ? String.format(INSERT_SQL, databaseSchema, tableName, String.join(", ", entityProperties), String.join(", ", Collections.nCopies(entityProperties.size(), "?")))
                : String.format(UPDATE_SQL, databaseSchema, tableName, entityProperties.stream().collect(Collectors.joining(" = ?, ",""," = ? ")));
        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql,  new String[]{"id"});

            IntStream.rangeClosed(1, values.size()).forEach(i -> setValue(ps, i, values.get(i - 1)));

            if (!isInsert) {
                ps.setInt(values.size() + 1, id);
            }
            return ps;
        }, keyHolder);

        return (S) findById((ID) keyHolder.getKey()).orElse(null);
    }

    private Object getValue(Object o, String propertyName) {
        try {
            Field field = o.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            return field.get(o);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

    private void setValue(PreparedStatement ps, int i, Object value) {
        try {
            if (value instanceof String) {
                ps.setString(i, (String) value);
            } else {
                ps.setInt(i, (int) value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> var1) {
        List<S> insertList = new ArrayList<>();
        List<S> updateList = new ArrayList<>();

        var1.forEach(s -> {
            if (Integer.valueOf(0).equals(getValue(s, "id"))) {
                insertList.add(s);
            } else {
                updateList.add(s);
            }
        });
        return Stream.of(insertAll(insertList), updateAll(updateList))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private <S extends T> List<S> insertAll(List<S> insertList) {
        String sql = String.format(INSERT_SQL, databaseSchema, tableName, String.join(", ", entityProperties), String.join(", ", Collections.nCopies(entityProperties.size(), "?")));
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List<Object> values = entityProperties.stream()
                        .map(fn -> getValue(insertList.get(i), fn))
                        .collect(Collectors.toList());
                IntStream.rangeClosed(1, values.size()).forEach(j -> setValue(ps, j, values.get(j - 1)));
            }

            @Override
            public int getBatchSize() {
                return insertList.size();
            }
        });

        return Collections.emptyList();
    }

    private <S extends T> List<S> updateAll(List<S> updateList) {
        String sql = String.format(UPDATE_SQL, databaseSchema, tableName, entityProperties.stream().collect(Collectors.joining(" = ?, ", "", " = ? ")));
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List<Object> values = entityProperties.stream()
                        .map(fn -> getValue(updateList.get(i), fn))
                        .collect(Collectors.toList());
                IntStream.rangeClosed(1, values.size()).forEach(j -> setValue(ps, j, values.get(j - 1)));

                ps.setInt( values.size() + 1, (Integer) getValue(updateList.get(i), "id"));
            }

            @Override
            public int getBatchSize() {
                return updateList.size();
            }
        });

        return Collections.emptyList();
    }

    @Override
    public void deleteById(ID var1) {
        jdbcTemplate.update(
                String.format(DELETE_SQL, databaseSchema, tableName),
                var1);
    }
}
