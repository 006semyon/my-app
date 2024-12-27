import {fetchCreateUser, fetchDeleteUser, fetchGetRoles, fetchGetUsers, fetchPatchUser} from "./api.js";
import {createElement} from "./utils.js";
import {openDeleteModal, openEditModal} from "./modal.js";

export async function createTable() {
    const users = await fetchGetUsers()
    const roles = await fetchGetRoles()
    const tableUsers = document.getElementById('t')
    tableUsers.innerHTML = ''
    const table = createElement('table', {
            classes: ['table', 'table-striped'],
            styles: {margin: `0`}
        }
    )

    const thead = createElement('thead')
    const tr = createElement('tr')
    const headers = ['ID', 'First Name', 'Last Name', 'Age', 'Email', 'Role', 'Edit', 'Delete'];
    headers.forEach(headerText => {
        const th = createElement('th', {attributes: { scope: `col`} })
        th.textContent = headerText
        tr.appendChild(th);
    });
    thead.appendChild(tr);

    const tableBody = document.createElement('tbody')
    users.forEach(user => {
        const row = document.createElement('tr')
        Object.entries(user).forEach(([key, value]) => {
            if (key !== 'username' && key !== 'password' && key !== 'roles' && key !== 'taxPayments' && key !== 'properties') {
                const field = document.createElement('td')
                field.textContent = value
                row.appendChild(field)
            }
        })
        const field = createElement('td')
        field.textContent = user['roles'].some(role => role.role.includes('ADMIN')) ? 'ADMIN' : 'USER';
        row.appendChild(field)

        row.appendChild(createEditButton(user, roles))
        row.appendChild(createDeleteButton(user, roles))
        tableBody.appendChild(row)
    })

    table.appendChild(thead)
    table.appendChild(tableBody)
    tableUsers.appendChild(table)
}


function createEditButton(user, roles) {
    const edit = createElement('td')
    const editButton = createElement('button', {
        styles: {color: `#ffffff`, backgroundColor: `#339bb1`},
    })
    editButton.textContent = 'Edit'
    editButton.addEventListener('click', () => {
        openEditModal(user, roles)
    })
    edit.appendChild(editButton)
    return edit
}

function createDeleteButton(user, roles) {
    const del = createElement('td')
    const deleteButton = createElement('button', {
        styles: {color: `#ffffff`, backgroundColor: `#ff0015`},
    })
    deleteButton.textContent = 'Delete'
    deleteButton.addEventListener('click', () => {
        openDeleteModal(user, roles)
    })
    del.appendChild(deleteButton)
    return del
}

async function createUserHandler(event) {
    event.preventDefault();

    const button = event.target;
    const form = button.closest('form');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const fieldIds = [
        'createUsername',
        'createFirstName',
        'createLastName',
        'createAge',
        'createEmail',
        'createPassword',
    ];

    const userData = {};
    fieldIds.forEach((id) => {
        const field = document.getElementById(id);
        const key = id.replace('create', '');
        userData[key.charAt(0).toLowerCase() + key.slice(1)] = field.value;
    });

    const rolesSelect = document.getElementById('createRoles')
    const selectedOptions = Array.from(rolesSelect.selectedOptions);
    userData.roles = selectedOptions.map((option) => option.value);

    await fetchCreateUser(userData);

    fieldIds.forEach((id) => {
        document.getElementById(id).value = '';
    });
    await createTable();
    document.getElementById('usersTable').checked = true;
}

async function editUserHandler(event) {
    event.preventDefault();

    const button = event.target;
    const form = button.closest('form');
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const userId = form.querySelector('input[name="id"]').value;
    const fieldIds = ['firstName', 'lastName', 'age', 'email', 'password'];

    let userData = { id: userId };
    fieldIds.forEach((fieldId) => {
        userData[fieldId] = form.querySelector(`#${fieldId}`).value;
    });

    console.log(userData)
    const rolesSelect = form.querySelector('select[name="roles"]');
    const selectedOptions = Array.from(rolesSelect.selectedOptions);
    userData.roles = selectedOptions.map((option) => option.value);

    await fetchPatchUser(userData);
    await createTable();
    const modal = document.getElementById('modal');
    modal.style.display = 'none';
}
export async function deleteUserHandler(event) {
    event.preventDefault();

    const button = event.target;
    const form = button.closest('form');
    const userId = form.querySelector('input[name="id"]').value;

    await fetchDeleteUser(userId);
    await createTable();
    const modal = document.getElementById('modal');
    modal.style.display = 'none';
}

const createButton = document.getElementById('createNewUser');
if (createButton) {
    createButton.addEventListener('click', createUserHandler);
}

document.addEventListener('click', async (event) => {
    if (event.target && event.target.matches('.action-button')) {
        const action = event.target.getAttribute('data-action');
        if (action === 'Edit') {
            await editUserHandler(event)
        } else if (action === 'Delete') {
            await deleteUserHandler(event);
        }
    }
});

setTimeout(async () => {
    await createTable();
}, 0);

