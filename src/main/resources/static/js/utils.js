export function createElement(tag, {classes = [], attributes = {}, styles = {}} = {}) {
    const element = document.createElement(tag);
    classes.forEach(className => element.classList.add(className));
    Object.entries(attributes).forEach(([key, value]) => element.setAttribute(key, value));
    Object.assign(element.style, styles);
    return element;
}

export function setFieldAccessibility(container, isDisabled) {
    const accessibilityContainer = createElement('fieldset', {attributes: {disabled: true}})
    accessibilityContainer.appendChild(container)
    if (!isDisabled) {
        accessibilityContainer.removeAttribute('disabled')
    }
    return accessibilityContainer
}