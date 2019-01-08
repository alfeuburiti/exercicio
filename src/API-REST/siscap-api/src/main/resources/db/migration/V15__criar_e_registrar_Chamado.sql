CREATE TABLE dbo.CHAMADO
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50)

	)  ON [PRIMARY]
GO

ALTER TABLE dbo.CHAMADO ADD CONSTRAINT
	PK_CHAMADO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO


INSERT INTO CHAMADO(descricao) VALUES ('chamado 1')
		
