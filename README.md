# HomeBudget 💰🏠

**HomeBudget** is an application for managing a household budget. It stores expense data and allows generating reports and statistics.

## 📌 Features

- Add, edit, delete, and view expenses
- Assign expenses to categories (e.g. food, entertainment, education)
- Full CRUD support for both expenses and categories
- Generate monthly reports showing which categories generate the most spending

## 🧩 Data Structure

Each expense includes:
- Who made the purchase
- What was purchased
- How much it cost
- The purchase date

Expenses are grouped into categories like:  
`food`, `cleaning`, `entertainment`, `growth/development`, etc.

## 🧪 Technologies

The project uses:

- Java 17+
- Gradle
- Spring Boot
- Spring Data (Repositories)
- Lombok
- Git & GitHub
- Testing: JUnit 5, Mockito
- PostgreSQL
- CI/CD pipeline (GitHub Actions)
- Deployment using Docker and Render
- REST API (planned)

## 📦 Architecture

The application follows the Repository + Service pattern:

- `CostRepository` / `CategoryRepository` – interfaces defining CRUD operations
- `ListBasedCostRepository` / `ListBasedCategoryRepository` – in-memory implementations using `ArrayList`
- `CostService` / `CategoryService` – service layer for business logic and Spring integration

## 🔧 Planned Extensions

- REST API endpoints

## 🚀 Running the App

```bash
./gradlew bootRun
