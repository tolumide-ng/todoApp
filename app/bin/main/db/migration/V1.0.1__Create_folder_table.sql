CREATE TABLE folder (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    parent UUID,
    name TEXT,
    owner UUID NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(name, parent, owner)
)