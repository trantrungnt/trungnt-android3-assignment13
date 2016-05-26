# trungnt-android3-assignment13

##Yêu cầu
+ [API :](http://g-service.herokuapp.com/api/techkids/login?username=android%40hungdepzai.techkids.vn&password=123456)
URL như thế nhé.
Yêu cầu là Activity Login có 2 cái textbox username và password nhập vào ấn Button Login thì chạy cái API kia nó sẽ trả về file json. Nếu login OK thì [nó trả về như thế này](http://g-service.herokuapp.com/api/techkids/login?username=android%40hungdepzai.techkids.vn&password=123456)
Còn không thì : [thế này](http://g-service.herokuapp.com/api/techkids/login?username=admin&password=12342)
Nhiệm vụ của ta là đọc cái json đó. Nếu login đéo đc thì báo không đọc đc, Còn được thì lưu username, password vào SharedPrefercences, từ các lần sau chẳng cần đăng nhập nữa. Login được thì làm những nhiệm vụ sau:
+ Trong JSON có trường link, đọc cái đó ra lấy fiel text về. Vì file này là file tạm nên để ở cache.
+ Đoc cái file text về. có 2 cái link. cái link đầu là file nhạc, ta tải về lưu trong mục MUSIC của thẻ nhớ. Link tiếp theo là file ảnh. Ta save nó vào mục files của ứng dụng

##Kiến thức để làm được bài
+ [Xem tại đây](https://github.com/trantrungnt/LearnStorargeData)
+ [Học sử dụng ADB, cấu hình để xem thư mục Package trong máy ảo Genymotion](https://youtu.be/-Gcm6Zzauh8)

##Học sử dụng ADB trong Android
![Learn ADB in Android](http://i477.photobucket.com/albums/rr132/trungepu/android%20debug%20bridge_zpsxajb4vkl.jpg)


##Môi trường phát triển
+ Bộ công cụ Android Studio 2.1
+ Máy ảo Genymotion dùng api min 17 và api max 23

##Tham khảo
+ [Tham khảo ví dụ thiết kế giao diện kiểu Masterial Login](sourcey.com/beautiful-android-login-and-signup-screens-with-material-design/)
+ [Alert Dialog uses Master Design](http://www.androidmaterial.info/2016/01/android-alertdialog-example-tutorial-in-material-design/)
+ [Example getFileDir](http://www.programcreek.com/java-api-examples/index.php?class=android.content.Context&method=getFilesDir)
+ [Data Storage in Android (basic)](https://developer.android.com/training/basics/data-storage/files.html)
+ [Saving Data in your app Android](http://blog.cindypotvin.com/saving-data-to-a-file-in-your-android-application/)
+ [Internal Storage in Android](http://www.tutorialspoint.com/android/android_internal_storage.htm)
+ [SharePrefrerences](https://developer.android.com/guide/topics/data/data-storage.html)
+ [Internal Storage Data in Android - Example](http://www.journaldev.com/9383/android-internal-storage-example-tutorial)
+ [File Path for Android when using Emulator](stackoverflow.com/questions/10703619/file-path-for-android-when-using-emulator)
+ [Storage situation internal storage](https://commonsware.com/blog/2014/04/07/storage-situation-internal-storage.html)
+ [How to Correctly Store App-Specific Files in Android](http://www.grokkingandroid.com/how-to-correctly-store-app-specific-files-in-android/)
+ [How can I read file in Android](How can I read a text file in Android?)
+ [Read and Write File text in Android - Example](http://www.androidinterview.com/android-internal-storage-read-and-write-text-file-example/)
+ [Android shared preferences Turtorial](http://www.tutorialspoint.com/android/android_shared_preferences.htm)
+ [Share Preferences - Example](http://androidopentutorials.com/android-sharedpreferences-tutorial-and-example/)
