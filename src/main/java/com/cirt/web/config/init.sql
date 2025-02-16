INSERT INTO users (name, email, password, enabled, role) VALUES 
('Raiyan Ferdous', 'raiyan@cirt.gov.bd', '$2a$12$SN3oqHiDU2qjEbODvAU/3uRq8s8x7/VkDMB049nGikmhNql6ZQpLa', true, 'ADMIN');

CREATE TABLE authorities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id)
);

INSERT INTO authorities (username, authority) VALUES 
('admin', 'ROLE_ADMIN');