window
    .addEventListener('load', function (e) {

        var messages = document.getElementById('messages');
        var button = document.getElementById('send');
        var message = document.getElementById('message');
        var websocket = new WebSocket('ws://localhost:8080/ws/chat'); //<1>

        websocket.addEventListener('message', function (e) {
            var element = document.createElement('div');
            element.innerText = e.data;
            messages.appendChild(element);
        });

        // <2>
        function send() {
            var value = message.value;
            message.value = '';
            websocket.send(JSON.stringify({'text': value.trim()}));
        }

        // <3>
        message.addEventListener('keydown', function (e) {
            var key = e.key;
            if (key === 'Enter') {
                send();
            }
        });


        button.addEventListener('click', function (e) {
            send();
            e.preventDefault();
            return false;
        });
    })
;