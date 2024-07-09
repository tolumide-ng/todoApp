package org.example.dao;

import java.util.UUID;

import org.example.mappers.FolderMapper;
import org.example.model.Folder;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface FolderDao {
    // c means child(child task)
    @SqlQuery("SELECT f.id AS id, f.name AS name," +
            "CASE WHEN c.parent_id= :id AND COUNT(c.id) > 0 THEN ARRAY_AGG(ROW(c.name, c.id)) ELSE NULL END AS children FROM folder f"
            +
            "LEFT JOIN task c ON f.id = c.parent_id" +
            "WHERE f.parent = :id OR f.id = :id" +
            "GROUP BY f.id, t.parent_id;")
    @RegisterRowMapper(FolderMapper.class)
    Folder getOneFolder(@BindBean("id") UUID id);
}