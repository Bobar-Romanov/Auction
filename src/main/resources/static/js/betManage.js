'use strict';
let betInput = document.querySelector('#betInput');
let bet = document.querySelector('#currentBet');
let msg = document.querySelector('#msg');
let submitBet = document.querySelector('#submitBet');
let lotId = document.querySelector('#lotId');
//let messageForm = document.querySelector('#messageForm');
//let messageInput = document.querySelector('#message');
//let messageArea = document.querySelector('#messageArea');
//let connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;

function connect() {
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);

}

function onConnected() {
    console.log(stompClient.subscribe("/topic/bet/" + lotId.textContent , onMessageReceived));
   }
function onError(error) {
    alert("NO!");
}
function newBet(event) {

    let betValue = betInput.value;

    if(betValue && stompClient) {
        console.log(betValue);
        let newBet = {
            lotId: lotId.textContent,
            content: betValue
        };
        stompClient.send("/app/auction/home/" + lotId.textContent +"/makeBet", {}, JSON.stringify(newBet));
        betInput.value = '';
    }
    event.preventDefault();
}
function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    bet.textContent = message;
}
connect();
submitBet.addEventListener('click', newBet, true);
