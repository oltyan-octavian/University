bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a - word, b, c - byte
segment data use32 class=data
    a dw 9
    b db 6
    c db 2
    

; a-b+c
segment code use32 class=code
    start:
        mov ax, [a]
        sub ax, [b]
        add ax, [c]
        mov [a], ax
        
        push dword 0      
        call [exit]       
