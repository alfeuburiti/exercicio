CREATE TABLE dbo.bairro
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.bairro ADD CONSTRAINT
	PK_bairro PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO Bairro (descricao) 
	VALUES('Boa Viagem')
INSERT INTO Bairro (descricao) 
	VALUES('Pina')
INSERT INTO Bairro (descricao) 
	VALUES('Casa Amarela')
INSERT INTO Bairro (descricao) 
	VALUES('Recife Antigo')
GO
