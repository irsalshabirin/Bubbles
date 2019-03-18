# Bubbles

Simple library for adding a Messenger style floating bubbles in your application!

## Screenshots:

#### Initial State
| Screen Overlay Permission| The Bubble per se | Just Before Delete    
| :-------------:        |:-------------:| :------------------:|
| ![The Bubble per se](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/default_state.png)|![On dragging the bubble](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/active_state.png)|![On dragging the bubble](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/on_close.png)

#### On Tapping
| Home Screen | App Menu | Over Another App    
| :-------------:        |:-------------:| :------------------:|
| ![Home Screen](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/home_screen.png)|![App Menu](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/app_menu.png)|![Over Another App](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/on_another_app.png)

## Package Structure of the Library

![Package Structure](https://github.com/siddharth2010/Bubbles/blob/master/screenshots/Screen%20Shot%202019-03-17%20at%203.16.13%20PM.png)

## Usage

### Setup
Let's start with a simple setup for the Service
```java
public class FloatingService extends FloatingBubbleService {
    ...
}
```

Adding your library in the manifest
```java
<service android:name="<YOUR_PACKAGE>.FloatingService" />
```

Start the service
```java
startService(new Intent(context, FloatingService.class));
```

### Customising the Service
```java
public class FloatingService extends FloatingBubbleService {

  @Override
  protected FloatingBubbleConfig getConfig() {
    return new FloatingBubbleConfig.Builder()
        // Set the drawable for the bubble
        .bubbleIcon(bubbleDrawable)

        // Set the drawable for the remove bubble
        .removeBubbleIcon(removeIconDrawable)

        // Set the size of the bubble in dp
        .bubbleIconDp(64)

        // Set the size of the remove bubble in dp
        .removeBubbleIconDp(64)

        // Set the padding of the view from the boundary
        .paddingDp(4)

        // Set the radius of the border of the expandable view
        .borderRadiusDp(4)

        // Does the bubble attract towards the walls
        .physicsEnabled(true)

        // The color of background of the layout
        .expandableColor(Color.WHITE)

        // The color of the triangular layout
        .triangleColor(Color.WHITE)

        // Horizontal gravity of the bubble when expanded
        .gravity(Gravity.END)

        // The view which is visible in the expanded view
        .expandableView(yourViewAfterClick)

        // Set the alpha value for the remove bubble icon
        .removeBubbleAlpha(0.75f)

        // Building
        .build();
  }
}
```

Override the onGetIntent function. It will return true if the intent is valid, else false
```java
  @Override
  protected boolean onGetIntent(@NonNull Intent intent) {
    // your logic to get information from the intent
    return true;
  }
```

You can change the state of the expanded view at runtime by
```java
// To expand
setState(true);

// To compress
setState(false);
```
