bits 32 

global start        


extern exit               
import exit msvcrt.dll    

;a - byte, b - word, c - double word, d - qword - Interpretare fara semn
segment data use32 class=data
    a db 1
    b dw 1
    c dd 1
    d dq 1
    x resq 1
    
; 14. (a+d)-(c-b)+c
segment code use32 class=code
    start:
        ; (a+d)
        mov eax, dword [d]
        mov edx, dword [d+4]
        mov dword [x], eax
        mov dword [x+4], edx
        mov eax, 0
        mov edx, 0
        mov al, [a]     ; edx:eax = a
        add dword [x], eax
        adc dword [x+4], edx  ; x = a+d
        
        ; (c-b)
        mov eax, [c]
        mov edx, 0
        mov dx, [b]
        sub eax, edx    ; eax = c-b
        
        ; (a+d)-(c-b)
        mov edx, 0
        sub dword [x], eax
        sbb dword [x+4], edx  ; x = (a+d)-(c-b)
        
        ; (a+d)-(c-b)+c
        mov eax, [c]
        mov edx, 0
        add dword [x], eax
        adc dword [x+4], edx  ; rezultatul se afla in x
        
        push    dword 0      
        call    [exit]       
