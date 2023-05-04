CREATE DATABASE nhathuocIUH;
go
/**
ALTER DATABASE nhathuocIUH SET SINGLE_USER WITH ROLLBACK IMMEDIATE
DROP DATABASE nhathuocIUH;
**/
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
	maNCC VARCHAR(10),

	CONSTRAINT F_Thuoc_NCC FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC),
);
go
CREATE TABLE KhachHang (
	maKH VARCHAR(10) PRIMARY KEY,
	ho NVARCHAR(50),
	ten NVARCHAR(50),
	sodienthoai NVARCHAR(20),
	email NVARCHAR(30),
	gioitinh bit
);
GO
CREATE TABLE HoaDon (
	maHoaDon VARCHAR(10) PRIMARY KEY,
	maKhachHang VARCHAR(10) NOT NULL,
	maNhanVien VARCHAR(10) NOT NULL,
	dangHoaDon bit,
	thanhToan bit,
	ngayMua DATE,
	tienKhach FLOAT,
	soLuong INT,
	tongGia FLOAT

	CONSTRAINT F_HD_KH FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKH),
	CONSTRAINT F_HD_NV FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);
GO
CREATE TABLE CT_HoaDon (
	maHoaDon VARCHAR(10),
	maThuoc VARCHAR(10),
	soLuong int,

	PRIMARY KEY (maHoaDon, maThuoc),

	CONSTRAINT F_CTHD_HD FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
	CONSTRAINT F_CTHD_Thuoc FOREIGN KEY (maThuoc) REFERENCES Thuoc(maThuoc)
);
GO
CREATE TABLE NhaCungCap (
	maNCC VARCHAR(10) PRIMARY KEY,
	tenNCC NVARCHAR(MAX),
	diaChi NVARCHAR(MAX),
	soDienThoai NVARCHAR(15),
	email NVARCHAR(50)
);


GO
//** Thêm dữ liệu nhà cung cấp **//
INSERT INTO NhaCungCap VALUES
('NCC1000',N'Mediphar USA',N'93 Đất Thánh, Phường 6, Quận Tân Bình, Thành phố Hồ Chí Minh',N'0903850866',N'medipharusa2018@gmail.com'),
('NCC1001',N'Traphaco',N'75 Yên Ninh, Quận Ba Đình, Thành phố Hà Nội',N'18006612',N'info@traphaco.com.vn'),
('NCC1002',N'Công ty cổ phần dược Hậu Giang',N'288 Bis Nguyễn Văn Cừ, Phường An Hòa, Quận Ninh Kiều, Thành phố Cần Thơ',N'02923891433',N'dhgpharma@dhgpharma.com.vn')
GO
//** Thêm dữ liệu ca trực **//
INSERT CaTruc([maCaTruc], [tencatruc], [ghichu]) VALUES 
	('CA01', N'Ca sáng', N'6 giờ đến 14 giờ'),
	('CA02', N'Ca chiều', N'14 giờ đến 22 giờ')

GO
//** Thêm dữ liệu nhân viên **//
INSERT INTO NhanVien
     VALUES
           ('NV100',N'Võ',N'Trường Khang',N'0974867266',N'khangdora2809@gmail.com',N'CA01',1,N'Nhân viên',900000,N'#Dx123#Dx123'),
           ('NV101',N'Nguyễn',N'Thế Lực',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123'),
           ('NV102',N'Phạm',N'Lê Thanh Nhiệt',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123'),
           ('NV103',N'Nguyễn',N'Đức Thắng',N'',N'',N'CA02',1,N'Nhân viên',943000,N'123'),
           ('NV104',N'Trần',N'Thị Ánh Thi',N'',N'',N'CA01',0,N'Quản trị viên',943000,N'123')

//** Thêm dữ liệu Thuốc **//
GO
INSERT INTO Thuoc
VALUES 
	('SP1006',N'Dầu mù u Thái Dương Đất Việt',N'Chai',100,46000,N'A. Palmitic, A. Stearic, A. Oleic, A. Lindeic',N'Việt Nam',N'Hỗ trợ trị bỏng, tái tạo da, làm lành vết thương',N'Dung dịch',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2024-04-16', 23),'/img/thumbnail/sp1000.jpg','NCC1000'), 
	('SP1001',N'Viên uống ArmoLipid Plus Rottapharm',N'Hộp',281,235000,N'Coenzyme Q10, Vi tảo lục, Cao khô Berberis aristata, Men gạo đỏ, Policosanol GDL-5, Acid folic',N'Ý',N'Hỗ trợ làm giảm cholesterol, triglycerid trong máu',N'Viên nhộng',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2025-04-17', 23),'/img/thumbnail/sp1001.jpg','NCC1000'),
	('SP1002',N'Que thử thai nhanh HCG Allisa Pregnancy Test Kit Traphaco',N'Hộp',738,15000,N'Que gỗ, hormone Human Chorionic Gonadotrophin',N'Việt Nam',N'Định tính hormone HCG trong nước tiểu để phát hiện sớm thai kỳ',N'Khác',CONVERT(date,'2023-01-01', 23),CONVERT(date,'2027-04-16', 23),'/img/thumbnail/sp1002.jpg','NCC1000'),
	('SP1003',N'Dung dịch Danospan Danapha',N'Chai',45,60000,N'Cao khô lá thường xuân, Tá dược',N'Việt Nam',N'Điều trị viêm đường hô hấp cấp tính có kèm theo ho',N'Dung dịch',CONVERT(date,'2023-02-11', 23),CONVERT(date,'2025-04-16', 23),'/img/thumbnail/sp1003.jpg','NCC1001'),
	('SP1004',N'Siro ho Prospan Forte Engelhard',N'Chai',100,85000,N'',N'Việt Nam',N'Cao khô lá thường xuyên, ethanol 30%',N'Dung dịch',CONVERT(date,'2023-02-11', 23),CONVERT(date,'2025-04-16', 23),'/img/thumbnail/sp1004.jpg','NCC1002'),
	('SP1005',N'Bột Dr.G Bifidus Bifido',N'Hộp',13,613000,N'Bifidobacterium bifidum, bifidobacterium longum, Lactobacillus acidophilus, kẽm oxide',N'Hàn Quốc',N'Cải thiện hệ tiêu hóa, ức chế hại khuẩn và tăng cường lợi khuẩn đường ruột',N'Bột',CONVERT(date,'2023-04-16', 23),CONVERT(date,'2026-04-16', 23),'/img/thumbnail/sp1005.jpg','NCC1000');
GO
INSERT INTO KhachHang VALUES
('KH1001',N'Nguyễn',N'Lê Hồng Thái',N'0974867266',N'thainguyen27@gmail.com',1)

GO
INSERT INTO dbo.HoaDon VALUES
('HD1001','KH1001','NV100',1,0,CONVERT(date,'2023-04-17', 23),1000000,1,700000)

SELECT COUNT(*) AS total FROM CT_HoaDon WHERE maHoaDon = ''

SELECT COUNT(*) AS total FROM HoaDon

SELECT * FROM KhachHang WHERE donViBan = N'Hộp' ORDER BY maThuoc ASC;

SELECT * FROM HoaDon WHERE dangHoaDon = 1

SELECT * FROM NhanVien WHERE (ho + ' ' + ten) = N'Võ Trường Khang'

SELECT * FROM CT_HoaDon

SELECT TOP 20 h.maKhachHang, k.ho, k.ten, COUNT(*) as total 
FROM HoaDon h INNER JOIN KhachHang k 
ON h.maKhachHang = k.maKH GROUP BY h.maKhachHang, k.ho, k.ten

/*SELECT * FROM Thuoc OFFSET 1 ROWS FETCH NEXT 25 ROWS ONLY

SELECT TOP 1 maHoaDon FROM HoaDon ORDER BY maHoaDon DESC

SELECT TOP 1 * FROM KhachHang WHERE sodienthoai = '0974867266'

SELECT * FROM Thuoc WHERE maThuoc = 'SP1001'

Select * from NhanVien where maNhanVien = 'NV100' and matkhau = '#Dx123#Dx123'

DROP TABLE NhanVien;
DROP TABLE CaTruc;

SELECT * FROM CaTruc
SELECT * FROM NhanVien
SELECT * FROM sys.foreign_keys */