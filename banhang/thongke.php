<?php
include "connect.php";
$query = "SELECT idsp, sanpham.TenSP, SUM(chitietdonhang.soluong) AS tong, sanpham.GiaSP FROM `chitietdonhang` INNER JOIN sanpham ON sanpham.MaSP = chitietdonhang.idsp GROUP BY `idsp`;";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

if (!empty($result)) {
    $arr = [
        'succes' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
} else {
    $arr = [
        'succes' => false,
        'message' => "khong thanh cong",
        'result' => $result
    ];
}
print_r(json_encode($arr));
