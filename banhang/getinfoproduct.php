<?php
include "connect.php";
$page = (int)$_POST['page'];
$total = 5;
$pos = ($page - 1) * $total;
$info = (int)$_POST['info'];
$query = "SELECT * FROM `sanpham` WHERE `Loai` = '{$info}' LIMIT {$pos},{$total}";
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
