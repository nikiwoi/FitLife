# 🏋️‍♂️ FitLife - Personal Fitness & Nutrition Management System

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)

FitLife is a Java-based fitness and nutrition management application engineered to help users track their workouts and daily calorie intake without the need for specialized gym equipment. Developed as a final project for an Object-Oriented Programming foundation, the system implements core OOP principles alongside flat-file data persistence to create an automated, lightweight, and reliable personal health-tracking ecosystem.

---

## 📌 Project Context & Metadata

| Attribute | Details |
| :--- | :--- |
| 🎓 Institution | Universitas Ciputra Surabaya |
| 🚀 Academic Timeline | Semester 2 - Object-Oriented Programming Final Project |
| 📅 Development Period | May 2025 – June 2025 |
| 👥 Team Size | 2 Developers |
| 💻 Interface | Terminal (CLI) |

---

## 🚀 Technical Features & Logic

### 🧠 Core OOP & Recommendation Engine
- Object-Oriented Architecture: Built on strict OOP principles (Inheritance, Polymorphism, Encapsulation, and Abstraction) to ensure decoupled code structures and clean domain logic.
- Smart Recommendation Engine: Implements an internal Body Mass Index (BMI) algorithm that automatically evaluates the user's physical profile to prescribe personalized workout difficulty levels (Beginner, Intermediate, Advanced).

### 🍏 Nutrition & Workout Intelligence
- Nutritionix API Integration: Connects with remote Nutritionix API endpoints to fetch accurate food calorie metrics, powering a daily nutrition log that monitors real-time caloric intake.
- Dynamic Workout Generator: Features a workout randomization module that programmatically curates daily routines, complete with step-by-step instructions and specific health benefits to keep exercise routines varied.
- Calorie Progress Tracking: Integrates a net calorie calculator (burn vs. intake) mapped against the user's targeted weight goals for real-time fitness progress monitoring.

### 💾 Data Layer & Memory Optimization
- Flat-File Persistence: Implements localized data storage using .txt files to serialize and preserve user profiles, daily nutrition logs, and workout histories across terminal sessions.
- Advanced Memory Management: Utilizes dynamic ArrayList structures for fluid data manipulation and StringBuilder for low-overhead, optimized string formatting during console rendering.
- Input Validation & Error Handling: Enforces a comprehensive exception-handling architecture to intercept malformed terminal inputs and maintain system reliability.

---

## 💻 Tech Stack

- Language: Java
- Interface: Terminal (CLI)
- Remote API: Nutritionix API
- Storage Engine: Flat File Storage (.txt)
- Paradigm: Object-Oriented Programming (OOP)
