bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a,b,c,d - byte, e,f,g,h - word
segment data use32 class=data
    a db 2
    b db 6
    c db 2
    d db 2
    e dw 2
    f dw 2
    g dw 2
    h dw 2

; [b*c-(e+f)]/(a+d)
segment code use32 class=code
    start:
        ; e+f
        mov ax, [f]
        add [e], ax
        
        ; b*c
        mov al, [b]
        mul byte [c]
        
        ; b*c-(e+f)
        sub ax, [e]
        
        ; a+d
        mov bl, [d]
        add [a], bl
        
        ; [b*c-(e+f)]/(a+d)
        div byte [a]
        
        ; rezultatul se afla in al
        
        push    dword 0      
        call    [exit]       
