$(document).ready(function() {
    $('#myForm').submit(function(event) {
        let eroare = "Campul/Campurile ";
        let ok = 1;
        event.preventDefault();

        const numeInput = $('#nume');
        const dataNasteriiInput = $('#data_nasterii');
        const varstaInput = $('#varsta');
        const emailInput = $('#email');

        const numePattern = /^[a-zA-Z]{3,}$/;
        if (!numePattern.test(numeInput.val().trim())) {
            numeInput.css('borderColor', 'red');
            ok = 0;
            eroare = eroare + "nume, ";
        } else {
            numeInput.css('borderColor', '');
        }

        if (dataNasteriiInput.val().trim() === '') {
            dataNasteriiInput.css('borderColor', 'red');
            eroare = eroare + "data nasterii, ";
            ok = 0;
        } else {
            dataNasteriiInput.css('borderColor', '');
        }

        const varstaInputValue = varstaInput.val().trim();
        if (varstaInputValue === '') {
            varstaInput.css('borderColor', 'red');
            eroare += "varsta, ";
            ok = 0;
        } else {
            const varstaNumber = parseInt(varstaInputValue);

            if (isNaN(varstaNumber) || varstaNumber < 1 || varstaNumber > 100) {
                varstaInput.css('borderColor', 'red');
                eroare += "varsta, ";
                ok = 0;
            } else {
                varstaInput.css('borderColor', '');
            }
        }

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(emailInput.val().trim())) {
            emailInput.css('borderColor', 'red');
            ok = 0;
            eroare = eroare + "email, ";
        } else {
            emailInput.css('borderColor', '');
        }

        if (ok) {
            alert("Datele sunt completate corect");
        } else {
            eroare = eroare + "nu sunt completate corect";
            alert(eroare);
        }
    });
});