# WristHub 2.0 — Wear OS “Sıradaki Eylem”

WristHub 2.0, saatte uzun görev listeleri göstermek yerine **o anda yapılacak tek küçük eylemi** öne çıkaran offline-first Wear OS uygulamasıdır.

## 2.0'da neler değişti?

- Ana ekran artık “Sıradaki Eylem” odaklıdır.
- Eylem doğrudan **Tamam / +10 dk Ertele / Atla** ile yönetilir.
- Rutinler cihazda kalıcı tutulur; hesap veya backend gerekmez.
- Zamanı gelen rutin için yerel bildirim ve haptik uyarı vardır.
- Bildirim üzerinden uygulamayı açmadan Tamam / +10 dk / Atla yapılabilir.
- Wear OS **Tile** eklendi: sıradaki eylem ve kalan süre bir bakışta görünür.
- Saat yüzü için **SHORT_TEXT complication** veri kaynağı eklendi.
- Gizli önizleme modu; bildirim, Tile ve complication üzerinde hassas metni saklayabilir.
- Hazır rutin şablonları eklendi.
- Saatten sesle yeni rutin ekleme eklendi.
- Günlük tamamlanan / atlanan eylem sayacı vardır.
- Eski Odak, Su ve Sayaç özellikleri “Araçlar” bölümünde korunur.

## Teknik yapı

- Kotlin
- Jetpack Compose for Wear OS Material 3
- Wear Tiles
- ProtoLayout / Material 3
- Wear complication data source
- DataStore Preferences
- AlarmManager tabanlı yerel zamanlama
- compileSdk 37 / targetSdk 35 / minSdk 30
- JDK 17

## Otomatik APK

GitHub Actions içindeki **Build Wear OS APK** workflow’u her push’ta debug APK üretir. Çıktı, workflow run sayfasındaki **Artifacts** bölümünden indirilebilir.
