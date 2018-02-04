# Enkompass
A sane way of working with Android Spannables

## Usage
```kotlin
"Kotlin builders are neat!".enkompass("neat!") {
     bold()
     italic()
     clickable(textview) { foo() }
     colorize(getColor(R.color.light_urple))
 }
```
