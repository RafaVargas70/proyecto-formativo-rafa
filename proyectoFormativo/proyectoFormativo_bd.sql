CREATE TABLE pacientes(
uuid NVARCHAR2(50) PRIMARY KEY,
nombre NVARCHAR2(50) NOT NULL,
apellido NVARCHAR2(50) NOT NULL,
edad INT NOT NULL, 
enfermedad NVARCHAR2(100) NOT NULL,
cuarto INT NOT NULL,
cama INT NOT NULL,
medicamentos NVARCHAR2(200) NOT NULL,
ingreso NVARCHAR2(10) NOT NULL,
horaMedicamentos NVARCHAR2(10) NOT NULL
);

select* from pacientes

