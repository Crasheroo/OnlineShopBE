# 🛒 OnlineShopBE – Backend for Microservice-based Online Store

## 📌 Project Overview

**OnlineShopBE** is a backend system for an online store built using **microservice architecture** and the **hexagonal architecture pattern (Ports and Adapters)**. The goal is to provide a complete e-commerce backend that supports product browsing, configuration, ordering, and user interaction – all with a clean separation of concerns and modular design.

This backend is designed with scalability, testability, and future extensibility in mind.

---

## ⚙️ Technologies

- **Java 21**
- **Spring Boot / Spring Framework**
- **Hexagonal Architecture (Ports & Adapters)**
- **Docker / Docker Compose**
- **RESTful APIs**
- (Optional) Messaging queues for async communication

---

## 🧱 Architecture

The system is organized into independent microservices, each responsible for a specific domain area. Microservices communicate via HTTP REST APIs and may be extended to use asynchronous messaging in the future. The hexagonal architecture ensures that core domain logic is independent from transport, persistence, or external systems.

Each microservice is initialized as its own Git repository or branch and developed using **GitHub Flow**.

---

## 📦 Microservices

### 1. Product Service

Handles product management and dynamic configuration.

- Add, edit, delete, and fetch products
- Fetch all products or filter by product type
- Supports extended product configuration for:
  - **Computer**: Choose CPU, RAM size, etc.
  - **Smartphone**: Select color, battery capacity, and accessories
- Products of type `Electronics` have no additional configuration

---

### 2. Cart Service

Manages shopping cart operations.

- Add products to cart
- View cart contents
- Create a cart session
- Non-blocking behavior – users can continue browsing after adding items

---

### 3. Order Service

Handles the checkout and ordering process.

- Submit an order with user details:
  - Name, address, delivery method, payment method, etc.
- View order history
- Process order and generate invoices
- (Invoice generation may use internal logic or external APIs)

---

### 4. User Interaction Service (Gateway)

Acts as a proxy service and primary interface for the frontend.

- Frontend interacts **only with this service**
- Exposes endpoints required for completing the purchase process
- Delegates calls to internal microservices (Product, Cart, Order)
- Filters out unnecessary data, exposing only what the frontend requires

---

## 🛠 Setup Instructions

### Prerequisites

- Java 21
- Docker & Docker Compose
- Git

### Running the System

To run the entire system using Docker Compose:

```bash
docker-compose up --build
