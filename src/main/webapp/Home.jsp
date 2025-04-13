<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.bean.UserBean"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.dao.UserDao"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PennyWise | Dashboard</title>
<link rel="stylesheet" href="assets/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
	<header>
		<div class="container">
			<nav>
				<a href="Home.jsp" class="logo">
					<div class="logo-icon">P</div>
					<div class="logo-text">PennyWise</div>
				</a>
				<div class="nav-links">
					<a href="LogoutServlet" class="button button-outline">Logout</a> <a
						href="ProfileServlet" class="logo"> <img
						src="assets/images/profile.png" class="logo-profile">
					</a>


					<%
					if (session == null) {
						response.sendRedirect("Login.jsp"); // Redirect to login if no session exists
						return;
					}
					UserDao userDao = new UserDao();
					int userId = (Integer) (session.getAttribute("userId"));
					UserBean userBean = userDao.findById(userId);
					String userName = userBean.getUserName();
					%>
				</div>
			</nav>
		</div>
	</header>



	<!-- Dashboard/Home Page -->
	<section class="dashboard">
		<div class="container">
			<div class="dashboard-header">
				<div class="welcome-text">
					<h1>
						Welcome back,
						<%=userName%>!
					</h1>
					<p>Here's your financial summary</p>
				</div>
			</div>


			<!-- Expected Expense Card -->
			<div class="balance-card" id="expected-expense-card"></div>

			<!-- Balance Overview Card -->
			<div class="balance-card">
				<div class="balance-title">TOTAL BALANCE</div>
				<div class="balance-amount">Rs. ${totalSum}</div>
				<div class="balance-stats">
					<div class="stat stat-income">
						<div class="stat-title">INCOME</div>
						<div class="stat-amount">Rs. ${incomeSum}</div>
					</div>
					<div class="stat stat-expense">
						<div class="stat-title">EXPENSES</div>
						<div class="stat-amount">Rs. ${expenseSum}</div>
					</div>
				</div>
			</div>

			<!-- Transactions Section -->
			<!-- Transactions Section -->
			<div class="balance-card">
				<div class="card-header">
					<h3 class="card-title">Transaction Filter</h3>
				</div>
				<div class="filter-container">
					<form action="FilterServlet" method="get" class="filter-form">
						<div class="form-group">
							<label for="date-filter" class="form-label">Filter By</label> <select
								id="date-filter" name="filterType" class="form-input">
								<option value="week">This Week</option>
								<option value="month">This Month</option>
								<option value="year">This Year</option>
								<option value="custom">Custom Date</option>
							</select>
						</div>
						<div class="form-group">
							<label for="filter-date" class="form-label">Select Date</label> <input
								type="date" id="filter-date" name="filterDate"
								class="form-input">
						</div>

						<!-- Category Filter Section with Single Value Dropdown -->
						<div class="form-group">
							<label for="category-filter" class="form-label">Filter by
								Category</label> <select id="category-filter" name="category"
								class="form-input">
								<option value="all">All Categories</option>
								<optgroup label="Income">
									<option value="Salary">Salary</option>
									<option value="Allowance">Allowance</option>
									<option value="Interest">Interest</option>
								</optgroup>
								<optgroup label="Expenses">
									<option value="Shopping">Shopping</option>
									<option value="Food">Food</option>
									<option value="Groceries">Groceries</option>
									<option value="Travel">Travel</option>
									<option value="Bills">Bills</option>
									<option value="Rent">Rent</option>
									<option value="Personal">Personal</option>
									<option value="Entertainment">Entertainment</option>
									<option value="Transportation">Transportation</option>
									<option value="Gifts and Donations">Gifts</option>
									<option value="Gifts">Donations</option>
									<option value="Others">Others</option>
								</optgroup>
							</select>
						</div>

						<button type="submit" class="button button-primary">Apply
							Filter</button>
						<a href="GetExpenseIncomeServlet"
							style="text-decoration: none; color: inherit;"> <span
							style="font-size: 18px; font-weight: bold;">Remove Filter</span>
						</a>
					</form>
				</div>
			</div>

			<div class="transactions-container">
				<!-- Income Transactions -->
				<div class="transaction-card income">
					<div class="card-header">
						<h3 class="card-title">Income</h3>
						<button class="button button-primary add-button"
							onclick="openModal('add-income')">+ Add Income</button>
					</div>
					<ul class="transaction-list"
						style="max-height: 300px; overflow-y: auto; padding: 0; margin: 0; list-style: none;">
						<c:forEach var="income" items="${incomeList}">
							<li class="transaction-item income">
								<div class="transaction-info">
									<div class="transaction-icon">+</div>
									<div class="transaction-details">
										<h4>${income.cname}</h4>
										<!-- Category Name -->
										<p>${income.eDate}</p>
										<!-- Expense Date -->
										<p class="transaction-description">${income.description}</p>
										<!-- Description Field -->
									</div>
								</div>
								<div class="transaction-amount-container">
									<div class="transaction-amount">+Rs. ${income.amount}</div>
									<button class="action-button edit-button"
										onclick="openEditIncomeModal(${income.expenseId},'${income.cname}', ${income.amount},'${income.eDate}','${income.description}')"
										style="color: #00AAAA; font-weight: 400; font-size: 16px">edit</button>
									<button class="action-button edit-button"
										onclick="deleteModal(${income.expenseId})"
										style="color: #FF0000; font-weight: 400; font-size: 16px">delete</button>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>


				<!-- Expense Transactions -->
				<div class="transaction-card expense">
					<div class="card-header">
						<h3 class="card-title">Expenses</h3>
						<button class="button button-primary add-button"
							onclick="openModal('add-expense')">+ Add Expense</button>
					</div>
					<ul class="transaction-list"
						style="max-height: 300px; overflow-y: auto; padding: 0; margin: 0; list-style: none;">
						<c:forEach var="expense" items="${expenseList}">
							<li class="transaction-item expense">
								<div class="transaction-info">
									<div class="transaction-icon">-</div>
									<div class="transaction-details">
										<h4>${expense.cname}</h4>
										<!-- Category Name -->
										<p>${expense.eDate}</p>
										<!-- Expense Date -->
										<p class="transaction-description">${expense.description}</p>
									</div>
								</div>
								<div class="transaction-amount-container">
									<div class="transaction-amount">+Rs. ${expense.amount}</div>
									<button class="action-button edit-button"
										onclick="openEditExpenseModal(${expense.expenseId},'${expense.cname}', ${expense.amount},'${expense.eDate}','${expense.description}')"
										style="color: #00AAAA; font-weight: 400; font-size: 16px">edit</button>
									<button class="action-button edit-button"
										onclick="deleteModal(${expense.expenseId})"
										style="color: #FF0000; font-weight: 400; font-size: 16px">delete</button>
								</div>

							</li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="balance-card" id="expected-expense-card">
				<h3 class="modal-title" id="expected-expense-modal-title">
					Download csv file of your expenses and income click the button
					below</h3>
				</br>
				<button class="button button-primary" onclick="downloadCSV()">Download
					CSV</button>

			</div>

			<!-- Add this code after the transactions-container div -->
			<div class="balance-card">
				<h3 class="section-title">Expense Analytics</h3>

				<div class="analytics-cards">

					<!-- Monthly Expected Expense Card -->
					<div class="analytics-card">
						<div class="card-header">
							<h3 class="card-title">Monthly Expected Expense</h3>
						</div>
						<div class="card-content">
							<div class="chart-container">
								<canvas id="monthlyExpectedChart"></canvas>
							</div>
						</div>
					</div>

					<!-- Category-wise Expense Card -->
					<div class="analytics-card">
						<div class="card-header">
							<h3 class="card-title">Category-wise Expenses</h3>
						</div>
						<div class="card-content">
							<div class="chart-container">
								<canvas id="categoryExpenseChart"></canvas>
							</div>
						</div>
					</div>

					<!-- Month Comparison Card -->
					<div class="analytics-card">
						<div class="card-header">
							<h3 class="card-title">Last Month Comparison</h3>
						</div>
						<div class="card-content">
							<div class="chart-container">
								<canvas id="monthComparisonChart"></canvas>
							</div>
						</div>
					</div>

					<!-- Expected vs Actual Card -->
					<div class="analytics-card">
						<div class="card-header">
							<h3 class="card-title">Expected vs Actual Expenses</h3>
						</div>
						<div class="card-content">
							<div class="chart-container">
								<canvas id="expectedVsActualChart"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Add Expense Modal -->
	<div class="modal-overlay" id="expense-modal">
		<div class="modal">
			<div class="modal-header">
				<h3 class="modal-title" id="modal-title">Add Expense</h3>
				<button class="close-button" onclick="closeModal()">&times;</button>
			</div>
			<form id="expense-form" action="AddExpenseServlet" method="POST">
				<input type="hidden" id="expense-id" value=""> <input
					type="hidden" id="modelname" name="modelname" value="">
				<div class="form-group">
					<label for="expense-category" class="form-label">Category</label> <select
						id="expense-category" name="category" class="form-input" required>
					</select>
				</div>
				<div class="form-group">
					<label for="expense-description" class="form-label">Description</label>
					<input type="text" id="expense-description" name="description"
						class="form-input" placeholder="Enter a description" required>
				</div>
				<div class="form-group">
					<label for="expense-amount" class="form-label">Amount (₹)</label> <input
						type="number" id="expense-amount" name="amount" class="form-input"
						placeholder="0.00" step="0.01" required>
				</div>
				<div class="form-group">
					<label for="expense-date" class="form-label">Date</label> <input
						type="date" name="date" id="expense-date" class="form-input"
						required>
				</div>
				<div class="modal-footer">
					<button type="button" class="button button-outline"
						onclick="closeModal()">Cancel</button>
					<button type="submit" class="button button-primary">Save</button>
				</div>
			</form>
		</div>
	</div>
	<!-- edit Expense Modal -->

	<div class="modal-overlay" id="edit-expense-modal">
		<div class="modal">
			<div class="modal-header">
				<h3 class="modal-title" id="edit-modal-title">Edit Expense</h3>
				<button class="close-button" onclick="closeModal()">&times;</button>
			</div>
			<form id="expense-form" action="EditExpenseServlet" method="POST">
				<input type="hidden" id="edit-expense-id" name="expenseId" value="">
				<input type="hidden" id="modelName" name="edit-expense-type"
					value="">
				<div class="form-group">
					<label for="expense-category" class="form-label">Category</label> <select
						id="edit-expense-category" name="category" class="form-input"
						required>
					</select>
				</div>
				<div class="form-group">
					<label for="expense-description" class="form-label">Description</label>
					<input type="text" id="edit-expense-description" name="description"
						class="form-input" placeholder="Enter a description" required>
				</div>
				<div class="form-group">
					<label for="expense-amount" class="form-label">Amount (₹)</label> <input
						type="number" id="edit-expense-amount" name="amount"
						class="form-input" placeholder="0.00" step="0.01" required>
				</div>
				<div class="form-group">
					<label for="expense-date" class="form-label">Date</label> <input
						type="date" name="date" id="edit-expense-date" class="form-input"
						required>
				</div>
				<div class="modal-footer">
					<button type="button" class="button button-outline"
						onclick="closeModal()">Cancel</button>
					<button type="submit" class="button button-primary">Edit</button>
				</div>
			</form>
		</div>
	</div>

	<div class="modal-overlay" id="edit-income-modal">
		<div class="modal">
			<div class="modal-header">
				<h3 class="modal-title" id="edit-income-modal-title">Edit
					Income</h3>
				<button class="close-button" onclick="closeModal()">&times;</button>
			</div>
			<form id="expense-form" action="EditIncomeServlet" method="POST">
				<input type="hidden" id="edit-income-id" name="expenseId" value="">
				<input type="hidden" id="Income-modelName" name="edit-expense-type"
					value="">
				<div class="form-group">
					<label for="expense-category" class="form-label">Category</label> <select
						id="edit-income-category" name="category" class="form-input"
						required>
					</select>
				</div>

				<div class="form-group">
					<label for="expense-description" class="form-label">Description</label>
					<input type="text" id="edit-income-description" name="description"
						class="form-input" placeholder="Enter a description" required>
				</div>
				<div class="form-group">
					<label for="expense-amount" class="form-label">Amount (₹)</label> <input
						type="number" id="edit-income-amount" name="amount"
						class="form-input" placeholder="0.00" step="0.01" required>
				</div>
				<div class="form-group">
					<label for="expense-date" class="form-label">Date</label> <input
						type="date" name="date" id="edit-income-date" class="form-input"
						required>
				</div>
				<div class="modal-footer">
					<button type="button" class="button button-outline"
						onclick="closeModal()">Cancel</button>
					<button type="submit" class="button button-primary">Edit</button>
				</div>
			</form>
		</div>
	</div>



	<div class="modal-overlay" id="expected-expense-modal">
		<div class="modal">
			<div class="modal-header">
				<h3 class="modal-title" id="expected-expense-modal-title">Set
					Expected Expense</h3>
				<button class="close-button" onclick="closeExpectedExpenseModal()">&times;</button>
			</div>
			<form action="SetExpectedExpenseServlet" method="POST"
				id="expected-expense-form">
				<div class="form-group">
					<label for="expected-expense-amount" class="form-label">Expected
						Monthly Expense (₹)</label> <input type="number"
						id="expected-expense-amount" name="expectedExpense"
						class="form-input" placeholder="0.00" required>
				</div>
				<div class="modal-footer">
					<button type="button" class="button button-outline"
						onclick="closeExpectedExpenseModal()">Cancel</button>
					<button type="submit" onclick=(fetchExpectedExpense())
						class="button button-primary">Save</button>
				</div>
			</form>
		</div>
	</div>

	<script>
	// Fetch transactions when the page loads
		function deleteModal(expenseId) {
    // Build URL with concatenation instead
    const url = "DeleteExpenseServlet?expenseId=" + expenseId;

    fetch(url, {
        method: 'GET',
    })
    .then(() => {
        // After deletion completes, redirect to the data fetching servlet
         window.location.href = "GetExpenseIncomeServlet";
    })
    .catch(error => {
        console.error("Error deleting expense:", error);
        alert("An error occurred while deleting the expense.");
    });
}
			

        // Modal functions
       function openModal(type) {
    const modal = document.getElementById('expense-modal');
    const modalTitle = document.getElementById('modal-title');
    const modelName = document.getElementById('modelname'); // Fix modelname reference
    const selectCategory = document.getElementById('expense-category');

    // Reset form
    document.getElementById('expense-form').reset();
    document.getElementById('expense-id').value = '';

    // Set default date to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('expense-date').value = today;

    // Clear existing options
    selectCategory.innerHTML = '';

    if (type==='add-expense') {
        modalTitle.textContent = 'Add Expense';
        modelName.value = 'e'; // Fix modelname assignment

        // Add expense categories
        selectCategory.innerHTML = `
            <option value="" disabled selected>Select a Category</option>
            <option value="Shopping">Shopping</option>
            <option value="Food">Food</option>
            <option value="Groceries">Groceries</option>
            <option value="Travel">Travel</option>
            <option value="Bills">Bills</option>
            <option value="Rent">Rent</option>
            <option value="Personal">Personal</option>
            <option value="Entertainment">Entertainment</option>
            <option value="Transportation">Transportation</option>
            <option value="Gifts">Gifts</option>
            <option value="Donations">Donations</option>
            <option value="Others">Others</option>
        `;
    } else {
        modalTitle.textContent = 'Add Income';
        modelName.value = 'i';

        // Add income categories
        selectCategory.innerHTML = `
            <option value="" disabled selected>Select a Category</option>
            <option value="Salary">Salary</option>
            <option value="Interest">Interest</option>                    
            <option value="Allowance">Allowance</option>
            <option value="Others">Others</option>
        `;
    }

    // Show modal
    modal.style.display = 'flex';
}

       function openEditExpenseModal(id, cname, amount, date, description) {
    	   
			const modal = document.getElementById('edit-expense-modal');
			const modalTitle = document.getElementById('edit-modal-title');
			const modelName = document.getElementById('modelname');
			const selectCategory = document.getElementById('edit-expense-category');
			selectCategory.innerHTML = `
	            <option value="" disabled selected>Select a Category</option>
	            <option value="Shopping">Shopping</option>
	            <option value="Food">Food</option>
	            <option value="Groceries">Groceries</option>
	            <option value="Travel">Travel</option>
	            <option value="Bills">Bills</option>
	            <option value="Rent">Rent</option>
	            <option value="Personal">Personal</option>
	            <option value="Entertainment">Entertainment</option>
	            <option value="Transportation">Transportation</option>
	            <option value="Gifts and Donations">Gifts and Donations</option>
	            <option value="Others">Others</option>
	        `;
			// Set form values
			modelName.value = 'e';
			document.getElementById('edit-expense-id').value = id;
			document.getElementById('edit-expense-category').value = cname;
			document.getElementById('edit-expense-amount').value = amount;
			document.getElementById('edit-expense-date').value = date;
			document.getElementById('edit-expense-description').value = description;
			

			modalTitle.textContent = 'Edit Expense';
			

			modal.style.display = 'flex';
			
		}
       
       function openEditIncomeModal(id, cname, amount, date, description) {
    	   const modal = document.getElementById('edit-income-modal');
			const modalTitle = document.getElementById('edit-income-modal-title');
			const modelName = document.getElementById('Income-modelName');
			const selectCategory = document.getElementById('edit-income-category');
			
			selectCategory.innerHTML = `
	            <option value="" disabled selected>Select a Category</option>
	            <option value="Salary">Salary</option>
	            <option value="Gifts">Gifts</option>
	            <option value="Interest">Interest</option>                    
	            <option value="Allowance">Allowance</option>
	            <option value="Others">Others</option>
	        `;
			// Set form values
			modelName.value = 'i';
			document.getElementById('edit-income-id').value = id;
			document.getElementById('edit-income-category').value = cname;
			document.getElementById('edit-income-amount').value = amount;
			document.getElementById('edit-income-date').value = date;
			document.getElementById('edit-income-description').value = description;
			

			modalTitle.textContent = 'Edit Income';

			modal.style.display = 'flex';
		}
        
        function closeModal() {
            const modal = document.getElementById('expense-modal');
            const editExpenseModal = document.getElementById('edit-expense-modal');
            const editIncomeModal = document.getElementById('edit-income-modal');

            modal.style.display = 'none';
            editExpenseModal.style.display = 'none';
			editIncomeModal.style.display = 'none';
        }
     	
		// Set initial state 
        let hasExpectedExpense = false;

        // Fetch expected expense on page load
        document.addEventListener('DOMContentLoaded', function () {
    fetchExpectedExpense();
});

        // Function to fetch expected expense from servlet
        function fetchExpectedExpense() {
    fetch('GetExpectedExpenseServlet', {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        console.log("Fetched Expected Expense:", data); // Debugging log
        
        if (data.status === "success" && data.expectedExpense && data.expectedExpense > 0) {
            hasExpectedExpense = true;
            updateExpectedExpenseCard(data.expectedExpense);
        } else {
            hasExpectedExpense = false;
            updateExpectedExpenseCard();
        }
    })
    .catch(error => console.error("Error fetching expected expense:", error));
}

        // Update the expected expense card based on state
        function updateExpectedExpenseCard(expectedExpense = 0) {
    const card = document.getElementById('expected-expense-card');
    console.log("Expected Expense:", expectedExpense);

    if (!card) {
        console.error("Error: 'expected-expense-card' not found in DOM.");
        return;
    }

    // Clear existing content
    card.innerHTML = "";

    // Create elements dynamically
    const titleDiv = document.createElement("div");
    titleDiv.className = "balance-title";
    titleDiv.innerText = "EXPECTED EXPENSE";

    const amountDiv = document.createElement("div");
    amountDiv.className = "balance-amount";
    amountDiv.innerText = "Rs. " + expectedExpense; // Dynamically set the text

    card.appendChild(titleDiv);

    if (hasExpectedExpense) {
        card.appendChild(amountDiv);
    } else {
        const buttonDiv = document.createElement("div");
        buttonDiv.className = "no-expected-expense";

        const button = document.createElement("button");
        button.className = "button button-primary";
        button.innerText = "Set Expected Expense";
        button.onclick = openExpectedExpenseModal;

        buttonDiv.appendChild(button);
        card.appendChild(buttonDiv);
    }
}

        document.addEventListener('DOMContentLoaded', updateExpectedExpenseCard);
        // Expected Expense Modal functions
        function openExpectedExpenseModal() {
            document.getElementById('expected-expense-modal').style.display = 'flex';
        }

        function closeExpectedExpenseModal() {
            document.getElementById('expected-expense-modal').style.display = 'none';
        }

        // Handle form submission (Save Expected Expense)
        
        document.getElementById("expected-expense-form").addEventListener("submit", function(event) {
    //event.preventDefault(); // Prevent default form submission
    let formData = new FormData(this);

    fetch("SetExpectedExpenseServlet", {
        method: "POST",
        body: formData
    })
    .then(response => response.json()) // Parse JSON response
    .then(data => {
        if (data.status === "success") {
            //alert("Expected expense saved: ₹" + data.expectedExpense);
            closeExpectedExpenseModal(); 
            fetchExpectedExpense();
        } 
    })
    .catch(error => {
        console.error("Error:", error);
        alert("An unexpected error occurred.");
    });
});
        function downloadCSV() {
            window.location.href = "DownloadCSVServlet"; // Calls the servlet to generate and download CSV
        }
    	// Chart initialization
    	  document.addEventListener('DOMContentLoaded', function() {
    	    initializeCharts();
    	  });
    	  
    	  function initializeCharts() {
    		  // Define categories globally for all charts
    		  const categories =[];

    		  // Fetch expense history data
    		  fetch('GetExpectedExpenseHistoryServlet')
    		    .then(response => {
    		      if (!response.ok) {
    		        throw new Error('Network response was not ok');
    		      }
    		      return response.json();
    		    })
    		    .then(data => {
    		      console.log("Fetched chart data:", data);
    		      
    		      // Extract months and values from the data
    		      const months = data.map(item => item.month);
    		      const expectedExpenses = data.map(item => item.expectedExpense);
    		      const actualExpenses = data.map(item => item.monthlyExpense);
    		      
    		      // Create all charts with the real data
    		      createMonthlyExpenseChart(months, expectedExpenses);
    		      createCategoryExpenseChart(categories);
    		      createMonthComparisonChart(categories);
    		      createExpectedVsActualChart(months, expectedExpenses, actualExpenses);
    		    })
    		    .catch(error => {
    		      console.error("Error fetching chart data:", error);
    		      // Create charts with sample data if fetch fails
    		      createChartsWithSampleData();
    		    });
    		}

    		// 1. Monthly Expected Expense Line Chart
    		function createMonthlyExpenseChart(months, expectedExpenses) {
    		  const monthlyCtx = document.getElementById('monthlyExpectedChart').getContext('2d');
    		  new Chart(monthlyCtx, {
    		    type: 'line',
    		    data: {
    		      labels: months,
    		      datasets: [
    		        {
    		          label: 'Expected Expense',
    		          data: expectedExpenses,
    		          borderColor: '#4361ee',
    		          backgroundColor: 'rgba(67, 97, 238, 0.1)',
    		          borderWidth: 2,
    		          tension: 0.3,
    		          fill: true
    		        }
    		      ]
    		    },
    		    options: {
    		      responsive: true,
    		      maintainAspectRatio: false,
    		      scales: {
    		        y: {
    		          beginAtZero: false,
    		          title: {
    		            display: true,
    		            text: 'Amount (₹)'
    		          }
    		        }
    		      }
    		    }
    		  });
    		}

    		// 2. Category-wise Expense Pie Chart
    		function createCategoryExpenseChart(categories) {
    		  // For the pie chart, you would typically fetch category data from a different endpoint
    		  // Using sample data for now
    			 fetch('GetCategoryExpensesServlet')
    			    .then(response => {
    			      if (!response.ok) {
    			        throw new Error('Network response was not ok');
    			      }
    			      return response.json();
    			    })
    			    .then(data => {
    			      console.log("Fetched category data:", data);
    			      
    			      // Extract categories and amounts
    			      const categories = data.map(item => item.category);
    			      const amounts = data.map(item => item.amount);
    			      
    			      // Create the pie chart with real data
    			      renderCategoryExpenseChart(categories, amounts);
    			    })
    			    .catch(error => {
    			      console.error("Error fetching category data:", error);
    			      // Fallback to sample data
    			      renderCategoryExpenseChartWithSampleData();
    			    });
    		}
    		function renderCategoryExpenseChart(categories, amounts) {
    			  const categoryCtx = document.getElementById('categoryExpenseChart').getContext('2d');
    			  new Chart(categoryCtx, {
    			    type: 'pie',
    			    data: {
    			      labels: categories,
    			      datasets: [{
    			        data: amounts,
    			        backgroundColor: [
    			          '#4361ee',
    			          '#3a0ca3',
    			          '#7209b7',
    			          '#f72585',
    			          '#4cc9f0',
    			          '#fb5607',
    			          '#ffbe0b'
    			        ],
    			        borderWidth: 1
    			      }]
    			    },
    			    options: {
    			      responsive: true,
    			      maintainAspectRatio: false,
    			      plugins: {
    			        legend: {
    			          position: 'right'
    			        },
    			        tooltip: {
    			          callbacks: {
    			            label: function(context) {
    			              let label = context.label || '';
    			              if (label) {
    			                label += ': ';
    			              }
    			              if (context.raw !== null) {
    			                label += '₹' + context.raw.toFixed(2);
    			              }
    			              return label;
    			            }
    			          }
    			        }
    			      }
    			    }
    			  });
    			}


    		// 3. Month Comparison Bar Chart
    		function createMonthComparisonChart(categories) {
    			fetch('GetMonthComparisonServlet')
    		    .then(response => {
    		      if (!response.ok) {
    		        throw new Error('Network response was not ok');
    		      }
    		      return response.json();
    		    })
    		    .then(data => {
    		      console.log("Fetched month comparison data:", data);
    		      
    		      const categories = data.categories;
    		      const currentMonthAmounts = data.currentMonthAmounts;
    		      const previousMonthAmounts = data.previousMonthAmounts;
    		      const currentMonthName = data.currentMonthName;
    		      const previousMonthName = data.previousMonthName;
    		      
    		      renderMonthComparisonChart(categories, previousMonthAmounts, currentMonthAmounts, previousMonthName, currentMonthName);
    		    })
    		    .catch(error => {
    		      console.error("Error fetching month comparison data:", error);
    		      renderMonthComparisonChartWithSampleData();
    		    });
    		}
    		function renderMonthComparisonChart(categories, previousMonthData, currentMonthData, previousMonthName, currentMonthName) {
    			  const comparisonCtx = document.getElementById('monthComparisonChart').getContext('2d');
    			  new Chart(comparisonCtx, {
    			    type: 'bar',
    			    data: {
    			      labels: categories,
    			      datasets: [
    			        {
    			          label: previousMonthName,
    			          data: previousMonthData,
    			          backgroundColor: 'rgba(67, 97, 238, 0.6)',
    			          borderColor: '#4361ee',
    			          borderWidth: 1
    			        },
    			        {
    			          label: currentMonthName,
    			          data: currentMonthData,
    			          backgroundColor: 'rgba(247, 37, 133, 0.6)',
    			          borderColor: '#f72585',
    			          borderWidth: 1
    			        }
    			      ]
    			    },
    			    options: {
    			      responsive: true,
    			      maintainAspectRatio: false,
    			      scales: {
    			        y: {
    			          beginAtZero: true,
    			          title: {
    			            display: true,
    			            text: 'Amount (₹)'
    			          }
    			        }
    			      },
    			      plugins: {
    			        tooltip: {
    			          callbacks: {
    			            label: function(context) {
    			              let label = context.dataset.label || '';
    			              if (label) {
    			                label += ': ';
    			              }
    			              if (context.parsed.y !== null) {
    			                label += '₹' + context.parsed.y.toFixed(2);
    			              }
    			              return label;
    			            }
    			          }
    			        }
    			      }
    			    }
    			  });
    			}

    		

    		// 4. Expected vs Actual Expense Bar Chart
    		function createExpectedVsActualChart(months, expectedExpenses, actualExpenses) {
    		  const expectedVsActualCtx = document.getElementById('expectedVsActualChart').getContext('2d');
    		  new Chart(expectedVsActualCtx, {
    		    type: 'bar',
    		    data: {
    		      labels: months,
    		      datasets: [
    		        {
    		          label: 'Expected',
    		          data: expectedExpenses,
    		          backgroundColor: 'rgba(67, 97, 238, 0.6)',
    		          borderColor: '#4361ee',
    		          borderWidth: 1
    		        },
    		        {
    		          label: 'Actual',
    		          data: actualExpenses,
    		          backgroundColor: 'rgba(114, 9, 183, 0.6)',
    		          borderColor: '#7209b7',
    		          borderWidth: 1
    		        }
    		      ]
    		    },
    		    options: {
    		      responsive: true,
    		      maintainAspectRatio: false,
    		      scales: {
    		        y: {
    		          beginAtZero: true,
    		          title: {
    		            display: true,
    		            text: 'Amount (₹)'
    		          }
    		        }
    		      },
    		      plugins: {
    		        tooltip: {
    		          callbacks: {
    		            label: function(context) {
    		              let label = context.dataset.label || '';
    		              if (label) {
    		                label += ': ';
    		              }
    		              if (context.parsed.y !== null) {
    		                label += '₹' + context.parsed.y.toFixed(2);
    		              }
    		              return label;
    		            }
    		          }
    		        }
    		      }
    		    }
    		  });
    		}
	</script>
</body>
</html>