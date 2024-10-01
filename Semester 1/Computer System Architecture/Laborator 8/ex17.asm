bits 32
global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    format db "%d", 0
    format2 db "%x", 0
    n dd 0
    mesaj db "Dati un numar in baza 10: ", 0
    mesaj2 db "Numarul introdus in baza 16 este: ", 0
    
    
; 17. Sa se citeasca de la tastatura un numar in baza 10 si sa se afiseze valoarea acelui numar in baza 16.
segment code use32 class=code
    start:
        ;citim nr
        push dword mesaj  ; punem parametrul pe stiva
		call [printf]       ; apelam functia printf
		add esp, 4 * 1     ; eliberam parametrii de pe stiva
        push dword n       ; punem parametrii pe stiva de la dreapta la stanga
		push dword format
		call [scanf]       ; apelam functia scanf pentru citire
		add esp, 4 * 2     ; eliberam parametrii de pe stiva
        
        ;afisam nr
        push dword mesaj2  ; punem parametrul pe stiva
		call [printf]       ; apelam functia printf
		add esp, 4 * 1     ; eliberam parametrii de pe stiva
        push dword [n]  ; punem parametrii pe stiva de la dreapta la stanga
		push dword format2  
		call [printf]       ; apelam functia printf
		add esp, 4 * 2     ; eliberam parametrii de pe stiva
        
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
