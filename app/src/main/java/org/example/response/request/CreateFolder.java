package org.example.response.request;

import java.util.UUID;

public record CreateFolder(String name, UUID parent, String owner) {}
