# Enkompass
A sane way of working with Android Spannables

[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=5a76b835336d9f0001a00f1f&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/5a76b835336d9f0001a00f1f/build/latest?branch=master)

## Usage

### Set your spans by feeding Enkompass a substring:
```kotlin
"Kotlin builders are neat!".enkompass("neat!") {
     bold()
     italic()
     colorize(getColor(R.color.light_urple))
     clickable(textview) { foo() }
 }
```

### Or by IntRange, if your substring occurs more than once in the outer string:
```kotlin
"Wow Bob Wow.".enkompass(0 until 3) {
    monospace()
}
```
