// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!', 'q onda guey', 'cringe', 'You\'re still learning English? The Language you Speak? How stupid are you?', 'Live Long, and Prosper.',
      'Can I make text <b>BOLD</b> in quotes?'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function randomImage(){
   // const fs = require('fs');
    const dir = 'images/senior-pics/';

    //fs.readdir(dir, (err,files) => { 
        const imageIndex = Math.floor(Math.random() * 15)+1;
  
        const imgUrl = dir + imageIndex +'.jpg';

        const imgElement = document.createElement('img');
        imgElement.src = imgUrl;

        const imageContainer = document.getElementById('senior-picture-container');
//remove previous image
        imageContainer.innerHTML = '';
        imageContainer.appendChild(imgElement);
        imageContainer.style.width = "800";//I'm struggling so desperately to constrain this width
         // });
}

//tried doing a simple print("hello world") only for Google Chrome to attempt to print my webpage
//thus inspiring this function
function gagFunction()
{
print("hello world");
}
