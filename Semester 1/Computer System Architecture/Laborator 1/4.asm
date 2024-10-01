bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a,b,c - byte, d - word
segment data use32 class=data
    a db 2
    b db 6
    c db 2
    d dw 20
    e db 2

; [d-(a+b)*2]/c
segment code use32 class=code
    start:
        ; a+b
        mov ax, [a]
        add [b], ax
        
        ; (a+b)*2
        mov al, [b]
        mul byte [e]
        
        ; d-(a+b)*2
        sub [d], ax
        
        ; [d-(a+b)*2]/c
        mov ax, [d]
        div byte [c]
        
        ; rezultatul se afla in al
        
        push    dword 0      
        call    [exit]       
