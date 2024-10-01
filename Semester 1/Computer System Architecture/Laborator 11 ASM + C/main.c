#include <stdio.h>
#include <stdlib.h>

int verificare(int numar);
int len_n, len_p, n[100], p[100];

int main()
{
    FILE *fisier;
    int numar;
    fisier = fopen("numere.txt", "r");
    
    while(fscanf(fisier, "%u", &numar) == 1)
    {
        if(verificare(numar) == 1)
        {
            n[len_n] = numar;
            len_n++;
        }
        else
        {
            p[len_p] = numar;
            len_p++;
        }
    }
    printf("Numerele impare: ");
    for(int i = 0; i < len_n; i++)
        printf("%d ", n[i]);
    printf("\n");
    printf("Numerele pare: ");
    for(int i = 0; i < len_p; i++)
        printf("%d ", p[i]);
    fclose(fisier);
    return 0;
}
