let stompClient = null
let chatId = null;
let currentSubscription = null;
let isPublicChat="false";

function connectToWebSocket() {


    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.send("/app/chat.requestActiveUsers", {}, {});

        stompClient.subscribe('/topic/public/ActiveUsersAtStart', function (message) {
            var activeUsers = JSON.parse(message.body);

                if (Array.isArray(activeUsers)) {
                    activeUsers.forEach(userId => updateUserStatus(userId, "CONNECTED"));

            }
        });



        stompClient.subscribe('/topic/public', function (message) {
            var parsedMessage = JSON.parse(message.body);
            console.log(parsedMessage.id) //todo zwraca to samą 2 czyli jest dobrze . teraz zajmij sie metodą
            updateUserStatus(parsedMessage.id, parsedMessage.status)

        });


    });


}

function updateUserStatus(id, status) {


    var userElements = document.querySelectorAll('aside div[data-userid]');

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
    chatId = friendId


    const firstNameFromMessageWindow = element.getAttribute('data-first-name-from-window');
    const lastNameFromMessageWindow = element.getAttribute('data-last-name-from-window');
    const imageFromMessageWindow = element.getAttribute('data-image-from-window');
    const modifiedImageFromWindow='data:image/jpeg;base64,'+imageFromMessageWindow

    const firstName = element.getAttribute('data-first-name');
    const lastName = element.getAttribute('data-last-name');
    let profilePicture = element.getAttribute('data-image');
    profilePicture = 'data:image/jpeg;base64,' + profilePicture;


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
            chatId = messageId;


            console.log('Received message: ', message.body);

            let sender = JSON.parse(message.body);
            let fullName = sender.sender;
            let splitName = fullName.split(" ");
            let firstName = splitName[0];
            let lastName = splitName[1];
            let senderImage = 'data:image/jpeg;base64,' + sender.senderEncodedPicture;

            displayMessageWithImage(senderImage, firstName, lastName, sender.content);
        });
        receiveRabbitMq(chatId)
    });
    var profileLink = '/profile?firstName=' + firstName + '&lastName=' + lastName + '&id=' + friendId;

    var friendLink = document.createElement('a');

    const imgElement = document.createElement('img');

    imgElement.src = modifiedImageFromWindow;
    if (imgElement.src.includes(null)){
        imgElement.src = profilePicture;
    }






    imgElement.alt = firstName + ' ' + lastName;
    imgElement.className = 'profile-image';

    friendLink.appendChild(imgElement);
    if (firstNameFromMessageWindow && lastNameFromMessageWindow) {
        friendLink.innerHTML +=firstNameFromMessageWindow + ' ' + lastNameFromMessageWindow;
    } else {
        friendLink.innerHTML +=firstName + ' ' + lastName;
    }
    friendLink.href = profileLink;
    friendLink.className = 'friend-link';


    var chatFriendName = document.getElementById('chatFriendName');
    chatFriendName.innerHTML = '';
    chatFriendName.appendChild(friendLink);


    const chatContent = document.getElementById('chatContent');
    chatContent.innerHTML = '';


    const allMessageItems = document.querySelectorAll('#allMessages span');
    console.log("rozmiar" + allMessageItems.length)


    allMessageItems.forEach(function (span) {
        const chatId = span.getAttribute('data-chatId');

        if (chatId === messageId) {
            const sender = span.getAttribute('data-sender');
            const senderName = span.getAttribute('data-sender-name');
            const senderSurname = span.getAttribute('data-sender-surname');
            const contextOfMessage = span.getAttribute('data-contextOfMessage');
            const profilePicture = span.getAttribute('data-profile-picture');


            const modifiedProfilePicture = 'data:image/jpeg;base64,' + profilePicture;

            displayMessageWithImage(modifiedProfilePicture, senderName, senderSurname, contextOfMessage);


            console.log('Sender ID:', sender);
            console.log('Sender Name:', senderName);

        }


    });


    /*   var contextOfMessage = element.querySelector('span').getAttribute('data-contextOfMessage');
          var chatId = element.querySelector('span').getAttribute('data-chatId');*/


    var elements = document.querySelectorAll('aside div[data-message]');

    console.log("abcd" + elements.length)


    elements.forEach(function (element) {
        var senderName = element.querySelector('span').getAttribute('data-sender-name');

        console.log("Sender Name: " + senderName);
    });


}


function receiveRabbitMq(chatId) {
    console.log("received1")
    stompClient.send("/app/chat.receiveRabbitMq/" + chatId, {}, JSON.stringify({chatId:chatId}));
console.log("received2")
}

function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value;
    stompClient.send("/app/chat.sendMessage/" + chatId, {}, JSON.stringify({chatId:chatId,content: message,isPublicChat:isPublicChat}));
      console.log("wysłałem na :"+chatId)
    messageInput.value = '';
}

/*function sendMessageOnPublicGroup() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value;
    stompClient.send("/app/chat.sendMessageOnPublicGroup/" + chatId, {}, JSON.stringify({content: message}));

    messageInput.value = '';
}*/





function displayMessage(message) {
    const chatContent = document.getElementById('chatContent');
    const messageElement = document.createElement('div');

    messageElement.textContent = message;

    chatContent.appendChild(messageElement);


}


function closeMessageWindow() {


    document.getElementById('messageWindow').style.display = 'none';


}

function displayMessageWithImage(profilePicture, senderName, senderSurname, contextOfMessage) {

    const chatContent = document.getElementById('chatContent');
    const messageElement = document.createElement('div');

    const imgElement = document.createElement('img');
    imgElement.src = profilePicture;
    imgElement.alt = senderName + ' ' + senderSurname;
    imgElement.className = 'sender-image';

    const textElement = document.createElement('span');
    textElement.textContent = senderName + ' ' + senderSurname + ": " + contextOfMessage;
    console.log("afd"+textElement.textContent)
    messageElement.appendChild(imgElement);
    messageElement.appendChild(textElement);

    chatContent.appendChild(messageElement);



}



function openPublicChatWindow(element) {
    const groupImage = element.getAttribute('data-group-image');
    const groupName = element.getAttribute('data-group-name');
    const groupId = element.getAttribute('data-group-id');
    const modifiedGroupImage = 'data:image/jpeg;base64,' + groupImage;
    chatId=groupId
    document.getElementById('chatWindow').style.display = 'flex';
    isPublicChat="true";
    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }

    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);
    console.log("połączyłem1")

    stompClient.connect({}, function (frame) {
        console.log("połączyłem2")
        console.log('Connected: ' + frame);
        currentSubscription = stompClient.subscribe('/topic/messages/chat/'+groupId, function (message) {
          console.log("połącz")
            let sender = JSON.parse(message.body);
            let fullName = sender.sender;
            let splitName = fullName.split(" ");
            let firstName = splitName[0];
            let lastName = splitName[1];
            let senderImage = 'data:image/jpeg;base64,' + sender.senderEncodedPicture;
            displayMessageWithImage(senderImage, firstName, lastName, sender.content);


        });
    });

    var chatFriendName = document.getElementById('chatFriendName');
    chatFriendName.innerHTML = `<span>${groupName}</span>`;

    const chatContent = document.getElementById('chatContent');
    chatContent.innerHTML = '';

    const imgElement = document.createElement('img');
    imgElement.src = modifiedGroupImage;
    imgElement.alt = groupName;
    imgElement.className = 'profile-image';
    chatFriendName.prepend(imgElement);
    const allMessageItems = document.querySelectorAll('#allMessages span');
    console.log("rozmiar" + allMessageItems.length);

    allMessageItems.forEach(function (span) {
        const publicChatId = span.getAttribute('data-public-ChatId');

        if (publicChatId === groupId) {
            // const sender = span.getAttribute('data-sender');
            const senderName = span.getAttribute('data-sender-name');
            const senderSurname = span.getAttribute('data-sender-surname');
            const contextOfMessage = span.getAttribute('data-contextOfMessage');
            const profilePicture = span.getAttribute('data-profile-picture');

            const modifiedProfilePicture = 'data:image/jpeg;base64,' + profilePicture;

            displayMessageWithImage(modifiedProfilePicture, senderName, senderSurname, contextOfMessage);


        }
    });

}
