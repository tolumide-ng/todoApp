CREATE TABLE task (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    parent UUID NOT NULL,
    name TEXT NOT NULL,
    extension VARCHAR(64),
    FOREIGN KEY (parent)  REFERENCES folder(id) ON DELETE CASCADE,
    UNIQUE(name, parent)
)