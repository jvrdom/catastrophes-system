
var app = {
        
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        //document.addEventListener('deviceready', this.onDeviceReady, false);
        document.addEventListener('deviceready', init, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        //app.receivedEvent('deviceready');
        app.getLocation();
        app.seePosition();
        app.mandarJquery();

        app.receivedEvent('deviceready');
        var pushNotification = window.plugins.pushNotification;
        pushNotification.register(app.successHandler, app.errorHandler,{"senderID":"466388852109","ecb":"app.onNotificationGCM"});
    
        //abro la base de datos
        var db = window.sqlitePlugin.openDatabase({name: "my.db"});       
                db.transaction(function(tx) {
                    tx.executeSql('DROP TABLE IF EXISTS test_table');
                    tx.executeSql('CREATE TABLE IF NOT EXISTS test_table (id integer primary key, data text, data_num integer)');

                    // demonstrate PRAGMA:
                    db.executeSql("pragma table_info (test_table);", [], function(res) {
                      console.log("PRAGMA res: " + JSON.stringify(res));
                    });

                    tx.executeSql("INSERT INTO test_table (data, data_num) VALUES (?,?)", ["test1", 100], function(tx, res){
                        console.log("insertId: " + res.insertId + " -- probably 1");
                        console.log("rowsAffected: " + res.rowsAffected + " -- should be 1");
                    });
                    tx.executeSql("INSERT INTO test_table (data, data_num) VALUES (?,?)", ["test2", 100], function(tx, res){
                        console.log("insertId: " + res.insertId + " -- probably 2");
                        console.log("rowsAffected: " + res.rowsAffected + " -- should be 1");
                    });
                    tx.executeSql("INSERT INTO test_table (data, data_num) VALUES (?,?)", ["test3", 100], function(tx, res){
                        console.log("insertId: " + res.insertId + " -- probably 3");
                        console.log("rowsAffected: " + res.rowsAffected + " -- should be 1");
                    });
                    tx.executeSql("INSERT INTO test_table (data, data_num) VALUES (?,?)", ["test4", 100], function(tx, res){
                        console.log("insertId: " + res.insertId + " -- probably 4");
                        console.log("rowsAffected: " + res.rowsAffected + " -- should be 1");
                    });

                    tx.executeSql("INSERT INTO test_table (data, data_num) VALUES (?,?)", ["test5", 100], function(tx, res) {
                      console.log("insertId: " + res.insertId + " -- probably 5");
                      console.log("rowsAffected: " + res.rowsAffected + " -- should be 1");


                      db.transaction(function(tx) {
                        tx.executeSql("select count(id) as cnt from test_table;", [], function(tx, res) {
                          console.log("lgh res.rows.length: " + res.rows.length + " -- should be 1");
                          console.log("lgh res.rows.item(0).cnt: " + res.rows.item(0).cnt + " -- should be 1");
                        });
                      });

                    }, function(e) {
                      console.log("ERROR: " + e.message);
                    });
                });


    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);

        
    },
    // result contains any message sent from the plugin call
    successHandler: function(result) {
        alert('Callback Success! Result = '+result)
    },
    errorHandler: function(error) {
        alert(error);
    },
    onNotificationGCM: function(e) {
        switch( e.event )
        {
            case 'registered':
                if ( e.regid.length > 0 )
                {
                    console.log("Regid " + e.regid);
        //            alert('registration id = '+e.regid);
                }
            break;
 
            case 'message':
              // this is the actual push notification. its format depends on the data model from the push server
              alert('message = '+e.message+' msgcnt = '+e.msgcnt);
              app.persistirLocal();

            break;
 
            case 'error':
              alert('GCM error = '+e.msg);
            break;
 
            default:
              alert('An unknown GCM event has occurred');
              break;
        }
    },
    // geolocation plugin
    getLocation: function(){
        var onSuccess = function(position) { // es el codigo de la funcion leaflet
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            
            var map = L.map('map').setView([lat, lon], 13);
            // add an OpenStreetMap tile layer
            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
            // add a marker in the given location, attach some popup content to it and open the popup
            L.marker([lat, lon]).addTo(map)
                .bindPopup('Posición Aproximada') 
                .openPopup();

        };

        // onError Callback receives a PositionError object
        //
        function onError(error) {
            console.log('code: '    + error.code    + '\n' +
                  'message: ' + error.message + '\n');
        }
        navigator.geolocation.getCurrentPosition(onSuccess, onError);
    },
    
    seePosition: function(){
        // onSuccess Callback
        //   This method accepts a `Position` object, which contains
        //   the current GPS coordinates
        //
        function onSuccess(position) {
            // var element = document.getElementById('geolocation');
            // element.innerHTML = 'Latitude: '  + position.coords.latitude      + '<br />' +
            //                     'Longitude: ' + position.coords.longitude     + '<br />' +
            //                     '<hr />'      + element.innerHTML;
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            
            var map = L.map('map').setView([lat, lon], 5);// original pasaba 13 en vez de 5, no cambia nada
            // add an OpenStreetMap tile layer
            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
            // add a marker in the given location, attach some popup content to it and open the popup
            L.marker([lat, lon]).addTo(map)
                .bindPopup('Posición Aproximada') 
                .openPopup();

        }
        
        // onError Callback receives a PositionError object
        //
        function onError(error) {
            alert('code: '    + error.code    + '\n' +
                  'message: ' + error.message + '\n');
        }

        // Options: throw an error if no update is received every 30 seconds.
        //
        var watchID = navigator.geolocation.watchPosition(onSuccess, onError, { timeout: 30000 });
    },

    mandarJquery: function(){
        $(document).ready(function() {
            $( ".menu").click(function(){
                console.log('lgh el boton sigue andando');
                //abro la base de datos
            
               alert('clickeaste');
            });// end - $( ".menu").click(function(){
            


        });// end - $(document).ready
    }
    
    
};


// base de datos
app.db = null;

app.openDb = function() {
   if (window.navigator.simulator === true) {
        // For debugin in simulator fallback to native SQL Lite
        console.log("Use built in SQL Lite");
        app.db = window.openDatabase("Todo", "1.0", "Cordova Demo", 200000);
    }
    else {
        app.db = window.sqlitePlugin.openDatabase("Todo");
    }
}

app.createTable = function() {
    var db = app.db;
    db.transaction(function(tx) {
        tx.executeSql("CREATE TABLE IF NOT EXISTS todo(ID INTEGER PRIMARY KEY ASC, todo TEXT, added_on DATETIME)", []);
    });
}
      
app.addTodo = function(todoText) {
    var db = app.db;
    db.transaction(function(tx) {
        var addedOn = new Date();
        tx.executeSql("INSERT INTO todo(todo, added_on) VALUES (?,?)",
                      [todoText, addedOn],
                      app.onSuccess,
                      app.onError);
    });
}
      
app.onError = function(tx, e) {
    console.log("Error: " + e.message);
} 
      
app.onSuccess = function(tx, r) {
    app.refresh();
}
      
app.deleteTodo = function(id) {
    var db = app.db;
    db.transaction(function(tx) {
        tx.executeSql("DELETE FROM todo WHERE ID=?", [id],
                      app.onSuccess,
                      app.onError);
    });
}

app.refresh = function() {
    var renderTodo = function (row) {
        return "<li>" + "<div class='todo-check'></div>" + row.todo + "<a class='button delete' href='javascript:void(0);'  onclick='app.deleteTodo(" + row.ID + ");'><p class='todo-delete'></p></a>" + "<div class='clear'></div>" + "</li>";
    }
    
    var render = function (tx, rs) {
        var rowOutput = "";
        var todoItems = document.getElementById("todoItems");
        for (var i = 0; i < rs.rows.length; i++) {
            rowOutput += renderTodo(rs.rows.item(i));
        }
      
        todoItems.innerHTML = rowOutput;
    }
    
    var db = app.db;
    db.transaction(function(tx) {
        tx.executeSql("SELECT * FROM todo", [], 
                      render, 
                      app.onError);
    });
}

function init() {
    navigator.splashscreen.hide();
    app.openDb();
    app.createTable();
    app.refresh();
}
      
function addTodo() {
    var todo = document.getElementById("todo");
    app.addTodo(todo.value);
    todo.value = "";
}

    // accion para el boton segun la clase menu
/*    
    $( ".menu").click(function(){
        $.ajax({
            url:"http://192.168.1.43:8080/catastrophes-system-web/rest/MyRESTApplication"
        }).then(function(data) {
            console.log('lgh en llamoRest dentro del .then');
            alert(data);
           // $('#holarest-id').append(data);
        });
    });// end - $( ".menu").click(function(){
*/



// ,

    // bindClickBoton: function(){
    //     $(document).ready(function() {
    //         $(".menu").click(function(){
    //             $(".menu")addClass("menuonclick");
    //         });
    //     });
    // }
    //,

    // llamoRest: function(){
    // console.log('lgh en llamoRest ');
    //     $(document).ready(function() {
    //         //$.ajax({
    //         $.get({
    //             url: "http://todoapp-todoapphpdluz.rhcloud.com/rest/todos"
    //         }).then(function(data) {
    //            //$('#datarest').append(data);
    //            console.log('lgh data del rest ' + data);
    //         });
    //     });
    // }


/////////////////////////////////////// EJEMPLOS


    //leaflet maps
    // leaflet: function() { // ejemplo
    //     console.log('lgh en leaflet');        
    //     var map = L.map('map').setView([51.505, -0.09], 13);
    //     // add an OpenStreetMap tile layer
    //     L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    //         attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    //     }).addTo(map);
    //     L.marker([51.5, -0.09]).addTo(map)
    //         .bindPopup('A pretty CSS3 popup. <br> Easily customizable.') 
    //         .openPopup();
    // },

///////////////////////////////////////////