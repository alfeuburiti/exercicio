CREATE TABLE dbo.TIPO_SOLICITACAO
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.TIPO_SOLICITACAO ADD CONSTRAINT
	PK_TIPO_SOLICITACAO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO TIPO_SOLICITACAO (descricao) 
	VALUES('TIPO SOLICITACAO A')
INSERT INTO TIPO_SOLICITACAO (descricao) 
	VALUES('TIPO SOLICITACAO AB')
INSERT INTO TIPO_SOLICITACAO (descricao) 
	VALUES('TIPO SOLICITACAO A3')
INSERT INTO TIPO_SOLICITACAO (descricao) 
	VALUES('TIPO SOLICITACAO A4')
GO
