const regex = /[^a-z0-9A-Z.,!_-]/;
let regbtn = document.getElementById('regbutton');
function checkLog(){
    let login = document.getElementById('nickname').value
    let error = document.getElementById('logError');
    if(login.length > 20 || login.length < 4){
        error.textContent = "Логин должен быть длинной от 4 до 20 символов";
        regbtn.setAttribute('type', 'button')
        return;
    }else {
        error.textContent = "";
    }
    if (regex.test(login)) {
        error.textContent = "Логин содержит недопустимые символы";
        regbtn.setAttribute('type', 'button')
        return;
    } else {
        error.textContent = "";
        regbtn.setAttribute('type', 'submit')
    }
}
function checkPass(){
    let pass = document.getElementById('password').value
    let error = document.getElementById('passError');
    if(pass.length > 20 || pass.length < 4){
        error.textContent = "Пароль должен быть длинной от 8 до 20 символов";
        regbtn.setAttribute('type', 'button')
        return;
    }else {
        error.textContent = "";
    }
    if (regex.test(pass)) {
        error.textContent = "Пароль содержит недопустимые символы..?";
        regbtn.setAttribute('type', 'button')
        return;
    } else {
        error.textContent = "";
        regbtn.setAttribute('type', 'submit')
    }
}