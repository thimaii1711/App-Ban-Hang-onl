<?php
include "connect.php";
if ($_GET['key'] && $_GET['reset']) {
  $email = $_GET['key'];
  $pass = $_GET['reset'];
  $query = "select email,pass from user where `email`='{$email}' and `pass`='{$pass}'";
  $data = mysqli_query($conn, $query);
  if (mysqli_num_rows($data) == 1) {
?>
    <!DOCTYPE html>
    <html>

    <head>
      <title>Password Reset</title>
      <style>
        body {
          font-family: Arial, sans-serif;
          background-color: #f1f1f1;
          padding: 20px;
        }

        form {
          max-width: 400px;
          margin: 0 auto;
          background-color: #fff;
          padding: 20px;
          border-radius: 5px;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        input[type="password"] {
          width: 100%;
          padding: 10px;
          margin-bottom: 10px;
          border: 1px solid #ccc;
          border-radius: 3px;
          box-sizing: border-box;
        }

        input[type="submit"] {
          width: 100%;
          padding: 10px;
          background-color: #007bff;
          color: #fff;
          border: none;
          border-radius: 3px;
          cursor: pointer;
        }

        input[type="submit"]:hover {
          background-color: #0056b3;
        }
      </style>
    </head>

    <body>
      <form method="post" action="submit_new.php">
        <input type="hidden" name="email" value="<?php echo $email; ?>">
        <p>Nhập lại mật khẩu mới</p>
        <input type="password" name="password">
        <input type="submit" name="submit_password">
      </form>
    </body>

    </html>
<?php
  }
}
?>