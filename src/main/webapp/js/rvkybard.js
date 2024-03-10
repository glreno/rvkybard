/*
Copyright (c) 2024 Gerald Reno, Jr.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
*/
let flagsdown = new Set();
let keysdown = new Set();

const send = async () => {
    //console.log(flagsdown);
    //console.log(keysdown);
    let u='../../k?f=';
    flagsdown.forEach((x)=>{u=u+x+','});
    u=u+'&k=';
    keysdown.forEach((x)=>{u=u+x+','});
    //console.log(u);
    response = await fetch(u);
}

function flagDown(key) {
    flagsdown.add(key);
    send();
}
function flagUp(key) {
    flagsdown.delete(key);
    send();
}
function keyDown(key) {
    keysdown.add(key);
    send();
}
function keyUp(key) {
    keysdown.delete(key);
    send();
}
