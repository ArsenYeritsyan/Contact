ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '';

GRANT ALL PRIVILEGES ON contactsdb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
