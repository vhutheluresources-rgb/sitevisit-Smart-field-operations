// companies.js - Company Management Frontend Logic
const API_URL = '/api/companies';

// ===== CSRF TOKEN HELPERS =====
function getCsrfToken() {
    // Try meta tag first (Spring Boot default)
    const metaToken = document.querySelector('meta[name="_csrf"]')?.content;
    if (metaToken) return metaToken;
    
    // Try hidden input field
    const inputToken = document.querySelector('input[name="_csrf"]')?.value;
    if (inputToken) return inputToken;
    
    // Try cookie (fallback)
    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'XSRF-TOKEN') {
            return decodeURIComponent(value);
        }
    }
    return null;
}

function getCsrfHeaderName() {
    return document.querySelector('meta[name="_csrf_header"]')?.content || 'X-CSRF-TOKEN';
}

// ===== INITIALIZE ON PAGE LOAD =====
document.addEventListener('DOMContentLoaded', () => {
    console.log('Page loaded, fetching companies...');
    fetchCompanies();
    setupEventListeners();
});

// ===== SETUP EVENT LISTENERS =====
function setupEventListeners() {
    const form = document.getElementById('companyForm');
    if (form) {
        form.addEventListener('submit', handleFormSubmit);
    }

    const viewAllBtn = document.getElementById('viewAllBtn');
    if (viewAllBtn) {
        viewAllBtn.addEventListener('click', fetchCompanies);
    }
}

// ===== FETCH & DISPLAY COMPANIES (✅ FIXED) =====
async function fetchCompanies() {
    try {
        console.log('Fetching companies from:', API_URL);
        const response = await fetch(API_URL);
        
        if (!response.ok) {
            if (response.status === 401 || response.status === 403) {
                console.warn('Authentication required - redirecting to login');
                window.location.href = '/login';
                return;
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Received companies:', data);
        
        const tbody = document.getElementById('companyTableBody');
        if (!tbody) {
            console.error('Table body not found!');
            return;
        }

        tbody.innerHTML = '';

        if (data.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" style="text-align:center; padding: 2rem;">No companies yet</td>
                </tr>`;
            return;
        }

        data.forEach(c => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${escapeHtml(c.regNumber)}</td>
                <td>${escapeHtml(c.name)}</td>
                <td>${escapeHtml(c.email)}</td>
                <td>${escapeHtml(c.phone || '-')}</td>
                <td>${escapeHtml(c.address || '-')}</td>
                <td><span class="status-pill ${c.status?.toLowerCase() || 'active'}">${escapeHtml(c.status || 'Active')}</span></td>
                <td>
                    <button class="edit-btn" onclick='prepEdit(${JSON.stringify(c)})'>
                        <i class='bx bx-edit'></i> Edit
                    </button>
                    <button class="delete-btn" onclick="deleteCompany(${c.id})">
                        <i class='bx bx-trash'></i> Delete
                    </button>
                </td>
            `;
            tbody.appendChild(row);
        });

    } catch (err) {
        console.error('Error fetching companies:', err);
        const tbody = document.getElementById('companyTableBody');
        if (tbody) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" style="text-align:center; padding: 2rem; color: #dc2626;">
                        <i class='bx bx-error-circle'></i> Error loading companies. Make sure backend is running.
                    </td>
                </tr>`;
        }
    }
}

// ===== CREATE / UPDATE =====
async function handleFormSubmit(e) {
    e.preventDefault();
    console.log('Form submitted');

    const id = document.getElementById('compId').value;

    const companyData = {
        name: document.getElementById('name').value.trim(),
        regNumber: document.getElementById('regNumber').value.trim(),
        email: document.getElementById('email').value.trim(),
        phone: document.getElementById('phone').value.trim(),
        address: document.getElementById('address').value.trim(),
        status: document.getElementById('status').value
    };

    console.log('Company data to save:', companyData);

    if (!companyData.name || !companyData.regNumber || !companyData.email) {
        alert('Please fill all required fields (marked with *)');
        return;
    }

    try {
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/${id}` : API_URL;

        console.log(`Sending ${method} request to ${url}`);

        // Build headers with CSRF token
        const headers = { 
            'Content-Type': 'application/json' 
        };
        
        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeaderName();
        
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
            console.log('CSRF token included in request');
        } else {
            console.warn('CSRF token not found - request may be rejected');
        }

        const response = await fetch(url, {
            method: method,
            headers: headers,
            body: JSON.stringify(companyData),
            credentials: 'same-origin' // Important for cookies/session
        });

        console.log('Response status:', response.status);

        // Handle authentication/authorization errors
        if (response.status === 401 || response.status === 403) {
            console.warn('Access denied - redirecting to login');
            alert('⚠️ Session expired. Please log in again.');
            window.location.href = '/login';
            return;
        }

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Server error:', errorText);
            throw new Error(`Server error: ${response.status} - ${errorText}`);
        }

        const savedCompany = await response.json();
        console.log('Saved company:', savedCompany);

        alert('✅ Company saved successfully!');
        resetForm();
        fetchCompanies();

    } catch (err) {
        console.error('Error saving company:', err);
        alert(`❌ Failed to save company: ${err.message}\n\nCheck console for details.`);
    }
}

// ===== DELETE =====
async function deleteCompany(id) {
    if (!confirm('Are you sure you want to delete this company?')) return;

    try {
        console.log('Deleting company with ID:', id);
        
        // Build headers with CSRF token for DELETE
        const headers = {};
        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeaderName();
        
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: headers,
            credentials: 'same-origin'
        });

        // Handle authentication/authorization errors
        if (response.status === 401 || response.status === 403) {
            alert('⚠️ Session expired. Please log in again.');
            window.location.href = '/login';
            return;
        }

        if (!response.ok) {
            throw new Error(`Delete failed: ${response.status}`);
        }

        alert('✅ Company deleted successfully');
        fetchCompanies();

    } catch (err) {
        console.error('Error deleting company:', err);
        alert('❌ Error deleting company');
    }
}

// ===== EDIT =====
function prepEdit(c) {
    console.log('Preparing edit for company:', c);
    
    document.getElementById('compId').value = c.id || '';
    document.getElementById('name').value = c.name || '';
    document.getElementById('regNumber').value = c.regNumber || '';
    document.getElementById('email').value = c.email || '';
    document.getElementById('phone').value = c.phone || '';
    document.getElementById('address').value = c.address || '';
    document.getElementById('status').value = c.status || 'Active';

    const formTitle = document.getElementById('formTitle');
    const saveBtn = document.getElementById('saveBtn');
    
    if (formTitle) formTitle.innerText = 'Edit Company';
    if (saveBtn) saveBtn.innerHTML = '<i class=\'bx bx-check\'></i> Update Company';
    
    // Scroll to form
    document.querySelector('.panel').scrollIntoView({ behavior: 'smooth' });
}

// ===== RESET =====
function resetForm() {
    document.getElementById('companyForm').reset();
    document.getElementById('compId').value = '';
    
    const formTitle = document.getElementById('formTitle');
    const saveBtn = document.getElementById('saveBtn');
    
    if (formTitle) formTitle.innerText = 'Add Company';
    if (saveBtn) saveBtn.innerHTML = '<i class=\'bx bx-plus\'></i> Save Company';
}

// ===== SECURITY =====
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ===== GLOBAL FUNCTIONS =====
window.deleteCompany = deleteCompany;
window.prepEdit = prepEdit;