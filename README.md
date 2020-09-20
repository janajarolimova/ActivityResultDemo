# ActivityResultDemo
Examples of the new Activity Result APIs from Jetpack, available from alpha-02.
<br /><br />
You need the following dependencies in your app/build.gradle:<br />
`implementation "androidx.activity:activity-ktx:$activity_version"`<br />
`implementation "androidx.fragment:fragment-ktx:$fragment_version"`
<br />
#### Navigation Component
Sharing data between fragments cannot be done with these APIs if using the Navigation Component. 
The default/prebuilt contracts work as intended, as well as requesting data from another Activity.
The recommended approach for passing data between fragments is to either use a ViewModel scoped to the
same Activity, or else set a [FragmentResultListener](https://developer.android.com/reference/kotlin/androidx/fragment/app/FragmentResultListener),
as is done in this demo.