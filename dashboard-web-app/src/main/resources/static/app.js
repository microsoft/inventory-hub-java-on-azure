var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/greeting');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/reply', function (greeting) {
            console.log("Received = " + greeting);
            console.log("===========");
            showGreeting(JSON.parse(greeting.body).name);
            showTable();
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    console.log("Received = " + message);
    // $("#greetings").append("<tr><td>" + message + "</td></tr>");
    $("#greetings").prepend("<tr><td>" + message + "</td></tr>");

}

function showTable() {
    console.log("Refreshing Product table content");
    var jsonData = [
        { field1: 'value a1', field2: 'value a2', field3: 'value a3', field4: 'value a4' },
        { field1: 'value b1', field2: 'value b2', field3: 'value b3', field4: 'value b4' },
        { field1: 'value c1', field2: 'value c2', field3: 'value c3', field4: 'value c4' }
    ];
    loadTable('product-table', ['field1', 'field2', 'field3'], jsonData);
}

function loadTable(tableId, fields, data) {
    //$('#' + tableId).empty(); //not really necessary
    var rows = '';
    $.each(data, function(index, item) {
        var row = '<tr>';
        $.each(fields, function(index, field) {
            row += '<td>' + item[field+''] + '</td>';
        });
        rows += row + '<tr>';
    });
    $('#' + tableId).html(rows);
}

$(function () {
    connect();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});