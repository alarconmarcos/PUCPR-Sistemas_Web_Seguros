CREATE TABLE IF NOT EXISTS empregado
(
    matricula VARCHAR(10)  NOT NULL,
    nome VARCHAR(256),
    admissao DATE,
    desligamento DATE,
    salario FLOAT,
    PRIMARY KEY (matricula)
);   
    
CREATE TABLE IF NOT EXISTS solicitacao
(
    id integer NOT NULL,
    data timestamp without time zone,
    descricao VARCHAR(256),
    situacao integer,   
    matricula VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id)    
);
   
    
CREATE TABLE IF NOT EXISTS usuario
(
    nome VARCHAR(256),    
    perfil integer,
    senha VARCHAR(256),  
    matricula VARCHAR(10),
    PRIMARY KEY (nome)    
);