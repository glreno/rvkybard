# rvkybard

Remote Virtual Retro Keyboard

# What is it?

A Raspberry Pi based server that lets you use your phone as a
virtual keyboard for any PC, and most of the little plug-and-play
'retro' computers.

# Why?

Because the one flaw with the Retro Games Atari THE400MINI is that he keyboard doesn't work, so I can't play Star Raiders. And if you plug in a standard keyboard, you have to remember that Start is F7, when it was F2 on Altirra.

And because it would be nice to have all the keys of a Spectrum emulator properly labelled.

# Requirements

- A Raspberry Pi Zero W
    - It must have Wifi, so a plain Zero with a USB Wifi adapter would probably work
    - It must work as a USB OTG gadget, so it must be a Zero
- A blank MicroSD card
    - 4GB is more than enough
- A computer that can flash the SD card

# Overall Setup Instructions

1. Flash the SD card with Raspberry Pi OS Lite
    - Set up wifi during setup
    - use the name rvkybard.local (or anything else, really)
2. Boot the Raspberry Pi
3. Install the rvkybard deb file
4. Shut down the pi
5. Plug the pi into the computer you want to control
6. Wait a minute
7. Open something you can type in
8. On your phone, go to http://rvkybard.local:6502
9. Pick a keyboard
10. Type something

# Detailed setup instructions

1. Start the Raspberry Pi Imager
2. Choose Device: Raspberry Pi Zero 
3. Choose OS: Raspberry Pi OS (other), Raspberry Pi OS (Legacy, 32-bit) Lite
4. Choose Storage: SDHC crd
5. Next
6. WOuld you like to apply OS customization settings? EDIT SETTINGS
7. Edit settings - GENERAL
8. Set hostname: rvkybard.local
9. Set username, password
10. Configure wireless LAN
11. Edit settings - SERVICES
12. Enable SSH, use password auth
13. Save
14. Would you like to apply OS customization settings? YES
15. All existing data on 'SDHC Card' will be erased. Are you sure you want to continue? YES 
16. tea break
17. Insert SD card into pi, plug pi into PC's USB port
18. tea break
19. ssh rvkybard.local
20. Verify that the pi is up and running
21. Download the deb file to the pi:
    - wget https://github.com/glreno/rvkybard/releases/download/Release_0.01/rvkybard_0.01.SNAPSHOT_all.deb
    - scp rvkybard_0.01.SNAPSHOT_all.deb loser@rvkybard.local:
22. sudo apt install ./rvkybard_0.01.SNAPSHOT_all.deb
    (the ./ is important, it says to read the file!)
    and say Y to install it all.
23. tea break
24. Reboot! sudo /sbin/shutdown -r now
24. tea break
25. Open a window to type in
26. On your phone, go to http://rvkybard.local:6502
27. Pick a keyboard
28. Type something
29. ssh rvkybard.local
30. Shut it down: sudo /sbin/shutdown -h now
31. You can now connect the Pi to any device that can use a USB keyboard.
# What about something other than a Raspberry Pi?

You need something that can work as a USB OTG gadget,
and can run Tomcat or Jetty.
The server is in Java, so it should work on just about anything.
But you probably need to rebuild it to write to a device other than /dev/hidg0. The definition is in src/main/webapp/WEB-INF/applicationContext.xml

# What about a keyboard for retro system X?

Add it to the "Issues" list if it isn't there already.
Or send me a pull request.

If you just need to rearrange or relabel a few keys, you can edit the kb.jsp file on the pi in /var/lib/tomcat8/webapps/ROOT/kb
