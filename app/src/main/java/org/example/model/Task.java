package org.example.model;

import java.util.UUID;

public record Task(String name, UUID id, UUID parent, String extension) {
}
