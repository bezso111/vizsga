
function registeruserfunc() {
	var baseurl = "http://localhost:8080";
	var funcurl = "/registerUser";
	var hiba = "";
	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;
	var name = document.getElementById("name").value;
	var url = baseurl+funcurl
	console.log("proba")
	var re = /\S+@\S+\.\S+/;
	if (!re.test(email)) { hiba=hiba+"Hibás email cím! "}
	if (password.length <8) {hiba=hiba+"A jelszó legalább 8 karakter! "}
	if (name === "") {hiba=hiba+"Nincs név! "}
	if (hiba === "") {
		console.log("The url: " + url+"email="+email+"&password="+password+"&name="+name)
		var xmlHttp = new XMLHttpRequest();						
		xmlHttp.onload = function () {
			console.log("proba");
			if(xmlHttp.readyState === XMLHttpRequest.DONE && xmlHttp.status === 200) {
				console.log(xmlHttp.responseText);
				var converted = JSON.parse(xmlHttp.responseText)
				console.log(converted)				
				if (converted.errorcode===0) {
					document.getElementById("results").innerHTML = "Sikeres Regisztrácíó!";
					document.getElementById("results").style.backgroundColor="#00ff00";
				} else if (converted.errorcode===1062 ) {
					document.getElementById("results").innerHTML = "Ez az email cím már szerepelt";
					document.getElementById("results").style.backgroundColor="#ff0000"; 
				} else
				{ 
					document.getElementById("results").innerHTML = converted.content;
					document.getElementById("results").style.backgroundColor="#ff0000" ;
				}
			} else {
				document.getElementById("results").innerHTML = "Hibás ajax hívás!";
				document.getElementById("results").style.backgroundColor="#ff0000" ;
			}
		};			
		xmlHttp.addEventListener("error", () => { document.getElementById("results").value = "Hiás szerver elérés!" })
		xmlHttp.open( "POST", url, true); // false for synchronous request  
		xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlHttp.send( "email="+email+"&password="+password+"&name="+name );
	} else {
		document.getElementById("results").innerHTML = hiba;
		document.getElementById("results").style.backgroundColor="#ff0000" ;
	}
}

function login() {
	var baseurl = "http://localhost:8080";
	var funcurl = "/loginUser";
	var hiba = "";
	var user = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var url = baseurl+funcurl
	console.log("The url: " + url+"    login="+user+"&password="+password)
	var xmlHttp = new XMLHttpRequest();						
	xmlHttp.onload = function () {
		console.log("proba");
		if(xmlHttp.readyState === XMLHttpRequest.DONE && xmlHttp.status === 200) {
			console.log(xmlHttp.responseText);
			var converted = JSON.parse(xmlHttp.responseText)
			console.log(converted)				
			if (converted.errorcode===0 & converted.azon>0 ) {
				// document.getElementById("results").innerHTML = "Üdvözlöm "+converted.name+"!";
				sessionStorage.setItem("id",converted.azon);
				sessionStorage.setItem("name",converted.name);
				sessionStorage.setItem("email",user);
				window.location.href = "Penz_kereso.html";
			} else { 
				document.getElementById("results").innerHTML = converted.name;
				document.getElementById("results").style.backgroundColor="#ff0000" 
				sessionStorage.removeItem("id");
				sessionStorage.removeItem("name");
				sessionStorage.removeItem("email");
			}
		} else {
			document.getElementById("results").innerHTML = "Hibás ajax hívás!"
			document.getElementById("results").style.backgroundColor="#ff0000" 
		}
	};			
	xmlHttp.addEventListener("error", () => { document.getElementById("results").innerHTML = "Hiás szerver elérés!" })
	xmlHttp.open( "POST", url, true); // false for synchronous request  
	xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlHttp.send( "user="+user+"&password="+password );	
}

function kilepes() {				
	sessionStorage.removeItem("id");
	sessionStorage.removeItem("name");
	sessionStorage.removeItem("email");
	getsession();
}


function getsession() {
	if ( sessionStorage.getItem("name") ) {
		// document.getElementById("login").innerHTML = "Üdvözlöm "+sessionStorage.getItem("name")+"!";
		document.getElementById("belepes").innerHTML = "<a class=\"nav-link\" href=\"javascript:kilepes();\">Kilépés ("+sessionStorage.getItem("name")+")</a>";
		try {
			document.getElementById("bekuldes").hidden = false;
		}
		catch (error) {}
	} else {
		// document.getElementById("login").innerHTML = "Nincs senki bejelentkezve!";
		document.getElementById("belepes").innerHTML = "<a class=\"nav-link\" href=\"belepes.html\">Belépés</a>";
		try {
			document.getElementById("bekuldes").hidden = true;
		}
		catch (error) {}
	}
}

function szamolVegosszeget() {
	var baseurl = "http://localhost:8080";
	var funcurl = "/kamat";
	var hiba = "";
	var url = baseurl+funcurl
	console.log("The url: " + url)
	var xmlHttp = new XMLHttpRequest();						
	xmlHttp.onload = function () {
		console.log("proba");
		if(xmlHttp.readyState === XMLHttpRequest.DONE && xmlHttp.status === 200) {
			console.log(xmlHttp.responseText);
			var converted = JSON.parse(xmlHttp.responseText)
			console.log(converted)				
			if (converted.errorcode===0 & converted.kamat>0 ) {
				var kamatmero = converted.kamat;
				var osszeg = parseInt(document.querySelector('input[name="amount"]:checked').value);
				var futamido = parseInt(document.querySelector('input[name="futamido"]:checked').value);
				// A kamat számítása
				var kamat = osszeg * kamatmero * futamido /1200;			
				// A végösszeg számítása
				var vegosszeg = osszeg + kamat;
				console.log("kamat:"+kamat+" osszeg:"+osszeg)
				document.getElementById("vegosszeg").innerHTML=vegosszeg;
			} else { 
				document.getElementById("vegosszeg").innerHTML = converted.name;
			}
		} else {
			document.getElementById("vegosszeg").innerHTML = "Hibás ajax hívás!"
		}
	};			
	xmlHttp.addEventListener("error", () => { document.getElementById("vegosszeg").innerHTML = "Hibás szerver elérés!" })
	xmlHttp.open( "GET", url, true); // false for synchronous request  
	xmlHttp.send( );	
}

  function szamolVegosszeget2() {
    // A formában megadott adatok lekérése
    var osszegInput = document.querySelector('input[name="amount"]:checked');
    var futamidoInput = document.querySelector('input[name="futamido"]:checked');
  
    if (osszegInput && futamidoInput) {
      var osszeg = osszegInput.value;
      var futamido = futamidoInput.value;
  
      // Kérjük le a valós idejű kamatméréseket egy másik webhelyről az Ajax hívások segítségével
      var xhttp = new XMLHttpRequest();
      xhttp.open("GET", "https://www.mnb.hu/web/fooldal", true);
      xhttp.send();
  
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          // A kapott adatok feldolgozása
          var kamatmero = JSON.parse(this.responseText);
		  console.log(this.responseText);
		  
          // A kamat számítása
          var kamat = osszeg * kamatmero * futamido;
  
          // A végösszeg számítása
          var vegosszeg = osszeg + kamat;
  
          // A végösszeg megjelenítése az elembe
          document.getElementById("vegosszeg").textContent = vegosszeg;
        } else {
          document.getElementById("vegosszeg").textContent = "Nem adtál meg minden szükséges adatot!";
        }
      };
    }
  }
  