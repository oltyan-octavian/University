#include <stdio.h>

int main()
{
    int n, suma, i, x;
    suma = 0;
    scanf("%d", &n);
    for(i = 1; i <= n; i++)
    {
        scanf("%d", &x);
        suma = suma + x;
    }
    printf("Suma este: %d", suma);
    return 0;
}