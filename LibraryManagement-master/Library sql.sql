use `library_management_system`;

Create Table IF NOT EXISTS roles(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)  
);
-- ------------ -- 
CREATE TABLE IF NOT EXISTS users (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_roles(
	user_id BIGINT,
    role_id BIGINT,
    
	PRIMARY KEY ( user_id , role_id ),
    FOREIGN KEY ( user_id ) REFERENCES users(id),
    FOREIGN KEY ( role_id ) REFERENCES roles(id) 
);

CREATE TABLE IF NOT EXISTS authors(
	id BIGINT PRIMARY KEY AUTO_INCREMENT , 
	first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE ,
    death_date DATE , 
    nationality VARCHAR(50),
    biography TEXT
);

CREATE TABLE IF NOT EXISTS publishers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address TEXT,
    city VARCHAR(50),
    country VARCHAR(50),
    website VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS books(
	id BIGINT PRIMARY KEY AUTO_INCREMENT , 
	title VARCHAR(255) NOT NULL , 
	isbn VARCHAR(20) UNIQUE NOT NULL ,
	publication_year INT , 
	edition VARCHAR(50),
    language VARCHAR(30) ,
	page_count INT,
    summary TEXT,
    cover_image_url VARCHAR(500),
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2),
    
    publisher_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY(publisher_id) REFERENCES publishers(id)
);

CREATE TABLE IF NOT EXISTS book_authors (
    book_id BIGINT,
    author_id BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);


-- CREATE TABLE IF NOT EXISTS book_categories (
--     book_id BIGINT,
--     category_id BIGINT,
--     PRIMARY KEY (book_id, category_id),
--     FOREIGN KEY (book_id) REFERENCES books(id),
--     FOREIGN KEY (category_id) REFERENCES categories(id)
-- );

CREATE TABLE IF NOT EXISTS members(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
    membership_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    membership_date DATE NOT NULL,
    membership_expiry DATE,
    
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS borrow_transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    issued_by BIGINT NOT NULL,
    returned_by BIGINT,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    fine_amount DECIMAL(10,2) DEFAULT 0.00,
    fine_paid BOOLEAN DEFAULT FALSE,
    notes TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (issued_by) REFERENCES users(id),
    FOREIGN KEY (returned_by) REFERENCES users(id)
);

INSERT INTO users (username, password, email, first_name, last_name, phone, active)
VALUES
('admin1', 'hashed_password1', 'admin1@example.com', 'Alice', 'Admin', '123456789', TRUE),
('lib1', 'hashed_password2', 'lib1@example.com', 'Bob', 'Librarian', '987654321', TRUE),
('staff1', 'hashed_password3', 'staff1@example.com', 'Charlie', 'Staff', '555123456', TRUE);


INSERT INTO roles (id, name) VALUES
(1, 'ADMIN'),
(2, 'LIBRARIAN'),
(3, 'MEMBER');

-- 2. User Roles
INSERT INTO user_roles (user_id, role_id)
VALUES
(1, 1), -- admin1 -> ADMIN
(2, 2), -- lib1 -> LIBRARIAN
(3, 3); -- staff1 -> STAFF

-- 3. Publishers
INSERT INTO publishers (name, address, city, country, website)
VALUES
('Penguin Books', '80 Strand', 'London', 'UK', 'https://www.penguin.co.uk'),
('Reilly Media', '1005 Gravenstein Hwy N', 'Sebastopol', 'USA', 'https://www.oreilly.com');

INSERT INTO categories (name, description) VALUES
('Fiction', 'Fictional literature and novels'),
('Non-Fiction', 'Non-fictional books and educational material'),
('Science', 'Scientific books and research material'),
('Technology', 'Technology and computer science books'),
('History', 'Historical books and documentation'),
('Biography', 'Biographical and autobiographical books');

-- 4. Authors
INSERT INTO authors (first_name, last_name, birth_date, nationality, biography)
VALUES
('George', 'Orwell', '1903-06-25', 'British', 'Author of 1984 and Animal Farm'),
('Jane', 'Austen', '1775-12-16', 'British', 'Famous for Pride and Prejudice'),
('Mark', 'Lutz','1559-10-16' , 'American', 'Author of Learning Python');


INSERT INTO books
(title, isbn, publication_year, edition, language, page_count, summary, total_copies, available_copies, price, category_id, publisher_id, active)
VALUES
('1984', '9780451524935', 1949, '1st', 'English', 328, 'Dystopian novel about totalitarian regime', 5, 5, 12.99, 1, 1, TRUE),
('Pride and Prejudice', '9780141439518', 1813, '1st', 'English', 279, 'Classic romance novel', 4, 4, 9.99, 1, 1, TRUE),
('Learning Python', '9781449355739', 2013, '5th', 'English', 1648, 'Comprehensive guide to Python', 3, 3, 49.99, 4, 2, TRUE);


-- 6. Book Authors
INSERT INTO book_authors (book_id, author_id)
VALUES
(1, 1), -- 1984 -> George Orwell
(2, 2), -- Pride and Prejudice -> Jane Austen
(3, 3); -- Learning Python -> Mark Lutz

-- 7. Members
INSERT INTO members
(membership_number, first_name, last_name, email, phone, address, date_of_birth, membership_date, membership_expiry, active)
VALUES
('M001', 'David', 'Smith', 'david@example.com', '123123123', '123 Main St', '1990-05-10', '2024-01-01', '2025-01-01', TRUE),
('M002', 'Emma', 'Johnson', 'emma@example.com', '321321321', '456 Oak Ave', '1985-08-20', '2024-01-15', '2025-01-15', TRUE);

ALTER TABLE borrow_transactions
MODIFY COLUMN returned_by BIGINT DEFAULT NULL;


-- 8. Borrow Transactions
INSERT INTO borrow_transactions (book_id, member_id, issued_by, issue_date, due_date)
VALUES
(1, 1, 2, '2024-08-01', '2024-08-15'),
(3, 2, 2, '2024-08-05', '2024-08-20');





-- INSERT INTO roles (name , description) Values
-- ('ADMIN' , 'System Administrator with full access'),
-- ('LIBRARIAN' , 'Librarian with access to most library functions'),
-- ('STAFF' , 'Staff member with basic access');

-- INSERT INTO categories (name ,description) Values
-- ('Fiction' ,'Fictional literature and novels'),
-- ('Non-Fiction', 'Non-fictional books and educational material'),
-- ('Science', 'Scientific books and research material'),
-- ('Technology', 'Technology and computer science books'),
-- ('History', 'Historical books and documentation'),
-- ('Biography', 'Biographical and autobiographical books');

