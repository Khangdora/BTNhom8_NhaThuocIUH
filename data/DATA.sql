CREATE DATABASE nhathuocIUH;
go
USE nhathuocIUH;
go
CREATE TABLE CaTruc(
   maCaTruc VARCHAR (10) primary key,
   tencatruc NVARCHAR (30) NOT NULL,
   ghichu NVARCHAR (50) NULL
);
go
CREATE TABLE NhanVien (
   maNhanVien VARCHAR(10) PRIMARY KEY,
   ho NVARCHAR(50),
   ten NVARCHAR(50),
   sodienthoai NVARCHAR(20),
   email NVARCHAR(30),
   macatruc VARCHAR(10) NOT NULL,
   gioitinh bit,
   chucvu VARCHAR(20),
   tienluong FLOAT,
   matkhau NVARCHAR(30) NOT NULL,
   CONSTRAINT F_NV_CT FOREIGN KEY (macatruc) REFERENCES CaTruc(maCaTruc)
);
go
INSERT CaTruc([maCaTruc], [tencatruc], [ghichu]) VALUES ('CA01', N'Ca sáng', N'6 giờ đến 14 giờ')
INSERT CaTruc([maCaTruc], [tencatruc], [ghichu]) VALUES ('CA02', N'Ca chiều', N'14 giờ đến 22 giờ')
GO
INSERT INTO [dbo].[NhanVien]
           ([maNhanVien],[ho],[ten],[sodienthoai],[email],[macatruc],[gioitinh],[chucvu],[tienluong],[matkhau])
     VALUES
           ('NV100',N'Võ',N'Trường Khang',N'0974867266',N'khangdora2809@gmail.com',N'CA01',1,N'Nhân viên',900000,N'#Dx123#Dx123')
INSERT INTO [dbo].[NhanVien]
           ([maNhanVien],[ho],[ten],[sodienthoai],[email],[macatruc],[gioitinh],[chucvu],[tienluong],[matkhau])
     VALUES
           ('NV101',N'Nguyễn',N'Thế Lực',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123')
INSERT INTO [dbo].[NhanVien]
           ([maNhanVien],[ho],[ten],[sodienthoai],[email],[macatruc],[gioitinh],[chucvu],[tienluong],[matkhau])
     VALUES
           ('NV102',N'Phạm',N'Lê Thanh Nhiệt',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123')
INSERT INTO [dbo].[NhanVien]
           ([maNhanVien],[ho],[ten],[sodienthoai],[email],[macatruc],[gioitinh],[chucvu],[tienluong],[matkhau])
     VALUES
           ('NV103',N'Nguyễn',N'Đức Thắng',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123')
INSERT INTO [dbo].[NhanVien]
           ([maNhanVien],[ho],[ten],[sodienthoai],[email],[macatruc],[gioitinh],[chucvu],[tienluong],[matkhau])
     VALUES
           ('NV104',N'Trần',N'Thị Ánh Thi',N'',N'',N'CA01',0,N'Quản trị viên',943000,N'123')
GO
/*Select * from NhanVien where maNhanVien = 'NV100' and matkhau = '#Dx123#Dx123'

DROP TABLE NhanVien;
DROP TABLE CaTruc;

SELECT * FROM CaTruc
SELECT * FROM NhanVien
SELECT * FROM sys.foreign_keys */