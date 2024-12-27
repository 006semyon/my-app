export async function fetchGetUsers() {
    try {
        const response = await fetch("/api/users")
        return response.json();
    } catch (e) {
        console.log('Failed to get users: ' + e)
    }
}

export async function fetchCreateUser(userData) {
    try {
        const response = await fetch("/api/users", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData),
        })
    } catch (e) {
        console.log('Failed to post user: ' + e)
    }
}

export async function fetchPatchUser(userData) {
    try {
        return await fetch('/api/users', {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });
    } catch (e) {
        console.log('Failed to patch user: ' + e)
    }
}

export async function fetchDeleteUser(id) {
    try {
        return await fetch(`api/users/${id}`, {
            method: 'Delete',
            headers: {'Content-Type': 'application/json'},
        })
    } catch (e) {
        console.log('Failed to delete user: ' + e)
    }
}

export async function fetchGetRoles() {
    try {
        const response = await fetch("/api/roles")
        return response.json()
    } catch (e) {
        console.log('Failed to get roles: ' + e)
    }
}