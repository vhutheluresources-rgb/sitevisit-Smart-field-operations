/**
 * companies.js - Company Management Frontend Logic
 * Handles CRUD operations for companies via REST API
 */

const API_URL = '/api/companies';

// ===== CSRF TOKEN HELPERS (Spring Security) =====
function getCsrfToken() {
    const metaToken = document.querySelector('meta[name="_csrf"]')?.content;
    if (metaToken) return metaToken;
    
    const inputToken = document.querySelector('input[name="_csrf"]')?.value;
    if (inputToken) return inputToken;
    
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

function getAuthHeaders() {
    const headers = { 'Content-Type': 'application/json' };
    const csrfToken = getCsrfToken();
    const csrfHeader = getCsrfHeaderName();
    if (csrfToken) {
        headers[csrfHeader] = csrfToken;
    }
    return headers;
}

// ===== AUTH & PROFILE =====
function checkAuth() {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    if (isLoggedIn !== "true") {
        window.location.href = "/login";
        return false;
    }
    return true;
}

function updateProfileDisplay() {
    const fullName = localStorage.getItem("fullName");
    const role = localStorage.getItem("role");
    
    const avatar = document.getElementById("profileAvatar");
    const nameEl = document.getElementById("profileName");
    const roleEl = document.getElementById("profileRole");

    if (fullName && nameEl) {
        nameEl.textContent = fullName;
    }
    if (fullName && avatar) {
        avatar.textContent = fullName.charAt(0).toUpperCase();
    }
    if (role && roleEl) {
        roleEl.textContent = role;
    }
}

// ===== FETCH & DISPLAY COMPANIES =====
async function fetchCompanies() {
    const tbody = document.getElementById('companyTableBody');
    if (!tbody) return;
    
    tbody.innerHTML = `
        <tr><td colspan="6" style="text-align:center; padding:2rem;">
            <i class='bx bx-loader-alt bx-spin' style="font-size:24px;"></i> Loading...
        </td></tr>`;

    try {
        const response = await fetch(API_URL);
        
        if (response.status === 401 || response.status === 403) {
            window.location.href = '/login';
            return;
        }
        if (!response.ok) throw new Error(`HTTP ${response.status}`);

        const companies = await response.json();
        tbody.innerHTML = '';

        if (companies.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align:center; padding:2rem; color:#6b7280;">
                No companies registered yet. Add one above!
            </td></tr>`;
            return;
        }

        companies.forEach(c => {
            const statusClass = (c.status || 'Active').toLowerCase();
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><strong>${escapeHtml(c.regNumber)}</strong></td>
                <td>${escapeHtml(c.name)}</td>
                <td>${escapeHtml(c.email)}</td>
                <td>${escapeHtml(c.phone || '-')}</td>
                <td><span class="status ${statusClass}">${escapeHtml(c.status || 'Active')}</span></td>
                <td>
                    <button class="btn small-btn" onclick="editCompany(${c.id})">Edit</button>
                    <button class="btn small-btn btn-danger" onclick="deleteCompany(${c.id})">Delete</button>
                </td>
            `;
            tbody.appendChild(row);
        });

    } catch (err) {
        console.error('Error fetching companies:', err);
        tbody.innerHTML = `<tr><td colspan="6" style="text-align:center; padding:2rem; color:#dc2626;">
            ❌ Failed to load companies. Is the API running?
        </td></tr>`;
    }
}

// ===== CREATE / UPDATE COMPANY =====
async function handleFormSubmit(e) {
    e.preventDefault();
    if (!checkAuth()) return;

    const compId = document.getElementById('compId').value;
    const companyData = {
        name: document.getElementById('name').value.trim(),
        regNumber: document.getElementById('regNumber').value.trim(),
        email: document.getElementById('email').value.trim(),
        phone: document.getElementById('phone').value.trim(),
        address: document.getElementById('address').value.trim(),
        status: document.getElementById('status').value
    };

    if (!companyData.name || !companyData.regNumber || !companyData.email) {
        alert('Please fill all required fields (marked with *)');
        return;
    }

    try {
        const method = compId ? 'PUT' : 'POST';
        const url = compId ? `${API_URL}/${compId}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: getAuthHeaders(),
            body: JSON.stringify(companyData),
            credentials: 'same-origin'
        });

        if (response.status === 401 || response.status === 403) {
            alert('⚠️ Session expired. Please log in again.');
            window.location.href = '/login';
            return;
        }
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Server error: ${response.status} - ${errorText}`);
        }

        alert(`✅ Company ${compId ? 'updated' : 'created'} successfully!`);
        resetForm();
        fetchCompanies();

    } catch (err) {
        console.error('Error saving company:', err);
        alert(`❌ Failed to save: ${err.message}`);
    }
}

// ===== EDIT COMPANY =====
async function editCompany(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) throw new Error('Failed to load company');
        
        const comp = await response.json();
        
        document.getElementById('compId').value = comp.id;
        document.getElementById('name').value = comp.name || '';
        document.getElementById('regNumber').value = comp.regNumber || '';
        document.getElementById('email').value = comp.email || '';
        document.getElementById('phone').value = comp.phone || '';
        document.getElementById('address').value = comp.address || '';
        document.getElementById('status').value = comp.status || 'Active';

        document.getElementById('formTitle').textContent = 'Edit Company';
        document.getElementById('saveBtn').innerHTML = '<i class="bx bx-check"></i> Update Company';
        document.getElementById('cancelBtn').style.display = 'inline-block';
        
        document.querySelector('.form-container')?.scrollIntoView({ behavior: 'smooth' });

    } catch (err) {
        console.error('Error loading company:', err);
        alert('❌ Failed to load company details');
    }
}

// ===== DELETE COMPANY =====
async function deleteCompany(id) {
    if (!confirm('Are you sure you want to delete this company? This cannot be undone.')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders(),
            credentials: 'same-origin'
        });

        if (response.status === 401 || response.status === 403) {
            alert('⚠️ Session expired. Please log in again.');
            window.location.href = '/login';
            return;
        }
        if (!response.ok) throw new Error(`Delete failed: ${response.status}`);

        alert('✅ Company deleted successfully!');
        fetchCompanies();

    } catch (err) {
        console.error('Error deleting company:', err);
        alert('❌ Failed to delete company');
    }
}

// ===== RESET FORM =====
function resetForm() {
    document.getElementById('companyForm')?.reset();
    document.getElementById('compId').value = '';
    document.getElementById('formTitle').textContent = 'Add New Company';
    document.getElementById('saveBtn').innerHTML = '<i class="bx bx-save"></i> Save Company';
    document.getElementById('cancelBtn').style.display = 'none';
}

// ===== SECURITY: XSS Prevention =====
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// ===== EVENT LISTENERS =====
function setupEventListeners() {
    const form = document.getElementById('companyForm');
    if (form) {
        form.addEventListener('submit', handleFormSubmit);
    }

    const cancelBtn = document.getElementById('cancelBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', resetForm);
    }
}

// ===== GLOBAL EXPOSURE (for onclick attributes) =====
window.fetchCompanies = fetchCompanies;
window.editCompany = editCompany;
window.deleteCompany = deleteCompany;
window.resetForm = resetForm;

// ===== INITIALIZATION =====
document.addEventListener('DOMContentLoaded', () => {
    if (!checkAuth()) return;
    
    updateProfileDisplay();
    setupEventListeners();
    fetchCompanies();
    
    console.log('✅ Companies page initialized');
});