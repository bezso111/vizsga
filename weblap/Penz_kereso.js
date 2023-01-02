
            function registeruserfunc(){
                var baseurl = "http://localhost:8080";
                var funcurl = "/registerUser";
                var hiba = "";
                var email = document.getElementById("email").value;
                var password = document.getElementById("password").value;
                var name = document.getElementById("name").value;
                var url = baseurl+funcurl
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
                                document.getElementById("results").value = "Sikeres Regisztrácíó!";
                            } else if (converted.errorcode===1062 ) {
                                document.getElementById("results").value = "Ez az email cím már szerepelt";
                            } else
                            { 
                                document.getElementById("results").value = converted.content;
                            }
                        } else {
                            document.getElementById("results").value = "Hibás ajax hívás!"
                        }
                    };			
                    xmlHttp.addEventListener("error", () => { document.getElementById("results").value = "Hiás szerver elérés!" })
                    xmlHttp.open( "POST", url, true); // false for synchronous request  
                    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    xmlHttp.send( "email="+email+"&password="+password+"&name="+name );
                } else {
                    document.getElementById("results").value = hiba
                }
            }