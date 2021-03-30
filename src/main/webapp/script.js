
/** Fetches tasks from the server and adds them to the DOM. */
function loadLocations() {
  fetch('/stored-locations').then(response => response.json()).then((locations) => {
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

  const titleElement = document.createElement('span');
  titleElement.innerText = location.title;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteLocation(location);

    // Remove the task from the DOM.
    locationElement.remove();
  });

  locationElement.appendChild(locationElement);
  lcoationElement.appendChild(deleteButtonElement);
  return locationElement;
}

/** Tells the server to delete the location. */
function deleteLocation(location) {
  const params = new URLSearchParams();
  params.append('id', location.id);
  fetch('/delete-location', {method: 'POST', body: params});
}