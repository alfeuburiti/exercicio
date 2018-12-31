CREATE TABLE dbo.MICROREGIAO
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.MICROREGIAO ADD CONSTRAINT
	PK_MICROREGIAO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO MICROREGIAO (descricao) 
	VALUES('REGIAO 1')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('REGIAO 2')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('REGIAO 3')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('REGIAO 4')
GO
