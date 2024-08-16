package org.example.model;

import java.util.UUID;

public record File(String name, UUID id, UUID parent, String extension) {
}
