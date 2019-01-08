CREATE TABLE dbo.LONA
	(
	id int NOT NULL IDENTITY (1, 1),
	situacao bit,
	data varchar(20),
	justificativa varchar(50),
	metragem int,
	quantidade_pontos int
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.LONA ADD CONSTRAINT
	PK_LONA PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

INSERT INTO LONA (
	situacao,
	data,
	justificativa,
	metragem,
	quantidade_pontos) 
	VALUES (1, '2012-06-18 10:34:09', 'Sem justificativa', 100, 1)

