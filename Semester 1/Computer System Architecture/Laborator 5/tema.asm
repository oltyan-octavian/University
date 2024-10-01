bits 32

global start

extern exit
import exit msvcrt.dll

;19. Se dau 2 siruri de octeti A si B. 
;Sa se construiasca sirul R care sa contina doar elementele pare si negative din cele 2 siruri.
segment data use32 class=data
    a db 2, 1, 3, -3, -4, 2, -6
    lena equ $-a
    b db 4, 5, -5, 7, -6, -2, 1
    lenb equ $-b
    r times lena+lenb db 0

segment code use32 class=code
    start:
        ;parcurgem sirul a
        mov ecx, lena
        mov esi, a
        mov edi, r
        jecxz finala
        repetarea:
            mov bl, [esi]
            mov al, [esi]
            and al, 10000001
            cmp al, 10000000
            jne nexta
            mov [edi], bl
            inc edi
            nexta:
                inc esi
                loop repetarea
        finala:
        
        ;parcurgem sirul b
        mov ecx, lenb
        mov esi, b
        jecxz finalb
        repetareb:
            mov bl, [esi]
            mov al, [esi]
            and al, 10000001
            cmp al, 10000000
            jne nextb
            mov [edi], bl
            inc edi
            nextb:
                inc esi
                loop repetareb
        finalb:
        
        push dword 0
        call [exit]