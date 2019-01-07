CREATE TABLE dbo.VISTORIA
	(
	id int NOT NULL IDENTITY (1, 1),
	vistoria_data varchar(20),
	avaliador int,
	risco int,
	RPA int,
	microregiao int,
	localidade int NOT NULL,
	processo int NOT NULL

	)  ON [PRIMARY]
GO
ALTER TABLE dbo.VISTORIA ADD CONSTRAINT
	PK_VISTORIA PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_MICROREGIAO] FOREIGN KEY([MICROREGIAO])
REFERENCES [dbo].[MICROREGIAO] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_MICROREGIAO]
GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_PROCESSO] FOREIGN KEY([PROCESSO])
REFERENCES [dbo].[PROCESSO] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_PROCESSO]
GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_RPA] FOREIGN KEY([RPA])
REFERENCES [dbo].[RPA] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_RPA]
GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_RISCO] FOREIGN KEY([RISCO])
REFERENCES [dbo].[RISCO] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_RISCO]
GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_AVALIADOR] FOREIGN KEY([AVALIADOR])
REFERENCES [dbo].[AVALIADOR] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_AVALIADOR]
GO

ALTER TABLE [dbo].[VISTORIA]  WITH CHECK ADD  CONSTRAINT [FK_LOCALIDADE] FOREIGN KEY([LOCALIDADE])
REFERENCES [dbo].[LOCALIDADE] ([id])
GO

ALTER TABLE [dbo].[VISTORIA] CHECK CONSTRAINT [FK_LOCALIDADE]
GO

INSERT INTO VISTORIA (
	vistoria_data,
	avaliador,
	risco,
	RPA,
	microregiao,
	localidade,
	processo 
	) 
	VALUES ('2012-06-18 10:34:09', 1, 1, 1, 1, 1, 1)

