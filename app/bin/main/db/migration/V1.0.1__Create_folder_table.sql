CREATE TABLE folder {
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name TEXT
}