bits 32
global start

extern exit, fscanf, printf, fopen, fclose, verificare
import exit msvcrt.dll
import fscanf msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data public
    nume_fisier db "numere.txt", 0
    tip_deschidere db "r", 0
    descriptor dd 0
    format db "%u", 0
    format_afis db "%u ", 0
    eroare db "Fisierul nu a fost deschis cu succes.", 0
    afisare_impar db "Numerele impare: ", 0
    afisare_par db "Numerele pare: ", 0
    endl db 13
    numar dd 0
    restul dw 0
    len_n dd 0
    len_p dd 0
    n times 100 dd 0
    p times 100 dd 0
    
; 27. Se citesc din fisierul numere.txt mai multe numere (pare si impare).
; Sa se creeze 2 siruri rezultat N si P astfel: N - doar numere impare si P - doar numere pare.
; Afisati cele 2 siruri rezultate pe ecran.

segment code use32 class=code public
    start:
        ;fopen(nume_fisier, tip_deschidere)
        push dword tip_deschidere
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        ;verificam daca s a deschis fisierul
        cmp eax, 0
        je mesaj_eroare
        mov [descriptor], eax
        
        mov esi, n
        mov edi, p
        ;citim datele din fisier folosindu ne de fscanf(descriptor, format, n)
        citire:
        push dword numar
        push dword format
        push dword [descriptor]
        call [fscanf]
        add esp, 4*3 
        cmp eax, -1
        push eax
        je next
        push dword restul
        push dword [numar]
        call verificare
        ;in eax avem 0 sau 1, in functie de paritatea numarului din n
        cmp word [restul], 1
        jne numar_par
        ;numarul este impar, il adaugam in lista corespunzatoare
        mov ecx, [numar]
        mov [esi], ecx
        add esi, 4
        add dword [len_n], 1
        jmp next
        ;numarul este par, il adaugam in lista corespunzatoare
        numar_par:
        mov ecx, [numar]
        mov [edi], ecx
        add edi, 4
        add dword [len_p], 1
        next:
        pop eax
        cmp eax, -1  
        jne citire
        
        
        ;afisam numerele impare pe ecran folosindu ne de printf(format, var)
        push dword afisare_impar
        call [printf]
        add esp, 4
        mov ecx, [len_n]
        jecxz afisare_pare
        mov esi, n
        repetare1:
        push ecx
        push dword [esi]
        push dword format_afis
        call [printf]
        add esp, 4*2
        add esi, 4
        pop ecx
        loop repetare1
        
        push dword endl
        call [printf]
        add esp, 4
        
        ;afisam numerele pare pe ecran folosindu ne de printf(format, var)
        afisare_pare:
        push dword afisare_par
        call [printf]
        add esp, 4
        mov ecx, [len_p]
        jecxz final
        mov esi, p
        repetare2:
        push ecx
        push dword [esi]
        push dword format_afis
        call [printf]
        add esp, 4*2
        add esi, 4
        pop ecx
        loop repetare2
        
        jmp final
    
        mesaj_eroare:
        ;printf(eroare)
        push dword eroare
        call [printf]
        add esp, 4
        
        final:
        push dword [descriptor]
        call [fclose]
        add esp, 4
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
