This started out because it is bloody difficult to get to
the ATARI COMPUTER - MEMO PAD on the 400. It's a three-step process:

1. Get a thumb drive
2. Boot the BASIC disk
3. BYE
4. And if that put you in SELF TEST, change the config to Atari 800 mode. and try again.

So it occurred to me that the Memo Pad code is in ROM, I just need
a tiny executable to jump to it.
And why boot a disk, why not make a cartridge?

This would be The Worlds Most Useless Cartridge.

All I need to do is change the the cartridge's init vector, right?
Well, no. That doesn't work, because the init occurs before the
screen is set up. So what you actually need to do is do this:

JMP $E471

So I hand assembled that, and two more instructions to change
the background color, and built a cartridge. That's the file
useless.xxd, and you use the Linux 'xxd' command to compile it:

xxd -r useless.xxd useless.rom

The rom is 2K in size, but load it as an 8K cartridge.
On an Atari 800 emulator, it boots to memopad, on an XL, Self Test.
Completely useless.

