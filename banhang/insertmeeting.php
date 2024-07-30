<?php
include "connect.php";
$token = $_POST['token'];
$meetingId = $_POST['meetingId'];

$query = "INSERT INTO `meeting`(`meetingId`, `token`) VALUES ('$meetingId','$token')";
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
