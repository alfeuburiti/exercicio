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
	VALUES('3.1')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('2.1')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('5.1')
INSERT INTO MICROREGIAO (descricao) 
	VALUES('2.2')
GO
