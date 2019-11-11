create table students (B_no char(4) primary key check (B_no like 'B%'),
first_name varchar(15) not null, last_name varchar(15) not null, status varchar(10) 
check (status in ('freshman', 'sophomore', 'junior', 'senior', 'MS', 'PhD')), 
gpa decimal(3,2) check (gpa between 0 and 4.0), email varchar(20) unique,
bdate datetime, deptname varchar(4) not null);

create table tas (B_no char(4) primary key references students (B_no),
ta_level varchar(3) not null check (ta_level in ('MS', 'PhD')), 
office varchar(10));  

create table courses (dept_code varchar(4), course_no smallint
check (course_no between 100 and 799), title varchar(20) not null,
primary key (dept_code, course_no));

create table classes (classid char(5) primary key check (classid like 'c%'), 
dept_code varchar(4) not null, course_no smallint not null, 
sect_no tinyint, year smallint, semester varchar(8) 
check (semester in ('Spring', 'Fall', 'Summer 1', 'Summer 2')), class_limit smallint, 
class_size smallint, room varchar(10), ta_B_no char(4) references tas (B_no),
foreign key (dept_code, course_no) references courses (dept_code, course_no) on delete cascade, 
unique(dept_code, course_no, sect_no, year, semester), check (class_size <= class_limit));

create table enrollments (B_no char(4) references students (B_no), classid char(5) references classes (classid), 
lgrade varchar(2) check (lgrade in ('A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-','D', 
'F', 'I')), primary key (B_no, classid));

create table prerequisites (dept_code varchar(4) not null,
course_no smallint not null, pre_dept_code varchar(4) not null,
pre_course_no smallint not null,
primary key (dept_code, course_no, pre_dept_code, pre_course_no),
foreign key (dept_code, course_no) references courses (dept_code, course_no) on delete cascade,
foreign key (pre_dept_code, pre_course_no) references courses (dept_code, course_no) on delete cascade);

create table logs (log_no smallint primary key, op_name varchar(10) not null, op_time datetime not null,
table_name varchar(12) not null, operation varchar(6) not null, key_value varchar(10));

insert into students values ('B001', 'Anne', 'Broder', 'junior', 3.17, 'broder@bu.edu', '1990-01-17', 'CS');
insert into students values ('B002', 'Terry', 'Buttler', 'senior', 3.0, 'buttler@bu.edu', '1989-05-28', 'Math');
insert into students values ('B003', 'Tracy', 'Wang', 'senior', 4.0, 'wang@bu.edu','1993-08-06', 'CS');
insert into students values ('B004', 'Barbara', 'Callan', 'junior', 2.5, 'callan@bu.edu', '1991-10-18', 'Math');
insert into students values ('B005', 'Jack', 'Smith', 'MS', 3.2, 'smith@bu.edu', '1991-10-18', 'CS');
insert into students values ('B006', 'Terry', 'Zillman', 'PhD', 4.0, 'zillman@bu.edu', '1988-06-15', 'Biol');
insert into students values ('B007', 'Becky', 'Lee', 'senior', 4.0, 'lee@bu.edu', '1992-11-12', 'CS');
insert into students values ('B008', 'Tom', 'Baker', 'freshman', null, 'baker@bu.edu', '1996-12-23', 'CS');
insert into students values ('B009', 'Ben', 'Liu', 'MS', 3.8, 'liu@bu.edu', '1992-03-18', 'Math');
insert into students values ('B010', 'Sata', 'Patel', 'MS', 3.9, 'patel@bu.edu', '1990-10-12', 'CS');
insert into students values ('B011', 'Art', 'Chang', 'PhD', 4.0, 'chang@bu.edu', '1989-06-08', 'CS');
insert into students values ('B012', 'Tara', 'Ramesh', 'PhD', 3.98, 'ramesh@bu.edu', '1991-07-29', 'Math');

insert into tas values ('B005', 'MS', 'EB G26');
insert into tas values ('B009', 'MS', 'WH 112');
insert into tas values ('B010', 'MS', 'EB G26');
insert into tas values ('B011', 'PhD','EB P85');
insert into tas values ('B012', 'PhD','WH 112');

insert into courses values ('CS', 432, 'database systems');
insert into courses values ('Math', 314, 'discrete math');
insert into courses values ('CS', 240, 'data structure');
insert into courses values ('Math', 221, 'calculus I');
insert into courses values ('CS', 532, 'database systems');
insert into courses values ('CS', 552, 'operating systems');
insert into courses values ('Biol', 425, 'molecular biology');

insert into classes values ('c0001', 'CS', 432, 1, 2018, 'Fall', 2, 1, 'LH 005', 'B005');
insert into classes values ('c0002', 'Math', 314, 1, 2018, 'Spring', 4, 4, 'LH 009', 'B012');
insert into classes values ('c0003', 'Math', 314, 2, 2018, 'Spring', 4, 2, 'LH 009', 'B009');
insert into classes values ('c0004', 'CS', 432, 2, 2018, 'Fall', 2, 2, 'SW 222', 'B005');
insert into classes values ('c0005', 'CS', 240, 1, 2017, 'Spring', 3, 2, 'LH 003', 'B010');
insert into classes values ('c0006', 'CS', 532, 1, 2018, 'Fall', 3, 2, 'LH 005', 'B011');
insert into classes values ('c0007', 'Math', 221, 1, 2017, 'Fall', 7, 6, 'WH 155', null);
insert into classes values ('c0008', 'CS', 552, 1, 2018, 'Fall', 1, 0, 'EB R15', null);
insert into classes values ('c0009', 'CS', 240, 1, 2018, 'Fall', 2, 1, 'EB R15', null);

insert into prerequisites values ('Math', '314', 'Math', '221');
insert into prerequisites values ('CS', '432', 'Math', '314');
insert into prerequisites values ('CS', '532', 'Math', '314');
insert into prerequisites values ('CS', '552', 'CS', '240');

insert into enrollments values ('B001', 'c0001', null);
insert into enrollments values ('B001', 'c0003', 'B');
insert into enrollments values ('B001', 'c0007', 'B+');
insert into enrollments values ('B002', 'c0002', 'B');
insert into enrollments values ('B002', 'c0007', 'B');
insert into enrollments values ('B003', 'c0004', null);
insert into enrollments values ('B003', 'c0002', 'A-');
insert into enrollments values ('B003', 'c0007', 'B');
insert into enrollments values ('B004', 'c0004', null);
insert into enrollments values ('B004', 'c0005', 'B+');
insert into enrollments values ('B004', 'c0003', 'A');
insert into enrollments values ('B004', 'c0007', 'A');
insert into enrollments values ('B005', 'c0006', null);
insert into enrollments values ('B005', 'c0002', 'B');
insert into enrollments values ('B005', 'c0007', 'B');
insert into enrollments values ('B006', 'c0006', null);
insert into enrollments values ('B006', 'c0002', 'A');
insert into enrollments values ('B006', 'c0007', 'A-');
insert into enrollments values ('B007', 'c0005', 'C');
insert into enrollments values ('B008', 'c0009', null);
