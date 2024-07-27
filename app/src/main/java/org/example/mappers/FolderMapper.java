package org.example.mappers;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.model.Folder;
import org.example.model.Task;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FolderMapper implements RowMapper<Folder> {
    @Override
    public Folder map(ResultSet rs, StatementContext ctx) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("[[[[[[[>>>><<<<]]]]]]]" + metaData);

        for (int i = 1; i <= columnCount; i++) {
            System.out.println("!!!!!!@@@@@@@!!!!!!!!\n");
            String key = metaData.getColumnName(i);
            Object value = rs.getObject(i);

            System.out.print(">>>>>>>>>>>>>>" + key + ": " + value + " ");
        }
        System.out.println(
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n\n");

        String name = rs.getString("name");
        List<Folder> folders = Arrays.asList(rs.getArray("folders")).stream().map(folder -> parseFolder(folder))
                .collect(Collectors.toList());

        Task[] jsonFiles = parseFiles(rs.getString("files"));
        UUID id = UUID.fromString(rs.getString("id"));

        return new Folder(name, id, folders, jsonFiles);
        // return new Folder(null, null, null);
    }

    private Task[] parseFiles(String jsonFiles) {
        // Parse the JSON array to create File objects
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println("the received jsonFiles: " + jsonFiles);
            return objectMapper.readValue("", Task[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON files " + e);
        }
    }

    private Folder parseFolder(Array jsonFiles) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonFiles, Folder.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON files " + e);
        }
    }
}

// import java.sql.Array;
// import java.sql.SQLException;
// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;

// public record CustomType(int id, String name) {

// public static List<CustomType> fromSqlArray(Array sqlArray) throws
// SQLException {
// if (sqlArray == null) {
// return List.of();
// }

// Object[] array = (Object[]) sqlArray.getArray();
// return Arrays.stream(array)
// .map(obj -> {
// // Assuming each element is a String formatted as "id,name"
// String[] parts = obj.toString().split(",");
// int id = Integer.parseInt(parts[0]);
// String name = parts[1];
// return new CustomType(id, name);
// })
// .collect(Collectors.toList());
// }
// }

// java.sql.Array sqlArray = resultSet.getArray("your_array_column");

// // Use the factory method to convert the SQL array to a list of CustomType
// List<CustomType> customTypeList = CustomType.fromSqlArray(sqlArray);