<?php
include "connect.php";
$email = $_GET["email"];
$username = $_GET["username"];
$mobile = $_GET["mobile"];
$ImageUser = $_GET["ImageUser"];

$query = "UPDATE `user` SET `username`='{$username}', `mobile`='{$mobile}', `ImageUser`='{$ImageUser}' WHERE `email` = '{$email}'";
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
