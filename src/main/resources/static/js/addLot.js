let list = document.querySelector('#imgs');

function addImg(){

    let input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('name', 'images');
    input.required = true;
    let icon = document.createElement('img');
    icon.setAttribute('src', '/ico/close.png');
    icon.setAttribute('onclick', 'this.parentNode.parentNode.removeChild(this.parentNode);');
    icon.classList.add('delIco');
    let li = document.createElement('li');
    li.appendChild(input);
    li.appendChild(icon);
    list.appendChild(li);
}
