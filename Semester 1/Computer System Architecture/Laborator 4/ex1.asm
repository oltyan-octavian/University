bits 32

global start

extern exit
import exit msvcrt.dll

;4. Se da octetul A. Sa se obtina numarul intreg n reprezentat de bitii 2-4 ai lui A. 
;Sa se obtina apoi in B octetul rezultat prin rotirea spre dreapta a lui A cu n pozitii. 
;Sa se obtina dublucuvantul C:
;bitii 8-15 ai lui C sunt 0
;bitii 16-23 ai lui C coincid cu bitii lui B
;bitii 24-31 ai lui C coincid cu bitii lui A
;bitii 0-7 ai lui C sunt 1

segment data use32 class=data
    a db 10010101b
    b db 0
    c dd 0

segment code use32 class=code
    start:
        mov al, [a]
        and al, 00011100b
        mov cl, 2
        ror al, cl
        mov cl, al
        mov bl, [a]
        ror bl, cl
        mov [b], bl
        mov eax, 0
        mov ebx, 0
        mov bl, [a]
        or eax, ebx
        mov cl, 8
        rol eax, cl
        mov ebx, 0
        mov bl, [b]
        or eax, ebx
        mov cl, 16
        rol eax, cl
        or eax, 00000000000000000000000011111111b
        mov [c], eax
        
        
        push dword 0
        call [exit]
    