

check();
function check(){
   $.ajax({
      url: window.location + "/checkSub",
      success: function (data){
          if(data == 'true'){
             document.getElementById("heartIco").hidden = false;
          }else{
             document.getElementById("plusIco").hidden = false;
          }
      },
   });
}

function sub(){
   $.ajax({
      url: window.location + "/Sub",
      success: function (){
         document.getElementById("plusIco").hidden = true;
         document.getElementById("heartIco").hidden = false;
      },
   });
}
function unSub(){
   $.ajax({
      url: window.location + "/unSub",
      success: function (){
         document.getElementById("plusIco").hidden = false;
         document.getElementById("heartIco").hidden = true;
      },
   });
}

let commBtn = document.querySelector('#subCom')

function comFun(){
   let username = document.querySelector('#curUs').textContent;
   let text = document.querySelector('#message-2081').value;
   let commDiv = document.querySelector('#commDiv')
   if(text != ""){
      let div = document.createElement('div');
      div.classList.add('u-container-layout');
      div.classList.add('u-container-layout-2');
      let h4 = document.createElement('h4');
      h4.classList.add('u-text');
      h4.classList.add('u-text-default');
      h4.classList.add('u-text-1')
      let p = document.createElement('p');
      p.classList.add('u-custom-font');
      p.classList.add('u-font-merriweather');
      p.classList.add('u-text');
      p.classList.add('u-text-2');
      h4.textContent = username;
      p.textContent = text;
      div.appendChild(h4);
      div.appendChild(p);
      let div2 = document.createElement('div');
      div2.classList.add('u-container-style')
      div2.classList.add('u-group')
      div2.classList.add('u-palette-1-light-3')
      div2.classList.add('u-group-2')
      div2.appendChild(div);
      commDiv.insertAdjacentElement('afterend', div2);

   }
}

commBtn.addEventListener('click', comFun);
