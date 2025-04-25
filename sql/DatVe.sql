use QLV
-- Bảng KhachHang (Khách hàng)
CREATE TABLE KhachHang (
    maKH VARCHAR(10) PRIMARY KEY,
    tenKH NVARCHAR(50) NOT NULL,
    soDienThoai VARCHAR(15),
    email VARCHAR(50),
    diaChi NVARCHAR(100)
);

-- Bảng Phim (Phim)
CREATE TABLE Phim (
    maPhim VARCHAR(10) PRIMARY KEY,
    tenPhim NVARCHAR(100) NOT NULL,
    theLoai NVARCHAR(50),
    thoiLuong INT, -- Đơn vị: phút
    nhaSanXuat NVARCHAR(100),
    namSanXuat INT,
    moTa NVARCHAR(MAX),
    poster VARCHAR(255) -- Đường dẫn đến hình ảnh poster
);

-- Bảng PhongChieu (Phòng chiếu)
CREATE TABLE PhongChieu (
    maPhong INT PRIMARY KEY,
    tenPhong NVARCHAR(50) NOT NULL,
    soLuongGhe INT
);

-- Bảng Ghe (Ghế)
CREATE TABLE Ghe (
    maGhe INT PRIMARY KEY,
    maPhong INT,
    viTriGhe VARCHAR(5), 
    FOREIGN KEY (maPhong) REFERENCES PhongChieu(maPhong)
);
-- Bảng Ve (Vé)
CREATE TABLE Ve (
    maVe INT PRIMARY KEY IDENTITY(1,1),
    maKH VARCHAR(10),
    maLichChieu INT,
    maGhe INT,
    ngayDat DATETIME DEFAULT GETDATE(),
    phuongThucThanhToan NVARCHAR(50),
    ngayThanhToan DATETIME,
    maGiaoDich VARCHAR(50),
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maLichChieu) REFERENCES LichChieu(maLichChieu),
    FOREIGN KEY (maGhe) REFERENCES Ghe(maGhe),
    UNIQUE (maLichChieu, maGhe)
);

-- Bảng KhuyenMai (Khuyến mãi)
CREATE TABLE KhuyenMai (
    maKM VARCHAR(10) PRIMARY KEY,
    tenKM NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(MAX),
    giaTriKM DECIMAL(5, 2),
    ngayBatDau DATE,
    ngayKetThuc DATE
);

-- Bảng LichChieu (Lịch chiếu)
CREATE TABLE LichChieu (
    maLichChieu INT PRIMARY KEY IDENTITY(1,1),
    maPhim VARCHAR(10),
    maPhong INT,
    ngayChieu DATE NOT NULL,
    gioChieu TIME NOT NULL,
    giaVe DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (maPhim) REFERENCES Phim(maPhim),
    FOREIGN KEY (maPhong) REFERENCES PhongChieu(maPhong),
    UNIQUE (maPhim, maPhong, ngayChieu, gioChieu)
);
-- Bảng Ve_KhuyenMai (Liên kết Vé và Khuyến mãi - Tùy chọn)
CREATE TABLE Ve_KhuyenMai (
    maVe INT,
    maKM VARCHAR(10),
    PRIMARY KEY (maVe, maKM),
    FOREIGN KEY (maVe) REFERENCES Ve(maVe),
    FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM)
);

-- Thêm dữ liệu mẫu (Tùy chọn)
INSERT INTO KhachHang (maKH, tenKH, soDienThoai, email, diaChi) VALUES
('KH001', N'Nguyễn Văn A', '0901234567', 'nva@example.com', N'123 Đường ABC'),
('KH002', N'Trần Thị B', '0987654321', 'ttb@example.com', N'456 Đường XYZ');

INSERT INTO Phim (maPhim, tenPhim, theLoai, thoiLuong, nhaSanXuat, namSanXuat, moTa, poster) VALUES
('P001', N'Avengers: Endgame', N'Hành động, Phiêu lưu, Khoa học viễn tưởng', 181, N'Marvel Studios', 2019, N'Phần kết của kỷ nguyên MCU.', 'avengers_endgame.jpg'),
('P002', N'Spider-Man: No Way Home', N'Hành động, Phiêu lưu, Khoa học viễn tưởng', 148, N'Marvel Studios, Sony Pictures', 2021, N'Đa vũ trụ hỗn loạn.', 'spiderman_nowayhome.jpg');

INSERT INTO PhongChieu (maPhong, tenPhong, soLuongGhe) VALUES
(1, N'Phòng 1', 50),
(2, N'Phòng 2', 60);

INSERT INTO Ghe (maGhe, maPhong, viTriGhe) VALUES
(1, 1, 'A1'), (2, 1, 'A2'), (3, 1, 'A3'), (4, 1, 'B1'), (5, 1, 'B2'),
(1, 2, 'A1'), (2, 2, 'A2'), (3, 2, 'A3'), (4, 2, 'B1'), (5, 2, 'B2');

INSERT INTO LichChieu (maPhim, maPhong, ngayChieu, gioChieu, giaVe) VALUES
('P001', 1, '2025-04-26', '10:00:00', 80000),
('P001', 1, '2025-04-26', '14:00:00', 90000),
('P002', 2, '2025-04-26', '16:00:00', 85000);

INSERT INTO Ve (maKH, maLichChieu, maGhe, phuongThucThanhToan) VALUES
('KH001', 1, 1, N'Tiền mặt'),
('KH002', 3, 2, N'Thẻ tín dụng');

-- Thêm khuyến mãi mẫu (Tùy chọn)
INSERT INTO KhuyenMai (maKM, tenKM, moTa, giaTriKM, ngayBatDau, ngayKetThuc) VALUES
('KM001', N'Giảm 10% cho thành viên', N'Giảm 10% cho tất cả các thành viên', 0.10, '2025-04-01', '2025-04-30');

-- Liên kết vé với khuyến mãi (Tùy chọn)
INSERT INTO Ve_KhuyenMai (maVe, maKM) VALUES
(1, 'KM001');