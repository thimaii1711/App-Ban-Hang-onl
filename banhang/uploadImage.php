<?php
// Kiểm tra xem có dữ liệu được gửi lên không
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Đường dẫn đến thư mục lưu trữ ảnh tải lên
    $target_dir = "images/";

    // Kiểm tra xem có file hình ảnh được gửi lên không
    if (isset($_FILES["image"])) {
        $target_file = $target_dir . uniqid() . '_' . basename($_FILES["image"]["name"]);

        // Lấy phần mở rộng của file
        $imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

        // Kiểm tra kích thước tệp
        if ($_FILES["image"]["size"] > 500000) {
            echo "Tệp của bạn quá lớn.";
        } elseif (!in_array($imageFileType, array("jpg", "png", "jpeg", "gif"))) {
            echo "Chỉ chấp nhận các tệp JPG, JPEG, PNG & GIF.";
        } else {
            // Tiến hành tải lên ảnh
            if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
                echo "Tệp " . basename($_FILES["image"]["name"]) . " đã được tải lên thành công.";
            } else {
                echo "Đã xảy ra lỗi khi tải lên tệp của bạn.";
            }
        }
    } else {
        echo "Không có tệp hình ảnh nào được gửi lên.";
    }
} else {
    echo "Yêu cầu không hợp lệ.";
}
