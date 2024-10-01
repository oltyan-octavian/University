bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a,b,c,d - byte
segment data use32 class=data
    a db 14
    b db 6
    c db 5
    d db 12
    x resb 1

; (b+c)+(a+b-d)
segment code use32 class=code
    start:
        ; b+c
        mov al, [b]
        add [c], al
        
        ; a+b-d
        mov al, [a]
        add al, [b]
        sub al, [d]
        
        ; (b+c)+(a+b-d)
        add al, [c]
        mov [x], al
        
        ; rezultatul se afla in x
        
        push    dword 0      
        call    [exit]       
