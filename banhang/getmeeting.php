<?php
include "connect.php";
$query = "SELECT * FROM `meeting` ORDER BY `id` DESC LIMIT 1";
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
