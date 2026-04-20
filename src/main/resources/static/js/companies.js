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
    // Form submission
    document.getElementById('companyForm').addEventListener('submit', handleFormSubmit);
    
    // Optional: Add "View All" button if it exists in your HTML
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
        if (!response.ok) throw new Error('Failed to fetch companies');
        
        const data = await response.json();
        console.log('Received companies:', data);
        
        const tbody = document.getElementById('companyTableBody');
        
        if (!tbody) {
            console.error('Table body not found!');
            return;
        }

        tbody.innerHTML = '';

        if (!data || data.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="5" style="text-align: center; padding: 2rem; color: #64748b;">
                        No companies registered yet. Add one above!
                    </td>
                </tr>`;
            return;
        }

        data.forEach(c => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${escapeHtml(c.regNumber)}</td>
                <td>${escapeHtml(c.name)}</td>
                <td>${escapeHtml(c.email)}</td>
                <td><span class="status-pill ${c.status}">${escapeHtml(c.status)}</span></td>
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
    
    const id = document.getElementById('compId').value;
    const companyData = {
        name: document.getElementById('name').value.trim(),
        regNumber: document.getElementById('regNumber').value.trim(),
        email: document.getElementById('email').value.trim(),
        phone: document.getElementById('phone').value.trim(),
        status: document.getElementById('status').value
    };

    // Basic validation
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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(companyData)
        });

        if (!response.ok) throw new Error('Save failed');

        alert(id ? '✅ Company Updated!' : '✅ Company Saved!');
        resetForm();
        fetchCompanies(); // Refresh table
    } catch (err) {
        console.error('Error saving company:', err);
        alert(`❌ Failed to save company: ${err.message}\n\nCheck console for details.`);
    }
}

// ===== DELETE =====
async function deleteCompany(id) {
    if (!confirm('⚠️ Are you sure you want to delete this company? This action cannot be undone.')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        
        if (!response.ok) throw new Error('Delete failed');
        
        alert('🗑️ Company deleted successfully');
        fetchCompanies(); // Refresh table
        
        // If we were editing this company, reset the form
        if (document.getElementById('compId').value == id) {
            resetForm();
        }
    } catch (err) {
        console.error('Error deleting company:', err);
        alert('❌ Error deleting company');
    }
}

// ===== EDIT =====
function prepEdit(c) {
    // Populate form fields with company data
    document.getElementById('compId').value = c.id || '';
    document.getElementById('name').value = c.name || '';
    document.getElementById('regNumber').value = c.regNumber || '';
    document.getElementById('email').value = c.email || '';
    document.getElementById('phone').value = c.phone || '';
    document.getElementById('address').value = c.address || '';
    document.getElementById('status').value = c.status || 'Active';
    
    // Update UI labels
    document.getElementById('formTitle').innerText = '✏️ Edit Company';
    document.getElementById('saveBtn').innerHTML = '⟳ Update Company';
    
    // Smooth scroll to form
    document.querySelector('.panel')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
    
    // Highlight the form briefly
    const form = document.getElementById('companyForm');
    form.style.transition = 'background 0.3s ease';
    form.style.background = 'rgba(201, 169, 97, 0.1)';
    setTimeout(() => { form.style.background = 'transparent'; }, 1000);
}

// ===== RESET =====
function resetForm() {
    document.getElementById('companyForm')?.reset();
    document.getElementById('compId').value = '';
    document.getElementById('formTitle').innerText = '➕ Add Company';
    document.getElementById('saveBtn').innerHTML = '+ Save Company';
}

// ===== SECURITY =====
function escapeHtml(text) {
    if (!text) return '';
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

// ===== EXPOSE FUNCTIONS GLOBALLY (for onclick attributes) =====
window.deleteCompany = deleteCompany;
window.prepEdit = prepEdit;