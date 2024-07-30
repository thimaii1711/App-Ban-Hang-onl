<?php
include "connect.php";
$target_dir = "images/";

$query = "SELECT MAX(MaSP) AS ID FROM `sanpham`";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
   $result[] = $row;
}

if ($result[0]['ID'] == null) {
   $name = 1;
} else {
   $name = ++$result[0]['ID'];
}

$name = $name . ".jpg";

$target_file_name = $target_dir . $name;

if (isset($_FILES["file"])) {
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)) {
      $arr = [
         'succes' => true,
         'message' => "Thành công",
         "name" => $name
      ];
   } else {
      $arr = [
         'succes' => false,
         'message' => "Không thành công"
      ];
   }
} else {
   $arr = [
      'succes' => false,
      'message' => "Lỗi"
   ];
}

echo json_encode($arr);
