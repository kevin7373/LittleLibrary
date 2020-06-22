<template>
  <v-container class="mt-0" id="scan">
    <v-alert dark prominent :color="statusColor" border="bottom" >
      <h3><v-icon>{{ statusIcon }}</v-icon> {{ message }}</h3>
      <v-spacer></v-spacer>
      <v-btn tile depressed class="mt-3" @click="goBack()">뒤로가기</v-btn>
    </v-alert>
    <br>
    <div v-show="scans.length === 0">
      <div id="preview-container">
        <video playsinline muted autoplay id="preview"></video>
      </div>
    </div>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent width="400">
        <v-card>
          <v-card-title class="headline">QR코드가 인식되었습니다</v-card-title>
          <div v-if="getUserBook">
            <v-card-text>반납하시겠습니까?</v-card-text>
          </div>
          <div v-else>
            <v-card-text>대여하시겠습니까?</v-card-text>
          </div>
          <v-card-actions>
            <v-spacer></v-spacer>
            <div v-if="getUserBook">
              <v-btn tile depressed color="blue lighten-1" class="white--text" @click="bookBack()">반납</v-btn>
            </div>
            <div v-else>
              <v-btn tile depressed color="blue lighten-1" class="white--text" @click="borrowBook()">대여</v-btn>
            </div>
            <v-btn tile depressed color="black" class="white--text ml-2" @click="cancelBookRent()">취소</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'ScanQR',
    data() {
      return {
        scanner: null,
        activeCameraId: null,
        cameras: [],
        scans: [],
        statusColor: 'info',
        statusIcon: 'fa-info-circle',
        dialog: false,
        message: '책의 QR 코드를 찍어주세요',
        cameraSelect: 0,
      }
    },
    computed: {
      ...mapGetters([
        'requestHeader',
        'user',
        'getAxios',
        'getUserBook'
      ])
    },
    mounted: function () {
      var self = this
      const Instascan = require('instascan')
      self.scanner = new Instascan.Scanner({ video: document.getElementById('preview'), scanPeriod: 5 })
      self.scanner.addListener('scan', function (content, image) {
        self.scans.unshift({ date: +(Date.now()), content: content })
      })
      Instascan.Camera.getCameras().then(function (cameras) {
        self.cameras = cameras
        if (cameras.length > 0) {
          self.activeCameraId = cameras[cameras.length-1].id
          self.scanner.start(cameras[cameras.length-1])
        } else {
          console.error('No cameras found.')
        }
      }).catch(function (e) {
        console.error(e)
      })
    },
    watch: {
      scans: function(scans) {
        if (scans.length > 0) {
          this.dialog = true
        }
      },
    },
    beforeRouteLeave(to, from, next) {
      for (let i = 0; i < this.cameras.length; i++) {
        this.scanner.stop(this.cameras[i])
      }
      this.scanner = null
      next()
    },
    methods: {
      borrowBook: function() {
        if (this.scans.length > 0) {
          this.getAxios.post(`/book/rent/${this.scans[0].content}`, {}, this.requestHeader)
          .then(res => {
            if (res.data.message === '이미 대여된 책입니다' || res.data.message === '한 권만 대여할 수 있습니다.' || res.data.message === '등록되지 않은 도서번호 입니다.') {
              this.statusColor = 'red'
              this.statusIcon = 'fa-exclamation-triangle'
            }
            else {
              this.statusColor = 'green'
              this.statusIcon = 'fa-check-circle'
              this.$store.dispatch('insertUserBook', res.data.book)
            }
            this.scanner.stop(this.cameras[this.cameras.length-1])
            this.message = res.data.message
          })
          .catch(err => {
            console.log(err)
            this.$router.push('/main')
          })
        }
        this.scans = []
        this.dialog = false
      },
      bookBack: function() {
        if (this.scans.length > 0) {
          this.getAxios.post(`/book/return/${this.scans[0].content}`, {}, this.requestHeader)
          .then(res => {
            if (res.data.message === '이미 반납된 책입니다' || res.data.message === '대여한 책이 없습니다' || res.data.message === '대여한 책이 아닙니다' || res.data.message === '등록되지 않은 도서번호 입니다.') {
              this.statusColor = 'red'
              this.statusIcon = 'fa-exclamation-triangle'
            }
            else {
              this.statusColor = 'green'
              this.statusIcon = 'fa-check-circle'
              this.$store.dispatch('insertUserBook')
            }
            this.scanner.stop(this.cameras[this.cameras.length-1])
            this.message = res.data.message
          })
          .catch(err => {
            console.log(err)
            this.$router.push('/main')
          })
        }
        this.scans = []
        this.dialog = false
      },
      cancelBookRent: function() {
        this.scans = []
        this.dialog = false
        this.$router.push('/main')
      },
      goBack: function() {
        this.$router.go(-1)
      },
    }
  }
</script>

<style scoped>
#scan {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2C3E50;
  margin-top: 60px;
}
#preview {
  max-width: 100%;
  height: auto;
  object-fit: cover;
}
#preview-container {
  transform: rotateY(180deg);
  -webkit-transform:rotateY(180deg); /* Safari and Chrome */
  -moz-transform:rotateY(180deg); /* Firefox */
}
</style>