# 💸 PennyWise: Expense Tracker Web Application

A full-featured web application built to help users manage their personal expenses efficiently. It allows users to track, categorize, analyze, and control their spending habits.

## 🚀 Features

- **User Authentication**
  - Secure login and registration
  - Passwords encrypted using Bcrypt
  - Forgot Password functionality with SMTP email service
  - Option to change password after login

- **Expense Management**
  - Add, edit, delete, and categorize expenses (e.g., Food, Travel, Shopping, etc.)
  - Filter and view transactions in a clean tabular format

- **Reporting & Charts** (using Chart.js)
  - 📈 **Line Chart**: Displays expected expense over the last 6 months
  - 🥧 **Pie Chart**: Category-wise expense distribution for the current month
  - 📊 **Bar Chart**: Comparison of category-wise expenses between current and previous month
  - 🔁 **Bar Chart**: Comparison of expected vs actual expenses for current month

- **Expected Expense Feature**
  - Users can set an "expected expense" for each month (only once)
  - System notifies the user via email if their actual expenses exceed 75% of the expected amount

- **Export**
  - Export expense data to CSV format for offline usage

## 📧 Email Integration

- Integrated SMTP mail service for:
  - Forgot password recovery
  - Spending alert when expenses exceed 75% of the expected amount

## 🛡️ Security

- Used **Bcrypt** for password hashing
- Proper session handling and validation

## 🛠️ Technologies Used

- **Java**, **JSP/Servlets**, **Oracle SQL**
- **Chart.js** for data visualization
- **JavaMail API** for email services
- **HTML, CSS, JavaScript** for frontend

---

> ✅ This project helps users stay aware of their spending and promotes better financial habits by providing timely alerts, clean reports, and easy data tracking.

