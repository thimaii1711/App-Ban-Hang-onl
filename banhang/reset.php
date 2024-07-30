<?php

include "connect.php";

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';

$email = $_POST['email'];
$query = "SELECT * FROM `user` WHERE `email`='{$email}'";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

if (empty($result)) {
    $arr = [
        'succes' => false,
        'message' => "Mail khong chinh xac",
        'result' => $result
    ];
} else {
    //send mail
    $email = ($result[0]["email"]);
    $pass = ($result[0]["pass"]);
    $link = "<a href='http://10.10.10.58:8081/banhang/reset_pass.php?key=" . $email . "&reset=" . $pass . "'>Nhấn vào đây để thay đổi mật khẩu!</a>";
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;
    // GMAIL username
    $mail->Username = "tcshoptelephone@gmail.com"; // Mail hệ thống
    // GMAIL password
    $mail->Password = "skec vhyv yslu hecy"; // Mật khẩu cấp 2
    $mail->SMTPSecure = "ssl";
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From = "tcshoptelephone@gmail.com"; // Mail hệ thống
    $mail->FromName = 'App ban hang';
    $mail->AddAddress($email, 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body = $link;
    if ($mail->Send()) {
        $arr = [
            'succes' => true,
            'message' => "Vui long kiem tra mail cua ban",
            'result' => $result
        ];
    } else {
        $arr = [
            'succes' => false,
            'message' => "Gui mail khong thanh cong",
            'result' => $result
        ];
    }
}
print_r(json_encode($arr));
