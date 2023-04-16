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
   chucvu NVARCHAR(20),
   tienluong FLOAT,
   matkhau NVARCHAR(30) NOT NULL,
   CONSTRAINT F_NV_CT FOREIGN KEY (macatruc) REFERENCES CaTruc(maCaTruc)
);
go
CREATE TABLE Thuoc (
	maThuoc VARCHAR(10) PRIMARY KEY,
	tenThuoc NVARCHAR(MAX),
	donViBan NVARCHAR(30),
	soLuong INT,
	donGia FLOAT,
	thanhPhan NVARCHAR(MAX),
	xuatXu NVARCHAR(30),
	congDung NVARCHAR(MAX),
	dangBaoChe NVARCHAR(50),
	ngayNhap DATE,
	hanSuDung DATE,
	thumbnail TEXT,
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
INSERT INTO Thuoc(maThuoc, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhap, hanSuDung, thumbnail)
VALUES ('SP1000',N'Dầu mù u Thái Dương Đất Việt',N'Chai',100,46000,N'A. Palmitic, A. Stearic, A. Oleic, A. Lindeic',N'Việt Nam',N'Hỗ trợ trị bỏng, tái tạo da, làm lành vết thương',N'Dung dịch',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2024-04-16', 23),'/img/thumbnail/sp1000.jpg');
INSERT INTO Thuoc(maThuoc, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhap, hanSuDung, thumbnail)
VALUES 
('SP1001',N'Viên uống ArmoLipid Plus Rottapharm',N'Hộp',281,235000,N'Coenzyme Q10, Vi tảo lục, Cao khô Berberis aristata, Men gạo đỏ, Policosanol GDL-5, Acid folic',N'Ý',N'Hỗ trợ làm giảm cholesterol, triglycerid trong máu',N'Viên nhộng',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2025-04-17', 23),'/img/thumbnail/sp1001.jpg'),
('SP1002',N'Que thử thai nhanh HCG Allisa Pregnancy Test Kit Traphaco',N'Hộp',738,15000,N'Que gỗ, hormone Human Chorionic Gonadotrophin',N'Việt Nam',N'Định tính hormone HCG trong nước tiểu để phát hiện sớm thai kỳ',N'Khác',CONVERT(date,'2023-01-01', 23),CONVERT(date,'2027-04-16', 23),'/img/thumbnail/sp1002.jpg'),
('SP1003',N'Dung dịch Danospan Danapha',N'Chai',45,60000,N'Cao khô lá thường xuân, Tá dược',N'Việt Nam',N'Điều trị viêm đường hô hấp cấp tính có kèm theo ho',N'Dung dịch',CONVERT(date,'2023-02-11', 23),CONVERT(date,'2025-04-16', 23),'/img/thumbnail/sp1003.jpg'),
('SP1004',N'Siro ho Prospan Forte Engelhard',N'Chai',100,85000,N'',N'Việt Nam',N'Cao khô lá thường xuyên, ethanol 30%',N'Dung dịch',CONVERT(date,'2023-02-11', 23),CONVERT(date,'2025-04-16', 23),'/img/thumbnail/sp1004.jpg'),
('SP1005',N'Bột Dr.G Bifidus Bifido',N'Hộp',13,613000,N'Bifidobacterium bifidum, bifidobacterium longum, Lactobacillus acidophilus, kẽm oxide',N'Hàn Quốc',N'Cải thiện hệ tiêu hóa, ức chế hại khuẩn và tăng cường lợi khuẩn đường ruột',N'Bột',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2026-04-16', 23),'/img/thumbnail/sp1005.jpg');

SELECT * FROM NhanVien

/*Select * from NhanVien where maNhanVien = 'NV100' and matkhau = '#Dx123#Dx123'

DROP TABLE NhanVien;
DROP TABLE CaTruc;

SELECT * FROM CaTruc
SELECT * FROM NhanVien
SELECT * FROM sys.foreign_keys */