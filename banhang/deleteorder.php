<?php
include "connect.php";
$id = $_POST['idOrder'];

$query2 = "DELETE FROM `chitietdonhang` WHERE `iddonhang` = '$id'";
$data2 = mysqli_query($conn, $query2);

$query1 = "DELETE FROM `donhang` WHERE `id` = '$id'";
$data1 = mysqli_query($conn, $query1);

if ($data1 == true) {
    $arr = [
        'succes' => true,
        'message' => "Xóa thành công"
    ];
} else {
    $arr = [
        'succes' => false,
        'message' => "Xóa không thành công"
    ];
}

print_r(json_encode($arr));
