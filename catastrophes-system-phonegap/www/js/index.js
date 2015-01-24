
var app = {
    // Application Constructor
    initialize: function() {
        console.log('lgh initialize'); 
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
        //document.addEventListener('deviceready', this.onDeviceReady, false);
        document.addEventListener("menubutton", this.menu, false);
        //document.addEventListener("backbutton", backButton, false);

    },
    menu: function(){
        bootbox.alert('Apreto el menu', function() {
                            console.log('lgh Apreto el menu');
        });
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        //app.receivedEvent('deviceready');
        app.backButtonAccion = 'salir';
        app.getLocation();
        //app.seePosition();
        app.openDb();
        app.createTable();
        //app.deleteCatastrofe(); borro si sucsess al bajar la data por web service
        app.checkSesion();
        app.fabrica();
        
        app.getUsuarioForUpdate();
                    
        app.receivedEvent('deviceready');
        var pushNotification = window.plugins.pushNotification;
        pushNotification.register(app.successHandler, app.errorHandler,{"senderID":"466388852109","ecb":"app.onNotificationGCM"});

        
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

    lanzarSesion: function(){
        $('#load').addClass("delete");
        $('#menuboton').addClass("novisible");

        if(app.email ==null){ // no hay sesion
            $('#map').addClass("mapFondo");
            $('#formIngresar').addClass("visible");
        }
        else{                        
            var evento='login';
            app.dom(evento);
        }

        //app.mandarJquery();
            
    },

    openDb: function() {
        console.log('lgh en openDb');
        if (window.navigator.simulator === true) {
            // For debugin in simulator fallback to native SQL Lite
            console.log("Use built in SQL Lite");
            app.db = window.openDatabase("Todo", "1.0", "Cordova Demo", 200000);
        }
        else {
            app.db = window.sqlitePlugin.openDatabase("Todo");
        }
    },

    createTable: function() {
        var db = app.db;

        db.transaction(function(tx) {
            tx.executeSql("CREATE TABLE IF NOT EXISTS catastrofe(ID INTEGER PRIMARY KEY ASC, idC TEXT, nombre TEXT, latitud TEXT, longitud TEXT, planRiesgo TEXT, planEmergencia TEXT)", []);
        });

        db.transaction(function(tx) {
            tx.executeSql("CREATE TABLE IF NOT EXISTS todo(ID INTEGER PRIMARY KEY ASC, todo TEXT, added_on DATETIME)", []);
        });
        //creo la tabla email
        db.transaction(function(tx) {
            tx.executeSql("CREATE TABLE IF NOT EXISTS email(ID INTEGER PRIMARY KEY ASC, email TEXT, added_on DATETIME)", []);
        });
    },

    addTodo: function(todoText) {
        var db = app.db;
        db.transaction(function(tx) {
            var addedOn = new Date();
            tx.executeSql("INSERT INTO todo(todo, added_on) VALUES (?,?)",
                          [todoText, addedOn],
                          app.onSuccess,
                          app.onError);
        });
    },
      
    addCatastrofe: function(idCText, nombreText, latitudText, longitudText, planRiesgoText, planEmergenciaText ) {
        var db = app.db;
        db.transaction(function(tx) {
            var addedOn = new Date();         
            tx.executeSql("INSERT INTO catastrofe(idC, nombre, latitud, longitud, planRiesgo, planEmergencia ) VALUES (?,?,?,?,?,?)",
                          [idCText, nombreText, latitudText, longitudText, planRiesgoText, planEmergenciaText],
                          app.onSuccess,
                          app.onError);
        });
    },

    onError: function(tx, e) {
        console.log("Error: " + e.message);
    },
      
    onSuccess: function(tx, r) {
        //app.refresh();
        console.log('lgh on success');
        //app.refreshCatastrofe();
    },
      
    deleteTodo: function(id) {
        var db = app.db;
        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM todo WHERE ID=?", [id],
                          app.onSuccess,
                          app.onError);
        });
    },

    deleteCatastrofe: function(id) {
        console.log('lgh en deleteCatastrofe');
        var db = app.db;
        db.transaction(function(tx) {
            //tx.executeSql("DELETE FROM catastrofe WHERE ID=?", [id],
            tx.executeSql("DELETE FROM catastrofe", [],
                          app.onSuccess,
                          app.onError);
        });
    },

    setAppCatastrofe: function(id){
        window.localStorage.setItem("appCatastrofe", id);
        app.refreshCatastrofe();
        console.log('lgh en setAppCatastrofe id: ' + id);
// nombres de los campos de la tabla: catastrofe        
//idC, nombre, latitud, longitud, planRiesgo, planEmergencia
        var setCatastrofeAtributos = function (tx, rs) {
            for (var i = 0; i < rs.rows.length; i++) {
                //console.log(rs.rows.item(i).planRiesgo);
                app.nombre = rs.rows.item(i).nombre;
                app.planRiesgo = rs.rows.item(i).planRiesgo;
                app.planEmergencia = rs.rows.item(i).planEmergencia;
                app.latitud = rs.rows.item(i).latitud;
                app.longitud= rs.rows.item(i).longitud;
                var hrefRiesgo =  app.planRiesgo;
                var hrefEmergencia =  app.planEmergencia;
                $("#planriesgo").attr("href", hrefRiesgo);
                $("#planemergencia").attr("href", hrefEmergencia);
                //"https://drive.google.com/viewerng/viewer?url=https://eva.fing.edu.uy/pluginfile.php/76828/mod_resource/content/3/Tutorial%2520Estudiantes.pdf";//app.planRiesgo;
                //$("#planriesgo").attr("href", app.planRiesgo);
                //$("#planriesgo").attr("href","https://drive.google.com/viewerng/viewer?url=https://eva.fing.edu.uy/pluginfile.php/76828/mod_resource/content/3/Tutorial%2520Estudiantes.pdf");
                //$("#planriesgo").attr("href", hrefRiesgo);
                //$("#planriesgo").prop("href", rs.rows.item(i).planRiesgo);
            }
        }

        var db = app.db;

        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM catastrofe WHERE ID=?", [id],
                          setCatastrofeAtributos,
                          app.onError);
        });
        
    },

    refresh: function() {
        var renderTodo = function (row) {
            var seleccionada = window.localStorage.getItem("appCatastrofe");
            //alert('seleccionada ' + seleccionada);
            var seleccionada ="todo_"+seleccionada;
            var comparo  = "todo_"+row.ID
            //alert('seleccionada '+ seleccionada +' comparo '+ comparo);
            if(seleccionada == comparo){
                return "<li"+" id=todo_"+row.ID+" onclick='app.setAppCatastrofe(" + row.ID + ");' >" + "<div class='todo-check'></div>" + 'row id: '+ row.ID + row.todo + "<a class='button delete' href='javascript:void(0);'  onclick='app.deleteTodo(" + row.ID + ");'><p class='todo-delete'></p></a>" + "<div class='clear'></div>" + "</li>";
            }
            else{
                return "<li"+" id=todo_"+row.ID+" onclick='app.setAppCatastrofe(" + row.ID + ");'>" + '   row id: '+ row.ID + row.todo + "<a class='button delete' href='javascript:void(0);'  onclick='app.deleteTodo(" + row.ID + ");'><p class='todo-delete'></p></a>" + "<div class='clear'></div>" + "</li>";   
            }

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
    },

    refreshCatastrofe: function() {
        var renderCatastrofe = function (row) {
            var seleccionada = window.localStorage.getItem("appCatastrofe");
            //console.log('seleccionada ' + seleccionada);
            var seleccionada ="cat_"+seleccionada;
            var comparo  = "cat_"+row.ID
            console.log('seleccionada '+ seleccionada +' comparo '+ comparo);
            if(seleccionada == comparo){
                return "<li"+" id=cat_"+row.ID+" onclick='app.setAppCatastrofe(" + row.ID + ");' >" + "<div class='todo-check'></div>" + 'row id: '+ row.ID + row.nombre + "<a class='button delete' href='javascript:void(0);'  onclick='app.deleteTodo(" + row.ID + ");'><p class='todo-delete'></p></a>" + "<div class='clear'></div>" + "</li>";
            }
            else{
                return "<li"+" id=cat_"+row.ID+" onclick='app.setAppCatastrofe(" + row.ID + ");'>" + '   row id: '+ row.ID + row.nombre + "<a class='button delete' href='javascript:void(0);'  onclick='app.deleteTodo(" + row.ID + ");'><p class='todo-delete'></p></a>" + "<div class='clear'></div>" + "</li>";   
            }

        }
        
        var render = function (tx, rs) {
            //alert('en render');
            //lengthRows = rs.rows.length
            //console.log('lgh en resfreshCatastrofe lengthRows '+lengthRows);
            var rowOutput = "";
            var catastrofeItems = document.getElementById("catastrofeItems");
            for (var i = 0; i < rs.rows.length; i++) {
                rowOutput += renderCatastrofe(rs.rows.item(i));
                //alert('q paso ' + rs.rows.length );
                console.log('lgh en resfreshCatastrofe rowOutput '+rowOutput);
            }
          //todoItems.innerHTML = rowOutput;
          catastrofeItems.innerHTML = rowOutput;
          
            
        }
        
        var db = app.db;
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM catastrofe", [], 
                          render, 
                          app.onError);
        });
    },

    checkSesion: function(callback){
     var mostrarEmail = function (tx, rs) {
        var retorno = null;
            for (var i = 0; i < rs.rows.length; i++) {
                app.email = rs.rows.item(i).email;
                retorno = rs.rows.item(i).email;
            }

        console.log('lgh retorno: ' + retorno);

        app.email = retorno;
        
        app.lanzarSesion(); //chquea si hay sesion inicia o no y en base a eso renderiza
        //document.getElementById("sesion").value(retorno);
        
        // mando las acciones a los distintos botones o divs
        app.mandarJquery();

        }
        
        var db = app.db;
        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM email", [], 
                          mostrarEmail, 
                          app.onError);
        });
    },

    cerrarSesion: function(){
        var salir = function(){
            navigator.app.exitApp();
        }

        app.email = null;
        window.localStorage.setItem("idUsuario", 0);
        
        var db = app.db;
        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM email", [], 
                          salir,
                          app.onError);
        });
    },

    // result contains any message sent from the plugin call
    successHandler: function(result) {
        //alert('Callback Success! Result = '+result)
        console.log('lgh en successHandler(registrando para push): Callback Success! Result = '+result )
    },
    errorHandler: function(error) {
        //alert(error);
        bootbox.alert(error, function() {
                            console.log('lgh error: ' + error);
        });
    },
    onNotificationGCM: function(e) {
        switch( e.event )
        {
            case 'registered':
                if ( e.regid.length > 0 )
                {
                    //asigno a la app el regid obtenido
                    app.regid = e.regid;
                    
                    console.log("lgh Regid " + e.regid);
                    console.log('lgh en registered app.email: ' + app.email);
                    console.log('lgh en registered app.regid: ' + app.regid);
                    // LLAMA AL SERVICIO DE UPDATE, put DE USUARIO 
                    // PARA SETEARLE EL REGID
                    if(app.email !== null) {
                        //alert(' email not null');
                        setTimeout(function(){ app.putUsuario(app.regid); }, 3000);
                        //app.putUsuario(app.regid);
                    }
                    else{
                        //alert(' email null');   
                    }
                }
            break;
 
            case 'message':
                // this is the actual push notification. its format depends on the data model from the push server
                //alert('message = '+e.message+' msgcnt = '+e.msgcnt);
                bootbox.alert('message = '+e.message+' msgcnt = '+e.msgcnt, function() {
                            console.log('lgh message = '+e.message+' msgcnt = '+e.msgcnt);
                });
                app.persistirLocal();

            break;
 
            case 'error':
                //alert('GCM error = '+e.msg);
                bootbox.alert('GCM error = '+e.msg, function() {
                            console.log('lgh GCM error = '+e.msg);
                });

            break;
 
            default:
                //alert('An unknown GCM event has occurred');
                bootbox.alert('Un error desconocido de GCM Google Cloud Message ha ocurrido ', function() {
                            console.log('lgh Un error desconocido de GCM Google Cloud Message ha ocurrido');
                });

                break;
        }
    },

    addCatastrofeLocation: function(){
        var map = app.map;
        var lat = app.latitud;
        var lon = app.longitud;
        var boundLat = null;
        var boundLon = null;

        var rescatistaLat = window.localStorage.getItem("rescatistaLat");
        var rescatistaLon =window.localStorage.getItem("rescatistaLon");

        var circle = L.circle([lat, lon], 500, {
                        color: 'red',
                        fillColor: '#f03',
                        fillOpacity: 0.5
                    }).addTo(map).bindPopup(app.nombre).openPopup();
        
        
        //var catastrofeLat = -33.928769;
        //var catastrofeLon = -55.161164;
        // test bounds
        // map.fitBounds([
        //     [catastrofeLat, catastrofeLon],
        //     [-31.774, -53.1231]
        // ]);

        app.dom('addCatastrofeLocation');
    },

    // geolocation plugin
    // http://stackoverflow.com/questions/23383750/phonegap-geolocation-sometimes-not-working-on-android
    getLocation: function(){
        var onSuccess = function(position) { // es el codigo de la funcion leaflet
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            window.localStorage.setItem("rescatistaLat", lat);
            window.localStorage.setItem("rescatistaLon", lon);

            var map = L.map('map').setView([lat, lon], 13);
//             add an OpenStreetMap tile layer --->>> DEJO DE ANDAR!!!!
//             L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
//                 attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
//             }).addTo(map);
// nuevlo tileLayer
            
            L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', 
            {    maxZoom: 18  }).addTo(map);

            // add a marker in the given location, attach some popup content to it and open the popup
            L.marker([lat, lon]).addTo(map)
                .bindPopup('Posición Aproximada') 
                .openPopup();

            app.map = map;

        }

        // onError Callback receives a PositionError object
        function onError(error) {
            //console.log('en getLocation code: '    + error.code    + '\n' +
            alert('en getLocation code: '    + error.code    + '\n' +
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
            // add an OpenStreetMap tile layer --->>> DEJO DE ANDAR!!!!
            // L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            //     attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            // }).addTo(map);
// nuevlo tileLayer            
            L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', 
            {    maxZoom: 18  }).addTo(map);

            // add a marker in the given location, attach some popup content to it and open the popup
            L.marker([lat, lon]).addTo(map)
                .bindPopup('Posición Aproximada') 
                .openPopup();

        }
        
        // onError Callback receives a PositionError object
        //
        function onError(error) {
            alert('on error seeposition code: '    + error.code    + '\n' +
                  'message: ' + error.message + '\n');
        }

        // Options: throw an error if no update is received every 30 seconds.
        //
        var watchID = navigator.geolocation.watchPosition(onSuccess, onError, { timeout: 30000 });
    },

    addSesion: function(email) {
        var db = app.db;
        //delete de tabla email
        db.transaction(function(tx) {
            tx.executeSql("DELETE FROM email ", [],
                          app.onSuccess,
                          app.onError);
        });
        //creo la tabla email
        db.transaction(function(tx) {
            tx.executeSql("CREATE TABLE IF NOT EXISTS email(ID INTEGER PRIMARY KEY ASC, email TEXT, added_on DATETIME)", []);
        });
        //insert registro en email
        db.transaction(function(tx) {
            var addedOn = new Date();
            tx.executeSql("INSERT INTO email(email, added_on) VALUES (?,?)",
                          [email, addedOn],
                          app.onSuccess,
                          app.onError);
        });

        var mostrarEmail = function (tx, rs) {
            for (var i = 0; i < rs.rows.length; i++) {
                //alert('el select en i: '+ i +' '+ rs.rows.item(i).email);
            }
        }

        db.transaction(function(tx) {
            tx.executeSql("SELECT * FROM email", [], 
                          mostrarEmail, 
                          app.onError);
        });

    },
    noCerrar: function(noCerrarSesion, email){
        //alert('testf , noCerrarSesion: ' + noCerrarSesion);
        if(noCerrarSesion===1){
            //alert('clavo registro');
            app.addSesion(email);
        }
    },

    dom: function(evento){
        if(evento==="login"){
            $('#load').addClass("delete"); // no la voy a precisar mas
            //$("#formIngresar").addClass("derecha");
            //$("#formIngresar").addClass("novisible");
            $('#map').removeClass("mapFondo");
            $('#menuboton').addClass("visible");
            //alert('app.regid ' + app.regid);
            
            if(app.regid !== null) {
                app.putUsuarioEnLogin();
            }
            else{
                console.log('lgh en dom , evento login y app.regid === null');
            }

        }
        
        if(evento==="catastrofes"){
            $('#listCatastrofe').addClass("visible");
            $('#menuoffcanvas').removeClass("in canvas-slid");
            //document.addEventListener("backbutton", function(){$('#listCatastrofe').removeClass("visible");}, false);
        }

        if(evento==="addCatastrofeLocation"){
            $('#menuoffcanvas').removeClass("in canvas-slid");
            $('#listCatastrofe').removeClass("visible");                
            //document.addEventListener("backbutton", function(){$('#listCatastrofe').removeClass("visible");}, false);
        }


    },

    login: function(){
        var idUsuario = 0;
        // valores input
        var email = document.getElementById("inputEmail").value;
        var password = document.getElementById("inputPassword").value;
        var noCerrarSesion = $( "input:checked" ).length; // si n = 1 , entonces no cerrar sesion
        // llamo al servicio 

        //var url = "http://192.168.1.41:8080/catastrophes-system-web/rest/ServicesRescatista/login";
        //var url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/ServicesRescatista/login";
        var url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/usuarios/login/"+email+"/"+password;
        console.log('lgh la url para login ' + url);
        var form = $("#formLogin").serialize();
        var idUsuario;
        $.ajax({ 
            type: "GET",
            //data: form,
            url: url,
            contentType: "application/json",//; charset=utf-8",
            dataType: "json",
            crossDomain: true,
            //cache: false,
            timeout: 20 * 1000,
            success:function(response)
            {   
                //idUsuario = response.login; VERSION VIEJA
                idUsuario = response.id;
                console.log('lgh login success ' + JSON.stringify(response)) ;

                if(idUsuario==0) {
                    //alert('Credenciales invalidas, intente de nuevo');
                    bootbox.alert('Credenciales invalidas, intente de nuevo', function() {
                            console.log('lgh En login. Se produjo un error: if(idUsuario==0) ');
                    });
                }
                else{
                    if(idUsuario<0){
                        //alert('Se produjo un error, intente de nuevo');
                        bootbox.alert('Se produjo un error, intente de nuevo', function() {
                            console.log('lgh En login. Se produjo un error: if(idUsuario<0){ ');
                        });
                    }
                    else{
                        app.email = email;
                        app.noCerrar(noCerrarSesion,email);
                        window.localStorage.setItem("idUsuario", idUsuario);
                        //window.localStorage.setItem("password", password);
                        var evento='login';
                        app.dom(evento);
                    }
                }
                // if(!timeOutOccured){
                //     clearTimeout(timeOutInteger);
                //     onlineFunction();
                // }
            },
            error: function(xhr, textStatus, errorThrown) {
                bootbox.alert('Ingrese email y password para ingresar ' , function() {
                            console.log('lgh En Login, se produjo error: ingrese email y password ' + errorThrown);
                });
                //alert('Se produjo un error: ' + errorThrown );
                //  + ( errorThrown ? errorThrown : 
                //xhr.status );
            }
        });

 
    },

    fabrica: function(){ //data puedo pasarlo por parametro
        var setCatastrofeYRefresh = function(){
            // tomo la ultima catastrofe elegida del localstorage
            var seleccionada = window.localStorage.getItem("appCatastrofe");
            console.log('lgh en setCatastrofeYRefresh ');
            app.setAppCatastrofe(seleccionada); 
            app.refreshCatastrofe();
            /*  para el id
                tomarlo del localstorage
                sino hay traer de la base
                sino alertar */
        }

        var url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/catastrofes/";
        $.ajax({ 
            type: "GET",
            //data: form,
            url: url,
            contentType: "application/json",//; charset=utf-8",
            dataType: "json",
            crossDomain: true,
            //cache: false,
            timeout: 20 * 1000,
            success:function(data)
            {   
                app.deleteCatastrofe();

                $.each(data, function(i, obj) {
                  //use obj.id and obj.name here, for example:
                  console.log('lgh llamando addCatastrofe para '+obj.nombre);
                  app.addCatastrofe(obj.idC, obj.nombre, obj.latitud, obj.longitud, obj.planRiesgo, obj.planEmergencia );
                });
                console.log('lgh en success');
                //setCatastrofeYRefresh();
                var seleccionada = window.localStorage.getItem("appCatastrofe");
                console.log('lgh en setCatastrofeYRefresh ');
                //app.setAppCatastrofe(seleccionada); 
                app.setAppCatastrofe(1); 
                app.refreshCatastrofe();
            },
            error: function(xhr, textStatus, errorThrown) {
                //setCatastrofeYRefresh();
                var seleccionada = window.localStorage.getItem("appCatastrofe");
                console.log('lgh en setCatastrofeYRefresh ');
                //app.setAppCatastrofe(seleccionada); 
                app.setAppCatastrofe(1); 
                app.refreshCatastrofe();
                //alert('Se produjo un error al actualizar catastrofes: ' + errorThrown );
                //bootbox.alert('Se produjo un error al actualizar catastrofes: ' + errorThrown, function() {
                //     console.log("lgh error al actualizar catastrofes ");
                //});
            }
        });

    },

    putUsuarioEnLogin: function(){
        app.getUsuarioForUpdate();
        setTimeout(function(){ app.putUsuario(app.regid); }, 3000);
    },

    //getUsuarioForUpdate: function(id, pregId){
    getUsuarioForUpdate: function(){
        var id = window.localStorage.getItem("idUsuario");
        if(app.email === null) { console.log('lgh en getUsuarioForUpdate (app.email === null) ');}
        else{ 
            console.log('lgh en getUsuarioForUpdate id: ' + id);
            var usuarioJson = {};
            var url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/usuarios/"+id;
            
            $.ajax({ 
                type: "GET",
                //data: form,
                url: url,
                contentType: "application/json",//; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                //cache: false,
                timeout: 20 * 1000,
                success:function(response)
                {   
                    usuarioJson = response;
                    //usuarioJson.regId = pregId;
                    app.usuario = usuarioJson;
                    window.localStorage.setItem("appUsuario", usuarioJson);

                    console.log('lgh en get rest/usuarios/id , usuario: ' + JSON.stringify(usuarioJson));                    
                },
                error: function(xhr, textStatus, errorThrown) {
                    bootbox.alert('Se produjo un error al traer usuario: ' + errorThrown, function() {
                                console.log('lgh en get rest/usuarios/"+id, se produjo error: ' + errorThrown);
                    });
                }
            });
        }
    },

    putUsuario: function(pRegId){        
        app.usuario.regid = pRegId;
        console.log('lgh en put rest/usuarios/id , app.usuario: ' + JSON.stringify(app.usuario) );
        
        url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/usuarios/"+ app.usuario.id;
        
        $.ajax({ 
            type: "PUT",
            data: JSON.stringify({ 
                id: app.usuario.id ,
                version: app.usuario.version ,
                user: app.usuario.user ,
                password: app.usuario.password,
                rol: app.usuario.rol,
                nombre: app.usuario.nombre,
                apellido: app.usuario.test,
                nacimiento: app.usuario.nacimiento,
                telefono: app.usuario.telefono ,
                email: app.usuario.email,
                regId: app.usuario.regid,
                sexo: app.usuario.sexo
            }),
            url: url,
            contentType: "application/json; charset=utf-8",
            crossDomain: true,
            timeout: 20 * 1000,
            success:function()
            {   
                alert('anduvo');
                // bootbox.alert('Se actualizo el usuario con regid con exito: ' + response, function() {
                //     console.log('lgh en POST rest/usuarios/" , data: ' + data);
                // });
            },
            error: function(xhr, textStatus, errorThrown) {
                bootbox.alert('Se produjo un error en put usuario: ' + errorThrown, function() {
                            console.log('lgh en put rest/usuarios/"+id, se produjo error: ' + errorThrown);
                });
            }
        });
        
    },

    backButton: function(){
        if(app.backButtonAccion =='salir')
            navigator.app.exitApp();
        if(app.backButtonAccion =='quitarLoading')
            $("#loading").removeClass("alfrente");

    },

    mandarJquery: function(){
       
        $(document).ready(function() {
            // menu 
            //$('.navmenu').offcanvas()
            //$("#planriesgo").attr("href","https://drive.google.com/viewerng/viewer?url=https://eva.fing.edu.uy/pluginfile.php/76828/mod_resource/content/3/Tutorial%2520Estudiantes.pdf");
            // $("#planriesgo").click(function(){
            //     alert(app.planRiesgo);
            // });
            //alert('ready');
            
            

            $("#aCatastrofes").click(function(){
                app.dom('catastrofes');
            });
            
            // test llamada
            $( "#aRetorno").click(function(){
                //alert('aretorno');
                // $("#catastrofeItems li").each(function() {
                //  :8080/http://catastrophes-system-web/rest/ServicesUsuario/get   alert('clickeaste a li + this ' + $(this).attr('id') );
                // });
                //var url = "http://192.168.1.41:8080/catastrophes-system-web/rest/ServicesUsuario/get";
                var url = "http://"+app.hostservidor+":8080/catastrophes-system-web/rest/ServicesUsuario/get";
                //var url = "http://172.16.108.196:8080/catastrophes-system-web/rest/ServicesUsuario/get";
                $.ajax({ 
                    type: "GET",
                    //data: "{}",
                    url: url,
                    contentType: "application/json",//; charset=utf-8",
                    dataType: "json",
                    crossDomain: true,
                    //cache: false,
                    timeout: 20 * 1000,
                    success:function(response)
                    {   
                        console.log('lgh success ' + response) ;
                        alert('response: ' + response.llave);
                        // if(!timeOutOccured){
                        //     clearTimeout(timeOutInteger);
                        //     onlineFunction();
                        // }
                    },
                     error: function(xhr, textStatus, errorThrown) {
                        alert('An error occurred! ' + errorThrown );
                          //  + ( errorThrown ? errorThrown : 
                        //xhr.status );
                    }
                });
            });
            
            // $("#todoItems li").each(function() {
            //     var selector = $(this).attr('id');
            //     //$(this).attr('id').click(function(){
            //     $("#"+selector).click(function(){
            //         app.setAppCatastrofe(selector);
            //     });
            // });
            
               
            

            // $("li").click(function(){
            //     alert('clickeaste a li + this ' + $(this).attr('id') );
            // });

            //$("#formIngresar").addClass("pageLogin");
            $("#loading").addClass("novisible");
            $("#deviceready").addClass("novisible");
            $("#viewrest").addClass("novisible");
            $("#btn2").addClass("novisible");
            
            $( ".menu").addClass("novisible");
            //var viewrest = document.getElementById("viewrest");
            //viewrest.style.display = "none";
            function mandarderecha(){
                $("#sqlite").addClass("derecha");
            }

            // menu / acciones
            $( "#aSalir").click(function(){
                navigator.app.exitApp();
            });
            $( "#aCerrarSesion").click(function(){
                app.cerrarSesion();
                //navigator.app.exitApp();
            });

            /*
            $("#planriesgo").click(function(){
                $("#loading").addClass("alfrente");
                app.backButtonAccion('quitarLoading');
            });

            $("#planemergencia").click(function(){
                $("#loading").addClass("alfrente");
                app.backButtonAccion('quitarLoading');
            });
            */                


            // login de rescatista
            $( "#login").click(function(event){
                event.preventDefault();
                app.login();
                
            });
            

            $( "#btn2").click(function(){
                
                $("#map").addClass("derecha");

            });


            $( ".menu").click(function(){
                mandarderecha();
            });// end - $( "btn1").click(function(){
            
            $( ".menu1").click(function(){
                alert('boton 2');

            });// end - $( "btn2").click(function(){


            $( "#callrest").click(function(){
                console.log('lgh 1 entre');
                //var idUsuario = document.getElementById("inputEmail3").value;
                //var password = document.getElementById("inputPassword3").value;
                //var idUsuario = $("#inputEmail3").val();
                
                //var urljson = "http://192.168.43.166:8080/catastrophes-system-web/rest/ServicesUsuario/alta/"+idUsuario+"/"+password;
                //var urljson = "http://192.168.43.166:8080/catastrophes-system-web/rest/ServicesUsuario/alta/cucu/12";
                var urljson = "http://rest-service.guides.spring.io/greeting";
                //var urljson = "http://192.168.1.43:8080/catastrophes-system-web/rest/ServicesUsuario/alta/"+idUsuario+"/"+password;

                //var urljson = "http://192.168.1.43:8080/catastrophes-system-web/rest/ServicesUsuario/get";
                $.ajax({
                    url: "http://192.168.1.43:8080/catastrophes-system-web/rest/ServicesUsuario/get"
                }).then(function(data) {
                    console.log('lgh en llamoRest dentro del .then');
                    alert(data);
                });
                /*
                $.getJSON(urljson , function( data ) {
                  //alert("user: " + data.User + "password: "+ data.password);
                  alert(data);
                  console.log("lgh 2" + data);
                });
                
                console.log("lgh 3" + data);
                
                $.postJSON(urljson , function( data ) {
                  //alert("user: " + data.User + "password: "+ data.password);
                  alert(data);
                  console.log("lgh 2" + data);
                });
                console.log("lgh 3" + data);
                
                $.ajax({
                    url: urljson//""
                    }).then(function(data) {
                    console.log('lgh 2 data');
                    alert('lgh 3' + data);
                });
                */



                //$(".content").addClass(className)

            });// end - callrest

        });// end - $(document).ready
    
    }// end mandarJquery
    
    
};

app.hostservidor = "192.168.1.42";
//app.hostservidor = "172.16.102.205";
//app.hostservidor = "192.168.1.41";
//app.hostservidor = "172.16.100.4";
//app.hostservidor = "172.16.100.4";

app.map = null; 

app.email = null; // sesion

app.usuario = null;

app.regid = null;

app.nombre = null;
app.planRiesgo = null;
app.planEmergencia = null;
app.latitud = null;
app.longitud= null;

app.backButtonAccion= null;

// del ejemplo sqlite
app.db = null;
// function init() {
//     navigator.splashscreen.hide();
//     app.openDb();
//     app.createTable();
//     app.refresh();
// }      
function addTodo() {
    var todo = document.getElementById("todo");
    app.addTodo(todo.value);
    todo.value = "";
}
//FIN del ejemplo sqlite


/*


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