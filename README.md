
# 🏋️ AI-Based Fitness Application

A **microservices-based fitness tracking platform** that analyzes user workouts using AI to provide intelligent feedback and recommendations.

---

# 📌 Project Overview

The **Fitness Application** is built using **Spring Boot microservices architecture**.  
It allows users to record fitness activities and receive **AI-based analysis of their workouts**.

The system is composed of multiple independent services communicating through an **API Gateway**, with **Eureka for service discovery** and **Config Server for centralized configuration**.

Repository:  
https://github.com/Ajaykapratwar/fitness_application

---

# 🧩 System Architecture

```
                +-----------------------+
                |       Frontend        |
                |   Fitness Web App     |
                +-----------+-----------+
                            |
                            v
                    +---------------+
                    |  API Gateway  |
                    +-------+-------+
                            |
      ---------------------------------------------------
      |                    |                   |
      v                    v                   v
+-------------+     +--------------+     +--------------+
| User Service|     |Activity Serv.|     |  AI Service  |
+------+------+     +------+-------+     +------+-------+
       |                   |                    |
       -----------------------------------------
                           |
                           v
                      +---------+
                      | MongoDB |
                      +---------+

Infrastructure Services:
- Eureka Server → Service discovery
- Config Server → Centralized configuration
```

---

# ⚙️ Microservices

## User Service
Responsible for managing users.

Functions:
- User registration
- User login
- Profile management

Example APIs:

```
POST /users/register
POST /users/login
GET /users/{id}
```

---

## Activity Service

Stores and manages user workout activities.

Functions:

- Record workouts
- Store activity data
- Track duration and calories

Example APIs:

```
POST /activities
GET /activities/{userId}
GET /activities/{id}
```

---

## AI Service

Analyzes workout data and provides intelligent feedback.

Example analysis:

```
Workout Analysis
Duration: 90 minutes
Intensity: Moderate
Calories Burned: 500 kcal

Recommendation:
Increase speed slightly to improve calorie efficiency.
```

---

## API Gateway

Acts as the **single entry point** for all requests.

Routes requests to microservices.

Example routes:

```
/api/users → User Service
/api/activity → Activity Service
/api/ai → AI Service
```

---

# 🗄 Database Schema

## Users Collection

| Field | Type | Description |
|------|------|-------------|
| id | ObjectId | Unique user ID |
| name | String | User name |
| email | String | User email |
| password | String | Encrypted password |
| createdAt | Date | Account creation date |

---

## Activities Collection

| Field | Type | Description |
|------|------|-------------|
| id | ObjectId | Activity ID |
| userId | String | User reference |
| activityType | String | Running / Walking / Cycling |
| duration | Integer | Duration in minutes |
| caloriesBurned | Integer | Calories burned |
| distance | Double | Distance covered |
| activityDate | Date | Activity timestamp |

---

## AI Analysis Collection (Optional)

| Field | Type |
|------|------|
| id | ObjectId |
| userId | String |
| activityId | String |
| analysisSummary | String |
| recommendations | String |
| generatedAt | Date |

---

# 🚀 How to Run the Project

## Clone the repository

```
git clone https://github.com/Ajaykapratwar/fitness_application.git
cd fitness_application
```

---

## Start infrastructure services

Run in this order:

```
configserver
eureka
gateway
```

---

## Start microservices

```
userservice
activityservice
aiservice
```

Run each service:

```
mvn spring-boot:run
```

---

## Run frontend

Navigate to:

```
fitness-app-frontend
```

Start the frontend server.

---

# 🛠 Tech Stack

## Backend

- Java
- Spring Boot
- Spring Cloud
- Spring Security

## Infrastructure

- API Gateway
- Eureka Server
- Config Server

## Database

- MongoDB
- PostgreSQL

## Frontend

- HTML
- CSS
- JavaScript

---

# 🔄 Application Flow

1. User registers or logs in.
2. User records a fitness activity.
3. Activity service stores workout data.
4. AI service analyzes the activity.
5. AI generates fitness insights.
6. Frontend displays results.

---

# 📈 Future Improvements

- Wearable device integration
- AI workout planning
- Nutrition tracking
- Mobile app
- Real-time analytics

---

# 👨‍💻 Author

**Ajay Kapratwar**

GitHub:  
https://github.com/Ajaykapratwar
