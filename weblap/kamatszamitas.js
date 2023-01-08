function lekerdezKamatmerot() {
    // Létrehozzuk az XMLHttpRequest objektumot
    var xhttp = new XMLHttpRequest();
  
    // Megadjuk a lekérdezés típusát és címét
    xhttp.open("GET", "https://www.mnb.hu/Jegybanki_alapkamat_alakulasa", true);
  
    // Lekérdezés elküldése
    xhttp.send();
  
    // A válasz fogadása
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        // A kapott adatok feldolgozása
        var kamatmero = JSON.parse(this.responseText);
      }
    };
  }

  function szamolVegosszeget() {
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
  