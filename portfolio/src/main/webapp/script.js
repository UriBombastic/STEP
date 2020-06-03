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

function addRandomFunFact() {
  const funFacts =
      ['Hablo Espanol!','My first programming class was visual basic, sophomore year of high school, 2015',
      'I hold the highest bench, squat, and fastest mile time of anyone named Uri in my household.',
      'In two years, I went from working at Arby\'s, to doing game development with my university, to interning at Google.',
      'Minecraft probably takes the title of my favorite game of all time.',
      'My dad moved to the United States from Argentina',
      'Like literally every little boy, I once had an obsession with Dinosaurs, but later moved on to Ben 10 then Dragonball Z.',
      'My favorite sport is probably dodgeball. Actually, come to think of it, it\'s really the only sport I like.',
      'Belief in alien life: 100%. Belief in alien visitations: 75%. Belief in Alien abductions: 40%. Belief in ancient astronaut theories: 15%'];

  // Pick a random greeting.
  const fact = funFacts[Math.floor(Math.random() * funFacts.length)];

  // Add it to the page.
  const funFactContainer = document.getElementById('funFact-container');
  funFactContainer.innerText = fact;
}

function randomImage() {
  const imageIndex = Math.floor(Math.random() * 6)+1;
  
  const imgUrl = 'images/senior-pics/' + imageIndex +'.jpg';

  const imgElement = document.createElement('img');
  imgElement.src = imgUrl;

  const imageContainer = document.getElementById('senior-picture-container');
  //remove previous image
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
  imageContainer.style.width = "800";//I'm struggling so desperately to constrain this width
}

async function pullFromData() {
  const response = await fetch('/data');
  const quote = await response.text();
  document.getElementById('data-container').innerText = quote;
}

function getComments() {
  fetch('/data').then(response => response.json()).then((comments) =>{
    const dataContainer = document.getElementById('data-container');
    console.log(comments);
    //clear data
    dataContainer.innerHTML = ""; 
    //dataContainer.innerText = JSON.stringify(comments);
    //genereate comments
    for(i = 0; i < comments.length; i++) {
      dataContainer.appendChild(createCommentElement(comments[i]));
    }
  });
}

//copied from example; used to generate list of comments
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text + "\n";
  return liElement;
}

function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  
  const headerElement = document.createElement('span');
  headerElement.innerText = comment.posterName +":\n";

  const bodyElement = document.createElement('span');
  bodyElement.innerText = comment.comment +"\n";

  commentElement.appendChild(headerElement);
  commentElement.appendChild(bodyElement);
  return commentElement;
}

//tried doing a simple print("hello world") only for Google Chrome to attempt to print my webpage
//thus inspiring this function
function gagFunction() {
  print("hello world");
}