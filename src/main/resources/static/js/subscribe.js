

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
