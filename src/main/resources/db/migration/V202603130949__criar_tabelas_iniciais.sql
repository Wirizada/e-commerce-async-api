CREATE TABLE IF NOT EXISTS produtos (
    id UUID PRIMARY KEY,
    categoria VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(38,2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id UUID PRIMARY KEY,
    produto_id UUID NOT NULL,
    quantidade_comprada INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL CHECK (status IN ('PROCESSANDO','APROVADO','REJEITADO'))
);