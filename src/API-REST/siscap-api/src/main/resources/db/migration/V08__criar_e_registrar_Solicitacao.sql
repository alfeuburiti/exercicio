CREATE TABLE dbo.SOLICITACAO
	(
	id int NOT NULL IDENTITY (1, 1),
	descricao varchar(50) NOT NULL,
	ano varchar(4),
	mes varchar(2),
	momento	varchar(20),
	REGIONAL int,
	localidade varchar(30),
	bairro int,
	endereco varchar(30),
	roteiro varchar(30),
	RPA int,
	microregiao int,
	tiposolicitacao int NOT NULL,
	processo int NOT NULL

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.SOLICITACAO ADD CONSTRAINT
	PK_SOLICITACAO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO


ALTER TABLE [dbo].[SOLICITACAO]  WITH CHECK ADD  CONSTRAINT [FK_REGIONAL] FOREIGN KEY([REGIONAL])
REFERENCES [dbo].[REGIONAL] ([id])
GO

ALTER TABLE [dbo].[SOLICITACAO] CHECK CONSTRAINT [FK_REGIONAL]
GO

ALTER TABLE [dbo].[SOLICITACAO]  WITH CHECK ADD  CONSTRAINT [FK_BAIRRO] FOREIGN KEY([BAIRRO])
REFERENCES [dbo].[BAIRRO] ([id])
GO

ALTER TABLE [dbo].[SOLICITACAO] CHECK CONSTRAINT [FK_BAIRRO]
GO

ALTER TABLE [dbo].[SOLICITACAO]  WITH CHECK ADD  CONSTRAINT [FK_MICROREGIAO] FOREIGN KEY([MICROREGIAO])
REFERENCES [dbo].[MICROREGIAO] ([id])
GO

ALTER TABLE [dbo].[SOLICITACAO] CHECK CONSTRAINT [FK_MICROREGIAO]
GO

ALTER TABLE [dbo].[SOLICITACAO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO_SOL] FOREIGN KEY([PROCESSO])
REFERENCES [dbo].[PROCESSO] ([id])
GO

ALTER TABLE [dbo].[SOLICITACAO] CHECK CONSTRAINT [FK_PROCESSO_SOL]
GO

ALTER TABLE [dbo].[SOLICITACAO]  WITH CHECK ADD  CONSTRAINT [FK_RPA] FOREIGN KEY([RPA])
REFERENCES [dbo].[RPA] ([id])
GO

ALTER TABLE [dbo].[SOLICITACAO] CHECK CONSTRAINT [FK_RPA]
GO

INSERT INTO SOLICITACAO (descricao,
	ano,
	mes,
	momento,
	REGIONAL,
	localidade,
	bairro,
	endereco,
	roteiro,
	RPA, 
	microRegiao,
	tipoSolicitacao,
	processo) 
	VALUES ('descricao','2000', '12', '2012-06-18 10:34:09', 1, 'RECIFE', 1, 'RUA DAS FLORES', 'ROTEIRO A', 1, 1,1, 1)

