let stompClient = null
let chatId = null;
let currentSubscription = null;

function connectToWebSocket() {


    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);




        stompClient.subscribe('/topic/public/ActiveUsersAtStart', function (message) {
            console.log("komunikat")
            var activeUsers = JSON.parse(message.body);
            if (Array.isArray(activeUsers)) {
                if (Array.isArray(activeUsers)) {
                    activeUsers.forEach(userId => updateUserStatus(userId, "CONNECTED"));

                }
            }
        });
        stompClient.send("/app/chat.requestActiveUsers", {}, {});



        stompClient.subscribe('/topic/public', function (message) {
            var parsedMessage = JSON.parse(message.body);
            console.log(parsedMessage.id) //todo zwraca to samą 2 czyli jest dobrze . teraz zajmij sie metodą
            updateUserStatus(parsedMessage.id, parsedMessage.status)

        });


    });


}

function updateUserStatus(id, status) {


    var userElements = document.querySelectorAll('ul li[data-userid]');

    userElements.forEach(element => {
        if ((id.toString() === element.getAttribute('data-userid')) && status.toString() === "CONNECTED") {
            console.log("uzytkownik :" + id);
            element.classList.add('online');
        }
        if ((id.toString() === element.getAttribute('data-userid')) && status.toString() === "DISCONNECTED") {
            console.log("uzytkownik :" + id);
            element.classList.remove('online')
            element.classList.add('offline')
        } else {


        }


    });


}


function openChatWindow(friendId, messageId, element) {//todo  przypisz potem friendId do chatId zamiast messageId
    chatId = messageId

    const firstName = element.getAttribute('data-first-name');
    const lastName = element.getAttribute('data-last-name');

    console.log("JEST TO ID " + messageId)
    document.getElementById('chatWindow').style.display = 'flex';
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }


    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        currentSubscription = stompClient.subscribe('/queue/messages/user/' + messageId, function (message) {
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


    const allMessageItems = document.querySelectorAll('#allMessages span');
    console.log("rozmiar" + allMessageItems.length)


    allMessageItems.forEach(function (span) {
        const chatId = span.getAttribute('data-chatId');

        if (chatId === messageId) {
            const sender = span.getAttribute('data-sender');
            const senderName = span.getAttribute('data-sender-name');

            console.log('Sender ID:', sender);
            console.log('Sender Name:', senderName);

        }


    });


    /*   var contextOfMessage = element.querySelector('span').getAttribute('data-contextOfMessage');
          var chatId = element.querySelector('span').getAttribute('data-chatId');*/


    var elements = document.querySelectorAll('ul li[data-message]');

    console.log("abcd" + elements.length)


    elements.forEach(function (element) {
        var senderName = element.querySelector('span').getAttribute('data-sender-name');

        console.log("Sender Name: " + senderName);
    });


}

function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value;
    stompClient.send("/app/chat.sendMessage/" + chatId, {}, JSON.stringify({content: message}));

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