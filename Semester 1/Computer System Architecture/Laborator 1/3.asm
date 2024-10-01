bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a,b,c,d - word
segment data use32 class=data
    a dw 1279
    b dw 156
    c dw 627
    d dw 3473

; (d-a)+(b+b+c)
segment code use32 class=code
    start:
        ; d-a
        mov ax, [a]
        sub [d], ax
        
        ; b+b+c
        mov ax, [b]
        add ax, [b]
        add ax, [c]
        
        ; (d-a)+(b+b+c)
        add [d], ax
        
        
        ; rezultatul se afla in d
        
        push    dword 0      
        call    [exit]       
