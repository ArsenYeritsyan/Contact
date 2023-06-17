ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
# GRANT ALL PRIVILEGES ON contactsdb.* TO 'root'@'localhost';
CREATE DATABASE contactsdb;
FLUSH PRIVILEGES;