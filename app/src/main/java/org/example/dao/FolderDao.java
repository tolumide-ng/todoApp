package org.example.dao;

import java.util.UUID;

import org.example.mappers.FolderMapper;
import org.example.model.Folder;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

public interface FolderDao {
    // c means child(child task)
    @SqlQuery("SELECT f.id AS id, f.name AS name, " +
            "ARRAY_AGG(DISTINCT ROW(child_folder.name, child_folder.id)) AS folders, " +
            "CASE WHEN COUNT(child_file.id) > 0 THEN ARRAY_AGG(DISTINCT ROW(child_file.name, child_file.id)) ELSE ARRAY[]::record[] END AS files " +
            "FROM folder f " +
            "LEFT JOIN task child_file ON f.id = child_file.parent_id " +
            "LEFT JOIN folder child_folder ON child_folder.parent = f.id " +
            "WHERE f.id = :id " +
            "GROUP BY f.id, f.name;")
    // @UseRowMapper(FolderMapper.class)
    @RegisterRowMapper(FolderMapper.class)
    Folder getOneFolder(@Bind("id") UUID id);
}