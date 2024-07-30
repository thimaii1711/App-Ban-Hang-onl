-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 07, 2024 lúc 06:22 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dataappbanhangonl`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `iddonhang` int(11) NOT NULL,
  `idsp` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `gia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`iddonhang`, `idsp`, `soluong`, `gia`) VALUES
(76, 24, 1, '27399000'),
(77, 24, 2, '27399000'),
(78, 24, 1, '27399000'),
(79, 22, 1, '6000000'),
(80, 28, 1, '30000000'),
(81, 28, 1, '30000000'),
(82, 28, 1, '30000000'),
(82, 31, 1, '25000000'),
(83, 28, 1, '30000000'),
(84, 10, 5, '30000000'),
(85, 24, 1, '27399000'),
(87, 31, 100, '2500000000'),
(88, 24, 1, '27399000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuc`
--

CREATE TABLE `danhmuc` (
  `MaDM` int(11) NOT NULL,
  `TenSP` varchar(100) NOT NULL,
  `HinhAnh` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuc`
--

INSERT INTO `danhmuc` (`MaDM`, `TenSP`, `HinhAnh`) VALUES
(1, 'Trang chủ', 'https://tcshop.id.vn/banhang/images/home.png'),
(2, 'Điện thoại', 'https://tcshop.id.vn/banhang/images/phone.png'),
(3, 'Lap Top', 'https://tcshop.id.vn/banhang/images/laptop.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `diachi` text NOT NULL,
  `sodienthoai` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` varchar(255) NOT NULL,
  `ThoiGianDatHang` datetime NOT NULL DEFAULT current_timestamp(),
  `trangthai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `iduser`, `diachi`, `sodienthoai`, `email`, `soluong`, `tongtien`, `ThoiGianDatHang`, `trangthai`) VALUES
(76, 7, '17B TanTru', '0786505911', 'caocong123@gmail.com', 1, '27399000', '2024-06-02 01:50:18', 1),
(77, 7, '17B TanTru', '0786505911', 'caocong123@gmail.com', 2, '54798000', '2024-05-22 01:50:40', 4),
(78, 7, 'qs', '0786505911', 'caocong123@gmail.com', 1, '27399000', '2024-06-02 20:41:07', 0),
(79, 7, 'n', '0786505911', 'caocong123@gmail.com', 1, '6000000', '2024-06-02 20:41:34', 0),
(80, 7, 'uh', '0786505911', 'caocong123@gmail.com', 1, '30000000', '2024-06-03 11:10:33', 0),
(81, 7, 'GV g', '0786505911', 'caocong123@gmail.com', 1, '30000000', '2024-06-03 11:16:45', 0),
(82, 7, 'jdn', '0786505911', 'caocong123@gmail.com', 2, '55000000', '2024-06-03 11:29:30', 0),
(83, 7, 'uhh', '0786505911', 'caocong123@gmail.com', 1, '30000000', '2024-06-03 13:38:54', 0),
(84, 7, '17B', '0786505911', 'caocong123@gmail.com', 5, '150000000', '2024-06-03 13:59:00', 0),
(85, 7, 'tức', '0786505911', 'caocong123@gmail.com', 1, '27399000', '2024-06-03 14:13:37', 2),
(87, 7, 'ssafasfd', '0786505911', 'caocong123@gmail.com', 100, '250000000000', '2024-06-07 11:06:14', 0),
(88, 8, 'gyhkv', '0362111265', 'cao123@gmail.com', 1, '27399000', '2024-06-07 11:14:36', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `meeting`
--

CREATE TABLE `meeting` (
  `id` int(11) NOT NULL,
  `meetingId` varchar(250) NOT NULL,
  `token` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `meeting`
--

INSERT INTO `meeting` (`id`, `meetingId`, `token`) VALUES
(1, '3ci9-v43c-n7nn', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI0OWNlMjM4MS0wMTBkLTRjNDctYjFiZS04MGI4YTg4NGQzZTUiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxNzI3Mzk4MCwiZXhwIjoxNzE3ODc4NzgwfQ.XG7yWceek8pMNc9bg0KR0FI-cBG0Kd-u5VmdUIfLCEA'),
(2, 'k9g2-td4g-gxfd', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI0OWNlMjM4MS0wMTBkLTRjNDctYjFiZS04MGI4YTg4NGQzZTUiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxNzI3Mzk4MCwiZXhwIjoxNzE3ODc4NzgwfQ.XG7yWceek8pMNc9bg0KR0FI-cBG0Kd-u5VmdUIfLCEA'),
(3, 'yn5b-oxpk-dbak', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI0OWNlMjM4MS0wMTBkLTRjNDctYjFiZS04MGI4YTg4NGQzZTUiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxNzI3Mzk4MCwiZXhwIjoxNzE3ODc4NzgwfQ.XG7yWceek8pMNc9bg0KR0FI-cBG0Kd-u5VmdUIfLCEA');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `promotion`
--

CREATE TABLE `promotion` (
  `id` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  `information` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `promotion`
--

INSERT INTO `promotion` (`id`, `url`, `information`) VALUES
(1, 'https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png', 'Lễ hội Phụ kiện - Bùng nổ ưu đãi, sắm thả ga không lo giá!\r\nBạn đang tìm kiếm những món phụ kiện điện thoại, laptop chất lượng với giá cả phải chăng? Đừng bỏ lỡ Lễ hội Phụ kiện hoành tráng tại shop của chúng tôi!\r\n\r\nTừ ngày 01/06/2024 đến ngày 30/06/2024, bạn sẽ được tận hưởng hàng loạt ưu đãi cực kỳ hấp dẫn:\r\n\r\nGiảm giá đến 50% cho tất cả các phụ kiện điện thoại, laptop chính hãng.\r\nMua 1 tặng 1 với nhiều sản phẩm phụ kiện độc đáo.\r\nFlashsale mỗi ngày với giá siêu rẻ, số lượng có hạn.\r\nMiễn phí ship cho đơn hàng từ [Số tiền] trở lên.\r\nTặng voucher giảm giá cho khách hàng thân thiết.\r\nLễ hội Phụ kiện là cơ hội tuyệt vời để bạn sắm sửa những món đồ yêu thích với mức giá tiết kiệm nhất. Hãy đến ngay shop [Tên shop] để khám phá kho tàng phụ kiện phong phú và tận hưởng những ưu đãi vô cùng hấp dẫn!\r\n\r\nNgoài ra, shop còn có:\r\n\r\nĐội ngũ nhân viên tư vấn nhiệt tình, sẵn sàng giải đáp mọi thắc mắc của bạn.\r\nChính sách đổi trả linh hoạt trong vòng 120 ngày.\r\nHình thức thanh toán đa dạng, tiện lợi.\r\nĐừng bỏ lỡ cơ hội mua sắm tuyệt vời này!'),
(2, 'https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png', 'Trả góp - Sắm máy mới, rinh ngàn ưu đãi!\r\nBạn đang có nhu cầu mua sắm điện thoại, laptop mới nhưng e ngại về giá cả? Đừng lo lắng, đã có chúng tôi mang đến chương trình Trả góp với hàng loạt ưu đãi hấp dẫn, giúp bạn dễ dàng sở hữu sản phẩm mong muốn mà không lo về gánh nặng tài chính!\r\n\r\nTừ ngày 01/06/2024 đến ngày 30/06/2024, khi mua trả góp điện thoại, laptop tại đây, bạn sẽ được hưởng:\r\n\r\nLãi suất 0% cho tất cả các sản phẩm trả góp qua thẻ tín dụng.\r\nTrả trước 0 đồng hoặc chỉ cần thanh toán 10% giá trị sản phẩm.\r\nKỳ hạn trả góp linh hoạt từ 6 tháng đến 24 tháng.\r\nHỗ trợ thủ tục nhanh chóng, đơn giản.\r\nMiễn phí phí duy trì thẻ trong suốt thời gian trả góp.\r\nĐặc biệt, khi mua trả góp laptop, bạn còn được:\r\n\r\nTặng kèm bộ quà tặng phụ kiện trị giá [Số tiền].\r\nNâng cấp RAM/SSD miễn phí.\r\nCài đặt phần mềm miễn phí.\r\nTrả góp tại shoo của chúng tôi là lựa chọn thông minh giúp bạn:\r\n\r\nSở hữu sản phẩm chính hãng với giá cả phải chăng.\r\nGiảm áp lực tài chính, dễ dàng quản lý chi tiêu.\r\nTận hưởng những ưu đãi hấp dẫn và quà tặng độc đáo.\r\nNhanh tay đến ngay cửa hàng của chúng tôi để mua sắm trả góp và rinh ngàn ưu đãi!'),
(3, 'https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg', 'Big Kỷ Nguyên - Mua sắm thả ga, ưu đãi cực đã!\r\nBạn đang tìm kiếm những sản phẩm điện thoại, laptop chất lượng với giá cả phải chăng? Đừng bỏ lỡ chương trình Big Kỷ Nguyên hoành tráng tại shop của chúng tôi!\r\n\r\nTừ ngày 01/06/2024 đến ngày 30/06/2024, bạn sẽ được tận hưởng hàng loạt ưu đãi cực kỳ hấp dẫn:\r\n\r\nGiảm giá đến 50% cho tất cả các sản phẩm điện thoại, laptop chính hãng.\r\nMua 1 tặng 1 với nhiều sản phẩm phụ kiện độc đáo.\r\nFlashsale mỗi ngày với giá siêu rẻ, số lượng có hạn.\r\nMiễn phí ship cho đơn hàng từ 500.000 đồng trở lên.\r\nTặng voucher giảm giá cho khách hàng thân thiết.\r\nBig Kỷ Nguyên là cơ hội tuyệt vời để bạn sắm sửa những món đồ yêu thích với mức giá tiết kiệm nhất. Hãy đến ngay shop của chúng tôi để khám phá kho tàng sản phẩm phong phú và tận hưởng những ưu đãi vô cùng hấp dẫn!\r\n\r\nNgoài ra, shop còn có:\r\n\r\nĐội ngũ nhân viên tư vấn nhiệt tình, sẵn sàng giải đáp mọi thắc mắc của bạn.\r\nChính sách đổi trả linh hoạt trong vòng 30 ngày.\r\nHình thức thanh toán đa dạng, tiện lợi.\r\nĐừng bỏ lỡ cơ hội mua sắm tuyệt vời này!'),
(4, 'https://d3design.vn/uploads/5495874fhgtrty567.jpg', 'Chương trình khuyến mãi mùa hè');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSP` int(11) NOT NULL,
  `TenSP` varchar(255) NOT NULL,
  `GiaSP` varchar(100) NOT NULL,
  `HinhAnh` text NOT NULL,
  `MoTa` text NOT NULL,
  `Loai` int(11) NOT NULL,
  `LinkVideo` text NOT NULL,
  `DaXoa` int(11) NOT NULL,
  `SoLuongTon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSP`, `TenSP`, `GiaSP`, `HinhAnh`, `MoTa`, `Loai`, `LinkVideo`, `DaXoa`, `SoLuongTon`) VALUES
(1, 'Laptop Dell Vostro 3520', '12000000', 'https://hanoicomputercdn.com/media/product/69589_laptop_dell_vostro_3520_25.png', 'CPU: Intel Core i5 1235U (upto 4.4Ghz/12MB cache)\r\nRAM: 8GB DDR4 2666Mhz (1*8GB)\r\nỔ cứng: 512GB PCIe NVMe SSD\r\nVGA: Intel UHD Graphics\r\nMàn hình: 15.6 inch FHD (1920 x 1080) 250 nits WVA Anti-Glare LED Backlit\r\nMàu sắc: Xám', 2, 'lQ-5KG7l-6U', 0, 100),
(2, 'Samsung Galaxy A23 6GB', '12000000', '7up_lon.jpeg', '\"Kích thước màn hình 6.9 inches\r\nCông nghệ màn hình Dynamic AMOLED\r\nCamera sau 108 MP, f/1.8, 26mm (wide), 1/1.33\"\", 0.8µm, PDAF, Laser AF, OIS\r\n12 MP, f/3.0, 103mm (periscope telephoto), 1.0µm, PDAF, OIS, 5x optical zoom, 50x hybrid zoom\r\n12 MP, f/2.2, 13mm (ultrawide), 1/2.55\"\", 1.4µm\r\nCamera trước 10 MP, f/2.2, 26mm (wide), 1/3.2\"\", 1.22µm, Dual Pixel PDAF\r\nChipset Exynos 990 (7 nm+)\r\nDung lượng RAM 12 GB\"', 1, 'lQ-5KG7l-6U', 0, 100),
(5, 'Laptop HP Pavilion 14-eg2033TU', '16299000', 'https://hanoicomputercdn.com/media/product/68296_aaaaaaa_0004_layer_2.png', 'CPU: Intel® Core™ i5-1235U (3.30 GHz upto 4.40 GHz, 12MB)\r\nRAM: 8GB DDR4-3200 MHz RAM (2 x 4 GB)\r\nỔ cứng: 512GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel® Iris® Xᵉ Graphics\r\nMàn hình: 14 inch FHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Vàng', 2, 'lQ-5KG7l-6U', 0, 100),
(6, 'iPhone 13 Pro 128GB Vàng', '27399000', 'https://hanoicomputercdn.com/media/product/64690_iphone_13_pro_max_2.png', 'Công nghệ màn hình: OLED\r\nĐộ phân giải: 1170 x 2532 Pixels, 2 camera 12 MP, 12 MP\r\nMàn hình rộng: 6.1\"\r\nHệ điều hành: iOS 14\r\nChip xử lý (CPU): Apple A14 Bionic 6 nhân\r\nBộ nhớ trong (ROM): 128GB', 1, 'lQ-5KG7l-6U', 0, 100),
(7, 'Máy Điện Thoại Alcatel H3P', '2399000', 'https://hanoicomputercdn.com/media/product/68239_may_dien_thoai_alcatel_h3p__2_.jpg', '\"Màn hình 6.78 inch, AMOLED, FHD+, 2448 x 1080 Pixels\r\nCamera sau 50.0 MP + 13.0 MP + 5.0 MP\r\nCamera Selfie 12.0 MP\r\nRAM 12 GB\r\nBộ nhớ trong 256 GB\r\nCPU Snapdragon 8+ Gen 1\"', 1, 'lQ-5KG7l-6U', 0, 100),
(8, 'Laptop HP Pavilion 15-eg2057TU', '14999000', 'https://hanoicomputercdn.com/media/product/67297_laptop_hp_pavilion_15_10.jpeg', 'CPU: Intel® Core™ i5-1240P (upto 4.40 GHz, 16MB)\r\nRAM: 8GB (2 x 4GB) DDR4-3200 MHz ( 2 khe)\r\nỔ cứng: 256GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel®iris XE\r\nMàn hình: 15.6 inch FullHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Bạc', 2, 'lQ-5KG7l-6U', 0, 100),
(9, 'Laptop Dell Vostro 3520', '12000000', 'https://hanoicomputercdn.com/media/product/69589_laptop_dell_vostro_3520_25.png', 'CPU: Intel Core i5 1235U (upto 4.4Ghz/12MB cache)\r\nRAM: 8GB DDR4 2666Mhz (1*8GB)\r\nỔ cứng: 512GB PCIe NVMe SSD\r\nVGA: Intel UHD Graphics\r\nMàn hình: 15.6 inch FHD (1920 x 1080) 250 nits WVA Anti-Glare LED Backlit\r\nMàu sắc: Xám', 2, 'lQ-5KG7l-6U', 0, 100),
(10, 'Samsung Galaxy A23 6GB', '6000000', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/246199/samsung-galaxy-a33-5g-thumb-new-1-600x600.jpg', '\"Kích thước màn hình 6.9 inches\r\nCông nghệ màn hình Dynamic AMOLED\r\nCamera sau 108 MP, f/1.8, 26mm (wide), 1/1.33\"\", 0.8µm, PDAF, Laser AF, OIS\r\n12 MP, f/3.0, 103mm (periscope telephoto), 1.0µm, PDAF, OIS, 5x optical zoom, 50x hybrid zoom\r\n12 MP, f/2.2, 13mm (ultrawide), 1/2.55\"\", 1.4µm\r\nCamera trước 10 MP, f/2.2, 26mm (wide), 1/3.2\"\", 1.22µm, Dual Pixel PDAF\r\nChipset Exynos 990 (7 nm+)\r\nDung lượng RAM 12 GB\"', 1, 'lQ-5KG7l-6U', 0, 100),
(11, 'Laptop HP Pavilion 14-eg2033TU', '16299000', 'https://hanoicomputercdn.com/media/product/68296_aaaaaaa_0004_layer_2.png', 'CPU: Intel® Core™ i5-1235U (3.30 GHz upto 4.40 GHz, 12MB)\r\nRAM: 8GB DDR4-3200 MHz RAM (2 x 4 GB)\r\nỔ cứng: 512GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel® Iris® Xᵉ Graphics\r\nMàn hình: 14 inch FHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Vàng', 2, 'lQ-5KG7l-6U', 0, 100),
(12, 'iPhone 13 Pro 128GB Vàng', '27399000', 'https://hanoicomputercdn.com/media/product/64690_iphone_13_pro_max_2.png', 'Công nghệ màn hình: OLED\r\nĐộ phân giải: 1170 x 2532 Pixels, 2 camera 12 MP, 12 MP\r\nMàn hình rộng: 6.1\"\r\nHệ điều hành: iOS 14\r\nChip xử lý (CPU): Apple A14 Bionic 6 nhân\r\nBộ nhớ trong (ROM): 128GB', 1, 'lQ-5KG7l-6U', 0, 100),
(13, 'Máy Điện Thoại Alcatel H3P', '2399000', 'https://hanoicomputercdn.com/media/product/68239_may_dien_thoai_alcatel_h3p__2_.jpg', '\"Màn hình 6.78 inch, AMOLED, FHD+, 2448 x 1080 Pixels\r\nCamera sau 50.0 MP + 13.0 MP + 5.0 MP\r\nCamera Selfie 12.0 MP\r\nRAM 12 GB\r\nBộ nhớ trong 256 GB\r\nCPU Snapdragon 8+ Gen 1\"', 1, 'lQ-5KG7l-6U', 0, 100),
(14, 'Laptop HP Pavilion 15-eg2057TU', '14999000', 'https://hanoicomputercdn.com/media/product/67297_laptop_hp_pavilion_15_10.jpeg', 'CPU: Intel® Core™ i5-1240P (upto 4.40 GHz, 16MB)\r\nRAM: 8GB (2 x 4GB) DDR4-3200 MHz ( 2 khe)\r\nỔ cứng: 256GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel®iris XE\r\nMàn hình: 15.6 inch FullHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Bạc', 2, 'lQ-5KG7l-6U', 0, 100),
(15, 'Laptop Dell Vostro 3520', '12000000', 'https://hanoicomputercdn.com/media/product/69589_laptop_dell_vostro_3520_25.png', 'CPU: Intel Core i5 1235U (upto 4.4Ghz/12MB cache)\r\nRAM: 8GB DDR4 2666Mhz (1*8GB)\r\nỔ cứng: 512GB PCIe NVMe SSD\r\nVGA: Intel UHD Graphics\r\nMàn hình: 15.6 inch FHD (1920 x 1080) 250 nits WVA Anti-Glare LED Backlit\r\nMàu sắc: Xám', 2, 'lQ-5KG7l-6U', 0, 100),
(16, 'Samsung Galaxy A23 6GB', '6000000', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/246199/samsung-galaxy-a33-5g-thumb-new-1-600x600.jpg', '\"Kích thước màn hình 6.9 inches\r\nCông nghệ màn hình Dynamic AMOLED\r\nCamera sau 108 MP, f/1.8, 26mm (wide), 1/1.33\"\", 0.8µm, PDAF, Laser AF, OIS\r\n12 MP, f/3.0, 103mm (periscope telephoto), 1.0µm, PDAF, OIS, 5x optical zoom, 50x hybrid zoom\r\n12 MP, f/2.2, 13mm (ultrawide), 1/2.55\"\", 1.4µm\r\nCamera trước 10 MP, f/2.2, 26mm (wide), 1/3.2\"\", 1.22µm, Dual Pixel PDAF\r\nChipset Exynos 990 (7 nm+)\r\nDung lượng RAM 12 GB\"', 1, 'lQ-5KG7l-6U', 0, 100),
(17, 'Laptop HP Pavilion 14-eg2033TU', '16299000', 'https://hanoicomputercdn.com/media/product/68296_aaaaaaa_0004_layer_2.png', 'CPU: Intel® Core™ i5-1235U (3.30 GHz upto 4.40 GHz, 12MB)\r\nRAM: 8GB DDR4-3200 MHz RAM (2 x 4 GB)\r\nỔ cứng: 512GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel® Iris® Xᵉ Graphics\r\nMàn hình: 14 inch FHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Vàng', 2, 'lQ-5KG7l-6U', 0, 100),
(18, 'iPhone 13 Pro 128GB Vàng', '27399000', 'https://hanoicomputercdn.com/media/product/64690_iphone_13_pro_max_2.png', 'Công nghệ màn hình: OLED\r\nĐộ phân giải: 1170 x 2532 Pixels, 2 camera 12 MP, 12 MP\r\nMàn hình rộng: 6.1\"\r\nHệ điều hành: iOS 14\r\nChip xử lý (CPU): Apple A14 Bionic 6 nhân\r\nBộ nhớ trong (ROM): 128GB', 1, 'lQ-5KG7l-6U', 0, 100),
(19, 'Máy Điện Thoại Alcatel H3P', '2399000', 'https://hanoicomputercdn.com/media/product/68239_may_dien_thoai_alcatel_h3p__2_.jpg', '\"Màn hình 6.78 inch, AMOLED, FHD+, 2448 x 1080 Pixels\r\nCamera sau 50.0 MP + 13.0 MP + 5.0 MP\r\nCamera Selfie 12.0 MP\r\nRAM 12 GB\r\nBộ nhớ trong 256 GB\r\nCPU Snapdragon 8+ Gen 1\"', 1, 'lQ-5KG7l-6U', 0, 100),
(20, 'Laptop HP Pavilion 15-eg2057TU', '14999000', 'https://hanoicomputercdn.com/media/product/67297_laptop_hp_pavilion_15_10.jpeg', 'CPU: Intel® Core™ i5-1240P (upto 4.40 GHz, 16MB)\r\nRAM: 8GB (2 x 4GB) DDR4-3200 MHz ( 2 khe)\r\nỔ cứng: 256GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel®iris XE\r\nMàn hình: 15.6 inch FullHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Bạc', 2, 'lQ-5KG7l-6U', 0, 100),
(21, 'Laptop Dell Vostro 3520', '12000000', 'https://hanoicomputercdn.com/media/product/69589_laptop_dell_vostro_3520_25.png', 'CPU: Intel Core i5 1235U (upto 4.4Ghz/12MB cache)\r\nRAM: 8GB DDR4 2666Mhz (1*8GB)\r\nỔ cứng: 512GB PCIe NVMe SSD\r\nVGA: Intel UHD Graphics\r\nMàn hình: 15.6 inch FHD (1920 x 1080) 250 nits WVA Anti-Glare LED Backlit\r\nMàu sắc: Xám', 2, 'lQ-5KG7l-6U', 0, 100),
(22, 'Samsung Galaxy A23 6GB', '6000000', 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/246199/samsung-galaxy-a33-5g-thumb-new-1-600x600.jpg', '\"Kích thước màn hình 6.9 inches\r\nCông nghệ màn hình Dynamic AMOLED\r\nCamera sau 108 MP, f/1.8, 26mm (wide), 1/1.33\"\", 0.8µm, PDAF, Laser AF, OIS\r\n12 MP, f/3.0, 103mm (periscope telephoto), 1.0µm, PDAF, OIS, 5x optical zoom, 50x hybrid zoom\r\n12 MP, f/2.2, 13mm (ultrawide), 1/2.55\"\", 1.4µm\r\nCamera trước 10 MP, f/2.2, 26mm (wide), 1/3.2\"\", 1.22µm, Dual Pixel PDAF\r\nChipset Exynos 990 (7 nm+)\r\nDung lượng RAM 12 GB\"', 1, 'lQ-5KG7l-6U', 0, 100),
(23, 'Laptop HP Pavilion 14-eg2033TU', '16299000', 'https://hanoicomputercdn.com/media/product/68296_aaaaaaa_0004_layer_2.png', 'CPU: Intel® Core™ i5-1235U (3.30 GHz upto 4.40 GHz, 12MB)\r\nRAM: 8GB DDR4-3200 MHz RAM (2 x 4 GB)\r\nỔ cứng: 512GB PCIe® NVMe™ M.2 SSD\r\nVGA: Intel® Iris® Xᵉ Graphics\r\nMàn hình: 14 inch FHD (1920 x 1080), IPS, 250 nits, 45% NTSC\r\nMàu sắc: Vàng', 2, 'lQ-5KG7l-6U', 0, 100),
(24, 'iPhone 13 Pro 128GB Vàng', '27399000', 'https://hanoicomputercdn.com/media/product/64690_iphone_13_pro_max_2.png', 'Công nghệ màn hình: OLED\r\nĐộ phân giải: 1170 x 2532 Pixels, 2 camera 12 MP, 12 MP\r\nMàn hình rộng: 6.1\"\r\nHệ điều hành: iOS 14\r\nChip xử lý (CPU): Apple A14 Bionic 6 nhân\r\nBộ nhớ trong (ROM): 128GB', 1, 'lQ-5KG7l-6U', 0, 99),
(28, 'Iphone 15 Pro Max', '32000000', '26.jpg', 'Điện thoại xịn', 1, '', 1, 100),
(31, 'Iphone 15 plus', '25000000', '32.jpg', 'Màu hồng', 1, '', 1, 0),
(32, 'sh', '31949', '32.jpg', 'hh', 1, '', 0, 100);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `uid` text NOT NULL,
  `token` text NOT NULL,
  `status` int(2) NOT NULL DEFAULT 0,
  `ImageUser` text NOT NULL DEFAULT '\'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png\''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `email`, `pass`, `username`, `mobile`, `uid`, `token`, `status`, `ImageUser`) VALUES
(7, 'caocong123@gmail.com', '123456', 'Cong cao222', '0786505911', 'HBO7TXpmrvZK52tziEtYVNgbjkC2', 'cxlDnPHVSA6w5Z6Ec103lj:APA91bH2kne1-vS305xp9RdF3RfJ8nBtXkDRM3u7vTn8H6lLLiEQnpvehX-YPapq9N6FsaKn89zGumfhEi4XJ1g0x8BtycN9GXsnTledaoKPF6DE4KerK-Rp2X6M8q7dEQispemIJ6cq', 1, 'https://firebasestorage.googleapis.com/v0/b/appbanhang-b762c.appspot.com/o/images%2FIMG_20240528_104451.jpg?alt=media&token=5a881afc-e9ac-4874-be3e-b8fac3451d14'),
(8, 'cao123@gmail.com', '123456', 'taoproo', '0362111265', 'o94FjbESUYYCYKsOTkmQ5eUQrtt1', 'cxlDnPHVSA6w5Z6Ec103lj:APA91bH2kne1-vS305xp9RdF3RfJ8nBtXkDRM3u7vTn8H6lLLiEQnpvehX-YPapq9N6FsaKn89zGumfhEi4XJ1g0x8BtycN9GXsnTledaoKPF6DE4KerK-Rp2X6M8q7dEQispemIJ6cq', 0, 'https://firebasestorage.googleapis.com/v0/b/appbanhang-b762c.appspot.com/o/images%2FIMG_20240518_204351.jpg?alt=media&token=319fb313-e944-4607-b3f6-611ed81949b1'),
(9, 'caotancong2003@gmail.com', '123456', 'TaoPro26', '0362111265', 'keOK0trDa1VHegShbAoafT3g9zA2', 'fI-j6080R-6De6bsMTwNNg:APA91bHhXUwWED65uFRakUuCup8ln94WhaIMBFAB8sa8KtacVzN1_oeIzGyKOSknDrfffCyn3lTtVWypdpEbvz4f0x0FJ-DXwqTwSs6iFtePCm-SQRyeV8MkcJgZrKKAh6TrI40F1tJT', 0, '/storage/emulated/0/DCIM/Camera/IMG_20240518_204351.jpg');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`iddonhang`,`idsp`),
  ADD KEY `FK_CHITIETDONHANG_SANPHAM` (`idsp`);

--
-- Chỉ mục cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`MaDM`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_dh_user` (`iduser`);

--
-- Chỉ mục cho bảng `meeting`
--
ALTER TABLE `meeting`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSP`),
  ADD KEY `FK_sanpham_danhmuc` (`Loai`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `MaDM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT cho bảng `meeting`
--
ALTER TABLE `meeting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `promotion`
--
ALTER TABLE `promotion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `FK_CHITIETDONHANG_DONHANG` FOREIGN KEY (`iddonhang`) REFERENCES `donhang` (`id`),
  ADD CONSTRAINT `FK_CHITIETDONHANG_SANPHAM` FOREIGN KEY (`idsp`) REFERENCES `sanpham` (`MaSP`);

--
-- Các ràng buộc cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `FK_dh_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `FK_sanpham_danhmuc` FOREIGN KEY (`Loai`) REFERENCES `danhmuc` (`MaDM`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
