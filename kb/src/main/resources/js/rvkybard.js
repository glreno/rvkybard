/*
Copyright (c) 2024 Gerald Reno, Jr.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
*/
let flagsdown = new Set();
let flagsdownstash = new Set();
let keysdown = new Set();
let resetTo = 10;
let timerSendCountdown = 20;

const asyncsend = async () => {
    //console.log(flagsdown);
    //console.log(keysdown);
    let u='/k?f=';
    flagsdown.forEach((x)=>{u=u+x+','});
    u=u+'&k=';
    keysdown.forEach((x)=>{u=u+x+','});
    //console.log(u);
    response = await fetch(u);
    if ( response.ok )
    {
        leds = await response.json();
        //console.log(leds);
        for (const [key, value] of Object.entries(leds)) {
            setLedClass(key+'-LED',value);
        }
        setLedClass('CONTACT-LED',true);
        setLedClass('CONTACTLOST-LED',false);
        setStatusText('Server connection OK');
    }
    else
    {
        //console.log('not ok');
        setLedClass('CONTACT-LED',false);
        setLedClass('CONTACTLOST-LED',true);
        setStatusText('Server connection LOST');
    }

}
function send() {
    asyncsend();
    resetTo=1;
    timerSendCountdown=1;
}
function timersend() {
    timerSendCountdown--;
    if ( timerSendCountdown <= 0 )
    {
        asyncsend();
        timerSendCountdown = resetTo;
        resetTo=resetTo+resetTo;
        //console.log(timerSendCountdown);
    }
}
setInterval(timersend,500);

// Clear ALL key buffers and send 'all keys up'
function panic() {
    flagsdown.clear();
    flagsdownstash.clear();
    keysdown.clear();
    send();
}
// send 'all keys up' and post a menu
function menu() {
    const menu = document.getElementById('kybard-menu');
    console.log('Opening menu');
    menu.style.visibility='visible';
    menu.style.zIndex=10;
    const kbd = document.getElementById('kybard-main');
    kbd.enabled=false;
}
function closeMenu() {
    const menu = document.getElementById('kybard-menu');
    //console.log('Closing menu');
    menu.style.visibility='hidden';
    menu.style.zIndex=-10;
    const kbd = document.getElementById('kybard-main');
    kbd.enabled=true;
}
function mainMenu() {
    console.log('Going to main menu');
    window.location.replace("/");
}
const asynclogout = async () => {
    // issue a logout to /l --- any GET is a logout
    let u='/l';
    //console.log(u);
    response = await fetch(u);
    //console.log( response.ok )
    mainMenu();
}
function doLogout() {
    console.log('Logging out');
    asynclogout();
}
function doNothing()
{
}
function flagDown(elem,key) {
    //console.log('Down:'+key);
    elem.classList.add('kbbuttonDown');
    flagsdown.add(key);
    send();
}
function flagUp(elem,key) {
    //console.log('Up:'+key);
    elem.classList.remove('kbbuttonDown');
    flagsdown.delete(key);
    send();
}
function keyDown(elem,key) {
    //console.log('Down:'+key);
    elem.classList.add('kbbuttonDown');
    keysdown.add(key);
    send();
}
function keyUp(elem,key) {
    //console.log('Up:'+key);
    elem.classList.remove('kbbuttonDown');
    keysdown.delete(key);
    send();
}
/* For when you need to send a different key when SHIFT is down.
* key: Keycode to send
* shifted: Keycode to send when shift is down
*/
function keyDownShiftDiff(elem,key,shifted) {
    //console.log('Down?:'+key+" "+shifted);
    elem.classList.add('kbbuttonDown');
    if ( flagsdown.has('LEFT_SHIFT') || flagsdown.has('RIGHT_SHIFT') )
    {
        //console.log('Down:'+shifted);
        keysdown.add(shifted);
    }
    else
    {
        //console.log('Down:'+key);
        keysdown.add(key);
    }
    send();
}
function keyUpShiftDiff(elem,key,shifted) {
    //console.log('Up:'+key+" "+shifted);
    elem.classList.remove('kbbuttonDown');
    keysdown.delete(key);
    keysdown.delete(shifted);
    send();
}
/* For when you need to send something completely different
* key: Keycode to send
* shifted: Keycode to send when shift is down
*/
function keyDownRemap(elem,flags,key,shiftedflags,shifted,ctrlflags,ctrl) {
    //console.log('Down:'+key+" "+shifted+" "+ctrl);
    flagsdownstash.clear();
    flagsdown.forEach(x => flagsdownstash.add(x));
    flagsdown.clear();
    if ( flagsdownstash.has('LEFT_SHIFT') || flagsdownstash.has('RIGHT_SHIFT') )
    {
        //console.log('Down:'+shifted);
        shiftedflags.forEach(x => flagsdown.add(x));
        flagsdown.add(shiftedflags);
        keysdown.add(shifted);
    }
    else if ( flagsdownstash.has('LEFT_CTRL') || flagsdownstash.has('RIGHT_CTRL') )
    {
        //console.log('Down:'+ctrl);
        ctrlflags.forEach(x => flagsdown.add(x));
        keysdown.add(ctrl);
    }
    else
    {
        //console.log('Down:'+key);
        flags.forEach(x => flagsdown.add(x));
        keysdown.add(key);
    }
    //console.log("Flags: "+[...flagsdown]);
    //console.log("Stash: "+[...flagsdownstash]);
    send();
}
function keyUpRemap(elem,key,shifted,ctrl) {
    //console.log('Up '+key+' '+shifted+' '+ctrl);
    //console.log("Flags: "+[...flagsdown]);
    //console.log("Stash: "+[...flagsdownstash]);
    flagsdown.clear();
    flagsdownstash.forEach(x => flagsdown.add(x));
    //console.log("After: "+[...flagsdown]);
    elem.classList.remove('kbbuttonDown');
    keysdown.delete(key);
    keysdown.delete(shifted);
    keysdown.delete(ctrl);
    send();
}
function setLedClass(baseclass,on) {
    //console.log('Setting '+baseclass+' to '+on);
    const elems = document.getElementsByClassName(baseclass);
    for (let i = 0; i < elems.length; i++)
    {
        //console.log(i+" Classes "+[...elems[i].classList]);
        if ( on )
        {       
            elems[i].classList.remove(baseclass+'-OFF');
            elems[i].classList.add(baseclass+'-ON');
        }
        else
        {       
            elems[i].classList.add(baseclass+'-OFF');
            elems[i].classList.remove(baseclass+'-ON');
        }
        //console.log(i+" now    "+[...elems[i].classList]);
    }
}

function setStatusText(s) {
    const elems = document.getElementsByClassName('CONTACT-STATUS-TEXT');
    for (let i = 0; i < elems.length; i++)
    {
        elems[i].innerHTML=s;
    }
}
