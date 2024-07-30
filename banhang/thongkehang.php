<?php
include "connect.php";
$query = "SELECT *, SUM(tongtien) AS tongtienthang, MONTH(`ThoiGianDatHang`) AS thang FROM `donhang`GROUP BY YEAR(`ThoiGianDatHang`),MONTH(`ThoiGianDatHang`);";
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
