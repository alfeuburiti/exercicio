CREATE TABLE dbo.AVALIADOR
	(
	id int NOT NULL IDENTITY (1, 1),
	especialidade varchar(50) NOT NULL,

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.AVALIADOR ADD CONSTRAINT
	PK_AVALIADOR PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

	
INSERT INTO AVALIADOR (especialidade) 
	VALUES('ENGENHEIRO - AREA MORRO')
INSERT INTO AVALIADOR (especialidade) 
	VALUES('ENGENHEIRO - AREA ALAGADOS')
INSERT INTO AVALIADOR (especialidade) 
	VALUES('ASSISTENTE SOCIAL')
INSERT INTO AVALIADOR (especialidade) 
	VALUES('PSICOLOGO')
INSERT INTO AVALIADOR (especialidade) 
	VALUES('GEOLOGO')
GO
