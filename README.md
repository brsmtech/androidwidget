# Android Widget Library

The collection of the Android custom widgets.

## Getting Started

```
git clone https://github.com/brsmtech/androidwidget.git
cd androidwidget
./gradlew assembleDebug

```

## Library Contents

### EditTextView

```
<com.bstech.widlib.view.EditTextView
    android:id="@+id/login_email"
    style="@style/EditTextView"
    android:hint="your@email.com"
    android:inputType="textEmailAddress"
    android:nextFocusForward="@+id/login_password"
    bstech:clear="true"
    bstech:maxLength="128"
    bstech:message="Enter valid e-mail" />
```

### EditText2View

```
<com.bstech.widlib.view.EditText2View
    android:id="@+id/password"
    style="@style/EditTextView"
    android:inputType="textPassword"
    android:nextFocusForward="@+id/login_password"
    bstech:clear="true"
    bstech:maxLength="128"
    bstech:backgroundColor="@drawable/background_edit_text_view"
    bstech:message="Enter valid password"
    bstech:hint1="Create New Password"
    bstech:hint2="Repeat Password"
    bstech:message1="Wrong password format"
    bstech:message2="Password not matching"/>
```

### NiceTextView

```
<com.bstech.widlib.view.NiceTextView
    android:id="@+id/password_continue"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:text="CONTINUE"
    android:textColor="@android:color/holo_orange_light"
    android:textSize="36sp"
    android:textStyle="bold"
    bstech:backgroundColor="@drawable/background_login_button"
    bstech:textPadding="8dp"
    bstech:textStrokeColor="@android:color/black"
    bstech:textStrokeWidth="3dip" />
```


### Installing

A step by step series of examples that tell you have to get a development env running

#### Project level gradle.build

```
allprojects {
    repositories {
	        ...
	        maven { url 'https://jitpack.io' }
    }
}
```

#### App level gradle.build

```
dependencies {
		compile 'com.github.brsmtech:androidwidget:v1.0.0'
	}
```

#### Application or Activity class


```
override fun onCreate() {
    super.onCreate()

    ....
    FontManager.init(assets)
    ....
}
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/brsmtech/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Boris Rayskiy**(https://github.com/brayskiy) - *Initial work*

See also the list of [contributors](https://github.com/brsmtech/androidwidgets/contributors) who participated in this project.

## License

See the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* 

