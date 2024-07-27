package org.example.model;

import java.util.List;
import java.util.UUID;

public record Folder(String name, UUID id, List<Folder> folders, Task[] files) {
}