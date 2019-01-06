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
	VALUES('REGIONAL 1')
INSERT INTO REGIONAL (descricao) 
	VALUES('REGIONAL 2')
INSERT INTO REGIONAL (descricao) 
	VALUES('REGIONAL 3')
INSERT INTO REGIONAL (descricao) 
	VALUES('REGIONAL 4')
GO