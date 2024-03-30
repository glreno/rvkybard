# rvkybard

Remote Virtual Retro Keyboard

# What is it?

A Raspberry Pi based server that lets you use your phone as a
virtual keyboard for any PC, and most of the little plug-and-play
'retro' computers.

# Why?

Because the one flaw with the Retro Games Atari THE400MINI is that the keyboard is just printed on, so I can't play Star Raiders. And if you plug in a standard keyboard, you have to remember that Start is F7, when it was F2 on Altirra.

And because it would be nice to have all the keys of a Spectrum emulator properly labelled.

Note that my goal here is to play Star Raiders, not Quake. This is not a high-response-speed gamer keyboard. It is a replacement for a membrane keyboard on an early 80s microcomputer. And the tactile feedback is just as good as a ZX81.

# Requirements

- A Raspberry Pi Zero W
    - It must have Wifi, so a plain Zero with a USB Wifi adapter would probably work
    - It must work as a USB OTG gadget, so it must be a Zero
- A blank MicroSD card
    - 4GB is more than enough
- A computer that can flash the SD card
- A USB cable

# Overall Setup Instructions

1. Flash the SD card with Raspberry Pi OS Lite
    - Set up wifi during setup
    - use the name rvkybard.local (or anything else, really)
2. Boot the Raspberry Pi
3. Install the rvkybard deb file
4. Shut down the Pi
5. Plug the Pi into the computer you want to control
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
    6. Would you like to apply OS customization settings? EDIT SETTINGS
    7. Edit settings - GENERAL
    8. Set hostname: rvkybard.local
    9. Set username, password
    10. Configure wireless LAN
    11. Edit settings - SERVICES
    12. Enable SSH, use password auth
    13. Save
    14. Would you like to apply OS customization settings? YES
    15. All existing data on 'SDHC Card' will be erased. Are you sure you want to continue? YES 
16. Tea break!
    - Do not remove the SD card until the task is complete.
17. Insert the SD card into the Pi. Plug the Pi into PC's USB port
18. Tea break!
    - It will take a while for the Pi to boot for the first time, and connect to the WiFi.
19. ssh rvkybard.local
20. Verify that the Pi is up and running
21. Download the deb file to the Pi:
    - wget https://github.com/glreno/rvkybard/releases/download/Release_0.02/rvkybard_0.02_all.deb
    - scp rvkybard_0.02_all.deb loser@rvkybard.local:
22. Install the deb file
    - sudo apt install ./rvkybard_0.02_all.deb
        - (the ./ is important, it says to read the file!)
        - Say Y to install it rvkybard and all dependencies
23. Tea break!
    - It needs time to download the JDK, Tomcat, etc.
24. Reboot!
    - sudo /sbin/shutdown -r now
        - The reboot is necessary to install the USB device.
24. Tea break!
    - It takes about a minute for the Pi to boot, Tomcat to start, and the webapp to deploy.
25. Test
    25. Open a window to type in
    26. On your phone, go to http://rvkybard.local:6502
    27. Pick a keyboard
    28. Type something
30. Shut it down
    1. ssh rvkybard.local
    2. sudo /sbin/shutdown -h now
31. You can now connect the Pi to any device that can use a USB keyboard.
# What about something other than a Raspberry Pi?

You need something that can work as a USB OTG gadget,
and can run Tomcat or Jetty.
The server is in Java, so it should work on just about anything. Just install tomcat and replace ROOT.war with rvkybard-0.02.war

If you need it to write to a device other than /dev/hidg0, the definition is in WEB-INF/applicationContext.xml

# What about a keyboard for retro system X?

Add it to the "Issues" list if it isn't there already.
Or send me a pull request.

If you just need to rearrange or relabel a few keys, you can edit the kb.jsp file on the Pi in /var/lib/tomcat8/webapps/ROOT/kb
