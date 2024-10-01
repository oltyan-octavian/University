bits 32

global start        

extern exit               
import exit msvcrt.dll    

segment data use32 class=data
    a dw 14
    b db 6

; 14 / 6
segment code use32 class=code
    start:
        mov ax, [a]     
        div byte [b]  
        
        ; al = a / b, ah = a % b
        
        push    dword 0      
        call    [exit]       
