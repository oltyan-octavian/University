bits 32 


global start        


extern exit               
import exit msvcrt.dll    

; a - byte, b - word, c - double word, d - qword - Interpretare fara semn
segment data use32 class=data
    a db 9
    b dw 6
    c dd 3
    d dq 10

; (a+d)-(c-b)+c
segment code use32 class=code
    start:
        ; a+d
        mov eax, 0
        mov al, [a]
        mov edx, 0
        mov ebx, [d]
        mov ecx, [d+4]
        add eax, ebx
        adc edx, ecx
        mov [d], eax
        mov [d+4], edx
        
        push dword 0      
        call [exit]       
