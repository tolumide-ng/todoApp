package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.Database;
import org.example.utils.Folder;

public class FolderDao implements Helper {
    public List<Folder> getOneFolder(UUID folderId) throws SQLException {
        try (
                Connection conn = Database.connect();
                PreparedStatement st = conn.prepareStatement("""
                            SELECT f.id AS id, f.name AS name,
                            CASE
                                WHEN t.parent_id = ? AND COUNT(t.id) > 0
                                THEN ARRAY_AGG(ROW(t.name, t.id))  ELSE NULL
                            END AS children
                            FROM folder f
                            LEFT JOIN task t ON f.id = t.parent_id
                            WHERE  f.parent = ? OR f.id = ?
                            GROUP BY f.id, t.parent_id;
                        """)) {
            st.setObject(1, folderId);
            st.setObject(2, folderId);
            ResultSet rs = st.executeQuery();

            List<Folder> content = new ArrayList<>();

            while (rs.next()) {
                Folder folder = this.getFolder(rs);
                content.add(folder);
            }

            rs.close();
            return content;
        } catch (SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
}
