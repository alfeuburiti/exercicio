CREATE TABLE dbo.REGIONAL
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.REGIONAL ADD CONSTRAINT
	PK_REGIONAL PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO REGIONAL (descricao) 
	VALUES('PLANICIE')
INSERT INTO REGIONAL (descricao) 
	VALUES('GART')
INSERT INTO REGIONAL (descricao) 
	VALUES('PLANO')
INSERT INTO REGIONAL (descricao) 
	VALUES('NORTE')
GO
