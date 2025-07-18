let cardId = 0;

document.getElementById('card-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const title = document.getElementById('card-title').value.trim();
    const desc = document.getElementById('card-desc').value.trim();

    if (title && desc) {
        addCardToColumn('initial-list', title, desc);
        document.getElementById('card-form').reset();
    }
});

function addCardToColumn(columnId, title, desc) {
    const cardList = document.getElementById(columnId);
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.id = ++cardId;

    card.innerHTML = `
        <strong>${title}</strong><br>
        <small>${desc}</small>
        <button onclick="moveCard(this)">➡</button>
    `;

    cardList.appendChild(card);
}

function moveCard(button) {
    const card = button.parentElement;
    const parentId = card.parentElement.id;

    let nextColumnId;
    switch (parentId) {
        case 'initial-list':
            nextColumnId = 'pending-list';
            break;
        case 'pending-list':
            nextColumnId = 'final-list';
            break;
        case 'final-list':
            nextColumnId = 'cancel-list';
            break;
        default:
            nextColumnId = null;
    }

    if (nextColumnId) {
        document.getElementById(nextColumnId).appendChild(card);
    } else {
        button.disabled = true;
    }
}