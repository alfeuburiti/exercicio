CREATE TABLE dbo.PROCESSO
	(
	id int NOT NULL IDENTITY (1, 1),
	ano varchar(4),
	mes varchar(2),
	numero varchar(50) NOT NULL,
	descricao varchar(50) NOT NULL,
	data_conclusao varchar(20),
	ocorrencia varchar(50),
	situacao varchar(50),
	origem varchar(50),
	tipo_processo int,
	processo_status int,
	processo_localizacao int,
	lona int,
	vistoria int
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.PROCESSO ADD CONSTRAINT
	PK_PROCESSO PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

ALTER TABLE [dbo].[PROCESSO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([TIPO_PROCESSO])
REFERENCES [dbo].[TIPO_PROCESSO] ([id])
GO

ALTER TABLE [dbo].[PROCESSO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([PROCESSO_STATUS])
REFERENCES [dbo].[PROCESSO_STATUS] ([id])
GO

ALTER TABLE [dbo].[PROCESSO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([PROCESSO_LOCALIZACAO])
REFERENCES [dbo].[PROCESSO_LOCALIZACAO] ([id])
GO

ALTER TABLE [dbo].[PROCESSO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([LONA])
REFERENCES [dbo].[LONA] ([id])
GO

ALTER TABLE [dbo].[PROCESSO]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([VISTORIA])
REFERENCES [dbo].[VISTORIA] ([id])
GO

ALTER TABLE [dbo].[PROCESSO] CHECK CONSTRAINT [FK_PROCESSO]
GO
	
INSERT INTO PROCESSO (
	ano, 
	mes, 
	numero, 
	descricao, 
	data_conclusao, 
	ocorrencia, 
	situacao, 
	origem, 
	tipo_processo, 
	processo_status, 
	processo_localizacao,
	lona, 
	vistoria
	) 
	VALUES('2019', '01', '01', 'PROCESSO  A', NULL, NULL, NULL, NULL, 1, 1, 1, 1, 1)

GO
