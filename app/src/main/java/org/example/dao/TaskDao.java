package org.example.dao;

import java.util.UUID;

import org.example.mappers.TaskInsert;
import org.example.mappers.TaskMapper;
import org.example.model.Task;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TaskDao {
    @SqlUpdate("INSERT INTO task (parent, name) VALUES (:parent, :name) RETURNING *")
    @GetGeneratedKeys
    @RegisterRowMapper(TaskMapper.class)
    Task createTask(@Bind("parent") UUID parent, @Bind("name") String name);

    @SqlQuery("DELETE FROM task WHERE id = :id")
    @RegisterRowMapper(TaskMapper.class)
    void deleteTask(@Bind("id") UUID id);

    @SqlQuery("UPDATE task SET name = :name, parent = :parent WHERE id=:id RETURNING *")
    @RegisterRowMapper(TaskInsert.class)
    Task updateTask(@Bind("name") String name, @Bind("id") UUID id, @Bind("parent") UUID parent);
}
