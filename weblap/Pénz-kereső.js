<script>

function validateForm() {
   var username = document.getElementById('username').value;
   var password = document.getElementById('password').value;
      if (username == "") {
      alert("Username is required");
      return false;
    }
      if (password == "") {
         alert("Password is required");
         return false;
    }
</script>


   document.getElementById('nev').addEventListener('input', function (e) {
      var input = e.target.value;
      if (/[^a-zA-Z]/.test(input)) {
          e.target.value = input.replace(/[^a-zA-Z]/g, '');
      }
  });

  document.getElementById('email').addEventListener('input', function (e) {
      var input = e.target.value;
      if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input)) 
      {
         e.target.value = '';
      }
   });

   document.getElementById('jelszo').addEventListener('input', function (e) {
      var input = e.target.value;
      if (!/^[\S]{6,}$/.test(input)) {
         e.target.value = '';
      }
      });


   document.getElementById('havi_jövedelem').addEventListener('input', function (e) {
      var input = e.target.value;
      if (/[^0-9]/.test(input)) {
         e.target.value = input.replace(/[^0-9]/g, '');
      }
   });


   document.getElementById('real_estate').addEventListener('input', function (e) {
      var input = e.target.value;
      if (/[^0-9]/.test(input)) {
         e.target.value = input.replace(/[^0-9]/g, '');
   }
   });