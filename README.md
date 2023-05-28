# StoryApp

StoryApp adalah aplikasi untuk submission kelas android intermediate dengan kriteria aplikasi sebagai berikut:

<ol>
   <li dir="ltr">
      <p dir="ltr"><strong>Halaman Autentikasi<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Menampilkan halaman <strong>login&nbsp;</strong>untuk masuk ke dalam aplikasi. Berikut input yang dibutuhkan.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Email (R.id.ed_login_email)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Password (R.id.ed_login_password)</p>
               </li>
            </ul>
         </li>
         <li dir="ltr">
            <p dir="ltr">Membuat halaman <strong>register&nbsp;</strong>untuk mendaftarkan diri dalam aplikasi. Berikut input yang dibutuhkan.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Nama (R.id.ed_register_name)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Email (R.id.ed_register_email)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Password (R.id.ed_register_password)</p>
               </li>
            </ul>
         </li>
         <li>Password wajib disembunyikan.</li>
         <li dir="ltr">
            <p dir="ltr">Membuat<strong>&nbsp;Custom View</strong> berupa EditText <strong>pada halaman login atau register</strong> dengan ketentuan sebagai berikut.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Jika jumlah password kurang dari 8 karakter, menampilkan pesan error secara langsung pada EditText tanpa harus pindah form atau klik tombol dulu.</p>
               </li>
            </ul>
         </li>
         <li dir="ltr">
            <p dir="ltr">Menyimpan data sesi dan token di <strong>preferences</strong>. Data sesi digunakan untuk mengatur alur aplikasi dengan spesifikasi seperti berikut.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Jika sudah login langsung masuk ke halaman utama.</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Jika belum maka akan masuk ke halaman login.&nbsp;</p>
               </li>
            </ul>
         </li>
         <li dir="ltr">
            <p dir="ltr">Terdapat fitur untuk <strong>logout&nbsp;</strong>(R.id.action_logout)<strong>&nbsp;</strong>pada halaman utama dengan ketentuan sebagai berikut.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Ketika tombol logout ditekan, informasi token, dan sesi harus dihapus.</p>
               </li>
            </ul>
         </li>
      </ul>
   </li>
   <li dir="ltr">
      <p dir="ltr"><strong>Daftar Cerita (List Story)<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Menampilkan <strong>daftar cerita&nbsp;</strong>dari API yang disediakan. Berikut minimal informasi yang wajib Anda tampilkan.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Nama user (R.id.tv_item_name)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Foto &nbsp;(R.id.iv_item_photo)</p>
               </li>
            </ul>
         </li>
         <li dir="ltr">
            <p dir="ltr">Muncul <strong>halaman detail</strong> ketika salah satu item cerita ditekan. Berikut &nbsp;minimal informasi yang wajib Anda tampilkan.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Nama user (R.id.tv_detail_name)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Foto (R.id.iv_detail_photo)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Deskripsi (R.id.tv_detail_description)<br><br></p>
               </li>
            </ul>
         </li>
      </ul>
   </li>
   <li dir="ltr">
      <p dir="ltr"><strong>Tambah Cerita<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Membuat halaman untuk menambah cerita baru yang dapat diakses dari halaman daftar cerita. Berikut input minimal yang dibutuhkan.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">File foto (wajib bisa dari gallery)</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Deskripsi cerita (R.id.ed_add_description)</p>
               </li>
            </ul>
         </li>
         <li dir="ltr">
            <p dir="ltr">Berikut adalah ketentuan dalam menambahkan cerita baru:</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Terdapat tombol (R.id.button_add) untuk upload data ke server.&nbsp;</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Setelah tombol tersebut diklik dan proses upload berhasil, maka akan kembali ke halaman daftar cerita.&nbsp;</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Data cerita terbaru harus muncul di paling atas.</p>
               </li>
            </ul>
         </li>
      </ul>
   </li>
   <li dir="ltr">
      <p dir="ltr"><strong>Menampilkan Animasi<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Membuat animasi pada aplikasi dengan menggunakan salah satu jenis animasi berikut.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Property Animation</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Motion Animation</p>
               </li>
               <li dir="ltr">
                  <p dir="ltr">Shared Element</p>
               </li>
            </ul>
         </li>
         <li>
            <p dir="ltr">Tuliskan jenis dan lokasi animasi pada Student Note.</p>
         </li>
      </ul>
   </li>
    <li dir="ltr">
      <p dir="ltr"><strong>Menampilkan Maps<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Menampilkan satu halaman baru berisi peta yang menampilkan semua cerita yang memiliki lokasi dengan benar. Bisa berupa marker maupun icon berupa gambar. Data story yang memiliki lokasi latitude dan longitude dapat diambil melalui parameter location seperti berikut</p>
            <ul>
               <li>
                  <p><a href="https://story-api.dicoding.dev/v1/stories?location=1" target="_blank" title="https://story-api.dicoding.dev/v1/stories?location=1" rel="noreferrer noopener">https://story-api.dicoding.dev/v1/stories?<strong>location=1</strong></a>.</p>
               </li>
            </ul>
         </li>
      </ul>
   </li>
   <li dir="ltr">
      <p dir="ltr"><strong>Paging List<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr">
            <p dir="ltr">Menampilkan list story dengan menggunakan Paging 3 dengan benar.</p>
         </li>
      </ul>
   </li>
   <li>
      <p><strong>Membuat Testing<br></strong>Syarat yang harus dipenuhi sebagai berikut.</p>
      <ul>
         <li dir="ltr" title="">
            <p dir="ltr">Menerapkan unit test pada fungsi di dalam ViewModel yang mengambil list data Paging.</p>
         </li>
         <li>
            <p dir="ltr">Berikut skenario yang harus Anda buat. Pastikan setiap skenario tersebut sudah ada kodenya.</p>
            <ul>
               <li dir="ltr">
                  <p dir="ltr">Ketika berhasil memuat data cerita.</p>
                  <ul>
                     <li dir="ltr">
                        <p dir="ltr">Memastikan data tidak null.</p>
                     </li>
                     <li dir="ltr">
                        <p dir="ltr">Memastikan jumlah data sesuai dengan yang diharapkan.</p>
                     </li>
                     <li dir="ltr">
                        <p dir="ltr">Memastikan data pertama yang dikembalikan sesuai.</p>
                     </li>
                  </ul>
               </li>
               <li>
                  <p dir="ltr">Ketika tidak ada data cerita.</p>
                  <ul>
                     <li>
                        <p dir="ltr">Memastikan jumlah data yang dikembalikan nol.</p>
                     </li>
                  </ul>
               </li>
            </ul>
         </li>
      </ul>
   </li>
</ol>

<h2>Resource</h2>

<ul>
   <li dir="ltr">Story API. API untuk berbagi story seputar Dicoding, mirip seperti post Instagram namun spesial untuk Dicoding. Dokumentasi dapat dilihat di <a href="https://story-api.dicoding.dev/v1/" target="_blank" rel="noreferrer noopener">https://story-api.dicoding.dev/v1/</a></li>
   <li dir="ltr">Simpan token yang didapatkan ketika login karena dibutuhkan untuk request lainnya.</li>
   <li dir="ltr">Supaya tetap bersih, data yang dikirimkan akan otomatis hilang setelah satu jam.</li>
</ul>
