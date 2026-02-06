-- Tạo bảng sinh viên (giống cấu trúc Entity Lab 2)
CREATE TABLE IF NOT EXISTS students (
 id INTEGER PRIMARY KEY,
 name TEXT,
 email TEXT,
 age INTEGER
);
-- Thêm dữ liệu mẫu
INSERT INTO students (id, name, email, age) VALUES (1, 'Nguyen Van A',
'vana@example.com', 20);
INSERT INTO students (id, name, email, age) VALUES (2, 'Tran Thi B',
'thib@example.com', 21);