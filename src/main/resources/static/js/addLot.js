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
    let li = document.createElement('li');
    li.appendChild(input);
    li.appendChild(icon);
    list.appendChild(li);
    let uploads = document.getElementsByClassName('upload');
    for (let i = 0; i < uploads.length; i++) {
        uploads[i].addEventListener('change', updateSize);
    }

}
function updateSize() {
    let file = this.files[0];
    let parts = file.name.split('.');
    let ext = parts.pop();

    if(file.size >= 1048576){
        let err1 = document.createElement('p');
        err1.textContent = 'So BIG';
        err1.classList.add('error');
        this.appendChild(err1); //не работает надо исправить
    }
    if(ext == 'png'  || ext == 'jpg'  || ext == 'gif'  || ext == 'jpeg'  || ext == 'pdf'){
        return;
    }else {
        let err2 = document.createElement('p');
        err2.textContent = 'Wrong ext';
        err2.classList.add('error');
        this.appendChild(err2); //не работает надо исправить
    }

}

    let uploads = document.getElementsByClassName('upload');
    for (let i = 0; i < uploads.length; i++) {
        uploads[i].addEventListener('change', updateSize);
    }


