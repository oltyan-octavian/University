bits 32 

global start        


extern exit               
import exit msvcrt.dll    

; a - byte, b - word, c - doubleword, x - qword - Interpretare cu semn
segment data use32 class=data
    a db 12
    b dw 15
    c dd 12
    x dq 15
    z resd 1
    
; 14. x+(2-a*b)/(a*3)-a+c
segment code use32 class=code
    start:
        ;(2-a*b)
        mov al, [a]
        cbw
        imul word [b]
        push dx
        push ax
        pop eax
        mov ebx, 2
        sub ebx, eax    
        mov [z], ebx    ; rezultatul se afla in z (dword)
        
        ;(a*3)
        mov al, [a]
        mov dl, 3
        imul dl
        mov cx, ax      ; rezultatul se afla in cx (word)
        
        ;(2-a*b)/(a*3)
        mov ax, word [z]
        mov dx, word [z+2]
        idiv cx         ; rezultatul se afla in ax (word)
        
        ;x+(2-a*b)/(a*3)
        cwde
        cdq
        add dword [x], eax
        adc dword [x+4], edx
        
        ;x+(2-a*b)/(a*3)-a
        mov al, [a]
        cbw
        cwde
        cdq
        sub dword [x], eax
        sbb dword [x+4], edx
        
        ;x+(2-a*b)/(a*3)-a+c
        mov eax, [c]
        cdq
        add dword [x], eax
        adc dword [x+4], edx      ; rezultatul se afla in x
        
        push    dword 0      
        call    [exit]       
