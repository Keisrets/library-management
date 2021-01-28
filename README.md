# library-management

Library management system is a sandbox project which provides author, book and loan CRUD. Additional features include user registration/login functionality plus role management accessible from administrator panel.

## Used technologies/libraries
- Spring MVC
- Spring Security
- Thymeleaf
- Hibernate
- Bootstrap
- bootstrap-select
- jQuery
- MySQL
- Maven

### Role management:
- *Guest* (can search for books and check their availability based on book quantity in database)
- *User* (can receive loans from librarians, check their start and due dates, also change password)
- *Librarian* (author, book CRUD, loan issuing and deletion through librarian panel)
- *Administrator* (user CRUD and role management through admin panel)

### Main routes:
`.../lib-dashboard/` - Librarian panel  
`.../lib-admin/` - Administrator panel

## Set up
In terminal/command prompt, run the following command:  
`git clone https://github.com/keisrets/library-management.git`  
  
 To run the project, you'll need to provide the environment variables for MySQL database access:  
 - `DB_USER`
 - `DB_PASS`
 - `DB_URL`
 
 Example:  
 `DB_USER=username;DB_PASS=password;DB_URL=jdbc:mysql://localhost:3306/library-management`
   
     
     In case of any questions, feel free to contact me using the e-mail address in my profile.
