bits 32
global start

extern exit
import exit msvcrt.dll

segment data use32 class=data
    s dd 12015678h, 1A2C3C4Dh, 98FCDC76h
    len equ ($-s)/4
    d times len db 0
    
;8. Se da un sir de dublucuvinte. Sa se obtina sirul format din octetii inferiori ai
;   cuvintelor superioare din elementele sirului de dublucuvinte care sunt palindrom in scrierea in baza 10.
segment code use32 class=code
    start:
    mov ecx, len
    mov esi, s
    mov edi, d
    cld
    jecxz final
    repeta:
        lodsw
        lodsw   ; ax = high word
        mov ah, 0
        mov dh, al  ; dh - copia lui al
        mov bl, 100d
        div bl  ; al = cifra sutelor, ah = restul
        mov dl, al  ; dl - cifra sutelor
        mov al, ah
        mov ah, 0
        mov bl, 10d
        div bl  ; al = cifra zecilor, ah = cifra unitatilor
        cmp dl, 0
        je doua_cifre
        cmp dl, ah
        jne next
        je copy
        doua_cifre:
            cmp al, 0
            je copy
            cmp al, ah
            jne next
        copy:
        mov [edi], dh
        inc edi
        next:
        loop repeta
        
    
    final:
    push dword 0
    call [exit]