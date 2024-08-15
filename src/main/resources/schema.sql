-- Membuat tabel categories jika belum ada
CREATE TABLE IF NOT EXISTS public.categories (
    category_id VARCHAR(50) PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

-- Membuat tabel books jika belum ada
CREATE TABLE IF NOT EXISTS public.books (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    publication_year INTEGER,
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    category_id VARCHAR(50),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Membuat tabel users jika belum ada
CREATE TABLE IF NOT EXISTS public.users (
    id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(13) NOT NULL
);

-- Membuat tabel loan jika belum ada
CREATE TABLE IF NOT EXISTS public.loan (
    id VARCHAR(50) PRIMARY KEY,
    loan_date DATE NOT NULL,
    qty INTEGER NOT NULL,
    user_id VARCHAR(50),
    book_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
