bits 32
global start

extern exit, fprintf, printf, fopen, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
    sir db "ANA NU ARE mere!!23", 0
    len equ $-sir
    d times len db 0
    mod_acces db 'w', 0
    nume_fisier db "sir.txt", 0
    descriptor_fisier dd 0
    mesaj_eroare db "Deschiderea fisierului a esuat.", 0
    
; 14. Se dau un nume de fisier si un text (definite in segmentul de date). 
; Textul contine litere mici, litere mari, cifre si caractere speciale.
; Sa se transforme toate literele mari din textul dat in litere mici.
; Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
segment code use32 class=code
    start:
        ; eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces     
        push dword nume_fisier
        call [fopen]
        add esp, 4*2                ; eliberam parametrii de pe stiva
        mov [descriptor_fisier], eax   ; salvam valoarea returnata de fopen in variabila descriptor_fis
        
        ;verificam daca a deschis fisierul
        cmp eax, 0
        je eroare
        
        ;modificam sirul
        cld
        mov ecx, len
        mov esi, sir
        mov edi, d
        jecxz final
        repeta:
            lodsb
            cmp al, 'A'
            jb next
            cmp al, 'Z'
            ja next
            add al, 32
            next:
            stosb
            loop repeta
            
        
        ; fprintf(descriptor_fisier, d)
        push dword d
        push dword [descriptor_fisier]
        call [fprintf]
        add esp, 4*2
        
        ; apelam functia fclose pentru a inchide fisierul
        ; fclose(descriptor_fisier)
        push dword [descriptor_fisier]
        call [fclose]
        add esp, 4
        jmp final
        
    eroare:
        push dword mesaj_eroare
        call [printf]
        add esp, 4*1
    
    final:
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
