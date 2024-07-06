![Image: rrvkb logo](https://raw.githubusercontent.com/wiki/glreno/rvkybard/imx/rrvkb_logo.png)

# rrvkb: Retro Remote Virtual Keyboard

![Image: 400ish keyboard on tablet with THE400MINI](https://raw.githubusercontent.com/wiki/glreno/rvkybard/imx/rvkybard_setup.0.06.jpg)

# What is it?

A Raspberry Pi based server that lets you use your phone as a
virtual keyboard for any PC, and most of the little plug-and-play
'retro' computers.

# Why?

Because the one flaw with the Retro Games Atari THE400MINI is that the keyboard is just printed on, so I can't play Star Raiders. And if you plug in a standard keyboard, you have to remember that Start is F7, when it was F2 on Altirra.

And because it would be nice to have all the keys of a Spectrum emulator properly labelled.

Note that my goal here is to play Star Raiders, not Quake. This is not a high-response-speed gamer keyboard. It is a replacement for a membrane keyboard on an early 80s microcomputer. And the tactile feedback is just as good as a ZX81.

# Setup

System requirements and setup instructions are on the wiki: https://github.com/glreno/rvkybard/wiki/Step-by-Step-Installation---Raspberry-Pi-Zero-W

# What about something other than a Raspberry Pi?

You need something that can work as a USB OTG gadget,
and can run Tomcat or Jetty.

You will need to set up an HID driver; rrvkb sets it up in /dev/hidg0 as a libcomposite usb gadget. See http://www.isticktoit.net/?p=1383

The server is in Java, so it should work on just about anything. Just install tomcat and replace ROOT.war with rrvkb-usb-pizero-1.00.war

If you need it to write to a device other than /dev/hidg0, the definition is in WEB-INF/applicationContext.xml

# What about a keyboard for retro system X?

Add it to the "Issues" list if it isn't there already.
Or send me a pull request.

If you just need to rearrange or relabel a few keys, you can edit the kb.jsp file on the Pi in /var/lib/tomcat8/webapps/ROOT/kb
