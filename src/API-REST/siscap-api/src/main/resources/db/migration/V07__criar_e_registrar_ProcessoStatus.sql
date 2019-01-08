CREATE TABLE dbo.PROCESSO_STATUS
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.PROCESSO_STATUS ADD CONSTRAINT
	PK_PROCESSO_STATUS PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO PROCESSO_STATUS (descricao) 
	VALUES('ARQUIVADO')
INSERT INTO PROCESSO_STATUS (descricao) 
	VALUES('TRAMITACAO')
INSERT INTO PROCESSO_STATUS (descricao) 
	VALUES('CONCLUIDO')
GO
