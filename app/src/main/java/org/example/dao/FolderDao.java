package org.example.dao;

import java.util.UUID;

import org.example.mappers.FolderInsert;
import org.example.mappers.FolderMapper;
import org.example.model.Folder;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.AllowUnusedBindings;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FolderDao {
    // c means child(child task)
    @AllowUnusedBindings
    @SqlQuery("SELECT f.id AS id, f.name AS name, " +
            "ARRAY_AGG(DISTINCT ROW(child_folder.name, child_folder.id)) AS folders, " +
            "CASE WHEN COUNT(child_file.id) > 0 THEN ARRAY_AGG(DISTINCT ROW(child_file.name, child_file.id)) ELSE ARRAY[]::record[] END AS files "
            +
            "FROM folder f " +
            // "LEFT JOIN task child_file ON child_file.parent = f.id " +
            // "LEFT JOIN folder child_folder ON child_folder.parent = f.id " +
            "LEFT JOIN task child_file ON child_file.parent = :id::UUID " +
            "LEFT JOIN folder child_folder ON child_folder.parent = :id::UUID " +
            // "WHERE (:id IS NULL AND f.id IS NULL) OR f.id = :id::UUID " +
            "WHERE (:id::UUID IS NULL AND f.parent IS NULL) OR (:id::UUID IS NOT NULL AND f.id = :id::UUID) " +
            "GROUP BY f.id, f.name;")
    @RegisterRowMapper(FolderMapper.class)
    Folder getOneFolder(@Bind("id") UUID id);

    @SqlUpdate("INSERT INTO folder (parent, name, owner) VALUES (:parent, :name, :owner) RETURNING *")
    @GetGeneratedKeys
    @RegisterRowMapper(FolderInsert.class)
    Folder createFolder(@Bind("parent") UUID parent, @Bind("name") String name, @Bind("owner") UUID owner);

    // @AllowUnusedBindings
    // @SqlQuery("SELECT f.id AS id, f.name AS name, " +
    // "COUNT(DISTINCT ROW(cfolder.id)) AS folders, " +
    // "COUNT(DISTINCT ROW(cfile.id)) AS files " +
    // "FROM folder f " +
    // "LEFT JOIN task cfile ON f.id = cfile.parent " +
    // "LEFT JOIN folder cfolder ON f.id = cfolder.parent " +
    // // "WHERE f.parent = CASE WHEN :id IS NULL THEN NULL ELSE :id::UUID END " +
    // // "WHERE (f.parent = :id::UUID OR (:id IS NULL AND f.parent IS NULL)) " +
    // "WHERE f.parent IS NULL " +
    // "GROUP BY f.id, f.name")
    // @RegisterRowMapper(FolderMapper.class)
    // Folder[] getChildrenOf(@Bind("id") UUID id);
}