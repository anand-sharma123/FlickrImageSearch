# FlickrImageSearch
This project helps to search images on famous image hosting service flickr.

## Language Used
Kotlin

## Architecture Design Pattern 
MVP

## Technical flow

GalleryActivity - IT's a launcher activity that has search view for searching query in Flickr API 

https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=YOUR_API_KEY&format=json&nojsoncallback=1&safe_search=1&text=query

For loading data asyncronus used RX Java and Retrofit

Once done with data we are showing in recycler view with three coloums


