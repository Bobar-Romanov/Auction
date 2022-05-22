let list = document.querySelector('#imgs');


function addImg(){
    let input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('name', 'images');
    input.classList.add('upload');
    input.required = true;
    let icon = document.createElement('img');
    icon.setAttribute('src', '/ico/close.png');
    icon.setAttribute('onclick', 'this.parentNode.parentNode.removeChild(this.parentNode);');
    icon.classList.add('delIco');
    let p = document.createElement('p');
    p.textContent = '';
    p.classList.add('error');
    let li = document.createElement('li');
    li.appendChild(input);
    li.appendChild(icon);
    li.appendChild(p);
    list.appendChild(li);

}


