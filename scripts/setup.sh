#!/bin/bash

# Update a few system files
\mkdir backup
\cp /boot/config.txt backup
\cp /etc/modules backup
\cp /etc/rc.local backup

if grep -q "dtoverlay=dwc2" /boot/config.txt
then
    echo /boot/config.txt already up to date
else
    echo Updating /boot/config.txt 
    echo "dtoverlay=dwc2" >> /boot/config.txt
fi

if grep -q "dwc2" /etc/modules
then
    echo /etc/modules already up to date
else
    echo Updating /etc/modules 
    echo "dwc2" >> /etc/modules
fi

if grep -q "libcomposite" /etc/modules
then
    echo /etc/modules already up to date
else
    echo Updating /etc/modules 
    echo "libcomposite" >> /etc/modules
fi

# Copy the isticktoit_usb to /user/bin
echo Installing isticktoit_usb
\cp isticktoit_usb /usr/bin
\chmod +x /usr/bin/isticktoit_usb

if grep -q isticktoit_usb /etc/rc.local
then
    echo /etc/rc.local already up to date
else
    ed << __EOF__
/^exit
i
/usr/bin/isticktoit_usb # libcomposite configuration
.
w
q
__EOF__
fi
