# Student Management - Ứng dụng Quản lý Sinh viên

Ứng dụng web quản lý sinh viên được xây dựng bằng **Spring Boot**, **Thymeleaf** và **PostgreSQL (Neon Cloud)**.

## Thành viên nhóm

| MSSV    | Họ và Tên           |
|---------|---------------------|
| 2212865 | Đoàn Ngọc Văn Quý   |

## Public URL

https://student-management-i2e8.onrender.com/

---

# Chạy dự án local

## Yêu cầu

- Java 17+
- Maven 3.9+
- Docker + Docker Compose (tuỳ chọn)
- Sử dụng Neon Cloud Database

---

## Cấu hình Database (Neon PostgreSQL)

Neon cung cấp connection string dạng:

```
postgres://user:password@ep-xxx.ap-southeast-1.aws.neon.tech/dbname?sslmode=require
```

Trong Spring Boot, cần sử dụng dạng JDBC:

```
jdbc:postgresql://ep-xxx.ap-southeast-1.aws.neon.tech/dbname?sslmode=require
```

Lưu ý: Neon yêu cầu `sslmode=require`.

---

## Cách 1: Chạy bằng Docker Compose (khuyến nghị)

Tạo file `.env` ở thư mục gốc dự án:

```env
PORT=8080
DB_URL=jdbc:postgresql://ep-xxx.ap-southeast-1.aws.neon.tech/student_management?sslmode=require
DB_USERNAME=your_neon_user
DB_PASSWORD=your_neon_password
```

Docker Compose chỉ chạy ứng dụng:

```yaml
version: "3.9"

services:
  app:
    build: .
    ports:
      - "8080:8080"
    env_file:
      - .env
```

### Windows (PowerShell/CMD)

```powershell
docker compose up --build
```

### Linux/macOS

```bash
docker compose up --build
```

### Dừng container

```bash
docker compose down
```

Truy cập: http://localhost:8080/students

---

## Cách 2: Chạy trực tiếp bằng Maven

Tạo biến môi trường hoặc file `.env` với nội dung:

```env
DB_URL=jdbc:postgresql://ep-xxx.ap-southeast-1.aws.neon.tech/student_management?sslmode=require
DB_USERNAME=your_neon_user
DB_PASSWORD=your_neon_password
```

Hoặc cấu hình trực tiếp trong `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=${PORT:8080}
```

### Windows (PowerShell/CMD)

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

Lần đầu Maven sẽ tự động tải dependencies.

Truy cập: http://localhost:8080/students

---

# Câu trả lời lý thuyết Lab 1

## Q1. Bảng student sau khi insert và thực hiện các yêu cầu

![Lab1 - Student table](img/Lab1/Lab1_studentTable.png)

## Q2. Ràng buộc Primary Key

Khi insert bản ghi có `id` trùng với dữ liệu đã tồn tại, CSDL sẽ báo lỗi vì `id` là khóa chính:

- Không được trùng lặp (UNIQUE)
- Không được NULL

Điều này đảm bảo toàn vẹn thực thể và tránh dữ liệu trùng lặp.

**Minh họa:**

![Lab1 - Unique constraint](img/Lab1/Lab1_UniqueConstraint.png)

## Q3. Toàn vẹn dữ liệu (Constraints)

Nếu cột `name` không có `NOT NULL`, CSDL vẫn cho phép insert giá trị NULL. Việc này dễ gây:

- Dữ liệu thiếu thông tin
- Lỗi xử lý null trong ứng dụng
- Ảnh hưởng logic nghiệp vụ

Cần ràng buộc ở cả 2 tầng:

- Tầng CSDL: thêm `NOT NULL`
- Tầng ứng dụng: validation (`@NotBlank`, `@NotNull`)

**Minh họa:**

![Lab1 - Insert null name](img/Lab1/Lab1_InsertNullName.png)
![Lab1 - Insert 10 students](img/Lab1/Lab1_Insert10Student.png)

## Q4. Cấu hình Hibernate

Nếu dùng:

```properties
spring.jpa.hibernate.ddl-auto=create
```

Thì mỗi lần khởi động lại, Hibernate sẽ tạo lại bảng và có thể làm mất dữ liệu cũ.

Để giữ dữ liệu, nên dùng:

```properties
spring.jpa.hibernate.ddl-auto=update/validate
```

---

# Screenshot giao diện Lab 4

## 1. Danh sách sinh viên (`/students`)

- Hiển thị danh sách sinh viên
- Tìm kiếm theo tên
- Nút tạo mới, xem chi tiết, sửa, xóa

![Lab4 - Student List](img/Lab4/StudentList.png)

## 2. Chi tiết sinh viên (`/students/{id}`)

- Hiển thị thông tin chi tiết của sinh viên
- Có thao tác quay lại, sửa, xóa

![Lab4 - Student Detail](img/Lab4/StudentDetail.png)

## 3. Tạo mới sinh viên (`/students/create`)

![Lab4 - Create Student](img/Lab4/CreateStudentForm.png)

## 4. Cập nhật sinh viên (`/students/edit/{id}`)

![Lab4 - Update Student](img/Lab4/UpdateStudentForm.png)

---

# Công nghệ sử dụng

- Backend: Spring Boot 4.0.2, Spring Data JPA
- Frontend: Thymeleaf
- Database: PostgreSQL (Neon Cloud)
- Build: Maven, Docker
- Deploy: Render

---

# Chức năng chính

| Chức năng         | URL                  | Mô tả                    |
|-------------------|----------------------|--------------------------|
| Danh sách SV      | `GET /students`      | Hiển thị + tìm kiếm      |
| Chi tiết SV       | `GET /students/{id}` | Xem thông tin chi tiết   |
| Form tạo mới      | `GET /students/create` | Mở form tạo mới        |
| Lưu thông tin     | `POST /students/save`  | Tạo mới/cập nhật       |
| Form chỉnh sửa    | `GET /students/edit/{id}` | Mở form sửa         |
| Xóa SV            | `GET /students/delete/{id}` | Xóa sinh viên     |

---

# Cấu trúc project

```text
src/main/java/vn/edu/hcmut/cse/adsoftweng/lab/
|-- controller/
|   |-- ApplicationController.java
|   |-- StudentController.java
|   `-- StudentRestController.java
|-- entity/
|   `-- Student.java
|-- repository/
|   `-- StudentRepository.java
|-- service/
|   `-- StudentService.java
`-- StudentManagementApplication.java

src/main/resources/
|-- templates/students/
|   |-- list.html
|   |-- detail.html
|   `-- form.html
`-- application.properties
```

---

# Deployment

## Docker

```bash
docker build -t student-management:latest .
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://ep-xxx.ap-southeast-1.aws.neon.tech/student_management?sslmode=require \
  -e DB_USERNAME=your_neon_user \
  -e DB_PASSWORD=your_neon_password \
  student-management:latest
```

---

## Deploy trên Render (sử dụng Neon)

1. Tạo Web Service trên Render (Docker runtime)
2. Cấu hình Environment Variables:

```
DB_URL=jdbc:postgresql://ep-xxx.ap-southeast-1.aws.neon.tech/student_management?sslmode=require
DB_USERNAME=your_neon_user
DB_PASSWORD=your_neon_password
```

3. Render sẽ tự inject biến `PORT`
4. Deploy từ nhánh `main`