package br.edu.imepac.message.manager.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDatabaseCRUD<T> {

    int delete(Long id) throws SQLException;

    T read(Long id) throws SQLException;

    int save(T entity) throws SQLException;

    int update(T entity) throws SQLException;

    ArrayList<T> findAll() throws SQLException;

}
