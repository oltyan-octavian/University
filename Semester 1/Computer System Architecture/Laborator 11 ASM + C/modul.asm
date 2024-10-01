bits 32
global _verificare

segment data public data use32

segment code public code use32
_verificare:
    push ebp
    mov ebp, esp
    mov eax, [ebp+8]
    push eax
    pop ax
    pop dx
    mov bx, 2
    div bx
    cmp dx, 0
    je par
    ;nr e impar
    mov eax, 1
    jmp iesire
    par:
    mov eax, 0
    iesire:
    add esp, 4
    mov esp, ebp
    pop ebp
    ret