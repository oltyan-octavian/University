bits 32

global start

extern exit
import exit msvcrt.dll

; a,b,c,d-byte, e,f,g,h-word
segment data use32 class=data
    a db 2
    b db 1
    c db 2
    d db 3
    e dw 2
    h dw 2
    x resw 1
    y resb 1

; (a*d+e)/[c+h/(c-b)]
segment code use32 class=code
    start:
        ;a*d
        mov al, [a]
        mul byte [d]
        
        ;(a*d+e)
        add ax, [e]
        mov [x], ax     ; rezultatul il punem in x
        
        ;c-b
        mov al, [c]
        sub al, [b]
        mov bl, al      ;rezultatul il punem in bl
        
        ;h/(c-b)
        div bl
        
        ;c+h/(c-b)
        add [c], al
        
        ;(a*d+e)/[c+h/(c-b)]
        mov ax, [x]
        div byte [c]
        mov [y], al     ; rezultatul final va fi in y
        
        push dword 0
        call [exit]