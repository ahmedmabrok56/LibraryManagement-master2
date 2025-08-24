use lib2;


INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator role with full permissions'),
('LIBRARIAN', 'Can manage books and members'),
('MEMBER', 'Regular library member');

-
INSERT INTO users (username, password, email, first_name, last_name, phone, active, created_at, updated_at) VALUES
('admin', 'password123', 'admin@example.com', 'Admin', 'User', '01000000001', TRUE, NOW(), NOW()),
('librarian1', 'password123', 'lib1@example.com', 'Lib', 'Rarian', '01000000002', TRUE, NOW(), NOW()),
('member1', 'password123', 'member1@example.com', 'Member', 'One', '01000000003', TRUE, NOW(), NOW()),
('member2', 'password123', 'member2@example.com', 'Member', 'Two', '01000000004', TRUE, NOW(), NOW()),
('member3', 'password123', 'member3@example.com', 'Member', 'Three', '01000000005', TRUE, NOW(), NOW());


INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin is ADMIN
(2, 2), -- librarian1 is LIBRARIAN
(3, 3), -- member1 is MEMBER
(4, 3),
(5, 3);



INSERT INTO categories (name, description, parent_id) VALUES
('Fiction', 'Fiction books', NULL),
('Non-Fiction', 'Non-fiction books', NULL),
('Science', 'Science books', 2),
('Fantasy', 'Fantasy books', 1),
('History', 'History books', 2);

INSERT INTO authors (first_name, last_name, birth_date, death_date, nationality, biography) VALUES
('J.K.', 'Rowling', '1965-07-31', NULL, 'British', 'Author of Harry Potter series'),
('George', 'Orwell', '1903-06-25', '1950-01-21', 'British', 'Author of 1984'),
('Agatha', 'Christie', '1890-09-15', '1976-01-12', 'British', 'Famous mystery author'),
('Isaac', 'Asimov', '1920-01-02', '1992-04-06', 'American', 'Science fiction writer'),
('Stephen', 'King', '1947-09-21', NULL, 'American', 'Horror and thriller author');


INSERT INTO publishers (name, address, city, country, website) VALUES
('Penguin Books', '123 Penguin St', 'London', 'UK', 'https://penguin.com'),
('HarperCollins', '456 Harper St', 'New York', 'USA', 'https://harpercollins.com'),
('Random House', '789 Random St', 'New York', 'USA', 'https://randomhouse.com');


INSERT INTO books (title, isbn, publication_year, edition, language, summary, cover_image_url, total_copies, available_copies, price, active, created_at, updated_at, category_id, publisher_id) VALUES
('Harry Potter and the Philosopher''s Stone', '9780747532699', 1997, '1st', 'English', 'Fantasy novel', '', 10, 10, 20.0, TRUE, NOW(), NOW(), 4, 1),
('1984', '9780451524935', 1949, '1st', 'English', 'Dystopian novel', '', 8, 8, 15.0, TRUE, NOW(), NOW(), 2, 2),
('Murder on the Orient Express', '9780007119318', 1934, '1st', 'English', 'Mystery novel', '', 5, 5, 18.0, TRUE, NOW(), NOW(), 1, 3),
('Foundation', '9780553293357', 1951, '1st', 'English', 'Science fiction series', '', 7, 7, 22.0, TRUE, NOW(), NOW(), 3, 3),
('The Shining', '9780385121675', 1977, '1st', 'English', 'Horror novel', '', 6, 6, 19.0, TRUE, NOW(), NOW(), 1, 2);


INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1), -- Harry Potter -> J.K. Rowling
(2, 2), -- 1984 -> George Orwell
(3, 3), -- Murder on the Orient Express -> Agatha Christie
(4, 4), -- Foundation -> Isaac Asimov
(5, 5); -- The Shining -> Stephen King


INSERT INTO members (membership_number, first_name, last_name, email, phone, address, date_of_birth, membership_date, membership_expiry, active, created_at, updated_at) VALUES
('M001', 'Alice', 'Smith', 'alice@example.com', '01010010001', '123 Street, City', '1990-05-10', '2025-01-01', '2026-01-01', TRUE, NOW(), NOW()),
('M002', 'Bob', 'Johnson', 'bob@example.com', '01010010002', '456 Street, City', '1985-08-15', '2025-02-01', '2026-02-01', TRUE, NOW(), NOW()),
('M003', 'Charlie', 'Brown', 'charlie@example.com', '01010010003', '789 Street, City', '1992-03-20', '2025-03-01', '2026-03-01', TRUE, NOW(), NOW());


INSERT INTO borrow_transactions (book_id, member_id, issued_by, returned_by, issue_date, due_date, return_date, status, fine_amount, fine_paid, notes, created_at, updated_at) VALUES
(1, 1, 2, NULL, '2025-08-01', '2025-08-15', NULL, 'ACTIVE', 0.0, FALSE, 'First borrow', NOW(), NOW()),
(2, 2, 2, 3, '2025-07-15', '2025-07-29', '2025-07-28', 'RETURNED', 0.0, TRUE, '', NOW(), NOW()),
(3, 3, 2, NULL, '2025-08-10', '2025-08-24', NULL, 'ACTIVE', 0.0, FALSE, '', NOW(), NOW()),
(4, 1, 2, NULL, '2025-08-05', '2025-08-19', NULL, 'ACTIVE', 0.0, FALSE, '', NOW(), NOW()),
(5, 2, 2, NULL, '2025-08-12', '2025-08-26', NULL, 'ACTIVE', 0.0, FALSE, '', NOW(), NOW());
