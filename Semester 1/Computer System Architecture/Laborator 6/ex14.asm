bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dd 12345607h, 1A2B3C15h
    len equ $-s
    d times len db 0

;14. Se da un sir S de dublucuvinte.
;Sa se obtina sirul D format din octetii dublucuvintelor din sirul S sortati in ordine crescatoare in interpretarea fara semn.
segment code use32 class=code
    start:
        ; mutam octetii din s in d
        mov ecx, len
        mov esi, s
        mov edi, d
        cld
        jecxz final
        repetare:
            movsb
            loop repetare
        
        ;sortam sirul d
        mov ecx, len
        mov esi, d
        cld
        jecxz final
        parcurgere:
            mov edx, ecx
            mov ecx, len
            mov edi, d
            cld
            sortare:
                cmpsb
                dec esi
                dec edi
                ja next
                mov al, [edi]
                movsb
                dec esi
                dec edi
                mov [esi], al
                next:
                inc edi
                loop sortare
            mov ecx, edx
            inc esi
            loop parcurgere
        final:
        
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
