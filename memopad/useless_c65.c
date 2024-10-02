#include <atari.h>
int main(void)
{
    OS.color2 = 0xa0;
    __asm__ ("jmp $E471");
    return 0;
}
