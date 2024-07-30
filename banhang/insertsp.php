<?php
include "connect.php";
$tensp = $_POST['tensp'];
$gia = $_POST['gia'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai = $_POST['loai'];
$soLuongTon = $_POST['soLuongTon'];

$query = "INSERT INTO `sanpham`(`TenSP`, `GiaSP`, `HinhAnh`, `MoTa`, `Loai`, `SoLuongTon`) VALUES ('$tensp', $gia, '$hinhanh', '$mota', $loai, $soLuongTon)";

$data = mysqli_query($conn, $query);

if ($data == true) {
    $arr = [
        'succes' => true,
        'message' => "Thành công"
    ];
} else {
    $arr = [
        'succes' => false,
        'message' => "Không thành công"
    ];
}

print_r(json_encode($arr));
