bits 32
global verificare

segment code use32 class=code public
    verificare:
    mov eax, [esp+4]
    mov ecx, [esp+8]
    push eax
    pop ax
    pop dx
    mov bx, 2
    div bx
    cmp dx, 0
    je par
    ;nr e impar
    mov word [ecx], 1
    jmp iesire
    par:
    mov word [ecx], 0
    iesire:
    ret 4*2