import {
    createElement,
    setFieldAccessibility
} from "./utils.js";


const modal = document.getElementById('modal')
const modalBody = document.getElementById('modalBody')
const modalClose = document.getElementById('modalClose');

modalClose.addEventListener('click', () => {
    modal.style.display = 'none'
});

export function openEditModal(user, roles) {
    modalBody.innerHTML = '';
    modalBody.appendChild(createUserForm('Edit', user, roles));
    modal.style.display = 'flex';
}

export function openDeleteModal(user, roles) {
    modalBody.innerHTML = '';
    modalBody.appendChild(createUserForm('Delete', user, roles));
    modal.style.display = 'flex';
}


function createUserForm(action, user, roles) {
    const description = document.getElementById('description')
    description.textContent = `${action} user`

    const form = createElement('form', {
        classes: ['custom-flex'],
        styles: {flexDirection: 'column'}
    });

    form.appendChild(createElement('input', {
        attributes: {type: 'hidden', name: 'id', value: user.id},
    }));

    const isDisabled = action === 'Delete';

    const fields = [
        {name: 'ID', type: 'text', id: 'id', value: user.id, disabled: true},
        {name: 'First Name', type: 'text', id: 'firstName', value: user.firstName, disabled: isDisabled},
        {name: 'Last Name', type: 'text', id: 'lastName', value: user.lastName, disabled: isDisabled},
        {name: 'Age', type: 'number', id: 'age', value: user.age, disabled: isDisabled},
        {name: 'Email', type: 'email', id: 'email', value: user.email, disabled: isDisabled},
        {name: 'Password', type: 'password', id: 'password', value: user.password, disabled: isDisabled}
    ];

    fields.forEach(field => {
        form.appendChild(setFieldAccessibility(createInputField(field.name, field.type, field.id, field.value), field.disabled));
    });

    form.appendChild(setFieldAccessibility(createRolesSelect(user, roles), isDisabled))
    form.appendChild(createFormButtons(action, user, fields));

    return form;
}

export function createInputField(labelText, inputType, inputID, placeHolderValue = '') {
    const container = createElement('div', {classes: ['form-group']});

    const label = createElement('label');
    label.textContent = labelText;

    const input = createElement('input', {
        classes: ['form-control'],
        attributes: {
            type: inputType,
            id: inputID,
            placeholder: placeHolderValue,
            required: true
        },
    });

    container.appendChild(label);
    container.appendChild(input);

    return container;
}

export function createRolesSelect(user, roles) {
    const selectedRoles = user.roles;
    const rolesContainer = createElement('div', {
        classes: ['form-group'],
        styles: {width: '274px'}
    });
    const label = createElement('label', {
        classes: ['form-label'],
        attributes: {for: `editRoles-${user.id}`}
    });
    label.textContent = 'Role';

    const select = createElement('select', {
        classes: ['form-control'],
        attributes: {
            name: 'roles',
            id: `editRoles-${user.id}`,
            multiple: true,
            size: 2
        }
    });

    roles.forEach(role => {
        const option = createElement('option', {
            attributes: {
                value: role.id,
                selected: selectedRoles.includes(role.id) ? true : null
            }
        });
        option.textContent = role.role;
        select.appendChild(option);
    });

    rolesContainer.appendChild(label);
    rolesContainer.appendChild(select);

    return rolesContainer;
}


function createFormButtons(action, user, fields) {
    const sectionContainer = createElement('div', {classes: ['form-group']});
    sectionContainer.appendChild(createElement('hr'))

    const buttonContainer = createElement('div', {
        styles: {display: 'flex', backgroundColor: '#ffffff', alignContent: 'center'}
    });

    const closeButton = createElement('button', {
        classes: ['btn', 'btn-secondary', 'button', 'close-button'],
        attributes: {type: 'button'},
    });
    closeButton.textContent = 'Close';
    closeButton.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    const actionButton = createElement('button', {
        classes: ['btn', 'btn-secondary', 'modified-button', 'action-button'],
        attributes: {
            'data-action': action,
            'data-user-id': user.id,
        },
        styles: {backgroundColor: action === 'Edit' ? `#339bb1` : `#ff0015`}
    });
    actionButton.textContent = action;

    buttonContainer.appendChild(closeButton);
    buttonContainer.appendChild(actionButton);
    sectionContainer.appendChild(buttonContainer)

    return sectionContainer;
}