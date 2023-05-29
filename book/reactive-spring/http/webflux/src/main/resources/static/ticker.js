window.addEventListener('load', function (e) {
    var tickTockBlock = document.getElementById('updateBlock');
    var es = new EventSource('http://localhost:8080/ticker-stream');
    es.addEventListener('message', function (update) {
        tickTockBlock.innerHTML = update.data; // <1>
    });
})