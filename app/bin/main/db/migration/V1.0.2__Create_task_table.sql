CREATE TYPE status AS ENUM ('rest', 'inprogress', 'paused', 'completed', 'cancelled');

CREATE TABLE task (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    parent UUID NOT NULL,
    name TEXT,
    status status,
    FOREIGN KEY (parent)  REFERENCES folder(id) ON DELETE CASCADE,
    UNIQUE(name, parent)
)