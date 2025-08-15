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


CREATE TABLE IF NOT EXISTS book_categories (
    book_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

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


INSERT INTO roles (name , description) Values
('ADMIN' , 'System Administrator with full access'),
('LIBRARIAN' , 'Librarian with access to most library functions'),
('STAFF' , 'Staff member with basic access');

INSERT INTO categories (name ,description) Values
('Fiction' ,'Fictional literature and novels'),
('Non-Fiction', 'Non-fictional books and educational material'),
('Science', 'Scientific books and research material'),
('Technology', 'Technology and computer science books'),
('History', 'Historical books and documentation'),
('Biography', 'Biographical and autobiographical books');