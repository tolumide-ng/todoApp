CREATE TYPE status AS ENUM ('rest', 'inprogress', 'paused', 'completed', 'cancelled');

CREATE TABLE task (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name TEXT,
    status status,
    FOREIGN KEY (id)  REFERENCES folder(id) ON DELETE CASCADE
)