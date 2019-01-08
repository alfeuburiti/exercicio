CREATE TABLE dbo.BAIRRO
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
	VALUES('BOA VIAGEM')
INSERT INTO Bairro (descricao) 
	VALUES('PINA')
INSERT INTO Bairro (descricao) 
	VALUES('CASA AMARELA')
INSERT INTO Bairro (descricao) 
	VALUES('RECIFE ANTIGO')
INSERT INTO Bairro (descricao) 
	VALUES('ESPINHEIRO')
INSERT INTO Bairro (descricao) 
	VALUES('AFLITOS')
INSERT INTO Bairro (descricao) 
	VALUES('AGUA FRIA')
INSERT INTO Bairro (descricao) 
	VALUES('AFOGADOS')	
INSERT INTO Bairro (descricao) 
	VALUES('ALTO BELA VISTA')
INSERT INTO Bairro (descricao) 
	VALUES('ALTO DO MANDU')
INSERT INTO Bairro (descricao) 
	VALUES('ALTO JOSÉ BONIFACIO')
INSERT INTO Bairro (descricao) 
	VALUES('APIPUCOS')
INSERT INTO Bairro (descricao) 
	VALUES('CASA FORTE')
INSERT INTO Bairro (descricao) 
	VALUES('AREIAS')
INSERT INTO Bairro (descricao) 
	VALUES('CAXANGA')
INSERT INTO Bairro (descricao) 
	VALUES('VARZEA')		
GO
