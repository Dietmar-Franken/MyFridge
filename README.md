# MyFridge

This is an app I made as the final project for the Udacity course [Android Basics: Data Storage](https://www.udacity.com/course/android-basics-data-storage--ud845). The assignment was to build an app that maintains and displays a list of inventory for an imaginary business.

![alt text](https://github.com/marshallaf/MyFridge/blob/master/screen1_w280.jpg "Main app screen")
![alt text](https://github.com/marshallaf/MyFridge/blob/master/screen2_w280.jpg "Search prompt")
![alt text](https://github.com/marshallaf/MyFridge/blob/master/screen3_w280.jpg "Settings")


## The course
The course taught the basics of querying an API for JSON data in an Android app. I had some experience with Java JSON parsing and with `AsyncTask`s in Android, and from there the instructors outlined the benefits of using an `AsyncTaskLoader` (instead of only an `AsyncTask`) for more efficient handling of UI activity changes.

Throughout the course, we built the QuakeReport app, which displayed data retrieved from the [USGS API](http://earthquake.usgs.gov/fdsnws/event/1/). The NewsReader app, as assigned, is largely just re-implementing the fetching logic from the QuakeReport app and adding appropriate layout files to support displaying articles.

## The extra stuff
I didn't think that I'd learn much from simply porting code from the QuakeReport app to the NewsReader app. I decided to add a couple extra goals to make the app better, features that I'd have to learn how to implement on my own:
* Infinite scrolling
* Floating action button
* Image loading/display

### Infinite scrolling
The API returns only 10 results by default, which is a paltry amount if you're actually going to read the news. I wanted that functionality where the results just keep coming, as long as you keep scrolling.

This is pretty easy to implement with an `OnScrollListener` to check the user's current position, and then I used the same `Loader` to fetch additional data.

### Floating action button
Having to go into settings to change the search term is not a great user experience, IMO, so I thought I'd add one of those cool Material Design action buttons for an additional way to change the search term. I had never used a dialog box (outside of settings) in an Android app before, so that was good to learn.

### Image loading/display
Only having text in the main activity was pretty boring, and I wanted to add a small version of the article's associated image to the main `ListView`. I knew about image loading libraries like [Picasso](http://square.github.io/picasso/), but I wanted to try to implement it on my own this time. It's always good to have some idea about what kinds of things are being abstracted away from you when you use libraries.

And turns out with image loading, it's quite a bit.

I initially used an `AsyncTask` with a `Drawable` to fetch images from the URL provided by the API, which worked well enough. However, the supplied images were much larger than I needed, so I read the [Displaying Bitmaps Efficiently](https://developer.android.com/training/displaying-bitmaps/index.html) Android training to help me resize the image while loading it to use memory more efficiently. I then continued in the training to handle view recycling in the `ListView` and cancelling the image loading task if it was no longer needed.

This was a ton of logic to add, and I didn't even get to the proper caching of images! I can definitely see the benefit that libraries like Picasso provide, and I'll use them in the future with some knowledge of what they're taking care of under the hood.