
/** Fetches locations from the server and adds them to the DOM. */
function loadLocations() {
    fetch('/list-locations').then(response => response.json()).then((locations) => {
        const locationListElement = document.getElementById('location-list');
        //Create lists for each category
        var artLocations = locations.filter(location => location.category==='art');
        var culturalLocations = locations.filter(location => location.category==='cultural');
        var outdoorLocations = locations.filter(location => location.category==='outdoor');
        var landmarksLocations = locations.filter(location => location.category==='landmarks');


        //List locations under each category
        const artTag = document.createElement('h2');
        if (artLocations.size == 1){  //edge case
            artTag.innerText = `Art - 1 location`;
            locationListElement.appendChild(artTag);
        }
        else{
            artTag.innerText = `Art - ${artLocations.length} locations`;
            locationListElement.appendChild(artTag);
        }
        artLocations.forEach((location) => {
            locationListElement.appendChild(createLocationElement(location));
        })

        const culturalTag = document.createElement('h2');
        if (culturalLocations.length == 1){
            culturalTag.innerText = `Culture - 1 location`;
            locationListElement.appendChild(culturalTag);
        }
        else{
            culturalTag.innerText = `Culture - ${culturalLocations.length} locations`;
            locationListElement.appendChild(culturalTag);
        }
        culturalLocations.forEach((location) => {
            locationListElement.appendChild(createLocationElement(location));
        })

        const landmarkTag = document.createElement('h2');
        if (landmarksLocations.length == 1){
            landmarkTag.innerText = `Landmarks - 1 location`;
            locationListElement.appendChild(landmarkTag);
        }
        else{
            landmarkTag.innerText = `Landmarks - ${landmarksLocations.length} locations`;
            locationListElement.appendChild(landmarkTag);
        }
        landmarksLocations.forEach((location) => {
            locationListElement.appendChild(createLocationElement(location));
        })

        const outdoorTag = document.createElement('h2');
        if (outdoorLocations.length == 1){
            outdoorTag.innerText = `The Outdoors - 1 location`;
            locationListElement.appendChild(outdoorTag);
        }
        else{
            outdoorTag.innerText = `The Outdoors - ${outdoorLocations.length} locations`;
            locationListElement.appendChild(outdoorTag);
        }
        outdoorLocations.forEach((location) => {
        locationListElement.appendChild(createLocationElement(location));
        });
    });
}


/** Creates an element that represents a location, including its delete button. */
function createLocationElement(location) {
  const locationElement = document.createElement('li');
  locationElement.className = 'location';

  const infoElement = document.createElement('span');
  infoElement.innerText = `${location.name}

  Located in ${location.city}, ${location.state}

  ${location.description}
  
  Likes: ${location.num_likes}`;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteLocation(location);

    // Remove the task from the DOM.
    locationElement.remove();
  });

  //code for like button
  const likeButtonElement = document.createElement('button');
  likeButtonElement.innerText = 'Like';
  likeButtonElement.addEventListener('click', () => {
        location.num_likes++;

        infoElement.innerText = `${location.name}

        Located in ${location.city}, ${location.state}

        ${location.description}
  
        Likes: ${location.num_likes}`;
  });

  locationElement.appendChild(infoElement);
  locationElement.appendChild(likeButtonElement);
  locationElement.appendChild(deleteButtonElement);
  return locationElement;
}

/** Tells the server to delete the location. */
function deleteLocation(location) {
  const params = new URLSearchParams();
  params.append('id', location.id);
  fetch('/delete-location', {method: 'POST', body: params});
}