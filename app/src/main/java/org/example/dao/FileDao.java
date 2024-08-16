package org.example.dao;

import java.util.UUID;

import org.example.mappers.FileInsert;
import org.example.mappers.FileMapper;
import org.example.model.File;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FileDao {
    @SqlUpdate("INSERT INTO task (parent, name) VALUES (:parent, :name) RETURNING *")
    @GetGeneratedKeys
    @RegisterRowMapper(FileMapper.class)
    File createFile(@Bind("parent") UUID parent, @Bind("name") String name);

    @SqlQuery("SELECT id, parent, name WHERE id=:id RETURNING *")
    @RegisterRowMapper(FileMapper.class)
    File getFile(@Bind("id") UUID id);

    @SqlQuery("DELETE FROM task WHERE id = :id")
    @RegisterRowMapper(FileMapper.class)
    void deleteFile(@Bind("id") UUID id);

    @SqlQuery("UPDATE task SET name = :name, parent = :parent WHERE id=:id RETURNING *")
    @RegisterRowMapper(FileInsert.class)
    File updateFile(@Bind("name") String name, @Bind("id") UUID id, @Bind("parent") UUID parent);
}
