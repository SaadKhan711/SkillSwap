const API_BASE_URL = 'http://localhost:8080';

export const api = {
    get: (endpoint) => fetch(`${API_BASE_URL}/api${endpoint}`).then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
    }),
    post: (endpoint, body) => fetch(`${API_BASE_URL}/api${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    }).then(async res => {
        if (res.ok) {
            const text = await res.text();
            return text ? JSON.parse(text) : {};
        } else {
            const errorText = await res.text();
            try { return Promise.reject(JSON.parse(errorText)); } 
            catch (e) { return Promise.reject(errorText); }
        }
    }),
    delete: (endpoint) => fetch(`${API_BASE_URL}/api${endpoint}`, {
        method: 'DELETE'
    }).then(res => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return {};
    })
};
