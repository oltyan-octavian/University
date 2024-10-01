bits 32 

global start        


extern exit               
import exit msvcrt.dll    

; a - byte, b - word, c - doubleword, x - qword - Interpretare fara semn
segment data use32 class=data
    a db 1
    b dw 1
    c dd 12
    x dq 15
    z resd 1
    
; 14. x+(2-a*b)/(a*3)-a+c
segment code use32 class=code
    start:
        ;(2-a*b)
        mov ax, 0
        mov al, [a]
        mul word [b]
        push dx
        push ax
        pop eax
        mov ebx, 2
        sub ebx, eax    
        mov [z], ebx    ; rezultatul se afla in z (dword)
        
        ;(a*3)
        mov al, [a]
        mov dl, 3
        mul dl
        mov cx, ax      ; rezultatul se afla in cx (word)
        
        ;(2-a*b)/(a*3)
        mov ax, word [z]
        mov dx, word [z+2]
        div cx         
        mov bx, ax      ; rezultatul se afla in bx (word)
        
        ;x+(2-a*b)/(a*3)
        mov eax, 0
        mov edx, 0
        mov ax, bx
        add dword [x], eax
        adc dword [x+4], edx
        
        ;x+(2-a*b)/(a*3)-a
        mov eax, 0
        mov edx, 0
        mov al, [a]
        sub dword [x], eax
        sbb dword [x+4], edx
        
        ;x+(2-a*b)/(a*3)-a+c
        mov eax, [c]
        mov edx, 0
        add dword [x], eax
        adc dword [x+4], edx      ; rezultatul se afla in x
        
        push    dword 0      
        call    [exit]       
