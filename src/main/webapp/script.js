
/** Fetches locations from the server and adds them to the DOM. */
function loadLocations() {
  fetch('/list-locations').then(response => response.json()).then((locations) => {
    const locationListElement = document.getElementById('location-list');
    locations.forEach((location) => {
      locationListElement.appendChild(createLocationElement(location));
    })
  });
}


/** Creates an element that represents a location, including its delete button. */
function createLocationElement(location) {
  const locationElement = document.createElement('li');
  locationElement.className = 'location';

  const nameElement = document.createElement('span');
  nameElement.innerText = location.name;

  const descriptionElement = document.createElement('span');
  descriptionElement.innerText = location.description;

  const categoryElement = document.createElement('span');
  categoryElement.innerText = location.category;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteLocation(location);

    // Remove the task from the DOM.
    locationElement.remove();
  });

  locationElement.appendChild(nameElement);
  locationElement.appendChild(descriptionElement);
  locationElement.appendChild(categoryElement);
  locationElement.appendChild(deleteButtonElement);
  return locationElement;
}

/** Tells the server to delete the location. */
function deleteLocation(location) {
  const params = new URLSearchParams();
  params.append('id', location.id);
  fetch('/delete-location', {method: 'POST', body: params});
}