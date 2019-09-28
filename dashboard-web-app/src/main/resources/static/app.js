/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

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
        stompClient.subscribe('/topic/reply', async function (greeting) {
            console.log("Received = " + greeting);
            console.log("===========");
            showGreeting(JSON.parse(greeting.body).name);
            await showTable();
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

async function showTable() {
    console.log("Refreshing Product table content");

    var response = await window.fetch('api/locations', {
        method:"GET"
    });

    var locations = await response.json();

    var headTable = '<tr><th rowspan="0"></th>';
    Object.keys(locations).sort().forEach(function(location) {
        headTable += '<th>' + location + '</th>';
    });
    headTable += "</tr>";

    var response2 = await window.fetch('api/products', {
        method:"GET"
    });

    var products = await response2.json();

    Object.keys(products).forEach(function(productItem) {
        headTable += '<tr>';
        var product = products[productItem];
        headTable += '<td>' + product['description'] + '</td>';
        Object.keys(product.countByLocation).sort().forEach(function(countPerLocation) {
            headTable += '<td>' + product.countByLocation[countPerLocation] + '</td>';
        });
        headTable += '</tr>';
    });

    $('#product-table').html(headTable);
}

$(function () {
    connect();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});