package org.example.model;

import java.util.UUID;

public record Folder(String name, UUID id, Folder[] folders, Task[] files) {
}