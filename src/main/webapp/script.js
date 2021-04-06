
/** Fetches locations from the server and adds them to the DOM. */
function loadLocations() {
  fetch('/list-locations').then(response => response.json()).then((locations) => {
    //sortByCategory(locations);
    const locationListElement = document.getElementById('location-list');

    var artLocations = locations.filter(location => location.category==='art');
    var culturalLocations = locations.filter(location => location.category==='cultural');
    var outdoorLocations = locations.filter(location => location.category==='outdoor');
    var landmarksLocations = locations.filter(location => location.category==='landmarks');

    const artTag = document.createElement('h2');
    artTag.innerText = `Art - ${artLocations.length} locations`;
    locationListElement.appendChild(artTag);
    artLocations.forEach((location) => {
      locationListElement.appendChild(createLocationElement(location));
    })

    const culturalTag = document.createElement('h2');
    culturalTag.innerText = `Culture - ${culturalLocations.length} locations`;
    locationListElement.appendChild(culturalTag);
    culturalLocations.forEach((location) => {
      locationListElement.appendChild(createLocationElement(location));
    })

    const landmarkTag = document.createElement('h2');
    landmarkTag.innerText = `Landmarks - ${landmarksLocations.length} locations`;
    locationListElement.appendChild(landmarkTag);
    landmarksLocations.forEach((location) => {
      locationListElement.appendChild(createLocationElement(location));
    })

    const outdoorTag = document.createElement('h2');
    outdoorTag.innerText = `The Outdoors - ${outdoorLocations.length} locations`;
    locationListElement.appendChild(outdoorTag);
    outdoorLocations.forEach((location) => {
      locationListElement.appendChild(createLocationElement(location));
    })
  });
}


/** Creates an element that represents a location, including its delete button. */
function createLocationElement(location) {
  const locationElement = document.createElement('li');
  locationElement.className = 'location';

  const infoElement = document.createElement('span');
  infoElement.innerText = `${location.name}
  
  ${location.description}
  
  `;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteLocation(location);

    // Remove the task from the DOM.
    locationElement.remove();
  });

  locationElement.appendChild(infoElement);
  locationElement.appendChild(deleteButtonElement);
  return locationElement;
}

/** Tells the server to delete the location. */
function deleteLocation(location) {
  const params = new URLSearchParams();
  params.append('id', location.id);
  fetch('/delete-location', {method: 'POST', body: params});
}