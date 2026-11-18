# 📅 Quản Lý Lịch Hẹn - Native Android App

Đây là một ứng dụng di động Android thuần (Native Android) được xây dựng bằng Java và XML, sử dụng SQLite để quản lý cơ sở dữ liệu. Ứng dụng cung cấp các tính năng quản lý lịch trình cơ bản, hỗ trợ nhắc nhở thời gian với thiết kế Material Design hiện đại, đơn giản.

---

## 🚀 Các tính năng chính

*   **Xác thực cơ bản:** Màn hình đăng nhập an toàn, bảo vệ dữ liệu lịch trình của người dùng.
*   **Tổng quan trực quan:** Bảng điều khiển (Dashboard) hiển thị nhanh tổng số lịch hẹn và các lịch hẹn đã bật nhắc nhở.
*   **Quản lý danh sách:** Hiển thị danh sách lịch hẹn được sắp xếp theo trình tự thời gian (từ gần nhất đến xa nhất).
*   **Thêm mới chi tiết:** Giao diện thêm lịch hẹn dễ sử dụng với thanh cuộn thời gian, kiểm tra tính hợp lệ (bắt buộc thời gian hẹn phải ở trong tương lai).
*   **Chi tiết & Hành động:** Xem thông tin chi tiết từng cuộc hẹn, công tắc (switch) bật/tắt nhắc nhở tức thì, và tùy chọn xóa lịch hẹn.

---

## 🛠 Yêu cầu hệ thống (Prerequisites)

Để mở và chạy dự án này, máy tính của bạn cần được cài đặt các công cụ sau:

1.  **Android Studio:** Phiên bản tối thiểu khuyên dùng là *Android Studio Iguana* hoặc *Jellyfish* (Các phiên bản hỗ trợ tốt AndroidX, AppCompat, RecyclerView). Tải Android Studio tại: [https://developer.android.com/studio](https://developer.android.com/studio)
2.  **Java Development Kit (JDK):** JDK 11 hoặc JDK 17 (Thường đã được tích hợp sẵn bên trong Android Studio).
3.  **Hệ điều hành Android ảo (Emulator) / Máy thật:** Chạy Android 6.0 (API Level 23) trở lên.

---

## 📥 Hướng dẫn cài đặt & Khởi chạy (Setup Instructions)

### Bước 1: Mở dự án bằng Android Studio
1.  Giải nén thư mục mã nguồn dự án (Nếu bạn tải về dưới dạng `.zip`).
2.  Mở phần mềm **Android Studio**.
3.  Tại màn hình chào mừng (Welcome Screen), chọn **Open** (hoặc `File` > `Open` nếu đã ở trong một dự án khác).
4.  Điều hướng đến thư mục `TH03_APP_DaoXuanQuan_B24DCCC232` và bấm **OK**.

### Bước 2: Đồng bộ hóa Gradle (Gradle Sync)
1.  Ngay sau khi mở dự án, Android Studio sẽ tự động tiến hành tải về các thư viện cần thiết (Quá trình này yêu cầu kết nối Internet).
2.  Bạn có thể theo dõi tiến trình ở góc dưới cùng bên phải màn hình.
3.  Nếu không thấy tự động chạy, hãy nhấn vào biểu tượng **Sync Project with Gradle Files** (Hình con voi Gradle nhỏ có mũi tên trên thanh công cụ).
4.  Chờ đến khi nhận được thông báo `Build: BUILD SUCCESSFUL`.

### Bước 3: Chạy ứng dụng (Run App)
1.  **Chuẩn bị thiết bị:**
    *   *Sử dụng máy ảo:* Mở **Device Manager** (Biểu tượng điện thoại trên thanh công cụ), tạo một máy ảo mới (VD: Pixel 6, API 33) và khởi động nó.
    *   *Sử dụng máy thật:* Bật tính năng **USB Debugging** trên điện thoại Android, cắm cáp kết nối vào máy tính.
2.  Chọn thiết bị muốn chạy từ danh sách thả xuống trên thanh công cụ.
3.  Nhấn nút **Run 'app'** (Biểu tượng tam giác / Play màu xanh lá cây) hoặc dùng phím tắt `Shift + F10`.
4.  Đợi ứng dụng được biên dịch và tự động mở lên trên thiết bị.

---

## 🔑 Thông tin đăng nhập mặc định (Seed Data)

Khi cơ sở dữ liệu (SQLite) lần đầu được khởi tạo, ứng dụng sẽ tự động tạo một tài khoản mặc định để bạn trải nghiệm ngay:

*   **Tên đăng nhập:** `admin`
*   **Mật khẩu:** `123456`

*(Ngoài ra ứng dụng cũng sẽ tự động tạo sẵn 3 lịch hẹn mẫu trong cơ sở dữ liệu).*

---

## 📂 Cấu trúc mã nguồn chính (Source Code Structure)

Nếu bạn muốn khám phá mã nguồn, hãy tìm ở đường dẫn: `app/src/main/java/com/example/th03_app_daoxuanquan_b24dccc232/`

*   `activity/`: Chứa các màn hình (Giao diện Java logic).
    *   `LoginActivity.java`: Xử lý đăng nhập.
    *   `MainActivity.java`: Xử lý màn hình chính & hiển thị list.
    *   `AddAppointmentActivity.java`: Form thêm mới với Date/Time Picker.
    *   `AppointmentDetailActivity.java`: Xem chi tiết & xóa.
*   `adapter/`: 
    *   `AppointmentAdapter.java`: Cầu nối hiển thị dữ liệu lịch hẹn lên RecyclerView.
*   `database/`: 
    *   `DatabaseHelper.java`: Kế thừa `SQLiteOpenHelper`, xử lý toàn bộ truy vấn lưu trữ offline.
*   `model/`:
    *   `Appointment.java`: Lớp cấu trúc đối tượng dữ liệu.

Giao diện UI nằm ở: `app/src/main/res/layout/`.

---

## ⚠️ Lưu ý kỹ thuật dành cho Developer

*   Dự án sử dụng cơ sở dữ liệu thuần `SQLiteOpenHelper`.
*   Truyền tải dữ liệu giữa các màn hình (Intent) chỉ sử dụng duy nhất chuỗi `id` kiểu (int) và hệ thống sẽ tự query lại DB ở màn hình đích, không truyền Parcelable/Serializable.
*   Định dạng ngày lưu trong cơ sở dữ liệu bắt buộc là chuẩn ISO `yyyy-MM-dd` và giờ là `HH:mm` (24h) để đảm bảo mệnh đề SQL `ORDER BY` hoạt động trơn tru. Khi hiển thị lên thẻ card, dữ liệu sẽ được format sang chuẩn hiển thị của Việt Nam là `dd/MM/yyyy`.

---
*Chúc bạn có những trải nghiệm tuyệt vời cùng ứng dụng!*
