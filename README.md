# Multi-Factor Authentication System

This is a simple Spring Boot microservice project for an Agile lab/project.

## Problem covered
- Password + OTP authentication
- JUnit tests for unauthorized access attempts
- CI/CD pipeline configuration
- Docker deployment
- Kubernetes deployment

---

## Project structure

```text
mfa-auth-service/
├── src/
├── Dockerfile
├── Jenkinsfile
├── pom.xml
├── .github/workflows/ci.yml
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
└── README.md
```

---

## Default users for testing

| Username | Password |
|----------|----------|
| rakesh   | Password@123 |
| admin    | Admin@123 |

---

## API endpoints

### 1. Login and generate OTP
**POST** `/auth/login`

Request body:
```json
{
  "username": "rakesh",
  "password": "Password@123"
}
```

Sample response:
```json
{
  "message": "Password verified. OTP generated successfully.",
  "success": true,
  "otp": "123456"
}
```

### 2. Verify OTP
**POST** `/auth/verify-otp`

Request body:
```json
{
  "username": "rakesh",
  "otp": "123456"
}
```

---

# Full step-by-step process

## Step 1: Install required software
Install these in Windows:
1. **JDK 21**
2. **Maven**
3. **Git**
4. **Docker Desktop**
5. **VS Code** or **IntelliJ IDEA**
6. **kubectl**
7. **Minikube** (optional but recommended for Kubernetes locally)

Check installation:
```bash
java -version
mvn -version
git --version
docker --version
kubectl version --client
minikube version
```

---

## Step 2: Open project folder
Extract the ZIP.
Open terminal in the extracted project folder.

---

## Step 3: Run the project locally
Build the project:
```bash
mvn clean package
```

Run the application:
```bash
mvn spring-boot:run
```

or run JAR:
```bash
java -jar target/mfa-auth-service-1.0.0.jar
```

Application URL:
```text
http://localhost:8080
```

---

## Step 4: Test APIs using Postman

### Login API
- Method: `POST`
- URL: `http://localhost:8080/auth/login`
- Body:
```json
{
  "username": "rakesh",
  "password": "Password@123"
}
```

Copy the OTP from the response.

### Verify OTP API
- Method: `POST`
- URL: `http://localhost:8080/auth/verify-otp`
- Body:
```json
{
  "username": "rakesh",
  "otp": "<OTP_FROM_LOGIN_RESPONSE>"
}
```

---

## Step 5: Run JUnit tests
```bash
mvn test
```

This runs tests for:
- Invalid password
- Invalid OTP
- Missing fields
- Unknown user
- Valid OTP flow

---

## Step 6: Run with Docker
Build JAR first:
```bash
mvn clean package
```

Build Docker image:
```bash
docker build -t mfa-auth-service:1.0.0 .
```

Run Docker container:
```bash
docker run -p 8080:8080 mfa-auth-service:1.0.0
```

Now open:
```text
http://localhost:8080
```

---

## Step 7: Run with Kubernetes using Minikube
Start Minikube:
```bash
minikube start
```

Use Minikube Docker environment:
```bash
minikube image load mfa-auth-service:1.0.0
```

Apply Kubernetes files:
```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

Check pods:
```bash
kubectl get pods
```

Check service:
```bash
kubectl get svc
```

Access service:
```bash
minikube service mfa-auth-service
```

---

## Step 8: Push project to GitHub

### A. Create repository on GitHub
1. Open GitHub
2. Click **New repository**
3. Repository name: `mfa-auth-service`
4. Click **Create repository**

### B. Push from your laptop
Open terminal inside project folder and run:

```bash
git init
git add .
git commit -m "Initial commit - MFA auth service project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/mfa-auth-service.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

If Git asks for login:
- sign in with browser, or
- use GitHub Personal Access Token if required

---

## Step 9: Jenkins CI/CD pipeline
1. Open Jenkins
2. Create **New Item**
3. Choose **Pipeline**
4. Connect your GitHub repository URL
5. Jenkins automatically reads `Jenkinsfile`
6. Run **Build Now**

Pipeline stages:
- Checkout
- Build and Test
- Build Docker Image

---

## Viva / explanation points
- This project uses **two-factor authentication**.
- First factor is **username/password**.
- Second factor is **OTP verification**.
- Unauthorized access is blocked using validation logic.
- JUnit tests verify secure behavior.
- GitHub Actions and Jenkins support CI/CD.
- Docker provides containerized deployment.
- Kubernetes provides orchestration and scaling.

---

## Important note
This is a **demo academic project**.
In real projects:
- Passwords should be encrypted
- OTP should be sent by email/SMS
- OTP should expire after a time limit
- JWT/session tokens should be generated after success
- Database should be used instead of in-memory users
