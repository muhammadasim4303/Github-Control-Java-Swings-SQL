# **GitHub Sync App**  

## **Overview**  
GitHub Sync App is a Java-based desktop application that allows users to seamlessly synchronize their local projects with GitHub. The application provides an intuitive GUI for repository management, including authentication via GitHub OAuth, repository creation, and local folder linking.  

## **Features**  
- **User Authentication**:  
  - Supports GitHub OAuth login  
  - Secure authentication without manual token entry  
- **Repository Management**:  
  - Fetches and displays public and private repositories  
  - Allows users to create new repositories  
- **Local Sync with GitHub**:  
  - Links a local folder to a GitHub repository  
  - Enables commit and push operations  
  - Provides graphical file selection for commit and push  
- **User Account System**:  
  - MySQL-backed user registration and login  
  - Secure password storage and authentication  
  - Password recovery via OTP-based email verification  

## **Technologies Used**  
- **Programming Language**: Java (Swing for GUI)  
- **Database**: MySQL  
- **Version Control**: Git & GitHub API (JGit)  
- **Networking**: GitHub REST API for authentication and repository management  

## **Installation and Setup**  
### **1. Clone the Repository**  
```sh
git clone https://github.com/your-username/githubsyncapp.git
cd githubsyncapp
```

### **2. Configure Database**  
Ensure MySQL is installed and create the required database:  
```sql
CREATE DATABASE githubsync;
USE githubsync;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    otp VARCHAR(6)
);
```

### **3. Update Database Credentials**  
Modify `Database.java` to match your MySQL credentials:  
```java
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### **4. Install Dependencies**  
Ensure Maven is installed and run:  
```sh
mvn clean install
```

### **5. Run the Application**  
```sh
mvn exec:java -Dexec.mainClass="com.mycompany.githubsyncapp.GitHubSyncApp"
```

## **Usage**  
1. **User Registration & Login**  
   - Open the application and create an account.  
   - Log in using the registered credentials.  
2. **GitHub Authentication**  
   - Click "Login with GitHub" and authorize the application.  
3. **Repository Management**  
   - View all repositories linked to your GitHub account.  
   - Create new repositories directly from the UI.  
4. **Local Folder Sync**  
   - Select a local folder and link it to a GitHub repository.  
   - Commit and push changes through the GUI.  

## **Troubleshooting**  
- **Database Connection Issues**:  
  - Ensure MySQL is running and database credentials are correct.  
- **GitHub Authentication Fails**:  
  - Verify the GitHub OAuth credentials and callback URL.  
- **Unable to Push Changes**:  
  - Ensure the repository has a valid remote origin set.  

## **Contributing**  
1. Fork the repository.  
2. Create a new feature branch.  
3. Implement changes and test thoroughly.  
4. Submit a pull request with a clear description of changes.  

## **Contact**  
For any issues or feature requests, please create an issue in the repository.
