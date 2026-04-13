// companies.js - Company Management Frontend Logic
const API_URL = '/api/companies';

// ===== INITIALIZE ON PAGE LOAD =====
document.addEventListener('DOMContentLoaded', () => {
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

// ===== FETCH & DISPLAY COMPANIES (READ) =====
async function fetchCompanies() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Failed to fetch companies');
        
        const data = await response.json();
        const tbody = document.getElementById('companyTableBody');
        
        if (!tbody) {
            console.warn('Table body element not found');
            return;
        }
        
        tbody.innerHTML = ''; // Clear existing rows

        if (data.length === 0) {
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
                <td><strong>${escapeHtml(c.name)}</strong></td>
                <td>${escapeHtml(c.email)}</td>
                <td><span class="status-pill ${c.status}">${escapeHtml(c.status)}</span></td>
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
        alert('Please fill in all required fields (Name, Reg Number, Email)');
        return;
    }

    try {
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/${id}` : API_URL;

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
        alert('Failed to save company. Please try again.');
    }
}

// ===== DELETE COMPANY =====
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
        alert('Failed to delete company. Please try again.');
    }
}

// ===== PREPARE FORM FOR EDIT (UPDATE) =====
function prepEdit(c) {
    // Populate form fields with company data
    document.getElementById('compId').value = c.id || '';
    document.getElementById('name').value = c.name || '';
    document.getElementById('regNumber').value = c.regNumber || '';
    document.getElementById('email').value = c.email || '';
    document.getElementById('phone').value = c.phone || '';
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

// ===== RESET FORM TO ADD MODE =====
function resetForm() {
    document.getElementById('companyForm').reset();
    document.getElementById('compId').value = '';
    document.getElementById('formTitle').innerText = '➕ Add Company';
    document.getElementById('saveBtn').innerHTML = '+ Save Company';
}

// ===== SECURITY: Escape HTML to prevent XSS =====
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
window.fetchCompanies = fetchCompanies;