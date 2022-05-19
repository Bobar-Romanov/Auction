let list = document.querySelector('#imgs');
let subbtn = document.getElementById('sub');

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
function updateSize(elem) {
    if (elem == null){
        return true;
    }
    let file = elem.files[0];
    let parts = file.name.split('.');
    let ext = parts.pop();

    let parnt = elem.parentElement;

    if(file.size >= 1048576){
        parnt.lastChild.textContent = "so big";
        return false;
    }
    if(ext == 'png'  || ext == 'jpg'  || ext == 'gif'  || ext == 'jpeg'  || ext == 'pdf'){
        parnt.lastChild.textContent = "";
        return true;
    }else {
        parnt.lastChild.textContent = "wrong ext";
        return false;
    }
}


let mainimg = document.getElementById('mainUpload');
let err = document.getElementById('error1');

function sizecheck(){
    let file = mainimg.files[0];
    let parts = file.name.split('.');
    let ext = parts.pop();

    if(file.size >= 1048576){
        err.textContent = "so big";
        return false;
    }
    if(ext == 'png'  || ext == 'jpg'  || ext == 'gif'  || ext == 'jpeg'  || ext == 'pdf'){
        err.lastChild.textContent = "";
        return true;
    }else {
        err.lastChild.textContent = "wrong ext";
        return false;
    }
}

mainimg.addEventListener('change', sizecheck);


    function  check(){
        let uploads = document.getElementsByClassName('upload');
        for (let i = 0; i < uploads.length; i++) {
            if(updateSize(uploads[i]) == false){
                subbtn.setAttribute('type', 'button');
                alert("988");
                return;
            }
        }
        if(sizecheck() == false){
            subbtn.setAttribute('type', 'button');
            return;
        }
        subbtn.setAttribute('type', 'submit');
    }

subbtn.addEventListener('click', check);

