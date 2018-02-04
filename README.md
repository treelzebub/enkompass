# Enkompass
A sane way of working with Android Spannables
[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=5a76b835336d9f0001a00f1f&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/5a76b835336d9f0001a00f1f/build/latest?branch=master)

## Usage
```kotlin
"Kotlin builders are neat!".enkompass("neat!") {
     bold()
     italic()
     clickable(textview) { foo() }
     colorize(getColor(R.color.light_urple))
 }
```
