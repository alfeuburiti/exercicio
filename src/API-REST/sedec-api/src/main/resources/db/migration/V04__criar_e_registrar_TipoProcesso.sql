CREATE TABLE dbo.TIPO_PROCESSO
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.TIPO_PROCESSO ADD CONSTRAINT
	PK_TIPO_PROCESSO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO TIPO_PROCESSO (descricao) 
	VALUES('ATENDIMENTO')
GO
