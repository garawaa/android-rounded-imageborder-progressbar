![square image](https://vqbaiq-bn1306.files.1drv.com/y3mWYs9BuIe9N1T99aNPJ3OS0HEFXgJHCn96voCjTc0gUiysA7qbzPyLm0-2UiMdIkddCPIEX4uAXH7SHYa_pS8dm8M-S1Q0mkS_0wNhi3QPMb-A9d7-SzD_LIfdA5qyJdFX-9FrfjskYkkPf3jRUqg6MBmQnMRfOfaqY5i4bb6AZw?)

## First things first

This library is setup to work with the Android Studio and Gradle. If you're using the Eclipse environment then check out the legacy repository here: [android-square-progressbar-legacy](https://github.com/mrwonderman/android-square-progressbar-legacy).

You can find my blog post about the newest version here: [halcyon.ch - android-square-progressbar v.1.6.0](http://www.halcyon.ch/android-square-progressbar-v-1-6-0/), also check out the post about the previous major 1.5.0 version [here](http://www.halcyon.ch/android-square-progressbar-v-1-5-0/).

## General idea

Sometimes you don't have enough space in your layout to display a wide progressbar. So this library gives you a complete new possibility to display a progress. You can simply show a progressbar around an image. And this progressbar can be configured in a lot of different ways, like colour, outline, display of the percentage and so on.

### Examples

Here are some examples of how these progressbars could look like:

| normal / default                                                                                                                                                                                                                                                                                        | rounded corners                    | show percent                                                                                                                                                                                                                                                                                            | Counter clockwise                  |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- |
| ![](https://whxvpq.bn1303.livefilestore.com/y4mbpv6GEOuDrqEaDIkV_bo7t49qpQcERn5YXsEHf9owXaomI--3m5Zx0E5go4ZZIhpz32hnPNne4J7N55qK4sNbCaBY71aVgJ7aaXJYR6pZU-P5iFhLHukKQYgfJKJZacUNUvBcehqVWrnZzQza2V287yPOvxKbflYn3pt4NOFmiQ3ktvT0Z0i_EYWvofzyWwM8xhYPrxeEvNXYANpUmbu3Q?width=200&height=128&cropmode=none) | ![](https://i.imgur.com/r40ztii.png) | ![](https://wnxvpq.bn1303.livefilestore.com/y4mY2mdqL2fEDeHd6-qmZVC2P8CXIFETcT_1nXw8ZCTLCJjoNwjFW6_ToAruJ22d_jto0P4LuHL0DIa152e9rJ0Q_SXqmJrqq9oghuYSBwEPEQIWDBHBahX2i0tH5NgW3bE--WKnzq5gtIFuje4_9Fzu4dtyCE9ni7Nnf4UO10pC3WrftTfjWkGFsl0Irp4cARQ1f-I6ET1FinisByPKodMCg?width=200&height=128&cropmode=none) | ![](https://i.imgur.com/GmbveOV.png) |
|                                                                                                                                                                                                                                                                                                         | `setRoundedCorners(true)`        | `showProgress(true)`                                                                                                                                                                                                                                                                                  | `setCcw(true)`                   |

There are some further examples available here (with code) : [Examples](https://github.com/mrwonderman/android-square-progressbar/wiki/Examples)

### How to use it? / How to install? / How to contribute?

Check the wiki for more information about [how to use](https://github.com/mrwonderman/android-square-progressbar/wiki/Usage), [how to install](https://github.com/mrwonderman/android-square-progressbar/wiki/Use-with-an-Eclipse-Setup) or [how to contribute](https://github.com/mrwonderman/android-square-progressbar/wiki/How-To-Contribute).

If you have questions about the code or if you need some help, you can try the [Gitter-Group](https://gitter.im/mrwonderman/android-square-progressbar).

## Usage

### Gradle

Just add the following repository to your root build.gradle:

```
allprojects {
repositories {
jcenter()
maven { url 'https://jitpack.io' }
}
}
```

Then in your app build.gradle:

```
dependencies {
// other repos ...
implementation 'com.github.garawaa:android-rounded-imageborder-progressbar:1.6.6'
}
```

### Code

After adding the gradle depedency from above you can go to your xml layout and add the following code for a squareprogressbar:

```xml

<com.garawaa.squareprogressbar.SquareProgressBar
    android:id="@+id/squareprogressbar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

To set some basic settings use the following java-code:

```
SquareProgressBar squareProgressBar = findViewById(R.id.squareprogressbar);
squareProgressBar.setImage(R.drawable.example);
squareProgressBar.setProgress(50.0);
```

Now you can make the squareprogressbar as fancy as you like. Check the [usage page](https://github.com/garawaa/android-rounded-imageborder-progressbar/wiki/Usage) for all the different possiblities.
