
    const form = document.querySelector('form');
    const numeInput = document.getElementById('nume');
    const dataNasteriiInput = document.getElementById('data_nasterii');
    const varsta = document.getElementById('varsta');
    const emailInput = document.getElementById('email');


    form.addEventListener('submit', function(event) {
        let eroare="Campul/Campurile ";
        let ok=1;
        event.preventDefault();


        const numePattern = /^[a-zA-Z]{3,}$/;
        if (!numePattern.test(numeInput.value.trim())) {
            numeInput.style.borderColor = 'red';
            ok=0;
            eroare = eroare+ "nume, "
        } else {
            numeInput.style.borderColor = '';
        }



        if (dataNasteriiInput.value.trim() === '') {
            dataNasteriiInput.style.borderColor = 'red';
            eroare= eroare+ "data nasterii, "
            ok=0;
        } else {
            dataNasteriiInput.style.borderColor = '';
        }


        const varstaInputValue = varsta.value.trim();
        if (varstaInputValue === '') {
            varsta.style.borderColor = 'red';
            eroare += "varsta, ";
            ok = 0;
        } else {
            const varstaNumber = parseInt(varstaInputValue);


            if (isNaN(varstaNumber) || varstaNumber < 1 || varstaNumber > 100) {
                varsta.style.borderColor = 'red';
                eroare += "varsta, ";
                ok = 0;
            } else {
                varsta.style.borderColor = '';
            }
        }

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(emailInput.value.trim())) {
            emailInput.style.borderColor = 'red';
            ok=0;
            eroare=eroare+ "email, "
        } else {
            emailInput.style.borderColor = '';
        }

        if (ok) {
            alert("Datele sunt completate corect");
        } else {
            eroare= eroare+ "nu sunt completate corect";

            alert(eroare);
        }
    });