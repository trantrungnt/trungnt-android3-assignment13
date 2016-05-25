# trungnt-android3-assignment13

##Yêu cầu
+ [API :](http://g-service.herokuapp.com/api/techkids/login?username=android%40hungdepzai.techkids.vn&password=123456)
URL như thế nhé.
Yêu cầu là Activity Login có 2 cái textbox username và password nhập vào ấn Button Login thì chạy cái API kia nó sẽ trả về file json. Nếu login OK thì [nó trả về như thế này](http://g-service.herokuapp.com/api/techkids/login?username=android%40hungdepzai.techkids.vn&password=123456)
Còn không thì : [thế này](http://g-service.herokuapp.com/api/techkids/login?username=admin&password=12342)
Nhiệm vụ của ta là đọc cái json đó. Nếu login đéo đc thì báo không đọc đc, Còn được thì lưu username, password vào SharedPrefercences, từ các lần sau chẳng cần đăng nhập nữa pacman emoticon. Login được thì làm những nhiệm vụ sau:
+ Trong JSON có trường link, đọc cái đó ra lấy fiel text về. Vì file này là file tạm nên để ở cache.
+ Đoc cái file text về. có 2 cái link. cái link đầu là file nhạc, ta tải về lưu trong mục MUSIC của thẻ nhớ. Link tiếp theo là file ảnh. Ta save nó vào mục files của ứng dụng

##Kiến thức để làm được bài
+ [Xem tại đây](https://github.com/trantrungnt/LearnStorargeData)

##Môi trường phát triển
+ Bộ công cụ Android Studio 2.1
+ Máy ảo Genymotion dùng api min 17 và api max 23

##Tham khảo
+ [Tham khảo ví dụ thiết kế giao diện kiểu Masterial Login](sourcey.com/beautiful-android-login-and-signup-screens-with-material-design/)
+ [Alert Dialog uses Master Design](http://www.androidmaterial.info/2016/01/android-alertdialog-example-tutorial-in-material-design/)
+ [Example getFileDir](http://www.programcreek.com/java-api-examples/index.php?class=android.content.Context&method=getFilesDir)
+ [Data Storage in Android (basic)](https://developer.android.com/training/basics/data-storage/files.html)
+ [Saving Data in your app Android](http://blog.cindypotvin.com/saving-data-to-a-file-in-your-android-application/)
