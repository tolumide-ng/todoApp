package org.example.utils;

import java.sql.ResultSet;
import java.util.UUID;

// public class Folder {
//     private final String name;
//     private final UUID id;
//     private final Task[] files;
//     private final Folder[] children;

//     public Folder(String name, UUID id, Task[] files, Folder[] children) {
//         this.name = name;
//         this.id = id;
//         this.files = files;
//         this.children = children;
//     }
// }

public record Folder(String name, UUID id, Task[] files) {
}