package dao;

import java.sql.SQLException;

public interface DatabaseOperations<T> {
    void insert(T entity) throws SQLException;
    T read(String key) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(String key) throws SQLException;
}