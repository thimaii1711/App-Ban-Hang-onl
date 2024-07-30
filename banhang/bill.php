<?php
include "connect.php";
$phone = $_POST['phone'];
$email = $_POST['email'];
$total = $_POST['total'];
$iduser = $_POST['iduser'];
$address = $_POST['address'];
$quanlity = $_POST['quanlity'];
$billinfo = $_POST['billinfo'];

$query = "INSERT INTO `donhang`(`iduser`, `diachi`, `sodienthoai`, `email`, `soluong`, `tongtien`) VALUES ('{$iduser}','{$address}','{$phone}','{$email}','{$quanlity}','{$total}')";

$data = mysqli_query($conn, $query);

if ($data == true) {
    $query = "SELECT id AS iddonhang FROM `donhang` WHERE `iduser` = '{$iduser}' ORDER BY id DESC LIMIT 1";
    $data = mysqli_query($conn, $query);
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $idbill = ($row);
    }
    if (!empty($idbill)) {
        //co don hang
        $billinfo = json_decode($billinfo, true);
        foreach ($billinfo as $key => $value) {
            $query1 = "INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluong`, `gia`) VALUES ('{$idbill["iddonhang"]}','{$value["cartid"]}','{$value["quality"]}','{$value["price"]}')";
            $data = mysqli_query($conn, $query1);

            $queryStock = "SELECT `SoLuongTon` FROM `sanpham` WHERE `MaSP`= '{$value["cartid"]}'";
            $dataQuantityInStock = mysqli_query($conn, $queryStock);
            $quantityInStock = mysqli_fetch_assoc($dataQuantityInStock);

            $newQuantityInStock = $quantityInStock["SoLuongTon"] - $value["quality"];

            $queryUpdateStock = "UPDATE `sanpham` SET `SoLuongTon`= $newQuantityInStock WHERE `MaSP`= '{$value["cartid"]}'";
            $dataUpdate = mysqli_query($conn, $queryUpdateStock);
        }
        if ($data == true) {
            $arr = [
                'succes' => true,
                'message' => "thanh cong"
            ];
        } else {
            $arr = [
                'succes' => false,
                'message' => "khong thanh cong"
            ];
        }
        print_r(json_encode($arr));
    }
} else {
    $arr = [
        'succes' => false,
        'message' => "khong thanh cong"
    ];
    print_r(json_encode($arr));
}
