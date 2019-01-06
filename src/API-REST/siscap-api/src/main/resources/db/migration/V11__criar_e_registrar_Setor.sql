CREATE TABLE dbo.SETOR
	(
	id int NOT NULL IDENTITY (1, 1),area
	area varchar(50) NOT NULL,

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.SETOR ADD CONSTRAINT
	PK_SETOR PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO SETOR (area) 
	VALUES('SETOR A')
INSERT INTO SETOR (area) 
	VALUES('SETOR AB')
INSERT INTO SETOR (area) 
	VALUES('SETOR A3')
INSERT INTO SETOR (area) 
	VALUES('SETOR A4')
GO