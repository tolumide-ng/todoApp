package org.example.model;

import java.util.UUID;

import org.example.DataType;

public record Task(String name, UUID id, UUID parent_id, String extension, DataType type) {};

// public class Task {
//     private final String name;
//     private final UUID id;
//     private final UUID parent_id;
//     private final String extension;
//     private final DataType type;

//     public Task(String name, UUID id, UUID parent_id, String extension, DataType type) {
//         this.name = name;
//         this.id = id;
//         this.parent_id = parent_id;
//         this.extension = extension;
//         this.type = type;
//     }

//     public UUID getParentId() {
//         return this.parent_id;
//     }
// }