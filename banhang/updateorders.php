<?php
include "connect.php";
$id = $_POST['id'];
$trangthai = $_POST['trangthai'];

$query = "UPDATE `donhang` SET `trangthai` = '{$trangthai}' WHERE `id` = '{$id}'";

$data = mysqli_query($conn, $query);

if ($data == true) {
    $arr = [
        'succes' => true,
        'message' => "Sửa thành công"
    ];
} else {
    $arr = [
        'succes' => false,
        'message' => "Sửa không thành công"
    ];
}

print_r(json_encode($arr));
