// companies.js - Company Management Frontend Logic
const API_URL = '/api/companies';

// ===== INITIALIZE ON PAGE LOAD =====
document.addEventListener('DOMContentLoaded', () => {
    fetchCompanies();
    setupEventListeners();
});

// ===== SETUP EVENT LISTENERS =====
function setupEventListeners() {
    const companyForm = document.getElementById('companyForm');
    if (companyForm) {
        companyForm.addEventListener('submit', handleFormSubmit);
    }

    const viewAllBtn = document.getElementById('viewAllBtn');
    if (viewAllBtn) {
        viewAllBtn.addEventListener('click', fetchCompanies);
    }
}

// ===== FETCH & DISPLAY COMPANIES (READ) =====
async function fetchCompanies() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error(`Failed to fetch companies: ${response.status}`);
        }

        const data = await response.json();
        const tbody = document.getElementById('companyTableBody');

        if (!tbody) {
            console.warn('Table body element not found');
            return;
        }

        tbody.innerHTML = '';

        if (!data || data.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align: center; padding: 2rem; color: #64748b;">
                        No companies registered yet. Add one above!
                    </td>
                </tr>
            `;
            return;
        }

        data.forEach(c => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${escapeHtml(c.regNumber)}</td>
                <td><strong>${escapeHtml(c.name)}</strong></td>
                <td>${escapeHtml(c.email)}</td>
                <td>${escapeHtml(c.address)}</td>
                <td><span class="status-pill ${escapeHtml(c.status)}">${escapeHtml(c.status)}</span></td>
                <td>
                    <button class="edit-btn" onclick='prepEdit(${JSON.stringify(c).replace(/'/g, "\\'")})'>Edit</button>
                    <button class="delete-btn" onclick="deleteCompany(${c.id})">Delete</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (err) {
        console.error('Error fetching companies:', err);
        alert('Failed to load companies. Please check your connection.');
    }
}

// ===== HANDLE FORM SUBMIT (CREATE / UPDATE) =====
async function handleFormSubmit(e) {
    e.preventDefault();

    const id = document.getElementById('compId')?.value || '';

    const companyData = {
        name: document.getElementById('name')?.value.trim() || '',
        regNumber: document.getElementById('regNumber')?.value.trim() || '',
        email: document.getElementById('email')?.value.trim() || '',
        phone: document.getElementById('phone')?.value.trim() || '',
        status: document.getElementById('status')?.value || 'Active',
        address: document.getElementById('address')?.value.trim() || ''
    };

    if (!companyData.name || !companyData.regNumber || !companyData.email) {
        alert('Please fill in all required fields (Name, Reg Number, Email)');
        return;
    }

    try {
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/${id}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(companyData)
        });

        if (!response.ok) {
            throw new Error(`Save failed: ${response.status}`);
        }

        alert(id ? '✅ Company Updated!' : '✅ Company Saved!');
        resetForm();
        fetchCompanies();
    } catch (err) {
        console.error('Error saving company:', err);
        alert('Failed to save company. Please try again.');
    }
}

// ===== DELETE COMPANY =====
async function deleteCompany(id) {
    if (!confirm('⚠️ Are you sure you want to delete this company? This action cannot be undone.')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`Delete failed: ${response.status}`);
        }

        alert('🗑️ Company deleted successfully');
        fetchCompanies();

        if (document.getElementById('compId')?.value == id) {
            resetForm();
        }
    } catch (err) {
        console.error('Error deleting company:', err);
        alert('Failed to delete company. Please try again.');
    }
}

// ===== PREPARE FORM FOR EDIT (UPDATE) =====
function prepEdit(c) {
    document.getElementById('compId').value = c.id || '';
    document.getElementById('name').value = c.name || '';
    document.getElementById('regNumber').value = c.regNumber || '';
    document.getElementById('email').value = c.email || '';
    document.getElementById('phone').value = c.phone || '';
    document.getElementById('status').value = c.status || 'Active';
    document.getElementById('address').value = c.address || '';

    const formTitle = document.getElementById('formTitle');
    if (formTitle) {
        formTitle.innerText = '✏️ Edit Company';
    }

    const saveBtn = document.getElementById('saveBtn');
    if (saveBtn) {
        saveBtn.innerHTML = '⟳ Update Company';
    }

    document.querySelector('.panel')?.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
    });

    const form = document.getElementById('companyForm');
    if (form) {
        form.style.transition = 'background 0.3s ease';
        form.style.background = 'rgba(201, 169, 97, 0.1)';
        setTimeout(() => {
            form.style.background = 'transparent';
        }, 1000);
    }
}

// ===== RESET FORM TO ADD MODE =====
function resetForm() {
    document.getElementById('companyForm')?.reset();
    document.getElementById('compId').value = '';

    const formTitle = document.getElementById('formTitle');
    if (formTitle) {
        formTitle.innerText = '➕ Add Company';
    }

    const saveBtn = document.getElementById('saveBtn');
    if (saveBtn) {
        saveBtn.innerHTML = 'Save Company';
    }
}

// ===== SECURITY: Escape HTML to prevent XSS =====
function escapeHtml(text) {
    if (text === null || text === undefined) return '';
    return String(text).replace(/[&<>"']/g, function (m) {
        const map = {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        };
        return map[m];
    });
}

// ===== EXPOSE FUNCTIONS GLOBALLY =====
window.deleteCompany = deleteCompany;
window.prepEdit = prepEdit;
window.fetchCompanies = fetchCompanies;