# Enkompass
A sane framework for Android Spannables

## Usage
```kotlin
"Typesafe builders are neat!".enkompass("neat!") {
     bold()
     italic()
     clickable(textview) { foo() }
     colorize(getColor(R.color.light_urple)
 }
```
