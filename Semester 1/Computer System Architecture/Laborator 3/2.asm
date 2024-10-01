bits 32 

global start        


extern exit               
import exit msvcrt.dll    

;a - byte, b - word, c - double word, d - qword - Interpretare cu semn
segment data use32 class=data
    a db 1
    b dw 1
    c dd 5
    d dq 1
    x resd 1
    
; 14. c-b-(a+a)-b
segment code use32 class=code
    start:
        ; (a+a)
        mov al, [a]
        add al, [a]
        mov bl, al      ; rezultatul va fi in bl
        
        mov eax, [c]
        mov [x], eax
        mov ax, [b]
        cwde
        sub [x], eax
        mov al, bl
        cbw
        cwde
        sub [x], eax
        mov [b], ax
        cwde
        sub [x], eax    ; rezultatul va fi in x
        
        
        push    dword 0      
        call    [exit]       
