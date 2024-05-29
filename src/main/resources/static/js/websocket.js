let stompClient=null
let chatId=null;
let currentSubscription = null;
function connectToWebSocket() {





    const socket = new SockJS('http://localhost:8080/ws');
    stompClient= Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        /*stompClient.send("/app/chat.availableUser")*////todo raczej usunąć tą linijke*/
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/public',function(message) {
            var parsedMessage = JSON.parse(message.body);
            console.log(parsedMessage.id) //todo zwraca to samą 2 czyli jest dobrze . teraz zajmij sie metodą
            updateUserStatus(parsedMessage.id,parsedMessage.status)

        });
        /* stompClient.subscribe('/topic/public', function (message) {

            displayMessage('Server: ' + message.body);

        });*/




    });

}

function updateUserStatus(id,status) {




    var userElements = document.querySelectorAll('ul li[data-userid]');
    userElements.forEach(element => {
        if ((id.toString() === element.getAttribute('data-userid'))&&status.toString()==="CONNECTED") {
            console.log("uzytkownik :" + id);
            element.classList.add('online');
        }  if ((id.toString() === element.getAttribute('data-userid'))&&status.toString()==="DISCONNECTED"){
            console.log("uzytkownik :" + id);
            element.classList.remove('online')
            element.classList.add('offline')
        }else {


        }

        console.log("abc:" + id);

    });



}







function openChatWindow(friendId,messageId,element) {//todo  przypisz potem friendId do chatId zamiast messageId
    chatId=messageId

    const firstName = element.getAttribute('data-first-name');
    const lastName = element.getAttribute('data-last-name');

    console.log("JEST TO ID "+messageId)
    document.getElementById('chatWindow').style.display = 'flex';
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }








    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        currentSubscription= stompClient.subscribe( '/queue/messages/user/'+ messageId, function(message) {
            // displayMessage('Server: ' + message.body);
            displayMessage('Server: ' + message.body);

        });
    });


     var profileLink = '/profile?firstName=' + firstName + '&lastName=' + lastName + '&id=' + friendId;

    var friendLink = document.createElement('a');
     friendLink.innerText = 'Znajomy ' + firstName + ' ' + lastName;
    friendLink.href = '/profile?firstName=' + firstName + '&lastName=' + lastName + '&id=' + friendId;


    friendLink.className = 'friend-link';

    document.body.appendChild(friendLink);




    var chatFriendName = document.getElementById('chatFriendName');
    chatFriendName.innerHTML = '';
    chatFriendName.appendChild(friendLink);

}

function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value;
    stompClient.send("/app/chat.sendMessage/"+chatId, {}, JSON.stringify({content: message}));

    messageInput.value = '';
}






function displayMessage(message) {
    const chatContent = document.getElementById('chatContent');
    const messageElement = document.createElement('div');

    messageElement.textContent = message;

    chatContent.appendChild(messageElement);



}

function closeMessageWindow() {


    document.getElementById('messageWindow').style.display = 'none';



}