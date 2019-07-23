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


ImageViewsActivity - Used to diaplay image in full screen 

![Screenshot_2019-07-23-18-40-25](https://user-images.githubusercontent.com/52536039/61714836-6f367a00-ad79-11e9-8705-70eaa63d5086.png)

![Screenshot_2019-07-23-18-40-40](https://user-images.githubusercontent.com/52536039/61714926-97be7400-ad79-11e9-996f-7ceb1c7afcd0.png)


